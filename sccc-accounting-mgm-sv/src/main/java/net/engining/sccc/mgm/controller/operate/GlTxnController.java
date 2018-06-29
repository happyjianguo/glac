package net.engining.sccc.mgm.controller.operate;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.TxnOperate;
import net.engining.sccc.biz.bean.TxnOperateReq;
import net.engining.sccc.mgm.service.GlTxnService;

@Api(value="GlTxnController",description="表内外记账新增、科目对应辅助核算项查询")
@RequestMapping("/glTxn")
@RestController
public class GlTxnController {

	@Autowired
	private GlTxnService glTxnService;
	
	@Autowired
	private ParameterFacility parameterFacility;
	
	
	@RequestMapping(value = "/getAssist", method = RequestMethod.POST)
	@ApiOperation(value = "科目对应辅助核算项", notes = "")
	@PreAuthorize("hasAuthority('getAssist')")
	public @ResponseBody WebCommonResponse<List<String>> getAssist(@RequestBody String cd) {

		Subject subject = parameterFacility.loadParameter(Subject.class,cd);
		List<String> assList = subject.auxiliaryAssist;
		return new WebCommonResponseBuilder<List<String>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(assList);

	}

	/**
	 * 
	 * 表内外记账新增
	 *
	 * @param ApGlTxn
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/glTxnOperate", method = RequestMethod.POST)
	@ApiOperation(value = "表内记账操作", notes = "")
	@PreAuthorize("hasAuthority('glTxnOperate')")
	public @ResponseBody WebCommonResponse<Void> glTxnOperate(@RequestBody TxnOperateReq file) {
		// 当日总账交易流水入表
		ApGlTxn apGlTxn = glTxnService.glTxnAdd(file);

		// 会计分录拆分交易流水表入表
		for(TxnOperate TxnOperate : file.getList()){
			TxnOperate.setInOutFlag(file.getInOutFlag());
			glTxnService.volDtlAdd(TxnOperate, apGlTxn.getGltSeq());
		}
		
		//操作表
 		glTxnService.glTransOprAdd(file,apGlTxn.getGltSeq());

		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);

	}

}
