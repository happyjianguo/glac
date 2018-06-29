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

public class DateMigrationBatchDateIncrementer implements JobParametersIncrementer {

	private static final Logger log = LoggerFactory.getLogger(DateMigrationBatchDateIncrementer.class);
	
	private static final String batchSeqPrefix = "DateMigration";

	@Autowired
	private SystemStatusFacility systemStatusFacility;

	@Override
	public JobParameters getNext(JobParameters parameters) {

		JobParameters jobParam = null;

		SystemStatus status = systemStatusFacility.getSystemStatus();

		if (status.systemStatus.equals(SystemStatusType.B)) {
			throw new ErrorMessageException(ErrorCode.CheckError, "系统当前正处于日终批量处理中，不允许执行数据迁移批量，请检查系统，确认没有日终批量正在处理。");
		}

		jobParam = new JobParametersBuilder()
				.addDate(BatchJobParameterKeys.BatchDate, status.processDate, false)
				.addDate(BatchJobParameterKeys.BizDate, status.businessDate, false)
				// 以启动时，所处的联机日期作为JOB的批次号；只以此值作为job instance生成key的参数
				.addString(BatchJobParameterKeys.BatchSeq, batchSeqPrefix+DateFormatUtils.format(status.businessDate, "YYYYMMdd"))
				.toJobParameters();

		log.info("当前系统时间{}, Batch Job Parameters: batchDate={}, bizDate={}, batchSeq={}", new Date(), jobParam.getDate("batchDate"),
				jobParam.getDate("bizDate"), jobParam.getString("batchSeq"));
		return jobParam;
	}
}
