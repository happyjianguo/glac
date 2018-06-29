package net.engining.sccc.batch.config.endofday;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import net.engining.pg.support.core.context.ApplicationContextHolder;
import net.engining.sccc.batch.infrastructure.EndOfDayBatchDateIncrementer;
import net.engining.sccc.batch.infrastructure.EndOfDayBatchJobHandler;
import net.engining.sccc.batch.listener.BatchJobLoggedListener;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 
 *  日终批量配置
 * @author luxue
 *
 */
@EnableBatchProcessing
@Import(value = {
		EndOfDayStepsContextConfig.class
})
public class EndOfDayJobsContextConfig {
	
	@Autowired
	JobExplorer jobExplorer;
	
	@Autowired
	JobBuilderFactory jobBuilders;
 
	@Autowired
	BatchJobLoggedListener batchJobLoggedListener;
	
	@Autowired
	EndOfDayBatchDateIncrementer batchDateIncrementer;
	
	@Autowired
	BatchTaskProperties batchTaskProperties;
	
	@Profile({"master", "mixed"})
	@Bean
	public Job endOfDayJob(
			@Qualifier("stepSccc4900") Step stepSccc4900,
			@Qualifier("stepMasterSccc5701") Step stepMasterSccc5701,
			@Qualifier("sccc5800CombineFlowStep") Step sccc5800CombineFlowStep,
			@Qualifier("stepSccc5901T01") Step stepSccc5901T01,
			@Qualifier("sccc5902GenYongyouLedgerFile") Step sccc5902GenYongyouLedgerFile,
			@Qualifier("sccc9999ClearingData4EndEODStep") Step sccc9999ClearingData4EndEODStep,
			@Qualifier("stepSccc0903DataDealing") Step stepSccc0903DataDealing
			
			) throws Exception {
		
		return jobBuilders.get(EndOfDayBatchJobHandler.JOB_NAME)
				.incrementer(batchDateIncrementer)
				.listener(batchJobLoggedListener)
				.start((Flow)ApplicationContextHolder.getBean("sccc-1000-DataGovernance"))
				.next((Flow)ApplicationContextHolder.getBean("sccc-1101-OnlineReconciliation"))
				.next(stepSccc0903DataDealing)
				.next((Flow)ApplicationContextHolder.getBean("sccc-5400-DailyLedgerSummary"))
				.next((Flow)ApplicationContextHolder.getBean("sccc-5600-LedgerRemovalAndReset"))
				.next((Flow)ApplicationContextHolder.getBean("sccc-5401-DailyUpdateApGlBalance"))
				.next(stepMasterSccc5701)
				.next((Flow)ApplicationContextHolder.getBean("sccc-5702-RemovalAssSum"))
				.next((Flow)ApplicationContextHolder.getBean("sccc-5703-AssistSum"))
				.next(stepSccc4900)
				.next(sccc5800CombineFlowStep)
				.next(stepSccc5901T01)
				.next(sccc5902GenYongyouLedgerFile)
				.next(sccc9999ClearingData4EndEODStep)
				.end()
				.build();
	}
	
}
