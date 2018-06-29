package net.engining.sccc.batch.config.datamigration;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.DefaultStepExecutionAggregator;
import org.springframework.batch.core.partition.support.RemoteStepExecutionAggregator;
import org.springframework.batch.core.step.builder.PartitionStepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;

import net.engining.pg.support.core.context.ApplicationContextHolder;
import net.engining.sccc.batch.infrastructure.utils.PartitionHandlerUtils;
import net.engining.sccc.batch.listener.BatchStepLoggedListener;
import net.engining.sccc.batch.sccc0800.Sccc0810R01BtDataMigrationTempdtl;
import net.engining.sccc.batch.sccc0800.Sccc0820R02CactAccountTem;
import net.engining.sccc.batch.sccc0800.Sccc0830R03BtDataMigrationTempdtl;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 数据迁移批量Step 配置
 * 
 * @author luxue
 *
 */
public class DataMigrationStepsContextConfig {

	@Autowired
	JobExplorer jobExplorer;

	@Autowired
	StepBuilderFactory stepBuilders;

	@Autowired
	BatchStepLoggedListener batchStepLoggedListener;

	@Autowired
	RemoteStepExecutionAggregator remoteStepExecutionAggregator;

	@Autowired
	DefaultStepExecutionAggregator defaultStepExecutionAggregator;

	@Autowired
	BatchTaskProperties batchTaskProperties;

	@Autowired
	MessagingTemplate messageTemplate;
	
	@Autowired
	QueueChannel inboundRequests;

	/**
	 * 需要支持分布式处理的Step，master配置; master节点关键点是：其Step需要设置从节点的Step，和一个数据分区器；
	 * 数据分区器需要实现Partitioner接口：返回一个Map<String,
	 * ExecutionContext>的数据结构，这个结构完整的描述了每个从节点需要处理的分区片段，ExecutionContext保存了从节点要处理的数据边界。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean("stepMasterSccc0830")
	public Step stepMasterSccc0830() throws Exception {

		// slaveStep
		Step stepSlaveSccc0830 = ApplicationContextHolder.getBean("sccc-0830-DataMigration");
		// Partitioner and Step Reader
		Sccc0830R03BtDataMigrationTempdtl sccc0830R = ApplicationContextHolder.getBean("sccc0830R03BtDataMigrationTempdtl");
		// Partition Handler
		PartitionHandler partitionHandler = null;
		if (batchTaskProperties.isEnableRemotePartition()) {
			partitionHandler = PartitionHandlerUtils.remoteMessageChannelPartitionHandler(stepSlaveSccc0830, batchTaskProperties.getSccc0830GridSize(),
					messageTemplate, batchTaskProperties.getDefaultPollMills(), jobExplorer, null, inboundRequests);
		}
		else{
			partitionHandler = PartitionHandlerUtils.localTaskExecutorPartitionHandler(stepSlaveSccc0830, batchTaskProperties.getSccc0830GridSize(),
					ApplicationContextHolder.getBean("dataMigrationExecutor"));
		}
		
		PartitionStepBuilder pStepBuilder = stepBuilders.get("stepMasterSccc0830")
				.listener(batchStepLoggedListener)
				.partitioner(stepSlaveSccc0830.getName(), sccc0830R)
				.partitionHandler(partitionHandler)
				.gridSize(batchTaskProperties.getDefaultGridSize());
				
		if (batchTaskProperties.isEnableRemotePartition()) {
			pStepBuilder.aggregator(remoteStepExecutionAggregator);
		}
		else{
			pStepBuilder.aggregator(defaultStepExecutionAggregator);
		}

		return pStepBuilder.build();
		
	}

	@Bean("stepMasterSccc0820")
	public Step stepMasterSccc0820() throws Exception {

		// slaveStep
		Step stepSlaveSccc0820 = ApplicationContextHolder.getBean("sccc-0820-InsertAccount");
		// Partitioner and Step Reader
		Sccc0820R02CactAccountTem sccc0820R = ApplicationContextHolder.getBean("sccc0820R02CactAccountTem");
		
		// Partition Handler
		PartitionHandler partitionHandler = null;
		if (batchTaskProperties.isEnableRemotePartition()) {
			partitionHandler = PartitionHandlerUtils.remoteMessageChannelPartitionHandler(stepSlaveSccc0820, batchTaskProperties.getSccc0820GridSize(),
					messageTemplate, batchTaskProperties.getDefaultPollMills(), jobExplorer, null, inboundRequests);
		}
		else{
			partitionHandler = PartitionHandlerUtils.localTaskExecutorPartitionHandler(stepSlaveSccc0820, batchTaskProperties.getSccc0820GridSize(),
					ApplicationContextHolder.getBean("dataMigrationExecutor"));
		}
		
		PartitionStepBuilder pStepBuilder = stepBuilders.get("stepMasterSccc0820")
				.listener(batchStepLoggedListener)
				.partitioner(stepSlaveSccc0820.getName(), sccc0820R)
				.partitionHandler(partitionHandler)
				.gridSize(batchTaskProperties.getDefaultGridSize());
				
		if (batchTaskProperties.isEnableRemotePartition()) {
			pStepBuilder.aggregator(remoteStepExecutionAggregator);
		}
		else{
			pStepBuilder.aggregator(defaultStepExecutionAggregator);
		}

		return pStepBuilder.build();
		
	}

	@Bean("stepMasterSccc0810")
	public Step stepMasterSccc0810() throws Exception {

		// slaveStep
		Step stepSlaveSccc0810 = ApplicationContextHolder.getBean("stepSccc0810T10");
		// Partitioner and Step Reader
		Sccc0810R01BtDataMigrationTempdtl sccc0810R = ApplicationContextHolder.getBean("sccc0810R01BtDataMigrationTempdtl");

		// Partition Handler
		PartitionHandler partitionHandler = null;
		if (batchTaskProperties.isEnableRemotePartition()) {
			
//			partitionHandler = PartitionHandlerUtils.remoteMessageChannelPartitionHandler(stepSlaveSccc0810, batchTaskProperties.getSccc0810GridSize(),
//					messageTemplate, batchTaskProperties.getDefaultPollMills(), jobExplorer, null, inboundRequests);
			
			partitionHandler = PartitionHandlerUtils.remoteMutiThreadMessageChannelPartitionHandler(stepSlaveSccc0810, batchTaskProperties.getSccc0810GridSize(),
					messageTemplate, batchTaskProperties.getDefaultPollMills(), jobExplorer, null, inboundRequests, ApplicationContextHolder.getBean("dataMigrationExecutor"));
		}
		else{
			
			partitionHandler = PartitionHandlerUtils.localTaskExecutorPartitionHandler(stepSlaveSccc0810, batchTaskProperties.getSccc0810GridSize(),
					ApplicationContextHolder.getBean("dataMigrationExecutor"));
		}
		
		PartitionStepBuilder pStepBuilder = stepBuilders.get("stepMasterSccc0810")
				.listener(batchStepLoggedListener)
				.partitioner(stepSlaveSccc0810.getName(), sccc0810R)
				.partitionHandler(partitionHandler)
				.gridSize(batchTaskProperties.getDefaultGridSize());
				
		if (batchTaskProperties.isEnableRemotePartition()) {
			pStepBuilder.aggregator(remoteStepExecutionAggregator);
		}
		else{
			pStepBuilder.aggregator(defaultStepExecutionAggregator);
		}

		return pStepBuilder.build();
	}
}
