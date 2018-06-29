package net.engining.sccc.mgm.controller.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.AccountingRecordReq;
import net.engining.sccc.biz.bean.AcctIouNo;
import net.engining.sccc.biz.bean.HistoricalAccountRecord;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.biz.bean.ParameterAuditReq;
import net.engining.sccc.biz.bean.SubjectRecordReq;
import net.engining.sccc.biz.service.AccountingRecordService;
import net.engining.sccc.biz.service.ParameterAuditService;
import net.engining.sccc.biz.service.SubjectService;
import net.engining.sccc.mgm.bean.query.service.DataProcessService;
import net.engining.sccc.mgm.bean.query.service.HistoricalAccountExcelService;
import net.engining.sccc.mgm.bean.query.service.HistoricalAccountingRecordService;
@Api(value="会计分录查询EXCEL导出")
@RequestMapping("/export")
@RestController
public class ExportExcelController {

	@Autowired AccountingRecordService accountingRecordService;
	@Autowired DataProcessService<HistoricalAccountRecord> dataProcessService;
	@Autowired HistoricalAccountExcelService historicalAccountExcelService;
	@Autowired HistoricalAccountingRecordService historicalAccountingRecordService;
	
	@Autowired SubjectService subjectService;
	@Autowired ParameterAuditService parameterAuditService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@PreAuthorize("hasAuthority('accountingRecordInquiryOntheDay')")
	@ApiOperation(value="当日会计分录查询Excel", notes="")
	@RequestMapping(value="/excelAccountingRecordOntheDay",method=RequestMethod.POST)
	public void accountingRecordExportExcel(@RequestBody AccountingRecordReq accounting,HttpServletResponse response){
		FetchResponse<HistoricalAccountRecord> fetchResponse = accountingRecordService.getAccountingRecord(accounting.getCustId(),accounting.getIouNo(),accounting.getRange());
		historicalAccountExcelService.excelFile(fetchResponse.getData(), response);
	}
	
	@PreAuthorize("hasAuthority('historicalAccountingRecordInquiry')")
	@ApiOperation(value="历史会计分录查询Excel", notes="")
	@RequestMapping(value="/historicalExcelAccountingRecord", method=RequestMethod.POST)
	public void accountingRecordHisExportExcel(@RequestBody HistoricalaccountingRecordReq accounting,HttpServletResponse response){
		List<AcctIouNo> list = accountingRecordService.getHisroticAccountingRecord(accounting.getCustId(),accounting.getIouNo(),accounting.getDateType()
				,accounting.getBeginDate(),accounting.getEndDate());
		accounting.setList(list);
		FetchResponse<HistoricalAccountRecord> txnHstQuery;
		if(null==list||list.size()==0){
			txnHstQuery = new FetchResponse<HistoricalAccountRecord>();
			txnHstQuery.setData(new ArrayList());
		}else{
				try {
					txnHstQuery = historicalAccountingRecordService.getHisroticAccountingRecord(accounting);
			} catch (Exception e) {
				logger.info("出现异常",e);
				txnHstQuery = new FetchResponse<HistoricalAccountRecord>();
				txnHstQuery.setData(new ArrayList());
			}
		}
		historicalAccountExcelService.excelFile(txnHstQuery.getData(), response);
	}
	
	@PreAuthorize("hasAuthority('subjectRecordExportExcel')")
	@ApiOperation(value="会计科目查询Excel", notes="")
	@RequestMapping(value="/subjectRecordExportExcel", method=RequestMethod.POST)
	public void subjectRecordExportExcel(@RequestBody SubjectRecordReq req,HttpServletResponse response){
		FetchResponse<Subject> subjectList = subjectService.subjectRecord(req.getSubjectCd(), req.getType(),
				req.getStatus(), req.getRange());
		historicalAccountExcelService.subjectExportExcel(subjectList.getData(), response);
	}
	
	@PreAuthorize("hasAuthority('parameterAuditRecordExportExcel')")
	@ApiOperation(value = "参数维护历史查询Excel", notes = "")
	@RequestMapping(value = "/parameterAuditRecordExportExcel", method = RequestMethod.POST)
	public void parameterAuditRecordExportExcel(@RequestBody ParameterAuditReq req,HttpServletResponse response) {

		FetchResponse<Map<String, Object>> fetchResponse = parameterAuditService.parameterAuditRecord(
				req.getStartDate(), req.getEndDate(), req.getMtnId(), req.getMtnUser(), req.getRange());

		historicalAccountExcelService.parameterExportExcel(fetchResponse.getData(), response);
	}
	
}
