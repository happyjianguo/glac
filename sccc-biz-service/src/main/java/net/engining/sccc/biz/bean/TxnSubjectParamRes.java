package net.engining.sccc.biz.bean;

import java.io.Serializable;

import net.engining.pcx.cc.param.model.TxnSubjectParam;

public class TxnSubjectParamRes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String postCode;
	private String deadline;
	private String postName;
	private TxnSubjectParam txnSubjectParam;
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public TxnSubjectParam getTxnSubjectParam() {
		return txnSubjectParam;
	}
	public void setTxnSubjectParam(TxnSubjectParam txnSubjectParam) {
		this.txnSubjectParam = txnSubjectParam;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	
}
