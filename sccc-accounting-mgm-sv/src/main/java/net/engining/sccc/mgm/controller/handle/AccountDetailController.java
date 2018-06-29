package net.engining.sccc.mgm.controller.handle;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.AccountDetailRes;
import net.engining.sccc.biz.bean.AccountRecord;
import net.engining.sccc.biz.bean.AccountingRecordReq;
import net.engining.sccc.biz.bean.HistoricalCondition;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.biz.service.AccountDetailService;
import net.engining.sccc.mgm.bean.query.service.HistoricalAccountDetailService;

@Api(value="AccountController",description="记账明细查询")
@RequestMapping("/query")
@RestController
public class AccountDetailController {
	@Autowired AccountDetailService accountService;
	@Autowired HistoricalAccountDetailService historicalService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@PreAuthorize("hasAuthority('accountDetailedInquiry')")
	@ApiOperation(value="当日记账明细查询", notes="")
	@RequestMapping(value="/accountDetailedInquiry", method=RequestMethod.POST)
	public  WebCommonResponse<FetchResponse<AccountDetailRes>> accountDetailedInquiry(@RequestBody AccountingRecordReq account){
		FetchResponse<AccountDetailRes> accountInquiry = null;
		if(StringUtils.isNotBlank(account.getCustId())||StringUtils.isNotBlank(account.getIouNo())){
			accountInquiry = accountService.accountDetailedInquiry(account.getCustId(), account.getIouNo(), 
					account.getRange(), account.getPostSeq(), account.getTxnSeq(),account.getType());
		}else{
			accountInquiry = accountService.getDetailByNo(account);
		}
		/*FetchResponse<AccountDetailRes> accountInquiry = accountService.accountDetailedInquiry(account.getCustId(), account.getIouNo(), 
				account.getRange(), account.getPostSeq(), account.getTxnSeq(),account.getType());*/
		return new WebCommonResponseBuilder<FetchResponse<AccountDetailRes>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(accountInquiry);
	}
	
	@PreAuthorize("hasAuthority('accountDetailedInquiryList')")
	@ApiOperation(value="当日记账明细查询详情", notes="")
	@RequestMapping(value="/accountDetailedInquiryList", method=RequestMethod.POST)
	public WebCommonResponse<FetchResponse<AccountRecord>> accountDetailes(@RequestBody AccountingRecordReq account){
		List<AccountRecord> queryDetails = accountService.queryDetails(account.getPostSeq());
		FetchResponse<AccountRecord> res = new FetchResponse<AccountRecord>();
		res.setData(queryDetails);
		return new WebCommonResponseBuilder<FetchResponse<AccountRecord>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(res);
	}
	
	@PreAuthorize("hasAuthority('historicalAccountDetailedInquiry')")
	@ApiOperation(value="历史记账明细查询", notes="")
	@RequestMapping(value="/historicalAccountDetailedInquiry", method=RequestMethod.POST)
	public  WebCommonResponse<FetchResponse<AccountDetailRes>> historicalAccountDetailedInquiry(@RequestBody HistoricalaccountingRecordReq account){
		HistoricalCondition firstPart = null;
		FetchResponse<AccountDetailRes> second=null;
		try {
			if(StringUtils.isNotBlank(account.getCustId())||StringUtils.isNotBlank(account.getIouNo())){
				firstPart = accountService.getFirstPart(account.getCustId(), account.getIouNo(),account.getEndDate(),
						account.getPostSeq(),account.getTxnSeq(),account.getRange(),account.getType());
			}else{
				firstPart = accountService.getThirdPart(account);
			}
			second = historicalService.getSecond(firstPart);
			} catch (Exception e) {
			logger.info("出现异常"+e);
			second = new FetchResponse<AccountDetailRes>();
		}
		return new WebCommonResponseBuilder<FetchResponse<AccountDetailRes>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(second);
	}
	
	@PreAuthorize("hasAuthority('historicalAccountDetailedInquiryList')")
	@ApiOperation(value="历史记账明细查询详情", notes="")
	@RequestMapping(value="/historicalAccountDetailedInquiryList", method=RequestMethod.POST)
	public WebCommonResponse<FetchResponse<Map<String, Object>>> historicalAccountDetailes(@RequestBody HistoricalaccountingRecordReq account){
		FetchResponse<Map<String, Object>> details = historicalService.historicalAccountDetailes(account);
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(details);
	}
}
