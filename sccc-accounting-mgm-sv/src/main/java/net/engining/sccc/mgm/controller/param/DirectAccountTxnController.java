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
import net.engining.pcx.cc.param.model.TxnSubjectParam;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.service.DirectAccountTxnService;

@Api(value="直接记账会计分录查询")
@RequestMapping("/directAccount")
@RestController
public class DirectAccountTxnController {
	
	@Autowired
	DirectAccountTxnService directAccountTxnService;

	@PreAuthorize("hasAuthority('directAccountRecord')")
	@ApiOperation(value="直接记账会计分录查询", notes="")
	@RequestMapping(value="/directAccountRecord/{postCode}", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<TxnSubjectParam> directAccountRecord(@PathVariable String postCode) {
		TxnSubjectParam tsp = directAccountTxnService.directAccountRecord(postCode);
		
		return new WebCommonResponseBuilder<TxnSubjectParam>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(tsp);
	}
	
	@PreAuthorize("hasAuthority('directAccountInsert')")
	@ApiOperation(value="直接记账会计分录添加", notes="")
	@RequestMapping(value="/directAccountInsert", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> directAccountInsert(@RequestBody TxnSubjectParam txnSubjectParam) {
		
		directAccountTxnService.directAccountInsert(txnSubjectParam);
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
}
