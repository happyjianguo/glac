package net.engining.sccc.biz.bean.batchBean;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;

public class DataTransBean {
	
	/**
	 * cactTxnPost主键
	 */
	private String txnId;
	/**
	 * 交易代码
	 */
	private String postCode;
	/**
	 * 对账数据出现的状态（此处只会出现补记，挂账）
	 */
	private CheckAccountStatusDef checkAccountStatusDef;
	
	/**
	 * 请求数据
	 */
	private EveryDayAccountingBean everyDayAccountingBean;
	
	public CheckAccountStatusDef getCheckAccountStatusDef() {
		return checkAccountStatusDef;
	}

	public void setCheckAccountStatusDef(CheckAccountStatusDef checkAccountStatusDef) {
		this.checkAccountStatusDef = checkAccountStatusDef;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public EveryDayAccountingBean getEveryDayAccountingBean() {
		return everyDayAccountingBean;
	}

	public void setEveryDayAccountingBean(EveryDayAccountingBean everyDayAccountingBean) {
		this.everyDayAccountingBean = everyDayAccountingBean;
	}

}
