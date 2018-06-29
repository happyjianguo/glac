package net.engining.sccc.mgm.controller.query;

import java.util.List;
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
import net.engining.sccc.biz.bean.FetchDataProcess;
import net.engining.sccc.biz.bean.HistoricalSubjectDetail;
import net.engining.sccc.biz.bean.SubjectDetailForm;
import net.engining.sccc.biz.service.SubjectDetailService;
import net.engining.sccc.mgm.bean.query.service.DataProcessService;
import net.engining.sccc.mgm.bean.query.service.HistoricalSubjectDetailService;
@Api(value="SubjectDetailInquiryController",description="科目明细表查询")
@RequestMapping("/subject")
@RestController
public class SubjectDetailInquiryController {
	@Autowired
	private SubjectDetailService subjectDetailService;
	@Autowired
	private HistoricalSubjectDetailService historicalSubjectDetailService;
	@Autowired DataProcessService<HistoricalSubjectDetail> dataProcessService;
	
	@PreAuthorize("hasAuthority('subjectListInquiryOnTheDay')")
	@ApiOperation(value="当日科目明细表查询", notes="")
	@RequestMapping(value="/subjectListInquiryOnTheDay", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> quiryOnTheDay(@RequestBody SubjectDetailForm subjectDetail) {
		FetchResponse<Map<String, Object>> subjectListOnTheDay =  subjectDetailService.getSubjectListOnTheDay(subjectDetail.getSubjectNo(),subjectDetail.getInOutFlag(),subjectDetail.getRange());
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(subjectListOnTheDay);
	}
	
	@PreAuthorize("hasAuthority('subjectListInquiryDetailOnTheDay')")
	@ApiOperation(value="当日科目明细表查询详情", notes="")
	@RequestMapping(value="/subjectListInquiryDetailOnTheDay", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> quiryDetailOnTheDay(@RequestBody SubjectDetailForm subjectDetail) {
		FetchResponse<Map<String, Object>> subjectListDetailOnTheDay = subjectDetailService.getSubjectListDetailOnTheDay(subjectDetail.getSubjectNo());
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(subjectListDetailOnTheDay);
	}
	
	@PreAuthorize("hasAuthority('historicalSubjectListInquiry')")
	@ApiOperation(value="历史科目明细表查询", notes="")
	@RequestMapping(value="/historicalSubjectListInquiry", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> historicalListquiry(@RequestBody SubjectDetailForm subjectDetail) {
		FetchResponse<Map<String,Object>> historicalSubjectList;
		try {
			historicalSubjectList = subjectDetailService.getHistoricalSubjectList(subjectDetail.getBeginDate(),subjectDetail.getEndDate(),
					subjectDetail.getSubjectNo(),subjectDetail.getInOutFlag(),subjectDetail.getRange());
			} catch (Exception e) {
				historicalSubjectList=new FetchResponse<Map<String,Object>>();
				historicalSubjectList.setData(null);
		}
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(historicalSubjectList);
	}
	
	@PreAuthorize("hasAuthority('historicalSubjectListDetailInquiry')")
	@ApiOperation(value="历史科目明细表查询详情", notes="")
	@RequestMapping(value="/historicalSubjectListDetailInquiry", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<HistoricalSubjectDetail>> historicalListDetailquiry(@RequestBody SubjectDetailForm subjectDetail) {
		FetchResponse<HistoricalSubjectDetail> res;
		try {
			List<HistoricalSubjectDetail> historicalSubjectListDetail = historicalSubjectDetailService.getHistoricalSubjectListDetail(subjectDetail);
			
			List<HistoricalSubjectDetail> txnSubjectQuery = subjectDetailService.getTxnSubjectQuery(historicalSubjectListDetail);
	//		FetchDataProcess<HistoricalSubjectDetail> dataProgress = dataProcessService.getDataProgress(subjectDetail.getRange(), txnSubjectQuery);
			res = new FetchResponse<HistoricalSubjectDetail>();
			res.setData(txnSubjectQuery);
		} catch (Exception e) {
			res = new FetchResponse<HistoricalSubjectDetail>();
			res.setData(null);
		}
		return new WebCommonResponseBuilder<FetchResponse<HistoricalSubjectDetail>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(res);
	}
	
	@PreAuthorize("hasAuthority('subjectListInquiryOnTheDay')")
	@ApiOperation(value="当日根据科目层级查询科目号", notes="")
	@RequestMapping(value="/querySubjectNoBySubjectLevel", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<List<Map<String, Object>>> querySubjectNo(@RequestBody SubjectDetailForm subjectDetail) {
		List<Map<String, Object>> querySubjectNo = subjectDetailService.querySubjectNo(subjectDetail.getSubjectLevel());
		return new WebCommonResponseBuilder<List<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(querySubjectNo);
	}
	
	@PreAuthorize("hasAuthority('historicalSubjectListInquiry')")
	@ApiOperation(value="历史根据科目层级查询科目号", notes="")
	@RequestMapping(value="/querySubjectNoByHistory", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> queryHistoricalSubjectNo(@RequestBody SubjectDetailForm subjectDetail) {
		FetchResponse<Map<String, Object>> querySubjectNo = subjectDetailService.queryHistoricalSubjectNo(subjectDetail.getSubjectLevel());
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(querySubjectNo);
	}
}
