package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProfitAndLossOne implements Serializable{

	/**
	 * 损益结转查询
	 */
	private static final long serialVersionUID = 1L;

	private Date bizDate;//业务日期
	private String subjectNo;//科目编号
	private String subjectName;//科目名称
	private BigDecimal balance;//余额
	private String direction;//方向
	
	
	public Date getBizDate() {
		return bizDate;
	}
	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
