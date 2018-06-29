package net.engining.sccc.mgm.bean.query.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.AccountDetailRes;
import net.engining.sccc.biz.bean.HistoricalCondition;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.mgm.sao.HistoricalAccountDetailSao;

@Service
public class HistoricalAccountDetailService {

	@Autowired HistoricalAccountDetailSao historicalAccountDetailSao;

	public FetchResponse<AccountDetailRes> getSecond(HistoricalCondition firstPart) {
		return	historicalAccountDetailSao.doGetSecond(firstPart);
	}

	public FetchResponse<Map<String, Object>> historicalAccountDetailes(HistoricalaccountingRecordReq account) {
		return historicalAccountDetailSao.doAccountDetails(account);
	}
	
	
}
