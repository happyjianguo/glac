package net.engining.sccc.batch.infrastructure;

import java.io.Serializable;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;

import net.engining.sccc.batch.runner.enums.ActionRunnerOption;
import net.engining.sccc.batch.runner.enums.AdditionalRunnerOption;

/**
 * 批量任务Handler，用于对Job增强属性
 * @author luxue
 *
 */
public abstract class AbstractBatchJobHandler implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 连续行次数
	 */
	int count = 1;

	/**
	 * 启动参数项
	 */
	ActionRunnerOption actionRunnerOptions;

	/**
	 * 启动附加参数项
	 * 
	 */
	AdditionalRunnerOption additionalRunnerOption;

	public abstract String getJobName();
	
	public abstract BatchJobType getBatchJobType();
	
	/**
	 * 判断是否在同一个业务周期内
	 * @param execution
	 * @param jobParameters
	 * @return
	 */
	public abstract boolean isSameTerm(JobExecution execution, JobParameters jobParameters);

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the actionRunnerOptions
	 */
	public ActionRunnerOption getActionRunnerOptions() {
		return actionRunnerOptions;
	}

	/**
	 * @param actionRunnerOptions the actionRunnerOptions to set
	 */
	public void setActionRunnerOptions(ActionRunnerOption actionRunnerOptions) {
		this.actionRunnerOptions = actionRunnerOptions;
	}

	/**
	 * @return the additionalRunnerOption
	 */
	public AdditionalRunnerOption getAdditionalRunnerOption() {
		return additionalRunnerOption;
	}

	/**
	 * @param additionalRunnerOption the additionalRunnerOption to set
	 */
	public void setAdditionalRunnerOption(AdditionalRunnerOption additionalRunnerOption) {
		this.additionalRunnerOption = additionalRunnerOption;
	}

}
