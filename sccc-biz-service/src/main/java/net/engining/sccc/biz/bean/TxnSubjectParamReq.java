package net.engining.sccc.biz.bean;

import java.io.Serializable;

import net.engining.pcx.cc.param.model.enums.Deadline;

public class TxnSubjectParamReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String productCd;
	private Deadline deadline;
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public Deadline getDeadline() {
		return deadline;
	}
	public void setDeadline(Deadline deadline) {
		this.deadline = deadline;
	}
	
	
	
}
