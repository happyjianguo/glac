package net.engining.sccc.mgm.controller.param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.SubjectInsert;
import net.engining.sccc.biz.bean.SubjectRecordReq;
import net.engining.sccc.biz.bean.SubjectRecordRes;
import net.engining.sccc.biz.service.SubjectService;

@Api(value="会计科目查询")
@RequestMapping("/subject")
@RestController
public class SubjectController {
	
	@Autowired SubjectService subjectService;
	
	@PreAuthorize("hasAuthority('subjectRecord')")
	@ApiOperation(value="会计科目查询", notes="")
	@RequestMapping(value="/subjectRecord", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Subject>> subjectRecord(@RequestBody SubjectRecordReq req) {
		FetchResponse<Subject> subjectList = subjectService.subjectRecord(req.getSubjectCd(), req.getType(), req.getStatus(),req.getRange());
		
		return new WebCommonResponseBuilder<FetchResponse<Subject>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(subjectList);
	}
	
	@PreAuthorize("hasAuthority('subjectRecordInquiryDetails')")
	@ApiOperation(value="会计科目查询详情", notes="")
	@RequestMapping(value="/subjectRecordInquiryDetails/{subjectCd}", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<SubjectRecordRes> subjectRecordInquiryDetails(@PathVariable String subjectCd) {
		
		SubjectRecordRes srr = subjectService.subjectRecordInquiryDetails(subjectCd);
		return new WebCommonResponseBuilder<SubjectRecordRes>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(srr);
	}
	
	@PreAuthorize("hasAuthority('subjectRecordInsert')")
	@ApiOperation(value="会计科目添加", notes="")
	@RequestMapping(value="/subjectRecordInsert", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> subjectRecordInsert(@RequestBody SubjectInsert subjectInsert) {
		
		subjectService.subjectRecordInsert(subjectInsert);
		
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
}
