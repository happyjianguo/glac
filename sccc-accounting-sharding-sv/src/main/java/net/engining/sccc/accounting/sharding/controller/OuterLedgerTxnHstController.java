package net.engining.sccc.accounting.sharding.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiOperation;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlHst;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.BaseResponseBean;
import net.engining.pg.web.WebCommonResponse;
import net.engining.sccc.accounting.sharding.consumer.ShardingTableInsertMsgConsumer;
import net.engining.sccc.accounting.sharding.controller.bean.ApGlTxnHstResponseBean;
import net.engining.sccc.accounting.sharding.controller.bean.ApGlTxnRequestBean;
import net.engining.sccc.accounting.sharding.controller.bean.ApGlVolDtlHstRequestBean;
import net.engining.sccc.accounting.sharding.controller.bean.ApGlVolDtlHstResponseBean;
import net.engining.sccc.accounting.sharding.service.BatchTransferService;
import net.engining.sccc.accounting.sharding.service.OuterLedgerTxnHstService;

@RestController
public class OuterLedgerTxnHstController {
	
	@Autowired
	OuterLedgerTxnHstService outerLedgerTxnHstService; 
	
	@Autowired
	private BatchTransferService batchTransferService;

	@ApiOperation(value = "备份当日总账交易流水", notes = "")
	@RequestMapping(value = "/backupOuterLedgerTxnHst", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean backupOuterLedgerTxnHst(@RequestBody ApGlTxn apGlTxn) {
		
		outerLedgerTxnHstService.backup2Hst(apGlTxn);

		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}
	
	@ApiOperation(value = "查询总账交易流水", notes = "")
	@RequestMapping(value = "/queryOuterLedgerTxnHst", method = RequestMethod.POST)
	public @ResponseBody ApGlTxnHstResponseBean queryOuterLedgerTxnHst(@RequestBody ApGlTxnRequestBean apGlTxn) {
		
		FetchResponse<Map<String, Object>> fetchResponse = outerLedgerTxnHstService.queryHst(apGlTxn.getAcctSeq(), apGlTxn.getPostDate(), apGlTxn.getRange());

		ApGlTxnHstResponseBean baseResponseBean = new ApGlTxnHstResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		baseResponseBean.setApGlTxnHst(fetchResponse.getData());
		return baseResponseBean;

	}
	
	@ApiOperation(value = "备份当日会计明细交易流水", notes = "")
	@RequestMapping(value = "/backupSubjectTxnHst", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean backupSubjectTxnHst(@RequestBody ApGlVolDtlHst apGlVolDtlHst) {
		
		outerLedgerTxnHstService.backup2ApGlVolDtlHst(apGlVolDtlHst);

		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}
	
	@ApiOperation(value = "查询会计明细交易流水", notes = "")
	@RequestMapping(value = "/querySubjectTxnHst", method = RequestMethod.POST)
	public @ResponseBody ApGlVolDtlHstResponseBean querySubjectTxnHst(@RequestBody ApGlVolDtlHstRequestBean apGlTxn) {
		
		FetchResponse<Map<String, Object>> fetchResponse = outerLedgerTxnHstService.queryApGlVolDtlHst(apGlTxn.getDbSubjectCd(), apGlTxn.getCrSubjectCd(), apGlTxn.getVolDate(), apGlTxn.getRange());

		ApGlVolDtlHstResponseBean baseResponseBean = new ApGlVolDtlHstResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		baseResponseBean.setApGlVolDtlHst(fetchResponse.getData());
		return baseResponseBean;

	}
	
	@ApiOperation(value = "测试消费者获取消息", notes = "")
	@RequestMapping(value = "/handle4ApGlHst", method = RequestMethod.POST)
	public @ResponseBody BaseResponseBean handle4ApGlHst(@RequestBody List<String> keys) {
		
		batchTransferService.apGlTxnData(keys);

		BaseResponseBean baseResponseBean = new BaseResponseBean();
		baseResponseBean.setReturnCode(WebCommonResponse.CODE_OK);
		baseResponseBean.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return baseResponseBean;

	}

}