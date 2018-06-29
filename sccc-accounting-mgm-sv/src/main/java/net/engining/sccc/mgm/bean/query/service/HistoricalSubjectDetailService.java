package net.engining.sccc.mgm.bean.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;

import net.engining.sccc.biz.bean.HistoricalSubjectDetail;
import net.engining.sccc.biz.bean.SubjectDetailForm;
import net.engining.sccc.mgm.sao.HistoricalSubjectDetailSao;

@Service
public class HistoricalSubjectDetailService {

	@Autowired 
	private HistoricalSubjectDetailSao historicalSubjectDetailSao;
	
	public List<HistoricalSubjectDetail> getHistoricalSubjectListDetail(SubjectDetailForm subjectDetail) {
		return historicalSubjectDetailSao.doHistoricalSubjectListDetail(subjectDetail);
	}

}
