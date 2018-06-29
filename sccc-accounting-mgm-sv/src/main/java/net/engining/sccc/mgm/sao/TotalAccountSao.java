package net.engining.sccc.mgm.sao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.engining.sccc.biz.bean.SummaryBySubject;
import net.engining.sccc.biz.bean.TrialReqBean;
import net.engining.sccc.mgm.sao.imp.TotalAccountSaoImp;

@FeignClient(value = "sccc-accounting-sharding-sv", fallback = TotalAccountSaoImp.class)
public interface TotalAccountSao {
	@RequestMapping(value ="/trial/totalScoreCheck", method = RequestMethod.POST)
	SummaryBySubject doTotalAccountSao(@RequestBody TrialReqBean trial);
}
