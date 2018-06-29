package net.engining.sccc.batch.runner;

import static com.google.common.base.Preconditions.checkNotNull;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.NoSuchJobInstanceException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;

import net.engining.gm.facility.SystemStatusFacility;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.batch.infrastructure.AbstractBatchJobHandler;
import net.engining.sccc.batch.runner.enums.ActionRunnerOption;
import net.engining.sccc.batch.runner.enums.AdditionalRunnerOption;

/**
 * Batch Job Runner 抽象类
 * 
 * @author luxue
 *
 */
public abstract class AbstractBatchJobRunner {

	private static final Logger log = LoggerFactory.getLogger(AbstractBatchJobRunner.class);

	@Autowired
	JobOperator operator;

	@Autowired
	JobExplorer explorer;

	@Autowired
	SystemStatusFacility systemStatusFacility;

	AbstractBatchJobHandler batchJobHandler;

	/**
	 * 运行Job
	 * 
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
	public void run() throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException, NoSuchJobExecutionException,
			JobRestartException, JobParametersInvalidException, JobParametersNotFoundException, JobExecutionAlreadyRunningException,
			UnexpectedJobExecutionException {

		checkNotNull(batchJobHandler, "需要指定batchJobHandler");

		if (!Optional.fromNullable(batchJobHandler.getActionRunnerOptions()).isPresent()) {
			log.error("必须为批量任务指定启动方式, ActionRunnerOption: {},{}", ActionRunnerOption.ST.toString(), ActionRunnerOption.RS.toString());

		}
		else if (batchJobHandler.getActionRunnerOptions().equals(ActionRunnerOption.ST)) {

			if (batchJobHandler.getAdditionalRunnerOption().equals(AdditionalRunnerOption.FC)) {
				if (!unrepeatableRunCheck()) {
					restartLastTimeRun();
					// 重新执行也算一次执行
					batchJobHandler.setCount(batchJobHandler.getCount() - 1);
				}
				// 如果以上重新执行逻辑成功，还需要根据count，开始执行下次批量Job
				startJob();
			}
			else {
				if (!unrepeatableRunCheck()) {
					throw new ErrorMessageException(ErrorCode.CheckError, "非强制执行选项下，最近一次执行Job没有成功，不能启动新批量。");
				}
				// 检查可以开始执行下次批量Job
				startJob();
			}

		}
		else if (batchJobHandler.getActionRunnerOptions().equals(ActionRunnerOption.RS)) {
			restartLastTimeRun();
		}

	}

	/**
	 * 不可重复运行Job的检查
	 * 
	 * @return true:可以继续；false:不可继续；
	 * @throws NoSuchJobException
	 * @throws NoSuchJobInstanceException
	 */
	public abstract boolean unrepeatableRunCheck() throws NoSuchJobException, NoSuchJobInstanceException;

	/**
	 * 正常执行批量Job
	 * 
	 * @throws NoSuchJobException
	 * @throws JobParametersNotFoundException
	 * @throws JobRestartException
	 * @throws JobExecutionAlreadyRunningException
	 * @throws JobInstanceAlreadyCompleteException
	 * @throws UnexpectedJobExecutionException
	 * @throws JobParametersInvalidException
	 */
	public void startJob() throws NoSuchJobException, JobParametersNotFoundException, JobRestartException, JobExecutionAlreadyRunningException,
			JobInstanceAlreadyCompleteException, UnexpectedJobExecutionException, JobParametersInvalidException {

		log.info("准备运行" + batchJobHandler.getCount() + "个批量");
		for (int i = 0; i < batchJobHandler.getCount(); i++) {
			log.info("starting batch at process date: {}, at system date: {}", systemStatusFacility.getFormattedProcessDate(), new Date());
			Long executionId = operator.startNextInstance(batchJobHandler.getJobName());

			JobExecution execution = explorer.getJobExecution(executionId);
			ExitStatus status = execution.getExitStatus();
			if (!status.equals(ExitStatus.COMPLETED)) {
				log.error("批量执行失败，退出状态：{}", status);
				ErrorMessageException errorMessageException = new ErrorMessageException(ErrorCode.SystemError,
						String.format("批量执行失败，退出状态：%s", status));
				errorMessageException.dump(errorMessageException);
				throw errorMessageException;
			}
			else {
				log.info("批量正常完成");
			}
		}
	}

