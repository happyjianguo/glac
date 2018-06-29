package net.engining.sccc.biz.bean;

import java.io.Serializable;

public class AccountBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String iouNo;
	
	private String acctParamId;
	
	private String productId;
	
	private Integer acctNo;
	
	private String custId;
	
	private Integer totalPeriod;


	public String getIouNo() {
		return iouNo;
	}

	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}

	public String getAcctParamId() {
		return acctParamId;
	}

	public void setAcctParamId(String acctParamId) {
		this.acctParamId = acctParamId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(Integer acctNo) {
		this.acctNo = acctNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Integer getTotalPeriod() {
		return totalPeriod;
	}

	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}
	
	
}
