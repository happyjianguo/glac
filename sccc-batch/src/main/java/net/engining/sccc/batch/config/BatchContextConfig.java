package net.engining.sccc.batch.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.partition.support.DefaultStepExecutionAggregator;
import org.springframework.batch.core.partition.support.RemoteStepExecutionAggregator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import net.engining.pcx.cc.process.service.support.BatchProvider;
import net.engining.sccc.batch.config.datamigration.DataMigrationJobsContextConfig;
import net.engining.sccc.batch.config.endofday.EndOfDayJobsContextConfig;
import net.engining.sccc.batch.config.pre4eod.PrevProcess4EndOfDayJobsContextConfig;
import net.engining.sccc.batch.infrastructure.DataMigrationBatchJobHandler;
import net.engining.sccc.batch.infrastructure.DateMigrationBatchDateIncrementer;
import net.engining.sccc.batch.infrastructure.EndOfDayBatchDateIncrementer;
import net.engining.sccc.batch.infrastructure.EndOfDayBatchJobHandler;
import net.engining.sccc.batch.infrastructure.Prev4EODBatchDateIncrementer;
import net.engining.sccc.batch.infrastructure.Prev4EODBatchJobHandler;
import net.engining.sccc.batch.listener.BatchJobLoggedListener;
import net.engining.sccc.batch.listener.BatchStepLoggedListener;
import net.engining.sccc.batch.runner.RepeatableAnyTimeBatchJobRunner;
import net.engining.sccc.batch.runner.Unrepeatable4OneTermBatchJobRunner;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * @author luxue
 *
 */
@Configuration
// 该注解已经默认注入了jobRepository,jobLauncher,jobRegistry,jobExplorer;
// 另外需要在上下文里已经注入了DataSource和transactionManager;
// 考虑到需要支持有多个相互独立的Batch Job运行，因此配置将被模块化为多个应用程序上下文。这种情况下不应该创建任何@bean
// Job,而是供应他们在单独的(子Context)ApplicationContextFactory上下文；
@EnableBatchProcessing(modular = true)
// FIXME
// 暂时没有找到编程方式配置复杂step，默认stepbuilder只支持简单的ItemReader，不支持自定义的KeyBasedStreamReader；并且所有xml定义的step都要在顶层batchContexConfig中import；
// 由于<batch:xxx>的定义是默认注入StepScope和JobScope的，且其默认的AutoProxy是设置为false的，所以根据@Bean的加载顺序和生命周期特性，以下xml内声明的Bean都属于顶层Batch
// Scope中，这样其子batch的context才能直接通过@Autowired获取这些Step；
@ImportResource(locations = { 
		"classpath:/steps/sccc-0810-CactAccountTem.xml",
		"classpath:/steps/sccc-0820-InsertAccount.xml",
		"classpath:/steps/sccc-0830-DataMigration.xml",
		"classpath:/steps/sccc-0900-AccountCheckingFileCheck.xml",
		"classpath:/steps/sccc-0901-DirectPostLedger.xml",
		"classpath:/steps/sccc-0902-TxnPostLedger.xml",
		"classpath:/steps/sccc-0903-DataDealing.xml",
		"classpath:/steps/sccc-1000-DataGovernance.xml",
		"classpath:/steps/sccc-1101-OnlineReconciliation.xml",
		"classpath:/steps/sccc-4900-SystemStatus.xml",
		"classpath:/steps/sccc-5400-DailyLedgerSummary.xml",
		"classpath:/steps/sccc-5401-DailyUpdateApGlBalance.xml",
		"classpath:/steps/sccc-5600-LedgerRemovalAndReset.xml",
		"classpath:/steps/sccc-5701-AssistTypeSplit.xml",
		"classpath:/steps/sccc-5702-RemovalAssSum.xml",
		"classpath:/steps/sccc-5703-AssistSum.xml",
		"classpath:/steps/sccc-5801-TransReportData.xml",
		"classpath:/steps/sccc-5802-CRFReportData.xml",
		"classpath:/steps/sccc-5901-GenPgFileItemData.xml",
		"classpath:/steps/sccc-5902-GenYongyouLedgerFile.xml",
		"classpath:/steps/sccc-9999-ClearingData4EndEOD.xml"
		})
public class BatchContextConfig {

	@Autowired
	JobRepository jobRepository;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	JobRegistry jobRegistry;

	@Autowired
	JobExplorer jobExplorer;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	BatchTaskProperties batchTaskProperties;

//	   设置StepScope为AutoProxy时，会造成JobRepository等Bean不会自动加载，也需要显示设置
/*	 @Bean
	 public StepScope stepScope() {
	 final StepScope stepScope = new StepScope();
	 stepScope.setAutoProxy(true);
	 return stepScope;
	 }*/

