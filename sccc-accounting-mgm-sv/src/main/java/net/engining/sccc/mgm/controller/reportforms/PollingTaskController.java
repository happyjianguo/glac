package net.engining.sccc.mgm.controller.reportforms;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pcx.cc.infrastructure.shared.enums.PostTxnTypeDef;
import net.engining.pcx.cc.infrastructure.shared.model.PollingTask;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.mgm.bean.reportforms.FetchPollingTaskForm;
import net.engining.sccc.mgm.bean.reportforms.FileDownLoadForm;
import net.engining.sccc.mgm.bean.reportforms.PollingTaskForm;
import net.engining.sccc.mgm.service.reportforms.PollingTaskService;

/**
 * 
 * @author liqingfeng
 *
 */
@Api(value = "PollingTaskController", description = "报表部分轮循表操作")
@RequestMapping("/PollingTask")
@RestController
public class PollingTaskController {

	@Autowired
	private PollingTaskService pollingTaskService;

	/**
	 * 将查询报表的信息插入 POLLING_TASK 表
	 * 
	 * @param PollingTaskForm
	 * @return
	 */
	@RequestMapping(value = "/insertQueryIntoPollingTast", method = RequestMethod.POST)
	@ApiOperation(value = "将查询报表的信息插入 POLLING_TASK 表", notes = "")
	@PreAuthorize("hasAuthority('insertQueryIntoPollingTast')")
	public @ResponseBody WebCommonResponse<Void> insertQueryIntoPollingTast(
			@RequestBody PollingTaskForm pollingTaskForm) {
		pollingTaskService.insertQueryIntoPollingTast(pollingTaskForm.getQueryCondition(),
				pollingTaskForm.getReportTypeDef());

		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}

	/**
	 * 从轮循表查询轮循任务
	 * 
	 * @param reportType
	 * @param range
	 * @return
	 */
	@RequestMapping(value = "/fetchPollingTast", method = RequestMethod.POST)
	@ApiOperation(value = "从轮循表查询轮循任务", notes = "")
	@PreAuthorize("hasAuthority('fetchPollingTast')")
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> fetchPollingTast(
			@RequestBody FetchPollingTaskForm fetchPollingTaskForm) {
		FetchResponse<Map<String, Object>> responseData = pollingTaskService
				.fetchPollingTast(fetchPollingTaskForm.getReportTypeDef(), fetchPollingTaskForm.getRange());

		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(responseData);
	}

	 /**
	 * 下载文件
	 *
	 * @param reportType
	 * @param range
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/fileDownloadExcel", method = RequestMethod.POST)
	@ApiOperation(value = "下载文件", notes = "")
	@PreAuthorize("hasAuthority('fileDownloadExcel')")
	public @ResponseBody WebCommonResponse<Void> fileDownload(@RequestBody FileDownLoadForm fileDownLoadForm,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		PollingTask pollingTask = pollingTaskService.getFileInfo(fileDownLoadForm.getId());
		String fileName = pollingTask.getFileName();
		String filePath = System.getProperty("user.dir");
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=" + System.getProperty("file.encoding"));
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		pollingTaskService.downloadFile(filePath, fileName, response.getOutputStream());
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}

	/**
	 * 获取交易类型下拉列表
	 * @return
	 */
	@RequestMapping(value = "/tradeType", method = RequestMethod.POST)
	@ApiOperation(value = "获取交易类型下拉列表", notes = "")
	@PreAuthorize("hasAuthority('tradeType')")
	public @ResponseBody WebCommonResponse<List<Map<String,String>>> tradeType(){
			List<Map<String,String>> postTxnList = new ArrayList<>();
			Map<String, String> postTxn = new HashMap<>();
			postTxn.put("name", PostTxnTypeDef.INTEACC.name());
			postTxn.put("value","利息计提");
			postTxnList.add(postTxn);
			postTxn = new HashMap<>();
			postTxn.put("name", PostTxnTypeDef.PINTACC.name());
			postTxn.put("value", "罚息计提");
			postTxnList.add(postTxn);
			postTxn = new HashMap<>();
			postTxn.put("name", PostTxnTypeDef.TRANSFO.name());
			postTxn.put("value", "余额成份结转");
			postTxnList.add(postTxn);
			postTxn = new HashMap<>();
			postTxn.put("name", PostTxnTypeDef.BATREPA.name());
			postTxn.put("value", "批量还款");
			postTxnList.add(postTxn);
			postTxn = new HashMap<>();
			postTxn.put("name", PostTxnTypeDef.LoanTally.name());
			postTxn.put("value", "贷款发放记账");
			postTxnList.add(postTxn);
			postTxn = new HashMap<>();
			postTxn.put("name", PostTxnTypeDef.GrantTally.name());
			postTxn.put("value", "授额提降额记账");
			postTxnList.add(postTxn);
			postTxn = new HashMap<>();
			postTxn.put("name", PostTxnTypeDef.FeeTally.name());
			postTxn.put("value", "费用收取记账");
			postTxnList.add(postTxn);
			postTxn = new HashMap<>();
			postTxn.put("name", PostTxnTypeDef.RepTally.name());
			postTxn.put("value", "还款记账");
			postTxnList.add(postTxn);
			postTxn = new HashMap<>();
			postTxn.put("name", PostTxnTypeDef.RefTally.name());
			postTxn.put("value", "退款记账");
			postTxnList.add(postTxn);
			return new WebCommonResponseBuilder<List<Map<String,String>>>().build()
					.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(postTxnList);
	}
	
}
