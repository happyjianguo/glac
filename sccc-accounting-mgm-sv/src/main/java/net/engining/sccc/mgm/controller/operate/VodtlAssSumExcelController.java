package net.engining.sccc.mgm.controller.operate;

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
import net.engining.sccc.biz.bean.ApGlVoldtlSumReq;
import net.engining.sccc.biz.service.ApGlVolDtlSumService;
import net.engining.sccc.mgm.service.VodtlAssSumExcelService;

@Api(value="VodtlAssSumExcelController",description="当日辅助余额EXCEL导出")
@RequestMapping("/excel")
@RestController
public class VodtlAssSumExcelController {
	@Autowired VodtlAssSumExcelService  vodtlAssService;
	@Autowired
	private ApGlVolDtlSumService apGlVolDtlSumService;
	
	@PreAuthorize("hasAuthority('vodtlAssSumQuery')")
	@ApiOperation(value="当日辅助余额查询Excel", notes="")
	@RequestMapping(value="/vodtlAssSumExcelQuery",method=RequestMethod.POST)
	public void vodtlAssSumExcelQuery(@RequestBody ApGlVoldtlSumReq req,HttpServletResponse response){
		FetchResponse<Map<String, Object>> result = apGlVolDtlSumService.getVolDtlSumRes(req);
		FetchResponse<Map<String, Object>> fetchResponse = apGlVolDtlSumService.insertSubjectIntoResult(result);
		vodtlAssService.excelFile(fetchResponse.getData(), response);
	}
	
	@PreAuthorize("hasAuthority('vodtlAssSumHstQuery')")
	@ApiOperation(value="历史辅助余额查询Excel", notes="")
	@RequestMapping(value="/vodtlAssSumHstExcelQuery",method=RequestMethod.POST)
	public void vodtlAssSumHstExcelQuery(@RequestBody ApGlVoldtlSumReq req,HttpServletResponse response){
		FetchResponse<Map<String, Object>> fetchResponse=apGlVolDtlSumService.getVolDtlSumHstRes(req);
		vodtlAssService.excelFile(fetchResponse.getData(), response);
	}
}
