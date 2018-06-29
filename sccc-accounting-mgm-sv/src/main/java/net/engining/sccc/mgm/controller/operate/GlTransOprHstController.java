package net.engining.sccc.mgm.controller.operate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.gm.facility.SystemStatusFacility;
import net.engining.gm.param.model.SystemStatus;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.AccountRecord;
import net.engining.sccc.biz.bean.AccountingRecordDetailsRes;
import net.engining.sccc.biz.bean.TotalBookkingRes;
import net.engining.sccc.biz.bean.TransOprHstReq;
import net.engining.sccc.biz.service.TransOprHstService;
import net.engining.sccc.mgm.bean.query.service.HistoricalAccountingRecordService;
import net.engining.sccc.mgm.bean.query.service.PostQueryService;

@Api(value="GlTransOprHstController",description="综合记账查询")
@RequestMapping("/glTxnHst")
@RestController
public class GlTransOprHstController {

	@Autowired
	private TransOprHstService transOprHstService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired SystemStatusFacility systemFacility;
	@Autowired
	private PostQueryService postQueryService;
	
	@Autowired HistoricalAccountingRecordService historicalAccountingRecordService;
	/**
	 * 
	 * 综合记账查询
	 *
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/glTxnHstQuery", method = RequestMethod.POST)
	@ApiOperation(value = "综合记账查询", notes = "")
	@PreAuthorize("hasAuthority('glTxnHstQuery')")
	public @ResponseBody WebCommonResponse<FetchResponse<TotalBookkingRes>> glTxnHxtQuery(
			@RequestBody TransOprHstReq req) {
//		FetchResponse<Map<String, Object>> fetchResponse = transOprHstService.glTxnHstQuery(req.getCheckFlagDef(),
//				req.getOperDate(), req.getTxnDetailSeq(), req.getRange());
		FetchResponse<TotalBookkingRes> fetchResponse;
		if (transOprHstService.formatDate(req.getOperDate(),new Date())) {
			fetchResponse =transOprHstService.bookkeeping(req.getTxnDetailSeq(), req.getRange(),req.getCheckFlagDef());
//			fetchResponse = transOprHstService.transQuery(fetchResponse,req.getOperDate());
		}else{
			try {
				fetchResponse = postQueryService.getPostQueryService(req);
				fetchResponse = transOprHstService.historicalTwo(fetchResponse, req.getCheckFlagDef());
			} catch (Exception e) {
				logger.info("出现异常"+e);
				fetchResponse = new FetchResponse<TotalBookkingRes>();
				fetchResponse.setData(new ArrayList());
			}
		}
		return new WebCommonResponseBuilder<FetchResponse<TotalBookkingRes>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(fetchResponse);
	}

	 /**
	 *
	 *综合记账明细查询
	 *
	 * @param
	 * @param
	 * @return
	 */
	 @RequestMapping(value="/glTxnHstDetailQuery",method=RequestMethod.POST)
	 @ApiOperation(value="综合记账明细查询", notes="")
	 @PreAuthorize("hasAuthority('glTxnHstDetailQuery')")
	 public @ResponseBody WebCommonResponse<AccountingRecordDetailsRes> glTxnHstDetailQuery(@RequestBody AccountingRecordDetailsRes Details) {
		 SystemStatus systemStatus = systemFacility.getSystemStatus();
		 try {
			 if(transOprHstService.formatDate(Details.getPostDate(), systemStatus.businessDate)) {
				Details=transOprHstService.todayDetail(Details.getGltSeq());
			 }else{
				 Details=historicalAccountingRecordService.getHisroticAccountingRecordDetails(Details);
			 }
			List<AccountRecord> subjectName = transOprHstService.insertSubjectIntoResult(Details.getAccount());
			Details.setAccount(subjectName);
		 } catch (Exception e) {
			 logger.info("出现异常"+e);
			 Details.setAccount(new ArrayList());
			}
		return new WebCommonResponseBuilder<AccountingRecordDetailsRes>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(Details);
	 }

}
