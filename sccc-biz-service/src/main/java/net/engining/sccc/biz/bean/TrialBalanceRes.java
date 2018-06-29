package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import net.engining.pcx.cc.param.model.enums.SubjectType;

public class TrialBalanceRes implements Serializable{

	/**
	 * 试算平衡响应bean
	 */
	private static final long serialVersionUID = 1L;
	
	private SubjectType type;//科目 属性
	private BigDecimal dbAmtSum;//借方金额
	private BigDecimal crAmtSum;//贷方金额
	private String direction;//借贷方向
	private BigDecimal balance;//余额
	public SubjectType getType() {
		return type;
	}
	public void setType(SubjectType type) {
		this.type = type;
	}
	public BigDecimal getDbAmtSum() {
		return dbAmtSum;
	}
	public void setDbAmtSum(BigDecimal dbAmtSum) {
		this.dbAmtSum = dbAmtSum;
	}
	public BigDecimal getCrAmtSum() {
		return crAmtSum;
	}
	public void setCrAmtSum(BigDecimal crAmtSum) {
		this.crAmtSum = crAmtSum;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
