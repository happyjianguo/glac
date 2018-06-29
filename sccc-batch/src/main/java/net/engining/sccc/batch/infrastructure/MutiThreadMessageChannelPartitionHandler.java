package net.engining.sccc.batch.infrastructure;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.StepExecutionSplitter;
import org.springframework.batch.integration.partition.StepExecutionRequest;
import org.springframework.batch.poller.DirectPoller;
import org.springframework.batch.poller.Poller;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.MessageTimeoutException;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Payloads;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * 原Spring integration提供的MessageChannelPartitionHandler是单线程发送分区消息的，出现固定等待30秒才能发送成功的情况，未找到具体原因；<br>
 * 先通过多线程发送分区消息的方式解决，暂时不支持接受返回消息的方式获取分区step执行结果，只通过轮询本地查询分区step执行结果的方式；
 * @author luxue
 *
 */
@MessageEndpoint
public class MutiThreadMessageChannelPartitionHandler implements PartitionHandler, InitializingBean {

	
	private static final Logger logger = LoggerFactory.getLogger(MutiThreadMessageChannelPartitionHandler.class);

	private int gridSize = 1;

	private MessagingTemplate messagingGateway;

	private String stepName;

	private long pollInterval = 10000;

	private JobExplorer jobExplorer;

	private boolean pollRepositoryForResults = false;

	private long timeout = -1;

	private DataSource dataSource;

	/**
	 * pollable channel for the replies
	 */
	private PollableChannel replyChannel;
	
	private TaskExecutor taskExecutor = new SyncTaskExecutor();

	@Override
	public void afterPropertiesSet() throws Exception {
//		Assert.notNull(stepName, "A step name must be provided for the remote workers.");
//		Assert.state(messagingGateway != null, "The MessagingOperations must be set");

//		pollRepositoryForResults = !(dataSource == null && jobExplorer == null);

		if(pollRepositoryForResults) {
			logger.debug("MessageChannelPartitionHandler is configured to poll the job repository for slave results");
		}

		if(dataSource != null && jobExplorer == null) {
			JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
			jobExplorerFactoryBean.setDataSource(dataSource);
			jobExplorerFactoryBean.afterPropertiesSet();
			jobExplorer = jobExplorerFactoryBean.getObject();
		}
	}
	
	/**
	 * Sends {@link StepExecutionRequest} objects to the request channel of the {@link MessagingTemplate}, and then
	 * receives the result back as a list of {@link StepExecution} on a reply channel. Use the {@link #aggregate(List)}
	 * method as an aggregator of the individual remote replies. The receive timeout needs to be set realistically in
	 * the {@link MessagingTemplate} <b>and</b> the aggregator, so that there is a good chance of all work being done.
	 *
	 * @see PartitionHandler#handle(StepExecutionSplitter, StepExecution)
	 */
	public Collection<StepExecution> handle(StepExecutionSplitter stepExecutionSplitter,
			final StepExecution masterStepExecution) throws Exception {

		final Set<StepExecution> split = stepExecutionSplitter.split(masterStepExecution, gridSize);

		if(CollectionUtils.isEmpty(split)) {
			return null;
		}

		int count = 0;

		PollableChannel currentReplyChannel = replyChannel;

		if (!pollRepositoryForResults && currentReplyChannel == null) {
			currentReplyChannel = new QueueChannel();
		}//end if

		for (StepExecution stepExecution : split) {
			Message<StepExecutionRequest> request = createMessage(count++, split.size(), new StepExecutionRequest(
					stepName, stepExecution.getJobExecutionId(), stepExecution.getId()), currentReplyChannel);
			if (logger.isDebugEnabled()) {
				logger.debug("Sending request: " + request);
			}
			
			//异步多线程发送分区消息
			sendPartionMsg(request);
			
		}

//		if(!pollRepositoryForResults) {
//			return receiveReplies(currentReplyChannel);
//		}
//		else {
			return pollReplies(masterStepExecution, split);
//		}
	}
	
	/**
	 * @param messages the messages to be aggregated
	 * @return the list as it was passed in
	 */
	@Aggregator(sendPartialResultsOnExpiry = "true")
	public List<?> aggregate(@Payloads List<?> messages) {
		return messages;
	}
	
