package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class AcctIouNo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String iouNo;//借据号
	private int acctSeq;//账户编号
	private String txnDate;//交易日期
	private String txnDesc;//交易摘要
	private BigDecimal txnAmount;
	private String txnDetailSeq;
	private String postDate;
	
	
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnDesc() {
		return txnDesc;
	}
	public void setTxnDesc(String txnDesc) {
		this.txnDesc = txnDesc;
	}
	public BigDecimal getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getTxnDetailSeq() {
		return txnDetailSeq;
	}
	public void setTxnDetailSeq(String txnDetailSeq) {
		this.txnDetailSeq = txnDetailSeq;
	}
	public String getIouNo() {
		return iouNo;
	}
	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}
	public int getAcctSeq() {
		return acctSeq;
	}
	public void setAcctSeq(int acctSeq) {
		this.acctSeq = acctSeq;
	}
}
