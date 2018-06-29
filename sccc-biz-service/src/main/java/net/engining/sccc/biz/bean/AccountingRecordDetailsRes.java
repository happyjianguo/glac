package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AccountingRecordDetailsRes implements Serializable{

	/**
	 * 会计分录详情返回Bean
	 */
	private static final long serialVersionUID = 1L;
	private String gltSeq;//记账流水号
	private String txnSeq;//交易流水号
	private String custId;//客户号
	private String entryOperatorNo;//录入操作员号
	
	private String checkOperatorNo;//复核操作员号
	private Date operDate;//
	private String external;//外部流水号
	private List<AccountRecord> account;
	private Date postDate;
	
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public Date getOperDate() {
		return operDate;
	}
	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}
	public String getExternal() {
		return external;
	}
	public void setExternal(String external) {
		this.external = external;
	}
	public String getTxnSeq() {
		return txnSeq;
	}
	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
	}
	public String getGltSeq() {
		return gltSeq;
	}
	public void setGltSeq(String gltSeq) {
		this.gltSeq = gltSeq;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getEntryOperatorNo() {
		return entryOperatorNo;
	}
	public void setEntryOperatorNo(String entryOperatorNo) {
		this.entryOperatorNo = entryOperatorNo;
	}
	public String getCheckOperatorNo() {
		return checkOperatorNo;
	}
	public void setCheckOperatorNo(String checkOperatorNo) {
		this.checkOperatorNo = checkOperatorNo;
	}
	public List<AccountRecord> getAccount() {
		return account;
	}
	public void setAccount(List<AccountRecord> account) {
		this.account = account;
	}
	
}

