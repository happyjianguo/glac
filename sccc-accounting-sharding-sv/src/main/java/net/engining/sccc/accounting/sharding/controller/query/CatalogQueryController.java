package net.engining.sccc.accounting.sharding.controller.query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.AccountingRecordDetailsRes;
import net.engining.sccc.biz.bean.HistoricalAccountRecord;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.biz.service.AccountingRecordService;
@RequestMapping("/account")
@RestController
public class CatalogQueryController {

	@Autowired AccountingRecordService accountingRecordService;
	/**
	 * 历史会计分录
	 * @param accounting
	 * @return
	 */
	@RequestMapping(value="/historicalAccountingRecordInquiry", method=RequestMethod.POST)
	public @ResponseBody FetchResponse<HistoricalAccountRecord> historicalaccountingRecord(@RequestBody HistoricalaccountingRecordReq accounting) {
		FetchResponse<HistoricalAccountRecord> alist=accountingRecordService.txnHstQuery(accounting.getList(),
				accounting.getBeginDate(),accounting.getEndDate(),accounting.getDateType(),accounting.getRange());
		return alist;
	}
	
	/**
	 * 历史会计分录详情
	 * @param accounting
	 * @return
	 */
	@RequestMapping(value="/historicalAccountingRecordInquiryDetails", method=RequestMethod.POST)
	public @ResponseBody AccountingRecordDetailsRes historicalAccountingRecordDetails(@RequestBody AccountingRecordDetailsRes accounting) {
		 AccountingRecordDetailsRes getccountingRecordDetails =  accountingRecordService.getHistoricalAcountingRecordDetails(accounting);
		return getccountingRecordDetails;
	}
}
