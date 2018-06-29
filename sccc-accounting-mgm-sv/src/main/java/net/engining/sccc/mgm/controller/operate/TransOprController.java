package net.engining.sccc.mgm.controller.operate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.ApGlVoldtDetailReq;
import net.engining.sccc.biz.bean.TransOprHstDetail;
import net.engining.sccc.biz.bean.TransQueryReq;
import net.engining.sccc.biz.bean.TransUpdateReq;
import net.engining.sccc.biz.service.TransOprService;
import net.engining.sccc.mgm.service.TransHstDetailService;

@Api(value="TransOprController",description="记账复核")
@RequestMapping("/transOpr")
@RestController
public class TransOprController {

	@Autowired
	private TransOprService transOprService;

	@Autowired
	private TransHstDetailService transOprHstService;

	/**
	 * 
	 * 记账复核查询
	 *
	 * @param ApGlTxn
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/transOprQuery", method = RequestMethod.POST)
	@ApiOperation(value = "记账复核查询", notes = "")
	@PreAuthorize("hasAuthority('transOprQuery')")
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> transOprQuery(
			@RequestBody TransQueryReq req) {

		FetchResponse<Map<String, Object>> fetchResponse = transOprService.transOprQuery(req.getCheckerId(),
				req.getTransDate(),req.getCurrOperateId(), req.getRange());

		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(fetchResponse);
	}

	/**
	 * 
	 * 记账复核明细查询
	 *
	 * @param ApGlTxn
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/transOprDetailQuery", method = RequestMethod.POST)
	@ApiOperation(value = "综合记账明细查询", notes = "")
	@PreAuthorize("hasAuthority('transOprDetailQuery')")
	public @ResponseBody WebCommonResponse<List<TransOprHstDetail>> glTxnHxtDetailQuery(
			@RequestBody ApGlVoldtDetailReq req) {

		List<Tuple> fetchResponse = transOprService.transOprDetailQuery(req.getGltSeq(), req.getTxnDetailType());

		List<TransOprHstDetail> transOprHstDetailRes = transOprHstService.transOprDetail(fetchResponse);

		return new WebCommonResponseBuilder<List<TransOprHstDetail>>().build().setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(transOprHstDetailRes);
	}

	/**
	 * 
	 * 记账复核提交，拒绝
	 *
	 * @param ApGlTxn
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/transOprUpdate", method = RequestMethod.POST)
	@ApiOperation(value = "记账复核提交，拒绝", notes = "")
	@PreAuthorize("hasAuthority('transOprUpdate')")
	@Transactional
	public @ResponseBody WebCommonResponse<Void> transOprUodate(@RequestBody TransUpdateReq req) {

		transOprService.transOprUpdate(req.getCheckFlagDef(), req.getGltSeq(), req.getSeq(), req.getRefuseReason(),req.getOperaId());

		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
}
