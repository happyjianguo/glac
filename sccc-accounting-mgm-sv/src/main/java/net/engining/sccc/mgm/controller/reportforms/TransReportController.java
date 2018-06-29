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
@Api(value="TransReportController",description="Trans报表")
@RequestMapping("/transReport")
@RestController
public class TransReportController {

	@Autowired
	private FetchListService fetchListService;

	/**
	 * 获取交易类型下拉框
	 * 
	 */
	@RequestMapping(value = "/fetchTradeTypeList", method = RequestMethod.POST)
	@ApiOperation(value = "获取交易类型下拉框", notes = "")
	@PreAuthorize("hasAuthority('subjectList')")
	public @ResponseBody WebCommonResponse<List<Map<String, Object>>> tradeTypeList() {

		List<Map<String, Object>> fetchResponse = fetchListService.fetchTradeTypeList();

		return new WebCommonResponseBuilder<List<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(fetchResponse);

	}
}