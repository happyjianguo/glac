package net.engining.sccc.batch.config.pre4eod;

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
import net.engining.sccc.batch.infrastructure.Prev4EODBatchDateIncrementer;
import net.engining.sccc.batch.infrastructure.Prev4EODBatchJobHandler;
import net.engining.sccc.batch.listener.BatchJobLoggedListener;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 日终批量预处理Job配置
 * @author luxue
 *
 */
@EnableBatchProcessing
@Import(value = {
		PrevProcess4EndOfDayStepsContextConfig.class
})
public class PrevProcess4EndOfDayJobsContextConfig {
	
	@Autowired
	JobExplorer jobExplorer;
	
	@Autowired
	JobBuilderFactory jobBuilders;
 
	@Autowired
	BatchJobLoggedListener batchJobLoggedListener;
	
	@Autowired
	Prev4EODBatchDateIncrementer prev4EODBatchDateIncrementer;
	
	@Autowired
	BatchTaskProperties batchTaskProperties;
	
	@Profile({"master", "mixed"})
	@Bean
	public Job prevProcess4EndOfDayJob(
			@Qualifier("sccc090009010902CombineFlowStep") Step sccc090009010902CombineFlowStep
			) throws Exception{
		
		return  jobBuilders.get(Prev4EODBatchJobHandler.JOB_NAME)
				.incrementer(prev4EODBatchDateIncrementer)
				.listener(batchJobLoggedListener)
				.start(sccc090009010902CombineFlowStep)
				.on("*").to((Step)ApplicationContextHolder.getBean("sccc-0903-DataDealing")).end()
				.build();
	}
	
}
