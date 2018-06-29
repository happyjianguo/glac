package net.engining.sccc.mgm.controller.param;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.FetchDataProcess;
import net.engining.sccc.biz.bean.PostCodeInsertOneRes;
import net.engining.sccc.biz.bean.TxnSubjectParamInsertReq;
import net.engining.sccc.biz.bean.TxnSubjectParamReq;
import net.engining.sccc.biz.bean.TxnSubjectParamRes;
import net.engining.sccc.biz.service.TxnSubjectParamService;

@Api(value="会计分录查询")
@RequestMapping("/txnsubject")
@RestController
public class TxnSubjectParamController {
	
	@Autowired
	TxnSubjectParamService txnSubjectParamService;
	
	@PreAuthorize("hasAuthority('txnSubjectParamRecord')")
	@ApiOperation(value="会计分录查询", notes="")
	@RequestMapping(value="/txnSubjectParamRecord", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<List<TxnSubjectParamRes>> txnSubjectParamRecord(@RequestBody TxnSubjectParamReq req) {
		List<TxnSubjectParamRes> tsprList = txnSubjectParamService.txnSubjectParamRecord(req.getProductCd(), req.getDeadline());
		
//		FetchDataProcess<TxnSubjectParamRes> fetchDataProcess = new FetchDataProcess<TxnSubjectParamRes>();
//		fetchDataProcess.setData(tsprList);
//		fetchDataProcess.setRowCount(tsprList.size());
		
		return new WebCommonResponseBuilder<List<TxnSubjectParamRes>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(tsprList);
	}
	
	@PreAuthorize("hasAuthority('txnSubjectParamInsert')")
	@ApiOperation(value="会计分录添加", notes="")
	@RequestMapping(value="/txnSubjectParamInsert", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> txnSubjectParamInsert(@RequestBody TxnSubjectParamInsertReq txnSubjectParamInsertReq) {
		
		txnSubjectParamService.txnSubjectParamInsert(txnSubjectParamInsertReq);
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
	
	@PreAuthorize("hasAuthority('postCodeInsert')")
	@ApiOperation(value="入账交易码添加", notes="")
	@RequestMapping(value="/postCodeInsert/{sum}", method=RequestMethod.POST)
	@Transactional
	public @ResponseBody WebCommonResponse<List<String>> postCodeInsert(@PathVariable Integer sum) {
		
		List<String> res = txnSubjectParamService.postCodeInsert(sum);
		return new WebCommonResponseBuilder<List<String>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(res);
	}
	
	@PreAuthorize("hasAuthority('postCodeInsertOne')")
	@ApiOperation(value="单个入账交易码添加", notes="")
	@RequestMapping(value="/postCodeInsertOne", method=RequestMethod.POST)
	@Transactional
	public @ResponseBody WebCommonResponse<PostCodeInsertOneRes> postCodeInsertOne(@RequestBody TxnSubjectParamReq req) {
		
		PostCodeInsertOneRes res = txnSubjectParamService.postCodeInsertOne(req);
		return new WebCommonResponseBuilder<PostCodeInsertOneRes>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(res);
	}
}
