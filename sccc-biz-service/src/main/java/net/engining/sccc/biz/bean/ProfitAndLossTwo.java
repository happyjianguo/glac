package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProfitAndLossTwo implements Serializable{

	/**
	 * 损益结转查询
	 */
	private static final long serialVersionUID = 1L;

	private String dbSubjectNo;//借方号
	private String crSubjectNo;//贷方科目号
	private BigDecimal postAmount;//记账金额
	
	
	public String getDbSubjectNo() {
		return dbSubjectNo;
	}
	public void setDbSubjectNo(String dbSubjectNo) {
		this.dbSubjectNo = dbSubjectNo;
	}
	public String getCrSubjectNo() {
		return crSubjectNo;
	}
	public void setCrSubjectNo(String crSubjectNo) {
		this.crSubjectNo = crSubjectNo;
	}
	public BigDecimal getPostAmount() {
		return postAmount;
	}
	public void setPostAmount(BigDecimal postAmount) {
		this.postAmount = postAmount;
	}
}
