package net.engining.sccc.accounting.sao.impl;

import org.springframework.stereotype.Component;

import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.accounting.bean.ApGlTxnHstResponseBean;
import net.engining.sccc.accounting.bean.ApGlTxnRequestBean;
import net.engining.sccc.accounting.sao.AccountingSao;

/**
 * 调用微服务失败时的本地实现
 * @author luxue
 *
 */
@Component
public class AccountingSaoImpl implements AccountingSao{

	/* (non-Javadoc)
	 * @see net.engining.sccc.accounting.sao.OuterLedgerTxnHstSao#queryOuterLedgerTxnHst(net.engining.sccc.accounting.bean.ApGlTxnRequestBean)
	 */
	@Override
	public ApGlTxnHstResponseBean queryOuterLedgerTxnHst(ApGlTxnRequestBean apGlTxn) {
		ApGlTxnHstResponseBean user= new ApGlTxnHstResponseBean();
		user.setReturnCode(ErrorCode.Other.toString());
		user.setReturnDesc("server is busy, try it later");
		
		return user;
	}


}
