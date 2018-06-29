package net.engining.sccc.mgm.sao.imp;

import org.springframework.stereotype.Component;

import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.biz.bean.SummaryBySubject;
import net.engining.sccc.biz.bean.TrialReqBean;
import net.engining.sccc.mgm.sao.TotalAccountSao;
@Component
public class TotalAccountSaoImp implements TotalAccountSao{

	@Override
	public SummaryBySubject doTotalAccountSao(TrialReqBean trial) {
		throw new ErrorMessageException(ErrorCode.Other, "连接超时");
	}

}
