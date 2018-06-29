package net.engining.sccc.batch.config.datamigration;

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
import net.engining.sccc.batch.infrastructure.DataMigrationBatchJobHandler;
import net.engining.sccc.batch.infrastructure.DateMigrationBatchDateIncrementer;
import net.engining.sccc.batch.listener.BatchJobLoggedListener;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 数据迁移批量配置
 * @author luxue
 *
 */
@EnableBatchProcessing
@Import(value = {
		DataMigrationStepsContextConfig.class
})
public class DataMigrationJobsContextConfig {
	
	@Autowired
	JobExplorer jobExplorer;
	
	@Autowired
	JobBuilderFactory jobBuilders;
 
	@Autowired
	BatchJobLoggedListener batchJobLoggedListener;
	
	@Autowired
	DateMigrationBatchDateIncrementer dateMigrationBatchDateIncrementer;
	
	@Autowired
	BatchTaskProperties batchTaskProperties;
	
	@Profile({"master", "mixed"})
	@Bean
	public Job dataMigration(
			@Qualifier("stepSccc0810T01") Step stepSccc0810T01,
			@Qualifier("stepMasterSccc0810") Step stepMasterSccc0810,
			@Qualifier("stepMasterSccc0820") Step stepMasterSccc0820,
			@Qualifier("stepMasterSccc0830") Step stepMasterSccc0830
			) throws Exception {
		
		return jobBuilders.get(DataMigrationBatchJobHandler.JOB_NAME)
				.incrementer(dateMigrationBatchDateIncrementer)
				.listener(batchJobLoggedListener)
				.start(stepSccc0810T01)
				.next(stepMasterSccc0810)
				.next(stepMasterSccc0820)
				.next(stepMasterSccc0830)
				.build();
	}
	
}
