package net.engining.sccc.batch.infrastructure;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;

import net.engining.gm.facility.SystemStatusFacility;

/**
 * 日终预处理批量Job的Handler
 * @author luxue
 *
 */
public class Prev4EODBatchJobHandler extends AbstractBatchJobHandler{

	private static final long serialVersionUID = 1L;
	
	public static final String JOB_NAME= "prev-process-for-EOD-job";
	
	@Autowired
	SystemStatusFacility systemStatusFacility;

	/* (non-Javadoc)
	 * @see net.engining.sccc.batch.infrastructure.AbstractBatchJobHandler#getJobName()
	 */
	@Override
	public String getJobName() {
		return JOB_NAME;
	}

	/* (non-Javadoc)
	 * @see net.engining.sccc.batch.infrastructure.AbstractBatchJobHandler#getBatchJobType()
	 */
	@Override
	public BatchJobType getBatchJobType() {
		return BatchJobType.REPEATABLE_4_ONE_TERM;
	}

	/* 
	 * 日终预处理批量允许在同一个业务周期内执行多次，直接返回false
	 * (non-Javadoc)
	 * @see net.engining.sccc.batch.infrastructure.AbstractBatchJobHandler#isSameTerm()
	 */
	@Override
	public boolean isSameTerm(JobExecution execution, JobParameters jobParameters) {
		
		return false;
	}

}