	private void sendPartionMsg(Message<StepExecutionRequest> request){
		Assert.notNull(taskExecutor, "A taskExecutor must be provided.");
		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				messagingGateway.send(request);
			}
		});
		
	}

	private Collection<StepExecution> pollReplies(final StepExecution masterStepExecution, final Set<StepExecution> split) throws Exception {
		final Collection<StepExecution> result = new ArrayList<StepExecution>(split.size());

		Callable<Collection<StepExecution>> callback = new Callable<Collection<StepExecution>>() {
			@Override
			public Collection<StepExecution> call() throws Exception {

				for(Iterator<StepExecution> stepExecutionIterator = split.iterator(); stepExecutionIterator.hasNext(); ) {
					StepExecution curStepExecution = stepExecutionIterator.next();

					if(!result.contains(curStepExecution)) {
						StepExecution partitionStepExecution =
								jobExplorer.getStepExecution(masterStepExecution.getJobExecutionId(), curStepExecution.getId());

						if(!partitionStepExecution.getStatus().isRunning()) {
							result.add(partitionStepExecution);
						}
					}
				}

				if(logger.isDebugEnabled()) {
					logger.debug(String.format("Currently waiting on %s partitions to finish", split.size()));
				}

				if(result.size() == split.size()) {
					return result;
				}
				else {
					return null;
				}
			}
		};

		Poller<Collection<StepExecution>> poller = new DirectPoller<Collection<StepExecution>>(pollInterval);
		Future<Collection<StepExecution>> resultsFuture = poller.poll(callback);

		if(timeout >= 0) {
			return resultsFuture.get(timeout, TimeUnit.MILLISECONDS);
		}
		else {
			return resultsFuture.get();
		}
	}

	private Collection<StepExecution> receiveReplies(PollableChannel currentReplyChannel) {
		@SuppressWarnings("unchecked")
		Message<Collection<StepExecution>> message = (Message<Collection<StepExecution>>) messagingGateway.receive(currentReplyChannel);

		if(message == null) {
			throw new MessageTimeoutException("Timeout occurred before all partitions returned");
		} else if (logger.isDebugEnabled()) {
			logger.debug("Received replies: " + message);
		}

		return message.getPayload();
	}

	private Message<StepExecutionRequest> createMessage(int sequenceNumber, int sequenceSize,
			StepExecutionRequest stepExecutionRequest, PollableChannel replyChannel) {
		return MessageBuilder.withPayload(stepExecutionRequest).setSequenceNumber(sequenceNumber)
				.setSequenceSize(sequenceSize)
				.setCorrelationId(stepExecutionRequest.getJobExecutionId() + ":" + stepExecutionRequest.getStepName())
				.setReplyChannel(replyChannel)
				.build();
	}
	
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * When using job repository polling, the time limit to wait.
	 *
	 * @param timeout millisconds to wait, defaults to -1 (no timeout).
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	/**
	 * {@link org.springframework.batch.core.explore.JobExplorer} to use to query the job repository.  Either this or
	 * a {@link javax.sql.DataSource} is required when using job repository polling.
	 *
	 * @param jobExplorer {@link org.springframework.batch.core.explore.JobExplorer} to use for lookups
	 */
	public void setJobExplorer(JobExplorer jobExplorer) {
		this.jobExplorer = jobExplorer;
	}

	/**
	 * How often to poll the job repository for the status of the slaves.
	 *
	 * @param pollInterval milliseconds between polls, defaults to 10000 (10 seconds).
	 */
	public void setPollInterval(long pollInterval) {
		this.pollInterval = pollInterval;
	}

	/**
	 * {@link javax.sql.DataSource} pointing to the job repository
	 *
	 * @param dataSource {@link javax.sql.DataSource} that points to the job repository's store
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * A pre-configured gateway for sending and receiving messages to the remote workers. Using this property allows a
	 * large degree of control over the timeouts and other properties of the send. It should have channels set up
	 * internally: <ul> <li>request channel capable of accepting {@link StepExecutionRequest} payloads</li> <li>reply
	 * channel that returns a list of {@link StepExecution} results</li> </ul> The timeout for the repoy should be set
	 * sufficiently long that the remote steps have time to complete.
	 *
	 * @param messagingGateway the {@link org.springframework.integration.core.MessagingTemplate} to set
	 */
	public void setMessagingOperations(MessagingTemplate messagingGateway) {
		this.messagingGateway = messagingGateway;
	}

	/**
	 * Passed to the {@link StepExecutionSplitter} in the {@link #handle(StepExecutionSplitter, StepExecution)} method,
	 * instructing it how many {@link StepExecution} instances are required, ideally. The {@link StepExecutionSplitter}
	 * is allowed to ignore the grid size in the case of a restart, since the input data partitions must be preserved.
	 *
	 * @param gridSize the number of step executions that will be created
	 */
	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	/**
	 * The name of the {@link Step} that will be used to execute the partitioned {@link StepExecution}. This is a
	 * regular Spring Batch step, with all the business logic required to complete an execution based on the input
	 * parameters in its {@link StepExecution} context. The name will be translated into a {@link Step} instance by the
	 * remote worker.
	 *
	 * @param stepName the name of the {@link Step} instance to execute business logic
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public void setReplyChannel(PollableChannel replyChannel) {
		this.replyChannel = replyChannel;
	}
}
