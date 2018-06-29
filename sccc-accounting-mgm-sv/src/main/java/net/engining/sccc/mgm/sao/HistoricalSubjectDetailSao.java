package net.engining.sccc.mgm.sao;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.querydsl.core.Tuple;

import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.HistoricalSubjectDetail;
import net.engining.sccc.biz.bean.SubjectDetailForm;
import net.engining.sccc.mgm.sao.imp.AccountingRecordSaoImp;
import net.engining.sccc.mgm.sao.imp.HistoricalSubjectDetailSaoImp;

@FeignClient(value = "sccc-accounting-sharding-sv", fallback = HistoricalSubjectDetailSaoImp.class)
public interface HistoricalSubjectDetailSao {
	@RequestMapping(value ="/subject/historicalSubjectListDetailInquiry", method = RequestMethod.POST)
	public List<HistoricalSubjectDetail> doHistoricalSubjectListDetail(@RequestBody SubjectDetailForm subjectDetail);

}
