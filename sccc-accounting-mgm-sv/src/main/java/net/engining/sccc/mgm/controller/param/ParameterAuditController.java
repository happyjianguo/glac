package net.engining.sccc.mgm.controller.param;

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
import net.engining.sccc.biz.bean.ParameterAuditReq;
import net.engining.sccc.biz.service.ParameterAuditService;

@Api(value = "参数维护历史查询")
@RequestMapping("/parameter")
@RestController
public class ParameterAuditController {

	@Autowired
	ParameterAuditService parameterAuditService;

	@PreAuthorize("hasAuthority('parameterAuditRecord')")
	@ApiOperation(value = "参数维护历史查询", notes = "")
	@RequestMapping(value = "/parameterAuditRecord", method = RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> parameterAuditRecord(
			@RequestBody ParameterAuditReq req) {

		FetchResponse<Map<String, Object>> fetchResponse = parameterAuditService.parameterAuditRecord(
				req.getStartDate(), req.getEndDate(), req.getMtnId(), req.getMtnUser(), req.getRange());

		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(fetchResponse);
	}
}
