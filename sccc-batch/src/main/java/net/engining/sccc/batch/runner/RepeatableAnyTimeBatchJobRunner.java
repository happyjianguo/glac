package net.engining.sccc.batch.runner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobInstanceException;

import net.engining.sccc.batch.runner.enums.AdditionalRunnerOption;

/**
 * 任何业务周期内可重复执行的 Batch Job; 通常用于数据量较大，对性能有要求的轮询批量任务，可进行远程分区Setp;
 * 注意：该类型的批量任务，需要有防止重复处理相同数据的逻辑；
 * 
 * @author luxue
 *
 */
public class RepeatableAnyTimeBatchJobRunner extends AbstractBatchJobRunner{

	private static final Logger log = LoggerFactory.getLogger(RepeatableAnyTimeBatchJobRunner.class);

	/* (non-Javadoc)
	 * @see net.engining.sccc.batch.runner.AbstractBatchJobRunner#unrepeatableRunCheck()
	 */
	@Override
	public boolean unrepeatableRunCheck() throws NoSuchJobException, NoSuchJobInstanceException {
		boolean checkFlg = true;

		List<Long> instances = operator.getJobInstances(batchJobHandler.getJobName(), 0, 1);
		// 最近一次实例
		if (!instances.isEmpty()) {
			Long instanceId = instances.get(0);
			List<Long> executions = operator.getExecutions(instanceId);
			// 最近一次执行
			if (!executions.isEmpty()) {
				// 获取Job执行对象
				Long executionId = executions.get(0);
				JobExecution execution = explorer.getJobExecution(executionId);

				// 检查是否有未完成批量
				if (!ExitStatus.COMPLETED.equals(execution.getExitStatus())) {
					
					if (batchJobHandler.getAdditionalRunnerOption().equals(AdditionalRunnerOption.FC)) {
						
						log.warn("最近一次执行[{}]没有成功，将强制执行", executionId);
						
					} else {
						log.error("最近一次执行[{}]没有成功", executionId);
						checkFlg = false;
					}
				}
			}
		}
		return checkFlg;
	}

}
