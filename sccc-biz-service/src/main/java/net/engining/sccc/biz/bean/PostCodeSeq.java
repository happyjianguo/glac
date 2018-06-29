package net.engining.sccc.biz.bean;

import java.io.Serializable;

public class PostCodeSeq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 入账交易码
	 */
	private String postCode;
	/**
	 * 流水号
	 */
	private String txnSeq;
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getTxnSeq() {
		return txnSeq;
	}
	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
	}
	
}
