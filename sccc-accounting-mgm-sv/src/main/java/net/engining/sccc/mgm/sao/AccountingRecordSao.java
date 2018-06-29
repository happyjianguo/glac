
package net.engining.sccc.mgm.sao;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.AccountingRecordDetailsRes;
import net.engining.sccc.biz.bean.HistoricalAccountRecord;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.mgm.sao.imp.AccountingRecordSaoImp;

@FeignClient(value = "sccc-accounting-sharding-sv", fallback = AccountingRecordSaoImp.class)
public interface AccountingRecordSao {

	@RequestMapping(value = "/account/historicalAccountingRecordInquiry", method = RequestMethod.POST)
	public FetchResponse<HistoricalAccountRecord> doHisroticAccountingRecord(@RequestBody HistoricalaccountingRecordReq accounting);

	
	@RequestMapping(value = "/account/historicalAccountingRecordInquiryDetails", method = RequestMethod.POST)
	public AccountingRecordDetailsRes doHisroticAccountingRecordDetails(@RequestBody AccountingRecordDetailsRes accounting);

}
