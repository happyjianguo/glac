package net.engining.sccc.batch.master.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import net.engining.pg.web.BaseResponseBean;
import net.engining.pg.web.WebCommonResponse;
import net.engining.sccc.batch.service.BatchMgmService;
import net.engining.sccc.batch.service.BatchStepMQProducer;
import net.engining.sccc.biz.bean.TransferDataByKey;

@RequestMapping("/batchMgm")
@RestController
public class BatchMgmController {

	@Autowired
	BatchMgmService batchMgmService;
	
	@Autowired
	BatchStepMQProducer batchStepMQProducer;

	@ApiOperation(value = "启动日终批量任务", notes = "")
	@RequestMapping(value = "/startEndOfDayJob/{count}", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean startEndOfDayJob(@PathVariable int count)
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {

		batchMgmService.startEndOfDayJob(count);
		
		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}
	
	@ApiOperation(value = "重新启动最近一次失败的日终批量任务", notes = "")
	@RequestMapping(value = "/restartEndOfDayJob", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean restartEndOfDayJob()
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {

		batchMgmService.restartEndOfDayJob();
		
		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}
	
	@ApiOperation(value = "启动日终批量预处理任务", notes = "")
	@RequestMapping(value = "/startPrev4EODJob/{count}", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean startPrev4EODJob(@PathVariable int count)
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {

		batchMgmService.startPrev4EODJob(count);
		
		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}
	
	@ApiOperation(value = "重新启动最近一次失败的日终批量预处理任务", notes = "")
	@RequestMapping(value = "/restartPrev4EODJob", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean restartPrev4EODJob()
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {

		batchMgmService.restartPrev4EODJob();
		
		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}
	
	@ApiOperation(value = "启动数据迁移批量任务", notes = "")
	@RequestMapping(value = "/startDataMigrationJob/{count}", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean startDataMigrationJob(@PathVariable int count)
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {

		batchMgmService.startDataMigrationJob(count);
		
		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}
	
	@ApiOperation(value = "重新启动最近一次失败的数据迁移批量任务", notes = "")
	@RequestMapping(value = "/restartDataMigrationJob", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean restartDataMigrationJob()
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {

		batchMgmService.restartDataMigrationJob();
		
		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}
	
	@ApiOperation(value = "放弃正在执行的数据迁移批量任务", notes = "")
	@RequestMapping(value = "/abandonDataMigrationJob", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean abandonDataMigrationJob()
			throws NoSuchJobException, NoSuchJobInstanceException, JobInstanceAlreadyCompleteException,
			NoSuchJobExecutionException, JobRestartException, JobParametersInvalidException,
			JobParametersNotFoundException, JobExecutionAlreadyRunningException, UnexpectedJobExecutionException {

		batchMgmService.abandonDataMigrationJob();
		
		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}
	
	@ApiOperation(value = "测试step9999，发送消息到mq", notes = "")
	@RequestMapping(value = "/send2Step9999Queue", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean send2Step9999Queue(){
		TransferDataByKey bean = new TransferDataByKey();

		batchStepMQProducer.senderMsg4Step9999(bean);
		
		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}

}