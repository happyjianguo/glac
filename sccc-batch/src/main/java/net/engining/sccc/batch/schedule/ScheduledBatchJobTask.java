package net.engining.sccc.batch.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.NoSuchJobInstanceException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import net.engining.gm.facility.SystemStatusFacility;
import net.engining.gm.infrastructure.enums.SystemStatusType;
import net.engining.sccc.batch.service.BatchMgmService;
import net.engining.sccc.batch.service.CheckList4EndOfDayJobService;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 按指定时间执行的BatchJob；
 * 轮询启动的服务，只能单服务器执行；FIXME 暂时不可双活
 * 
 * @author luxue
 *
 */
@Profile("main-master")
@Component
public class ScheduledBatchJobTask {

	private static final Logger log = LoggerFactory.getLogger(ScheduledBatchJobTask.class);
	
	@Autowired
	ThreadPoolTaskScheduler taskScheduler;

	@Autowired
	BatchMgmService batchMgmService;
	
	@Autowired
	BatchTaskProperties taskProperties;
	
	@Autowired
	CheckList4EndOfDayJobService checkList4EndOfDayJobService;
	
	@Autowired
	SystemStatusFacility systemStatusFacility;

	/**
	 * 按指定时间执行日终批量
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
//	@Scheduled(cron = "${sccc.batch.end-of-day-schedule}")
	public void timingExecutedEndOfDayBatchJob()
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {
		
		log.info("按指定时间[cron:{}, now={}]开始检查是否执行End of Day Batch Job", taskProperties.getEndOfDaySchedule(), new Date());
		
		SystemStatusType sysStatusType= systemStatusFacility.getSystemStatus().systemStatus;
		//非批量状态
		if(sysStatusType.equals(SystemStatusType.N)){
			// Check List 检查是否可执行
			boolean canTriger = checkList4EndOfDayJobService.checkOk();
			if(canTriger){
				log.info("批前检查项通过，开始于{}，执行End of Day Batch Job", new Date());
				batchMgmService.startEndOfDayJob(1);
			}
			else {
				log.info("由于批前检查项未通过，未能执行End of Day Batch Job; 60秒后将再次进行日终批前检查");
			}
		}
		else {
			log.info("当前系统正处于日终批量状态中，当日不再执行End of Day Batch Job");
			taskScheduler.shutdown();
		}
		
	}
	
	/**
	 * 按指定时间间隔执行日终前预处理批量
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
//	@Scheduled(fixedDelayString = "${sccc.batch.prev4-eODSchedule}")
	public void timingExecutedPrevEODBatchJob()
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {

		log.info("按指定时间[fixed delay:{}, now={}]开始检查是否执行Prev-process for EOD Job", taskProperties.getPrev4EODSchedule(),
				new Date());
		SystemStatusType sysStatusType = systemStatusFacility.getSystemStatus().systemStatus;
		// 非批量状态
		if (sysStatusType.equals(SystemStatusType.N)) {
			log.info("当前系统正处于非日终批量状态，开始于{}，执行Prev-process for EOD Job", new Date());
			batchMgmService.startPrev4EODJob(1);

		} else {
			log.info("当前系统正处于日终批量状态中，不可执行Prev-process for EOD Job");
			taskScheduler.shutdown();
		}
	}

}