	/**
	 * 重启最近一次未运行成功的Job
	 * 
	 * @throws NoSuchJobException
	 * @throws NoSuchJobInstanceException
	 * @throws JobInstanceAlreadyCompleteException
	 * @throws NoSuchJobExecutionException
	 * @throws JobRestartException
	 * @throws JobParametersInvalidException
	 */
	public void restartLastTimeRun() throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException {
		List<Long> instances = operator.getJobInstances(batchJobHandler.getJobName(), 0, 1);
		if (instances.isEmpty()) {
			log.error("no job instances to restart");
			throw new ErrorMessageException(ErrorCode.CheckError, "no job instances to restart");
		}

		Long instanceId = instances.get(0);

		List<Long> executions = operator.getExecutions(instanceId);
		if (executions.isEmpty()) {
			log.error("no job executions to restart");
			throw new ErrorMessageException(ErrorCode.CheckError, "no job instances to restart");
		}

		Long executionId = executions.get(0);
		log.info("restarting batch at process date: {}, at system date: {}", systemStatusFacility.getFormattedProcessDate(), new Date());
		executionId = operator.restart(executionId);

		JobExecution execution = explorer.getJobExecution(executionId);
		ExitStatus es = execution.getExitStatus();
		if (!es.equals(ExitStatus.COMPLETED)) {
			log.error(MessageFormat.format("批量执行失败，退出状态：{0}", es));
			ErrorMessageException errorMessageException = new ErrorMessageException(ErrorCode.SystemError, String.format("批量执行失败，退出状态：%s", es));
			errorMessageException.dump(errorMessageException);
			throw errorMessageException;
		}
		else {
			log.info("重新执行批量[{}]正常完成", executionId);
		}
	}
	
	/**
	 * 标记正在执行的Job为ABANDONED <br>
	 * 如果Job执行的进程Died，通常停止信号会被忽略了，这时最好的方法来标记这个Job为ABANDONED(而不是STOP)。一个ABANDONED的Job可以restart，但是一个STOP的Job不能restart。
	 * @throws NoSuchJobException
	 * @throws NoSuchJobInstanceException
	 * @throws NoSuchJobExecutionException
	 * @throws JobExecutionAlreadyRunningException
	 */
	public void abandon() throws NoSuchJobException, NoSuchJobInstanceException, NoSuchJobExecutionException, JobExecutionAlreadyRunningException{
		checkNotNull(batchJobHandler, "需要指定batchJobHandler");
		
		List<Long> instances = operator.getJobInstances(batchJobHandler.getJobName(), 0, 1);
		if (instances.isEmpty()) {
			log.error("no job instances to restart");
			throw new ErrorMessageException(ErrorCode.CheckError, "no job instances to restart");
		}
		
		Long instanceId = instances.get(0);
		List<Long> executions = operator.getExecutions(instanceId);
		if (executions.isEmpty()) {
			log.error("no job executions to restart");
			throw new ErrorMessageException(ErrorCode.CheckError, "no job instances to restart");
		}
		
		Long executionId = executions.get(0);
		log.info("abandon batch at process date: {}, at system date: {}", systemStatusFacility.getFormattedProcessDate(), new Date());
		JobExecution execution = operator.abandon(executionId);
//		BatchStatus bs = execution.getStatus();
//		if(!bs.equals(BatchStatus.ABANDONED)){
//			
//		}
	}

	/**
	 * @return the batchJobHandler
	 */
	public AbstractBatchJobHandler getBatchJobHandler() {
		return batchJobHandler;
	}

	/**
	 * @param batchJobHandler
	 *            the batchJobHandler to set
	 */
	public void setBatchJobHandler(AbstractBatchJobHandler batchJobHandler) {
		this.batchJobHandler = batchJobHandler;
	}
}
