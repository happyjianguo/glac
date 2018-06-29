package net.engining.sccc.mgm.bean.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.sccc.biz.bean.SummaryBySubject;
import net.engining.sccc.biz.bean.TrialReqBean;
import net.engining.sccc.mgm.sao.TotalAccountSao;
@Service
public class TotalAccountService {
	@Autowired 
	private TotalAccountSao totalAccountSao;

	public SummaryBySubject totalAccount(TrialReqBean trial) {
		return totalAccountSao.doTotalAccountSao(trial);
	}
	
	
}
