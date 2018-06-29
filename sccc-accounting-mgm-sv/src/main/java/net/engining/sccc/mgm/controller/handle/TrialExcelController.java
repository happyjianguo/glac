package net.engining.sccc.mgm.controller.handle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.gm.facility.SystemStatusFacility;
import net.engining.gm.param.model.SystemStatus;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.DetailCheck;
import net.engining.sccc.biz.bean.TotalProrateCheckRes;
import net.engining.sccc.biz.bean.TrialBalanceRes;
import net.engining.sccc.biz.bean.TrialReqBean;
import net.engining.sccc.biz.service.TrialService;
import net.engining.sccc.mgm.bean.query.service.DetailCheckExcel;
import net.engining.sccc.mgm.bean.query.service.DetailCheckService;
import net.engining.sccc.mgm.bean.query.service.TotalScoreCheckExcel;
import net.engining.sccc.mgm.bean.query.service.TrialBanlanceExcelService;

@Api(value="试算平衡导出")
@RequestMapping("/trial")
@RestController
public class TrialExcelController {
	@Autowired SystemStatusFacility systemFacility;
	@Autowired TrialService trialService;
	@Autowired TrialBanlanceExcelService  trialExcelService;
	@Autowired DetailCheckService detailCheckService;
	@Autowired TotalScoreCheckExcel totalScoreCheckExcel;
	@Autowired DetailCheckExcel  detailCheckExcel;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@PreAuthorize("hasAuthority('trialBanlance')")
	@ApiOperation(value="试算平衡", notes="")
	@RequestMapping(value="/trialBanlanceExcel/{endDate}", method=RequestMethod.POST)
	public void trialBanlance(@PathVariable String endDate,HttpServletResponse response) throws ParseException{
		Date processDate;
		if("2018".equals(endDate)){
			SystemStatus systemStatus = systemFacility.getSystemStatus();
			processDate = new Date(systemStatus.processDate.getTime()-1000*60*60*24);
		}else{
			processDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate+"-12-31");
		}
		List<Map<String,TrialBalanceRes>> trialBanlance = trialService.trialBanlance(processDate);
		List<Map<String, TrialBalanceRes>> countTotal = trialService.countTotal(trialBanlance);
		trialExcelService.excelFile(countTotal, response);
	}
	
	@PreAuthorize("hasAuthority('totalScoreCheck')")
	@ApiOperation(value="总分核对", notes="")
	@RequestMapping(value="/totalScoreCheckExcel", method=RequestMethod.POST)
	public void totalScoreCheck(@RequestBody TrialReqBean trial,HttpServletResponse response){
		try {
			TotalProrateCheckRes scoreCheck = trialService.totalScoreCheck(trial.getEndDate(),trial.getSubjectNo());
			List<TotalProrateCheckRes> score = new ArrayList<TotalProrateCheckRes>();
			score.add(scoreCheck);
			totalScoreCheckExcel.excelFile(score, response);
		} catch (Exception e) {
			logger.info("出现异常" + e);
		}
	}
	
	@PreAuthorize("hasAuthority('detailCheck')")
	@ApiOperation(value="明细核对", notes="")
	@RequestMapping(value="/detailCheckExcel", method=RequestMethod.POST)
	public void detailCheck(@RequestBody TrialReqBean trial,HttpServletResponse response){
		FetchResponse<DetailCheck> detailCheck = trialService.detailCheck(trial.getEndDate(),trial.getRange());
		List<DetailCheck> check;
		if(detailCheck.getData().isEmpty()){
			check=new ArrayList();	
		}else{
			check = detailCheckService.getDetailCheck(detailCheck.getData());
		}
		detailCheckExcel.excelFile(check, response);
	}
}
