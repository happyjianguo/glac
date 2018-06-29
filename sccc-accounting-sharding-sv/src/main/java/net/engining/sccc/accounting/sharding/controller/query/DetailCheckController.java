package net.engining.sccc.accounting.sharding.controller.query;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.engining.sccc.biz.bean.DetailCheck;
import net.engining.sccc.biz.bean.SummaryBySubject;
import net.engining.sccc.biz.bean.TrialReqBean;
import net.engining.sccc.biz.service.LegerVolService;
import net.engining.sccc.biz.service.TrialService;

@RequestMapping("/trial")
@RestController
public class DetailCheckController {
	@Autowired TrialService trialService;
	@Autowired LegerVolService legerVolService;
	@RequestMapping(value="/detailCheck", method=RequestMethod.POST)
	public @ResponseBody  List<DetailCheck> postAccountQuery(@RequestBody List<DetailCheck> accounting) {
		List<DetailCheck> check = trialService.queryPostSeq(accounting);
		return check;
	}
	
	@RequestMapping(value="/totalScoreCheck", method=RequestMethod.POST)
	public @ResponseBody  SummaryBySubject totalAccount(@RequestBody TrialReqBean trial) {
		SummaryBySubject summary=legerVolService.queryApVolDtl(trial.getEndDate(),trial.getSubjectNo());
		return summary;
	}
}
