package net.engining.sccc.mgm.sao.imp;

import java.util.Map;

import org.springframework.stereotype.Component;

import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.AccountDetailRes;
import net.engining.sccc.biz.bean.HistoricalCondition;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.mgm.sao.HistoricalAccountDetailSao;
@Component
public class HistoricalAccountDetailSaoImp implements HistoricalAccountDetailSao{

	@Override
	public FetchResponse<AccountDetailRes> doGetSecond(HistoricalCondition firstPart) {
		throw new ErrorMessageException(ErrorCode.SystemError, "系统繁忙，请稍后再试");
	}

	@Override
	public FetchResponse<Map<String, Object>> doAccountDetails(HistoricalaccountingRecordReq account) {
		throw new ErrorMessageException(ErrorCode.SystemError, "系统繁忙，请稍后再试");
	}

}
