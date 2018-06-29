package net.engining.sccc.batch.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import net.engining.gm.facility.SystemStatusFacility;

public class BatchJobLoggedListener implements JobExecutionListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Autowired
	private SystemStatusFacility systemStatusFacility;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("{} 的批量开始执行, 于{}",
				DateFormatUtils.format(systemStatusFacility.getSystemStatus().processDate, "yyyy/MM/dd"),
				dateFormat.format(new Date()));

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("{} 的批量执行结束, 于{}",
					DateFormatUtils.format(systemStatusFacility.getSystemStatus().processDate, "yyyy/MM/dd"),
					dateFormat.format(new Date()));
		} else {
			logger.info("{} 的批量未成功执行结束, 批量状态={}, 于{}",
					DateFormatUtils.format(systemStatusFacility.getSystemStatus().processDate, "yyyy/MM/dd"),
					jobExecution.getStatus(), dateFormat.format(new Date()));
		}
	}
}
