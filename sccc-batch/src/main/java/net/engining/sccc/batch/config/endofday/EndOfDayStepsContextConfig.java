package net.engining.sccc.batch.config.endofday;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.DefaultStepExecutionAggregator;
import org.springframework.batch.core.partition.support.RemoteStepExecutionAggregator;
import org.springframework.batch.core.step.builder.PartitionStepBuilder;
import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;

import net.engining.pg.support.core.context.ApplicationContextHolder;
import net.engining.sccc.batch.infrastructure.utils.PartitionHandlerUtils;
import net.engining.sccc.batch.listener.BatchStepLoggedListener;
import net.engining.sccc.batch.sccc5701.Sccc5701R01ApGlVolDtl;
import net.engining.sccc.batch.sccc5800.Sccc5800R01Trans;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 *     日终批量Step 配置
 * @author luxue
 *
 */
public class EndOfDayStepsContextConfig {
	
	@Autowired
	JobExplorer jobExplorer;
	
	@Autowired
	StepBuilderFactory stepBuilders;
	
	@Autowired
	BatchStepLoggedListener batchStepLoggedListener;
	
	@Autowired
	RemoteStepExecutionAggregator remoteStepExecutionAggregator;
	
	@Autowired
	BatchTaskProperties batchTaskProperties;
	
	@Autowired
	MessagingTemplate messageTemplate;
	
	@Autowired
	DefaultStepExecutionAggregator defaultStepExecutionAggregator;
	
	@Autowired
	QueueChannel inboundRequests;
	
	/**
	 * 需要支持分布式处理的Step，master配置;
	 * master节点关键点是：其Step需要设置从节点的Step，和一个数据分区器；
	 * 数据分区器需要实现Partitioner接口：返回一个Map<String, ExecutionContext>的数据结构，这个结构完整的描述了每个从节点需要处理的分区片段，ExecutionContext保存了从节点要处理的数据边界。
	 * @return
	 * @throws Exception 
	 */
//	@Bean("stepMasterSccc1200")
//	public Step stepMasterSccc1200() throws Exception {
//		//slaveStep
//		Step stepSlaveSccc1200 = ApplicationContextHolder.getBean("sccc-1200-DistributedSample");
//		//Partitioner and Step Reader
//		Sccc1200R sccc1200R = ApplicationContextHolder.getBean("sccc1200R");
//		//Partition Handler
//		MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
//		partitionHandler.setStepName(stepSlaveSccc1200.getName());
//        //默认网格数
//        partitionHandler.setGridSize(batchTaskProperties.getDefaultGridSize());
//        partitionHandler.setMessagingOperations(messageTemplate);
//        //master对slave发起心跳的毫秒间隔
//        partitionHandler.setPollInterval(batchTaskProperties.getDefaultPollMills());
//        partitionHandler.setJobExplorer(jobExplorer);
//		partitionHandler.afterPropertiesSet();
//		
//		return stepBuilders.get("stepMasterSccc1200")
//				.listener(batchStepLoggedListener)
//                .partitioner(stepSlaveSccc1200.getName(), sccc1200R)
//                .aggregator(remoteStepExecutionAggregator)//aggregator用于聚合远程slave节点step执行的情况
//                .partitionHandler(partitionHandler)
//                .gridSize(batchTaskProperties.getDefaultGridSize())
//                .taskExecutor(ApplicationContextHolder.getBean("sccc1200Executor"))
//                .step(stepSlaveSccc1200)
//                .build();
//	}
	
	/**
	 * 批前数据治理
	 * @return
	 */
	@Bean("stepSccc4900")
	public Step stepSccc4900(){
		return ApplicationContextHolder.getBean("sccc-4900-SystemStatus");
	}
	
	@Bean("stepMasterSccc5701")
	public Step stepMasterSccc5701() throws Exception {
		//slaveStep
		Step stepSlaveSccc5701 = ApplicationContextHolder.getBean("sccc-5701-AssistTypeSplit");
		//Partitioner and Step Reader
		Sccc5701R01ApGlVolDtl sccc5701R01ApGlVolDtl = ApplicationContextHolder.getBean("sccc5701R01ApGlVolDtl");
		//Partition Handler
		PartitionHandler partitionHandler = null;
		if (batchTaskProperties.isEnableRemotePartition()) {
			partitionHandler = PartitionHandlerUtils.remoteMessageChannelPartitionHandler(stepSlaveSccc5701, batchTaskProperties.getSccc5701GridSize(),
					messageTemplate, batchTaskProperties.getDefaultPollMills(), jobExplorer, null, inboundRequests);
		}
		else{
			partitionHandler = PartitionHandlerUtils.localTaskExecutorPartitionHandler(stepSlaveSccc5701, batchTaskProperties.getSccc5701GridSize(),
					ApplicationContextHolder.getBean("endOfDayExecutor"));
		}
		
		PartitionStepBuilder pStepBuilder = stepBuilders.get("stepMasterSccc5701")
				.listener(batchStepLoggedListener)
				.partitioner(stepSlaveSccc5701.getName(), sccc5701R01ApGlVolDtl)
				.partitionHandler(partitionHandler)
				.gridSize(batchTaskProperties.getDefaultGridSize());
				
		if (batchTaskProperties.isEnableRemotePartition()) {
			pStepBuilder.aggregator(remoteStepExecutionAggregator);
		}
		else{
			pStepBuilder.aggregator(defaultStepExecutionAggregator);
		}

		return pStepBuilder.build();
		
		
//		MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
//		partitionHandler.setStepName(stepSlaveSccc5701.getName());
//        //默认网格数
//        partitionHandler.setGridSize(batchTaskProperties.getDefaultGridSize());
//        partitionHandler.setMessagingOperations(messageTemplate);
//        //master对slave发起心跳的毫秒间隔
//        partitionHandler.setPollInterval(batchTaskProperties.getDefaultPollMills());
//        partitionHandler.setJobExplorer(jobExplorer);
//		partitionHandler.afterPropertiesSet();
//		
//		return stepBuilders.get("stepMasterSccc5701")
//				.listener(batchStepLoggedListener)
//                .partitioner(stepSlaveSccc5701.getName(), sccc5701R01ApGlVolDtl)
//                .aggregator(remoteStepExecutionAggregator)//aggregator用于聚合远程slave节点step执行的情况
//                .partitionHandler(partitionHandler)
//                .gridSize(batchTaskProperties.getDefaultGridSize())
//                .step(stepSlaveSccc5701)
//                .build();
	}
	
