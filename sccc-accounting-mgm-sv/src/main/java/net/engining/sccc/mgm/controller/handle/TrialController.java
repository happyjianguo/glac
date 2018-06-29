package net.engining.sccc.mgm.controller.handle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.gm.facility.SystemStatusFacility;
import net.engining.gm.param.model.SystemStatus;
import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummaryHst;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.DetailCheck;
import net.engining.sccc.biz.bean.SummaryBySubject;
import net.engining.sccc.biz.bean.TotalProrateCheckRes;
import net.engining.sccc.biz.bean.TrialBalanceRes;
import net.engining.sccc.biz.bean.TrialReqBean;
import net.engining.sccc.biz.service.LedgerService;
import net.engining.sccc.biz.service.TrialService;
import net.engining.sccc.mgm.bean.query.service.DetailCheckService;
import net.engining.sccc.mgm.bean.query.service.TotalAccountService;

@Api(value = "试算平衡")
@Validated
@RequestMapping("/trial")
@RestController
public class TrialController {
	@Autowired
	TrialService trialService;
	@Autowired
	DetailCheckService detailCheckService;
	@Autowired
	SystemStatusFacility systemFacility;
	@Autowired
	TotalAccountService totalAccountService;
	@Autowired
	LedgerService ledgerService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@PreAuthorize("hasAuthority('trialBanlance')")
	@ApiOperation(value = "试算平衡", notes = "")
	@RequestMapping(value = "/trialBanlance/{endDate}", method = RequestMethod.POST)
	public WebCommonResponse<List<Map<String, TrialBalanceRes>>> trialBanlance(@PathVariable String endDate)
			throws ParseException {
		Date processDate;
		if ("2018".equals(endDate)) {
			SystemStatus systemStatus = systemFacility.getSystemStatus();
			processDate = new Date(systemStatus.businessDate.getTime() - 1000 * 60 * 60 * 24);
		} else {
			processDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate + "-12-31");
		}
		List<Map<String, TrialBalanceRes>> trialBanlance = trialService.trialBanlance(processDate);
		List<Map<String, TrialBalanceRes>> countTotal = trialService.countTotal(trialBanlance);
		return new WebCommonResponseBuilder<List<Map<String, TrialBalanceRes>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(countTotal);
	}

	@PreAuthorize("hasAuthority('totalScoreCheck')")
	@ApiOperation(value = "总分核对", notes = "")
	@RequestMapping(value = "/totalScoreCheck", method = RequestMethod.POST)
	public WebCommonResponse<TotalProrateCheckRes> totalScoreCheck(@RequestBody TrialReqBean trial) {
		TotalProrateCheckRes scoreCheck;
		try {
//			SummaryBySubject item = totalAccountService.totalAccount(trial);
//			ApSubjectSummaryHst process = ledgerService.process(item, trial.getEndDate());
//			scoreCheck = trialService.totalScoreCheck(trial.getEndDate(), trial.getSubjectNo(), process);
			scoreCheck = trialService.totalScoreCheck(trial.getEndDate(), trial.getSubjectNo());
		} catch (Exception e) {
			logger.info("出现异常" + e);
			scoreCheck = null;
		}
		return new WebCommonResponseBuilder<TotalProrateCheckRes>().build().setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(scoreCheck);
	}

	@PreAuthorize("hasAuthority('detailCheck')")
	@ApiOperation(value = "明细核对", notes = "")
	@RequestMapping(value = "/detailCheck", method = RequestMethod.POST)
	public WebCommonResponse<FetchResponse<DetailCheck>> detailCheck(@RequestBody TrialReqBean trial) {
		FetchResponse<DetailCheck> detailCheck=new FetchResponse<DetailCheck>();;
		List<DetailCheck> check;
		try {
			detailCheck = trialService.detailCheck(trial.getEndDate(), trial.getRange());
			check = detailCheckService.getDetailCheck(detailCheck.getData());
		} catch (Exception e) {
			logger.info("出现异常" + e);
			check = new ArrayList();
		}
		detailCheck.setData(check);
		return new WebCommonResponseBuilder<FetchResponse<DetailCheck>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(detailCheck);
	}

	@PreAuthorize("hasAuthority('trialBanlance')")
	@ApiOperation(value = "获取业务日期", notes = "")
	@RequestMapping(value = "/getSystemDate", method = RequestMethod.POST)
	public WebCommonResponse<SystemStatus> getSystemDate() {
		SystemStatus systemStatus = systemFacility.getSystemStatus();
		return new WebCommonResponseBuilder<SystemStatus>().build().setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(systemStatus);
	}
}
