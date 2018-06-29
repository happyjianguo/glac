package net.engining.sccc.mgm.sao.imp;


import org.springframework.stereotype.Component;

import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.AccountingRecordDetailsRes;
import net.engining.sccc.biz.bean.HistoricalAccountRecord;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.mgm.sao.AccountingRecordSao;
@Component
public class AccountingRecordSaoImp implements AccountingRecordSao{


	@Override
	public AccountingRecordDetailsRes doHisroticAccountingRecordDetails(AccountingRecordDetailsRes accounting) {
		throw new ErrorMessageException(ErrorCode.SystemError, "系统繁忙，请稍后再试");
	}

	@Override
	public FetchResponse<HistoricalAccountRecord> doHisroticAccountingRecord(HistoricalaccountingRecordReq accounting) {
		throw new ErrorMessageException(ErrorCode.SystemError, "系统繁忙，请稍后再试");
	}

}
