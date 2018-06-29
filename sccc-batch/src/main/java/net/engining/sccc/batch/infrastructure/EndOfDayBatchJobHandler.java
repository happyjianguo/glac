package net.engining.sccc.batch.infrastructure;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;

import net.engining.gm.facility.SystemStatusFacility;
import net.engining.gm.param.model.SystemStatus;

/**
 * 日终批量Job的Handler
 * @author luxue
 *
 */
public class EndOfDayBatchJobHandler extends AbstractBatchJobHandler{

	private static final long serialVersionUID = 1L;
	
	public static final String JOB_NAME= "end-of-day-job";
	
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
		return BatchJobType.UNREPEATABLE_4_ONE_TERM;
	}

	/* (non-Javadoc)
	 * @see net.engining.sccc.batch.infrastructure.AbstractBatchJobHandler#isSameTerm()
	 */
	@Override
	public boolean isSameTerm(JobExecution execution, JobParameters jobParameters) {
		boolean checkFlg = true;
		//最近一次执行的批次日期
		Date batchDate = jobParameters.getDate(BatchJobParameterKeys.BatchDate);
		SystemStatus status = systemStatusFacility.getSystemStatus();
		//批日期相同表示未成功的Job正是当前周期的
		if(status.processDate.compareTo(batchDate)==0){
			
			checkFlg = false;
		}
		return checkFlg;
	}

}
