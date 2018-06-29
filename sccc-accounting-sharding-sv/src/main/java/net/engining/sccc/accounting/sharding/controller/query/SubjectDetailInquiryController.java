package net.engining.sccc.accounting.sharding.controller.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;

import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.HistoricalSubjectDetail;
import net.engining.sccc.biz.bean.SubjectDetailForm;
import net.engining.sccc.biz.service.SubjectDetailService;

@RequestMapping("/subject")
@RestController
public class SubjectDetailInquiryController {
	@Autowired
	private SubjectDetailService subjectDetailService;
	
	
	@RequestMapping(value="/historicalSubjectListDetailInquiry", method=RequestMethod.POST)
	public @ResponseBody List<HistoricalSubjectDetail> historicalListDetailquiry(@RequestBody SubjectDetailForm subjectDetail) {
//		historicalSubjectDetailService.getHistoricalSubjectListDetail(subjectDetail);
		List<HistoricalSubjectDetail> historicalSubjectListDetail = subjectDetailService.getHistoricalSubjectListDetail(subjectDetail.getSubjectNo(),
				subjectDetail.getBeginDate(),subjectDetail.getEndDate());
		return historicalSubjectListDetail;
	}
}
