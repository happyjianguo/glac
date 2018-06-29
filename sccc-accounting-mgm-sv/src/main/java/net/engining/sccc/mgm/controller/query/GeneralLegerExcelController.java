package net.engining.sccc.mgm.controller.query;

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
import net.engining.sccc.biz.bean.GeneralQueryReq;
import net.engining.sccc.biz.service.GeneralLegerService;
import net.engining.sccc.mgm.bean.query.service.GeneralLegerExcelService;

@Api(value="GeneralLegerExcelController",description="总账查询EXCEL导出")
@RequestMapping("/leger")
@RestController
public class GeneralLegerExcelController {
@Autowired GeneralLegerService generalLegerService;
@Autowired GeneralLegerExcelService generalLegerExcelService;
	
	@PreAuthorize("hasAuthority('legerQueryOnTheDay')")
	@ApiOperation(value="当日总账查询", notes="")
	@RequestMapping(value="/excelLegerQueryOnTheDay", method=RequestMethod.POST)
	public void excelLegerQuery(@RequestBody GeneralQueryReq subjectDetail,HttpServletResponse response){
		FetchResponse<Map<String,Object>> generalLegerQuery = generalLegerService.generalLegerQueryOntheDay(subjectDetail.getSubjectLevel(),subjectDetail.getInOutFlag(),subjectDetail.getRange());
		generalLegerExcelService.excelFile(generalLegerQuery.getData(), response);
	}
	
	@PreAuthorize("hasAuthority('historicalLegerQuery')")
	@ApiOperation(value="历史总账查询", notes="")
	@RequestMapping(value="/historicalExcelLegerQuery", method=RequestMethod.POST)
	public void historicalExcelLegerQuery(@RequestBody GeneralQueryReq subjectDetail,HttpServletResponse response){
		FetchResponse<Map<String,Object>> generalLegerQuery = generalLegerService.historicalGeneralLegerQuery(subjectDetail.getSubjectLevel(),subjectDetail.getBeginDate(),
				subjectDetail.getEndDate(),subjectDetail.getInOutFlag(),subjectDetail.getRange());
		generalLegerExcelService.excelFile(generalLegerQuery.getData(), response);
	}
}
