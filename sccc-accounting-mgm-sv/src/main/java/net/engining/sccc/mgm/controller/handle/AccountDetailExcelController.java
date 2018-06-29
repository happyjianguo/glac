package net.engining.sccc.mgm.controller.handle;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.AccountDetailRes;
import net.engining.sccc.biz.bean.AccountingRecordReq;
import net.engining.sccc.biz.bean.HistoricalCondition;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.biz.service.AccountDetailService;
import net.engining.sccc.mgm.bean.query.service.AccountDetailExcelService;
import net.engining.sccc.mgm.bean.query.service.HistoricalAccountDetailService;

@Api(value="记账明细查询导出")
@RequestMapping("/query")
@RestController
public class AccountDetailExcelController {
	@Autowired AccountDetailService accountService;
	@Autowired HistoricalAccountDetailService historicalService;
	@Autowired AccountDetailExcelService accountExcelService;
	
	@PreAuthorize("hasAuthority('accountDetailedInquiry')")
	@ApiOperation(value="当日记账明细查询", notes="")
	@RequestMapping(value="/accountDetailedInquiryExcel", method=RequestMethod.POST)
	public void accountDetailedInquiryExcel(@RequestBody AccountingRecordReq account,HttpServletResponse response){
		FetchResponse<AccountDetailRes> accountInquiry = null;
		if(StringUtils.isNotBlank(account.getCustId())||StringUtils.isNotBlank(account.getIouNo())){
			accountInquiry = accountService.accountDetailedInquiry(account.getCustId(), account.getIouNo(), 
					account.getRange(), account.getPostSeq(), account.getTxnSeq(),account.getType());
		}else{
			accountInquiry = accountService.getDetailByNo(account);
		}
		accountExcelService.excelFile(accountInquiry.getData(), response);
	}
	
	@PreAuthorize("hasAuthority('historicalAccountDetailedInquiry')")
	@ApiOperation(value="历史记账明细查询", notes="")
	@RequestMapping(value="/historicalAccountDetailedInquiryExcel", method=RequestMethod.POST)
	public void historicalAccountDetailedInquiryExcel(@RequestBody HistoricalaccountingRecordReq account,HttpServletResponse response){
		HistoricalCondition firstPart = null;
		FetchResponse<AccountDetailRes> second=null;
		if(StringUtils.isNotBlank(account.getCustId())||StringUtils.isNotBlank(account.getIouNo())){
			firstPart = accountService.getFirstPart(account.getCustId(), account.getIouNo(),account.getEndDate(),
					account.getPostSeq(),account.getTxnSeq(),account.getRange(),account.getType());
		}else{
			firstPart = accountService.getThirdPart(account);
		}
		second = historicalService.getSecond(firstPart);
		accountExcelService.excelFile(second.getData(), response);
	}
	
}
