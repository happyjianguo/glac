package net.engining.sccc.accounting.sharding.controller.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.AccountDetailRes;
import net.engining.sccc.biz.bean.HistoricalCondition;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.biz.service.AccountDetailService;

@RequestMapping("/query")
@RestController
public class AccountDetailController {
	@Autowired AccountDetailService accountService;
	
	@RequestMapping(value="/historicalAccountDetailedInquiry", method=RequestMethod.POST)
	public FetchResponse<AccountDetailRes> getSecond(@RequestBody HistoricalCondition firstPart){
		FetchResponse<AccountDetailRes> second = accountService.getSecond(firstPart);
		return second;
	}
	
	@RequestMapping(value="/historicalAccountDetailes", method=RequestMethod.POST)
	public FetchResponse<Map<String, Object>> getAccountDetails(@RequestBody HistoricalaccountingRecordReq account){
		FetchResponse<Map<String, Object>> accountDetails = accountService.getAccountDetails(account.getEndDate(),account.getPostSeq());
		return accountDetails;
	}
}
