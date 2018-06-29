package net.engining.sccc.batch.infrastructure;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.beans.factory.annotation.Autowired;

import net.engining.gm.facility.SystemStatusFacility;
import net.engining.gm.infrastructure.enums.SystemStatusType;
import net.engining.gm.param.model.SystemStatus;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;

public class EndOfDayBatchDateIncrementer implements JobParametersIncrementer {

	private static final Logger log = LoggerFactory.getLogger(EndOfDayBatchDateIncrementer.class);
	
	private static final String batchSeqPrefix = "EndOfDay";

	@Autowired
	private SystemStatusFacility systemStatusFacility;

	@Override
	public JobParameters getNext(JobParameters parameters) {

		JobParameters jobParam = null;

		SystemStatus status = systemStatusFacility.getSystemStatus();

		if (status.systemStatus.equals(SystemStatusType.B)) {
			throw new ErrorMessageException(ErrorCode.SystemError, "系统当前正处于批量状态，不允许再次执行批量，请检查系统，确认没有批量正在处理，可能需要手动修改系统状态");
		}

		// 先做日切
		systemStatusFacility.doDateSwitch(status.businessDate);
		status = systemStatusFacility.getSystemStatus();

		jobParam = new JobParametersBuilder()
				.addDate(BatchJobParameterKeys.BatchDate, status.processDate, false)
				.addString(BatchJobParameterKeys.BatchDateString, DateFormatUtils.format(status.processDate, "yyyy-MM-dd"), false)
				.addDate(BatchJobParameterKeys.BizDate, status.businessDate, false)
				.addString(BatchJobParameterKeys.BizDateString, DateFormatUtils.format(status.businessDate, "yyyy-MM-dd"), false)
				.addDate(BatchJobParameterKeys.LastBatchDate, status.lastProcessDate, false)
				.addString(BatchJobParameterKeys.LastBatchDateString, DateFormatUtils.format(status.lastProcessDate, "yyyy-MM-dd"), false)
				// 以启动时，所处的批量日期作为日终批量JOB的批次号；只以此值作为job instance生成key的参数
				.addString(BatchJobParameterKeys.BatchSeq, batchSeqPrefix+DateFormatUtils.format(status.processDate, "YYYYMMdd"))
				.toJobParameters();

		log.info("当前系统时间{}, Batch Job Parameters: batchDate={}, bizDate={}, LastBatchDate={}, batchSeq={}", new Date(), jobParam.getDate("batchDate"),
				jobParam.getDate("bizDate"), jobParam.getDate("lastSystemDate"), jobParam.getString("batchSeq"));
		return jobParam;
	}
}
