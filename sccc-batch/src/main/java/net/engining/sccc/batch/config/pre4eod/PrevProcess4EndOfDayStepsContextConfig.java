package net.engining.sccc.batch.config.pre4eod;



import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.partition.support.RemoteStepExecutionAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.core.MessagingTemplate;

import net.engining.pg.support.core.context.ApplicationContextHolder;
import net.engining.sccc.batch.listener.BatchStepLoggedListener;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 日终批量预处理Step 配置
 * @author luxue
 *
 */
public class PrevProcess4EndOfDayStepsContextConfig {
	
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
	
	@Bean("sccc090009010902CombineFlowStep")
	public Step sccc090009010902CombineFlowStep(){
		
		//创建可并行的flow step; 0900,0901,0902 三个flow并行执行
		Flow splitFlow = new FlowBuilder<Flow>("splitFlowSccc1000")
				.split((TaskExecutor) ApplicationContextHolder.getBean("scccPrev4EODExecutor"))
				.add(
						(Flow)ApplicationContextHolder.getBean("sccc-0900-AccountCheckingFileCheck"),
						(Flow)ApplicationContextHolder.getBean("sccc-0901-DirectPostLedger"), 
						(Flow)ApplicationContextHolder.getBean("sccc-0902-TxnPostLedger")
					)
				.end();
		
		return stepBuilders.get("sccc090009010902CombineFlowStep")
				.listener(batchStepLoggedListener)
				.flow(splitFlow)
				.build();
				
	}
	
}