	@Bean
	public SimpleCompletionPolicy simpleCompletionPolicy() {
		return new SimpleCompletionPolicy(batchTaskProperties.getDefaultCommitInterval());
	}

	@Bean
	public BatchProvider batchProvider() {
		return new BatchProvider();
	}

	@Bean
	public EndOfDayBatchDateIncrementer batchDateIncrementer() {
		return new EndOfDayBatchDateIncrementer();
	}

	@Bean
	public Prev4EODBatchDateIncrementer prev4EODBatchDateIncrementer() {
		return new Prev4EODBatchDateIncrementer();
	}
	
	@Bean
	public DateMigrationBatchDateIncrementer dateMigrationBatchDateIncrementer() {
		return new DateMigrationBatchDateIncrementer();
	}

	@Bean
	public BatchJobLoggedListener batchJobLoggedListener() {
		return new BatchJobLoggedListener();
	}

	@Bean
	public BatchStepLoggedListener batchStepLoggedListener() {
		return new BatchStepLoggedListener();
	}

	@Bean
	public SimpleJobOperator simpleJobOperator() {
		SimpleJobOperator simpleJobOperator = new SimpleJobOperator();
		simpleJobOperator.setJobExplorer(jobExplorer);
		simpleJobOperator.setJobLauncher(jobLauncher);
		simpleJobOperator.setJobRegistry(jobRegistry);
		simpleJobOperator.setJobRepository(jobRepository);
		return simpleJobOperator;
	}

	/**
	 * 单个业务周期内不可重复执行的 Batch Job Runner
	 * 
	 * @return
	 */
	@Bean
	public Unrepeatable4OneTermBatchJobRunner unrepeatable4OneTermBatchJobRunner() {
		Unrepeatable4OneTermBatchJobRunner unrepeatable4OneTermBatchJobRunner = new Unrepeatable4OneTermBatchJobRunner();
		return unrepeatable4OneTermBatchJobRunner;
	}

	/**
	 * 任何业务周期内可重复执行的 Batch Job Runner
	 * 
	 * @return
	 */
	@Bean
	public RepeatableAnyTimeBatchJobRunner repeatableAnyTimeBatchJobRunner() {
		RepeatableAnyTimeBatchJobRunner repeatableAnyTimeBatchJobRunner = new RepeatableAnyTimeBatchJobRunner();
		return repeatableAnyTimeBatchJobRunner;
	}

	/**
	 * 通用的分布式Batch 远程Step执行聚合器
	 * 
	 * @param jobExplorer
	 * @return
	 */
	@Bean
	public RemoteStepExecutionAggregator remoteStepExecutionAggregator() {
		RemoteStepExecutionAggregator remoteStepExecutionAggregator = new RemoteStepExecutionAggregator();
		remoteStepExecutionAggregator.setJobExplorer(jobExplorer);
		remoteStepExecutionAggregator.setDelegate(defaultStepExecutionAggregator());
		return remoteStepExecutionAggregator;
	}

	@Bean
	public DefaultStepExecutionAggregator defaultStepExecutionAggregator() {
		return new DefaultStepExecutionAggregator();
	}

	@Bean
	public EndOfDayBatchJobHandler endOfDayBatchJobHandler() {
		EndOfDayBatchJobHandler endOfDayBatchJobHandler = new EndOfDayBatchJobHandler();
		return endOfDayBatchJobHandler;
	}

	@Bean
	public Prev4EODBatchJobHandler prev4EODBatchJobHandler() {
		Prev4EODBatchJobHandler prev4EODBatchJobHandler = new Prev4EODBatchJobHandler();
		return prev4EODBatchJobHandler;
	}
	
	@Bean
	public DataMigrationBatchJobHandler dataMigrationBatchJobHandler() {
		DataMigrationBatchJobHandler dataMigrationBatchJobHandler = new DataMigrationBatchJobHandler();
		return dataMigrationBatchJobHandler;
	}

	/**
	 * 日终批量子应用上下文，在调用触发时才会加载
	 * 
	 * @return
	 */
	@Bean
	public ApplicationContextFactory setupEndDayJobs() {
		return new GenericApplicationContextFactory(EndOfDayJobsContextConfig.class);
	}

	/**
	 * 预处理批量子应用上下文，在调用触发时才会加载
	 * 
	 * @return
	 */
	@Bean
	public ApplicationContextFactory setupPrevProcess4EODJobs() {
		return new GenericApplicationContextFactory(PrevProcess4EndOfDayJobsContextConfig.class);
	}
	
	/**
	 * 数据迁移批量子应用上下文，在调用触发时才会加载
	 * 
	 * @return
	 */
	@Bean
	public ApplicationContextFactory setupDataMigrationJobs() {
		return new GenericApplicationContextFactory(DataMigrationJobsContextConfig.class);
	}

}
