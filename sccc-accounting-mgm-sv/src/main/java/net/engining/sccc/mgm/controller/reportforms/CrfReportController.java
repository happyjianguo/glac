package net.engining.sccc.mgm.controller.reportforms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.service.FetchListService;

/**
 * 
 * @author liqingfeng
 *
 */
@Api(value="CrfReportController",description="CRF报表")
@RequestMapping("/crfReport")
@RestController
public class CrfReportController {

	@Autowired
	private FetchListService fetchListService;
	  
	/**
	 * 获取科目下拉框
	 * 
	 */
	@RequestMapping(value="/subjectList",method=RequestMethod.POST)
	@ApiOperation(value="获取科目列表", notes="")
	@PreAuthorize("hasAuthority('subjectList')")
	public @ResponseBody WebCommonResponse<List<Map<String,Object>>> subjectList() {
		
		List<Map<String,Object>> fetchResponse = fetchListService.fetchSubjectList();
		
		return new WebCommonResponseBuilder<List<Map<String,Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(fetchResponse);
		
	}
                 

	
}
