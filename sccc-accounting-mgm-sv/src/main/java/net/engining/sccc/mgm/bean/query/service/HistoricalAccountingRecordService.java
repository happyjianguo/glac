package net.engining.sccc.mgm.bean.query.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.AccountingRecordDetailsRes;
import net.engining.sccc.biz.bean.HistoricalAccountRecord;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.mgm.sao.AccountingRecordSao;

@Service
public class HistoricalAccountingRecordService {

	@Autowired
	private AccountingRecordSao accountingRecordSao;
	
	public FetchResponse<HistoricalAccountRecord> getHisroticAccountingRecord(HistoricalaccountingRecordReq accounting){
		return accountingRecordSao.doHisroticAccountingRecord(accounting);
	}
	
	
	public AccountingRecordDetailsRes getHisroticAccountingRecordDetails(AccountingRecordDetailsRes accounting){
		return accountingRecordSao.doHisroticAccountingRecordDetails(accounting);
	}
}
