package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;

public class AccountingRecordDetailsReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	private int acctSeq;//账户编号
	@NotBlank
	private String gltSeq;//入账流水号
	@NotBlank
	private TxnDetailType txnDetailType;//入账交易流水类型
	@NotBlank
	private String txnSeq;//交易流水号
	private Date postDate;
	
	
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getTxnSeq() {
		return txnSeq;
	}
	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
	}
	public int getAcctSeq() {
		return acctSeq;
	}

	public void setAcctSeq(int acctSeq) {
		this.acctSeq = acctSeq;
	}

	public String getGltSeq() {
		return gltSeq;
	}

	public void setGltSeq(String gltSeq) {
		this.gltSeq = gltSeq;
	}

	public TxnDetailType getTxnDetailType() {
		return txnDetailType;
	}

	public void setTxnDetailType(TxnDetailType txnDetailType) {
		this.txnDetailType = txnDetailType;
	}
	
}

