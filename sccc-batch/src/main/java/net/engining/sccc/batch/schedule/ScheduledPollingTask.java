package net.engining.sccc.batch.schedule;

import java.io.IOException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import net.engining.gm.facility.SystemStatusFacility;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.batch.service.BatchMgmService;
import net.engining.sccc.batch.service.PollingTaskService;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 按指定间隔轮询执行的Task； 轮询启动的服务，只能单服务器执行；FIXME 暂时不可双活
 * 
 * @author luxue
 *
 */
@Profile("main-master")
@Component
public class ScheduledPollingTask {

	private static final Logger log = LoggerFactory.getLogger(ScheduledPollingTask.class);

	@Autowired
	ThreadPoolTaskScheduler taskScheduler;

	@Autowired
	BatchMgmService batchMgmService;

	@Autowired
	BatchTaskProperties taskProperties;

	@Autowired
	PollingTaskService pollingTaskService;

	@Autowired
	SystemStatusFacility systemStatusFacility;

	
	@Scheduled(fixedDelayString = "${sccc.batch.generate-report-file-schedule}")
	public void timingExecuted4GenerateReportFiles() {
		log.info("按指定时间[fixed delay:{}, now={}]开始执行Generate Report Files",
				taskProperties.getGenerateReportFileSchedule(), new Date());
		// TODO 查询轮循任务表 状态为待处理的数据
		try {
			pollingTaskService.createReport();
		} catch (IOException e) {
			throw new ErrorMessageException(ErrorCode.CheckError,"导出文件失败");
		}finally{
			try {
				if(pollingTaskService.getFos() != null){
					pollingTaskService.getFos().close();
				}
				if(pollingTaskService.getWorkbook() != null){
					pollingTaskService.getWorkbook().close();
				}
			} catch (IOException e) {
				throw new ErrorMessageException(ErrorCode.CheckError,"关闭文件流失败");
			}
			
		}
		
		
	}

	
}
