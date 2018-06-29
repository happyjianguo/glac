package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.List;

import net.engining.pcx.cc.param.model.enums.Deadline;

public class TxnSubjectParamInsertReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String productCd;
	private String newProductCd;
	private Deadline deadline;
	private List<TransactionScene> transList;
	public Deadline getDeadline() {
		return deadline;
	}
	public void setDeadline(Deadline deadline) {
		this.deadline = deadline;
	}
	public List<TransactionScene> getTransList() {
		return transList;
	}
	public void setTransList(List<TransactionScene> transList) {
		this.transList = transList;
	}
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public String getNewProductCd() {
		return newProductCd;
	}
	public void setNewProductCd(String newProductCd) {
		this.newProductCd = newProductCd;
	}
	
}
