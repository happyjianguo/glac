package net.engining.sccc.mgm.controller.query;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.HistoricalSubjectDetail;
import net.engining.sccc.biz.bean.SubjectDetailForm;
import net.engining.sccc.biz.service.SubjectDetailService;
import net.engining.sccc.mgm.bean.query.service.HistoricalSubjectDetailExcelService;
import net.engining.sccc.mgm.bean.query.service.HistoricalSubjectDetailService;
import net.engining.sccc.mgm.bean.query.service.SubjectDetailDayExcelService;
import net.engining.sccc.mgm.bean.query.service.SubjectExcelOnTheDayService;
@Api(value="SubjectExcelController",description="科目明细表查询EXCEL导出")
@RequestMapping("/sujectExport")
@RestController
public class SubjectExcelController {
	@Autowired
	private SubjectDetailService subjectDetailService;
	@Autowired SubjectExcelOnTheDayService subjectExcelOnTheDayService;
	@Autowired SubjectDetailDayExcelService historicalSubjectExcelService;
	@Autowired
	private HistoricalSubjectDetailService historicalSubjectDetailService;
	@Autowired
	private HistoricalSubjectDetailExcelService historicalSubjectDetailExcelService;
	
	@PreAuthorize("hasAuthority('subjectListInquiryDetailOnTheDay')")
	@ApiOperation(value="当日科目明细表查询", notes="")
	@RequestMapping(value="/excelSubjectListOnTheDay", method=RequestMethod.POST)
	public void excelSubjectList(@RequestBody SubjectDetailForm subjectDetail,HttpServletResponse response){
		FetchResponse<Map<String, Object>> subjectListOnTheDay =  subjectDetailService.getSubjectListOnTheDay(subjectDetail.getSubjectNo(),subjectDetail.getInOutFlag(),subjectDetail.getRange());
		subjectExcelOnTheDayService.excelFile(subjectListOnTheDay.getData(), response);
	}
	
	@PreAuthorize("hasAuthority('subjectListInquiryDetailOnTheDay')")
	@ApiOperation(value="当日科目明细表查询详情", notes="")
	@RequestMapping(value="/excelSubjectListDetailOnTheDay", method=RequestMethod.POST)
	public void excelSubjectDetailList(@RequestBody SubjectDetailForm subjectDetail,HttpServletResponse response){
		FetchResponse<Map<String, Object>> subjectListDetailOnTheDay = subjectDetailService.getSubjectListDetailOnTheDay(subjectDetail.getSubjectNo());
		historicalSubjectExcelService.excelFile(subjectListDetailOnTheDay.getData(), response);
	}
	
	@PreAuthorize("hasAuthority('historicalSubjectListInquiry')")
	@ApiOperation(value="历史科目明细表查询", notes="")
	@RequestMapping(value="/excelHistoricalSubjectList", method=RequestMethod.POST)
	public void excelHistoricalSubjectList(@RequestBody SubjectDetailForm subjectDetail,HttpServletResponse response){
		FetchResponse<Map<String,Object>> historicalSubjectList = subjectDetailService.getHistoricalSubjectList(subjectDetail.getBeginDate(),subjectDetail.getEndDate(),
				subjectDetail.getSubjectNo(),subjectDetail.getInOutFlag(),subjectDetail.getRange());
		subjectExcelOnTheDayService.excelFile(historicalSubjectList.getData(), response);
	}
	
	@PreAuthorize("hasAuthority('historicalSubjectListDetailInquiry')")
	@ApiOperation(value="历史科目明细表查询详情", notes="")
	@RequestMapping(value="/excelHistoricalSubjectListDetail", method=RequestMethod.POST)
	public void excelHistoricalSubjectListDetail(@RequestBody SubjectDetailForm subjectDetail,HttpServletResponse response){
		List<HistoricalSubjectDetail> historicalSubjectListDetail = historicalSubjectDetailService.getHistoricalSubjectListDetail(subjectDetail);
		
		List<HistoricalSubjectDetail> txnSubjectQuery = subjectDetailService.getTxnSubjectQuery(historicalSubjectListDetail);
		historicalSubjectDetailExcelService.excelFile(txnSubjectQuery, response);
	}
}
