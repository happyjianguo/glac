package net.engining.sccc.mgm.controller.query;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.AccountingRecordDetailsReq;
import net.engining.sccc.biz.bean.AccountingRecordDetailsRes;
import net.engining.sccc.biz.bean.AccountingRecordReq;
import net.engining.sccc.biz.bean.AcctIouNo;
import net.engining.sccc.biz.bean.HistoricalAccountRecord;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.biz.service.AccountingRecordService;
import net.engining.sccc.mgm.bean.query.service.DataProcessService;
import net.engining.sccc.mgm.bean.query.service.HistoricalAccountingRecordService;
@Api(value="CatalogQueryController",description="会计分录查询")
@RequestMapping("/account")
@RestController
public class CatalogQueryController {

	@Autowired AccountingRecordService accountingRecordService;
	@Autowired HistoricalAccountingRecordService historicalAccountingRecordService;
	@Autowired DataProcessService<HistoricalAccountRecord> dataProcessService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@PreAuthorize("hasAuthority('accountingRecordInquiryOntheDay')")
	@ApiOperation(value="当日会计分录查询", notes="")
	@RequestMapping(value="/accountingRecordInquiryOntheDay", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<HistoricalAccountRecord>> accountingRecord(@RequestBody AccountingRecordReq accounting) {
		FetchResponse<HistoricalAccountRecord> fetchResponse = accountingRecordService.getAccountingRecord(accounting.getCustId(),accounting.getIouNo(),accounting.getRange());
		return new WebCommonResponseBuilder<FetchResponse<HistoricalAccountRecord>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(fetchResponse);
	}
	
	@PreAuthorize("hasAuthority('accountingRecordInquiryDetailsOntheDay')")
	@ApiOperation(value="当日会计分录查询详情", notes="")
	@RequestMapping(value="/accountingRecordInquiryDetailsOntheDay", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<AccountingRecordDetailsRes> accountingRecordDetails(@RequestBody AccountingRecordDetailsReq accounting) {
		 AccountingRecordDetailsRes getccountingRecordDetails = accountingRecordService.getAccountingRecordDetails(accounting.getAcctSeq(),accounting.getGltSeq(),
				 accounting.getTxnDetailType(),accounting.getTxnSeq());
		return new WebCommonResponseBuilder<AccountingRecordDetailsRes>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(getccountingRecordDetails);
	}
	
	@PreAuthorize("hasAuthority('historicalAccountingRecordInquiry')")
	@ApiOperation(value="历史会计分录查询", notes="")
	@RequestMapping(value="/historicalAccountingRecordInquiry", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<HistoricalAccountRecord>> historicalaccountingRecord(@RequestBody HistoricalaccountingRecordReq accounting) {
		
		List<AcctIouNo> list = accountingRecordService.getHisroticAccountingRecord(accounting.getCustId(),accounting.getIouNo(),accounting.getDateType()
				,accounting.getBeginDate(),accounting.getEndDate());
		accounting.setList(list);
		FetchResponse<HistoricalAccountRecord> txnHstQuery;
		if(null==list||list.size()==0){
			txnHstQuery = new FetchResponse<HistoricalAccountRecord>();
			txnHstQuery.setData(new ArrayList());
		}else{
				try {
					txnHstQuery = historicalAccountingRecordService.getHisroticAccountingRecord(accounting);
			} catch (Exception e) {
				logger.info("出现异常",e);
				txnHstQuery = new FetchResponse<HistoricalAccountRecord>();
				txnHstQuery.setData(new ArrayList());
			}
		}
		return new WebCommonResponseBuilder<FetchResponse<HistoricalAccountRecord>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(txnHstQuery);
	}

	
	@PreAuthorize("hasAuthority('historicalAccountingRecordInquiryDetails')")
	@ApiOperation(value="历史会计分录查询详情", notes="")
	@RequestMapping(value="/historicalAccountingRecordInquiryDetails", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<AccountingRecordDetailsRes> historicalAccountingRecordDetails(@RequestBody AccountingRecordDetailsReq accounting){
		
		AccountingRecordDetailsRes firstPart = accountingRecordService.getFirstPart(accounting.getAcctSeq(),accounting.getGltSeq(),accounting.getTxnDetailType(),accounting.getPostDate());
		AccountingRecordDetailsRes getccountingRecordDetails=historicalAccountingRecordService.getHisroticAccountingRecordDetails(firstPart);
//		AccountingRecordDetailsRes getccountingRecordDetails = accountingRecordService.getHistoricalAcountingRecordDetails(accounting.getAcctSeq(),accounting.getGltSeq(),accounting.getTxnDetailType());
		return new WebCommonResponseBuilder<AccountingRecordDetailsRes>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(getccountingRecordDetails);
	}
}