	@Bean("sccc5800CombineFlowStep")
	public Step sccc5800CombineFlowStep(@Qualifier("stepMasterSccc5801") Step stepMasterSccc5801){
		
		Flow flow5801 = new FlowBuilder<Flow>("splitFlow5801").start(stepMasterSccc5801).end();
		
		Flow splitFlow = new FlowBuilder<Flow>("splitFlow5800")
				.split((TaskExecutor) ApplicationContextHolder.getBean("sccc5800Executor"))
				.add(
						flow5801,
						(Flow)ApplicationContextHolder.getBean("sccc-5802-CRFReportData")
					)
				.end();
		
		return stepBuilders.get("sccc5800CombineFlowStep")
				.listener(batchStepLoggedListener)
				.flow(splitFlow)
				.build();
				
	}
	
	@Bean("stepMasterSccc5801")
	public Step stepMasterSccc5801() throws Exception {
		//slaveStep
		Step stepSlaveSccc5801 = ApplicationContextHolder.getBean("sccc-5801-TransReportData");
		//Partitioner and Step Reader
		Sccc5800R01Trans sccc5800R01Trans = ApplicationContextHolder.getBean("sccc5800R01Trans");
		//Partition Handler
		PartitionHandler partitionHandler = null;
		if (batchTaskProperties.isEnableRemotePartition()) {
			partitionHandler = PartitionHandlerUtils.remoteMessageChannelPartitionHandler(stepSlaveSccc5801, batchTaskProperties.getSccc5801GridSize(),
					messageTemplate, batchTaskProperties.getDefaultPollMills(), jobExplorer, null, inboundRequests);
		}
		else{
			partitionHandler = PartitionHandlerUtils.localTaskExecutorPartitionHandler(stepSlaveSccc5801, batchTaskProperties.getSccc5801GridSize(),
					ApplicationContextHolder.getBean("endOfDayExecutor"));
		}
		
		PartitionStepBuilder pStepBuilder = stepBuilders.get("stepMasterSccc5801")
				.listener(batchStepLoggedListener)
				.partitioner(stepSlaveSccc5801.getName(), sccc5800R01Trans)
				.partitionHandler(partitionHandler)
				.gridSize(batchTaskProperties.getDefaultGridSize());
				
		if (batchTaskProperties.isEnableRemotePartition()) {
			pStepBuilder.aggregator(remoteStepExecutionAggregator);
		}
		else{
			pStepBuilder.aggregator(defaultStepExecutionAggregator);
		}

		return pStepBuilder.build();
		
		
//		MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
//		partitionHandler.setStepName(stepSlaveSccc5801.getName());
//        //默认网格数
//        partitionHandler.setGridSize(batchTaskProperties.getDefaultGridSize());
//        partitionHandler.setMessagingOperations(messageTemplate);
//        //master对slave发起心跳的毫秒间隔
//        partitionHandler.setPollInterval(batchTaskProperties.getDefaultPollMills());
//        partitionHandler.setJobExplorer(jobExplorer);
//		partitionHandler.afterPropertiesSet();
//		
//		return stepBuilders.get("stepMasterSccc5801")
//				.listener(batchStepLoggedListener)
//                .partitioner(stepSlaveSccc5801.getName(), sccc5800R01Trans)
//                .aggregator(remoteStepExecutionAggregator)//aggregator用于聚合远程slave节点step执行的情况
//                .partitionHandler(partitionHandler)
//                .gridSize(batchTaskProperties.getDefaultGridSize())
//                .step(stepSlaveSccc5801)
//                .build();
	}
	
	
	@Bean("sccc9999ClearingData4EndEODStep")
	public Step sccc999ClearingData4EndEODStep(){
		
		Flow splitFlow = new FlowBuilder<Flow>("HandleDataAfterBatch9999")
				.split((TaskExecutor) ApplicationContextHolder.getBean("scccPrev4EODExecutor"))
				.add(
						(Flow)ApplicationContextHolder.getBean("sccc-9999-ClearingData4EndEOD") 
						)
				.end();
		
		return stepBuilders.get("sccc9999ClearingData4EndEODStep")
				.listener(batchStepLoggedListener)
				.flow(splitFlow)
				.build();
	}
	
	/**
	 * 对账后数据补全
	 * @return
	 */
	@Bean("stepSccc0903DataDealing")
	public Step stepSccc0903DataDealing(){
		return ApplicationContextHolder.getBean("sccc-0903-DataDealing");
	}
	
}
