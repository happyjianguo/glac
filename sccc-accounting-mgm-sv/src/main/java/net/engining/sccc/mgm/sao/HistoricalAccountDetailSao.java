package net.engining.sccc.mgm.sao;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.AccountDetailRes;
import net.engining.sccc.biz.bean.HistoricalCondition;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.mgm.sao.imp.HistoricalAccountDetailSaoImp;
@FeignClient(value = "sccc-accounting-sharding-sv", fallback = HistoricalAccountDetailSaoImp.class)
public interface HistoricalAccountDetailSao {
	@RequestMapping(value ="/query/historicalAccountDetailedInquiry", method = RequestMethod.POST)
	FetchResponse<AccountDetailRes> doGetSecond(@RequestBody HistoricalCondition firstPart);

	@RequestMapping(value ="/query/historicalAccountDetailes", method = RequestMethod.POST)
	FetchResponse<Map<String, Object>> doAccountDetails(@RequestBody HistoricalaccountingRecordReq account);

}
