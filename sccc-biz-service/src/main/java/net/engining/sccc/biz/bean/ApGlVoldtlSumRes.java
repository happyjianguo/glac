package net.engining.sccc.biz.bean;

import java.math.BigDecimal;
import java.util.Date;

import net.engining.gm.infrastructure.enums.TxnDirection;

public class ApGlVoldtlSumRes {
	/**
	 * kemu 
	 */
	private String subjectCd;
	
	private AssistTypeDef assistTypeDef;
	
	private String AssistAccountData;
	
	private Date volDt;
	
	private TxnDirection txnDirection;
	
	private BigDecimal lastBal;
	
	private BigDecimal bal;

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	public AssistTypeDef getAssistTypeDef() {
		return assistTypeDef;
	}

	public void setAssistTypeDef(AssistTypeDef assistTypeDef) {
		this.assistTypeDef = assistTypeDef;
	}

	public Date getVolDt() {
		return volDt;
	}

	public void setVolDt(Date volDt) {
		this.volDt = volDt;
	}

	public TxnDirection getTxnDirection() {
		return txnDirection;
	}

	public void setTxnDirection(TxnDirection txnDirection) {
		this.txnDirection = txnDirection;
	}

	public BigDecimal getLastBal() {
		return lastBal;
	}

	public void setLastBal(BigDecimal lastBal) {
		this.lastBal = lastBal;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}

	public String getAssistAccountData() {
		return AssistAccountData;
	}

	public void setAssistAccountData(String assistAccountData) {
		AssistAccountData = assistAccountData;
	}
	
	
	
}
