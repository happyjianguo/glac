package net.engining.sccc.mgm.controller.handle;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.InsertProfit;
import net.engining.sccc.biz.bean.TrialReqBean;
import net.engining.sccc.biz.service.FinancialService;
import net.engining.sccc.config.props.CommonProperties;

@Api(value="FinancialController",description="损益结转")
@RequestMapping("/financial")
@RestController
public class FinancialController {
	@Autowired FinancialService financialService;
	@Autowired CommonProperties properties;
	
	@PreAuthorize("hasAuthority('profitAndLoss')")
	@ApiOperation(value="损益结转", notes="")
	@RequestMapping(value="/profitAndLoss", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> profitAndLoss(@RequestBody TrialReqBean trial) throws ParseException{
		List<Map<String,Object>> profitAndLoss = financialService.profitAndLoss(trial.getEndDate(),properties.getTranSubjectNo());
		FetchResponse<Map<String, Object>> fetch = new FetchResponse<Map<String, Object>>();
		fetch.setData(profitAndLoss);
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(fetch);
	}
	
	
	@PreAuthorize("hasAuthority('profitAndLoss')")
	@ApiOperation(value="损益结转提交", notes="")
	@RequestMapping(value="/profitAndLossSubmit", method=RequestMethod.POST)
	@Transactional
	public @ResponseBody WebCommonResponse<Void> insertProfit(@RequestBody InsertProfit fetch){
		financialService.insertProfit(fetch.getProfit());
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
}
