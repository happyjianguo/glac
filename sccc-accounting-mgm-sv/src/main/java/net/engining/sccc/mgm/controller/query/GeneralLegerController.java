package net.engining.sccc.mgm.controller.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.GeneralQueryReq;
import net.engining.sccc.biz.service.GeneralLegerService;
@Api(value="GeneralLegerController",description="总账查询")
@RequestMapping("/general")
@RestController
public class GeneralLegerController {
	
	
	@Autowired GeneralLegerService generalLegerService;
	
	@PreAuthorize("hasAuthority('legerQueryOnTheDay')")
	@ApiOperation(value="当日总账查询", notes="")
	@RequestMapping(value="/legerQueryOnTheDay", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> generalLegerQueryOnTheDay(@RequestBody GeneralQueryReq subjectDetail) {
		FetchResponse<Map<String,Object>> generalLegerQuery = generalLegerService.generalLegerQueryOntheDay(subjectDetail.getSubjectLevel(),subjectDetail.getInOutFlag(),subjectDetail.getRange());
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(generalLegerQuery);
	}

	
	@PreAuthorize("hasAuthority('historicalLegerQuery')")
	@ApiOperation(value="历史总账查询", notes="")
	@RequestMapping(value="/historicalLegerQuery", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> historicalGeneralLegerQuery(@RequestBody GeneralQueryReq subjectDetail) {
		FetchResponse<Map<String,Object>> generalLegerQuery = generalLegerService.historicalGeneralLegerQuery(subjectDetail.getSubjectLevel(),subjectDetail.getBeginDate(),
				subjectDetail.getEndDate(),subjectDetail.getInOutFlag(),subjectDetail.getRange());
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(generalLegerQuery);
	}
}
