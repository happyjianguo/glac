package net.engining.sccc.batch.service;

import javax.validation.constraints.Min;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.NoSuchJobInstanceException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import net.engining.sccc.batch.infrastructure.DataMigrationBatchJobHandler;
import net.engining.sccc.batch.infrastructure.EndOfDayBatchJobHandler;
import net.engining.sccc.batch.infrastructure.Prev4EODBatchJobHandler;
import net.engining.sccc.batch.runner.RepeatableAnyTimeBatchJobRunner;
import net.engining.sccc.batch.runner.Unrepeatable4OneTermBatchJobRunner;
import net.engining.sccc.batch.runner.enums.ActionRunnerOption;
import net.engining.sccc.batch.runner.enums.AdditionalRunnerOption;

@Service
@Validated
public class BatchMgmService {

	@Autowired
	Unrepeatable4OneTermBatchJobRunner unrepeatable4OneTermBatchJobRunner;
	
	@Autowired
	RepeatableAnyTimeBatchJobRunner repeatableAnyTimeBatchJobRunner;
	
	@Autowired
	EndOfDayBatchJobHandler endOfDayBatchJobHandler;
	
	@Autowired
	Prev4EODBatchJobHandler prev4EODBatchJobHandler;
	
	@Autowired
	DataMigrationBatchJobHandler dataMigrationBatchJobHandler;

	/**
	 * 启动日终批量任务, 可多次
	 * @param count
	 * @throws NoSuchJobException
	 * @throws NoSuchJobInstanceException
	 * @throws JobInstanceAlreadyCompleteException
	 * @throws NoSuchJobExecutionException
	 * @throws JobRestartException
	 * @throws JobParametersInvalidException
	 * @throws JobParametersNotFoundException
	 * @throws JobExecutionAlreadyRunningException
	 * @throws UnexpectedJobExecutionException
	 */
	public void startEndOfDayJob(@Min(value=1) int count)
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {
		
		endOfDayBatchJobHandler.setCount(count);
		endOfDayBatchJobHandler.setActionRunnerOptions(ActionRunnerOption.ST);
		endOfDayBatchJobHandler.setAdditionalRunnerOption(AdditionalRunnerOption.RE);
		unrepeatable4OneTermBatchJobRunner.setBatchJobHandler(endOfDayBatchJobHandler);
		unrepeatable4OneTermBatchJobRunner.run();
		
	}
	
	/**
	 * 重启最近一次失败的批量
	 * @throws NoSuchJobException
	 * @throws NoSuchJobInstanceException
	 * @throws JobInstanceAlreadyCompleteException
	 * @throws NoSuchJobExecutionException
	 * @throws JobRestartException
	 * @throws JobParametersInvalidException
	 * @throws JobParametersNotFoundException
	 * @throws JobExecutionAlreadyRunningException
	 * @throws UnexpectedJobExecutionException
	 */
	public void restartEndOfDayJob()
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {
		
		endOfDayBatchJobHandler.setActionRunnerOptions(ActionRunnerOption.RS);
		unrepeatable4OneTermBatchJobRunner.setBatchJobHandler(endOfDayBatchJobHandler);
		unrepeatable4OneTermBatchJobRunner.run();
		
	}
	
	public void startPrev4EODJob(@Min(value=1) int count)
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {
		
		prev4EODBatchJobHandler.setCount(count);
		prev4EODBatchJobHandler.setActionRunnerOptions(ActionRunnerOption.ST);
		prev4EODBatchJobHandler.setAdditionalRunnerOption(AdditionalRunnerOption.FC);
		repeatableAnyTimeBatchJobRunner.setBatchJobHandler(prev4EODBatchJobHandler);
		repeatableAnyTimeBatchJobRunner.run();
		
	}
	
	public void restartPrev4EODJob()
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {
		
		prev4EODBatchJobHandler.setActionRunnerOptions(ActionRunnerOption.RS);
		repeatableAnyTimeBatchJobRunner.setBatchJobHandler(prev4EODBatchJobHandler);
		repeatableAnyTimeBatchJobRunner.run();
		
	}
	
	public void startDataMigrationJob(@Min(value=1) int count)
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {
		
		dataMigrationBatchJobHandler.setCount(count);
		dataMigrationBatchJobHandler.setActionRunnerOptions(ActionRunnerOption.ST);
		dataMigrationBatchJobHandler.setAdditionalRunnerOption(AdditionalRunnerOption.RE);
		unrepeatable4OneTermBatchJobRunner.setBatchJobHandler(dataMigrationBatchJobHandler);
		unrepeatable4OneTermBatchJobRunner.run();
		
	}
	
	public void restartDataMigrationJob()
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {
		
		dataMigrationBatchJobHandler.setActionRunnerOptions(ActionRunnerOption.RS);
		unrepeatable4OneTermBatchJobRunner.setBatchJobHandler(dataMigrationBatchJobHandler);
		unrepeatable4OneTermBatchJobRunner.run();
		
	}
	
	public void abandonDataMigrationJob()
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {
		
		unrepeatable4OneTermBatchJobRunner.setBatchJobHandler(dataMigrationBatchJobHandler);
		unrepeatable4OneTermBatchJobRunner.abandon();
		
	}

}