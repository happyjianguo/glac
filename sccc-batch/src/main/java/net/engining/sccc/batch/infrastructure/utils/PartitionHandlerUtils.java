/**
 * 
 */
package net.engining.sccc.batch.infrastructure.utils;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.PollableChannel;

import net.engining.sccc.batch.infrastructure.MutiThreadMessageChannelPartitionHandler;

/**
 * @author luxue
 *
 */
public class PartitionHandlerUtils {

	public static PartitionHandler remoteMessageChannelPartitionHandler(Step step, int gridsize, MessagingTemplate messageTemplate, long pollInterval,
			JobExplorer jobExplorer, DataSource dataSource, PollableChannel replyChannel) throws Exception {
		MessageChannelPartitionHandler partitionHandler1 = new MessageChannelPartitionHandler();
		partitionHandler1.setStepName(step.getName());
		// 默认网格数
		partitionHandler1.setGridSize(gridsize);
		partitionHandler1.setMessagingOperations(messageTemplate);
		// master对slave发起心跳的毫秒间隔
		partitionHandler1.setPollInterval(pollInterval);
		partitionHandler1.setJobExplorer(jobExplorer);
		partitionHandler1.setDataSource(dataSource);
		partitionHandler1.setReplyChannel(replyChannel);
		partitionHandler1.afterPropertiesSet();

		return partitionHandler1;
	}
	
	public static PartitionHandler remoteMutiThreadMessageChannelPartitionHandler(Step step, int gridsize, MessagingTemplate messageTemplate, long pollInterval,
			JobExplorer jobExplorer, DataSource dataSource, PollableChannel replyChannel, TaskExecutor taskExecutor) throws Exception {
		MutiThreadMessageChannelPartitionHandler partitionHandler1 = new MutiThreadMessageChannelPartitionHandler();
		partitionHandler1.setStepName(step.getName());
		// 默认网格数
		partitionHandler1.setGridSize(gridsize);
		partitionHandler1.setMessagingOperations(messageTemplate);
		// master对slave发起心跳的毫秒间隔
		partitionHandler1.setPollInterval(pollInterval);
		partitionHandler1.setJobExplorer(jobExplorer);
		partitionHandler1.setDataSource(dataSource);
		partitionHandler1.setReplyChannel(replyChannel);
		partitionHandler1.setTaskExecutor(taskExecutor);
		partitionHandler1.afterPropertiesSet();

		return partitionHandler1;
	}
	
	public static PartitionHandler localTaskExecutorPartitionHandler(Step step, int gridsize, TaskExecutor taskExecutor) throws Exception{
		TaskExecutorPartitionHandler partitionHandler1 = new TaskExecutorPartitionHandler();
		// 默认网格数
		partitionHandler1.setGridSize(gridsize);
		partitionHandler1.setStep(step);
		partitionHandler1.setTaskExecutor(taskExecutor);
		partitionHandler1.afterPropertiesSet();
		
		return partitionHandler1;
		
	}
}
