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
 * 单个业务周期内不可重复执行的 Batch Job; 通常是日终批量，年终批量等;
 * 
 * @author luxue
 *
 */
public class Unrepeatable4OneTermBatchJobRunner extends AbstractBatchJobRunner{

	private static final Logger log = LoggerFactory.getLogger(Unrepeatable4OneTermBatchJobRunner.class);

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
					// 如果是强制执行选项，还要考虑是否在同一周期内，同周期的不可强制执行，需要先重启最近一次失败的
					// 检查参数是否在同一Job周期内
					if (batchJobHandler.getAdditionalRunnerOption().equals(AdditionalRunnerOption.FC)) {
						if (batchJobHandler.isSameTerm(execution, execution.getJobParameters())) {
							log.error("强制执行选项时，最近一次执行[{}]没有成功，且当前属于相同批量周期内，不能启动新批量，需要重启该批量。", executionId);
							checkFlg = false;
						}
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
