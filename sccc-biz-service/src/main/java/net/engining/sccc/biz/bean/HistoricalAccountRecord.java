package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.engining.pcx.cc.infrastructure.shared.enums.PostTypeDef;

public class HistoricalAccountRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String txnDate;//交易日期
	private String postDate;//记账日期
	private String gltSeq;//记账流水号
	private String txnDesc;//交易摘要
	private String postDesc;//记账摘要
	private PostTypeDef postType;
	private BigDecimal txnAmount;
	private BigDecimal postAmount;
	private String iouNo;
	private Integer acctSeq;
	private String txnDetailSeq;
	
	public PostTypeDef getPostType() {
		return postType;
	}
	public void setPostType(PostTypeDef postType) {
		this.postType = postType;
	}
	public Integer getAcctSeq() {
		return acctSeq;
	}
	public void setAcctSeq(Integer acctSeq) {
		this.acctSeq = acctSeq;
	}
	public String getTxnDetailSeq() {
		return txnDetailSeq;
	}
	public void setTxnDetailSeq(String txnDetailSeq) {
		this.txnDetailSeq = txnDetailSeq;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public String getGltSeq() {
		return gltSeq;
	}
	public void setGltSeq(String gltSeq) {
		this.gltSeq = gltSeq;
	}
	public String getTxnDesc() {
		return txnDesc;
	}
	public void setTxnDesc(String txnDesc) {
		this.txnDesc = txnDesc;
	}
	public String getPostDesc() {
		return postDesc;
	}
	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}
	public BigDecimal getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}
	public BigDecimal getPostAmount() {
		return postAmount;
	}
	public void setPostAmount(BigDecimal postAmount) {
		this.postAmount = postAmount;
	}
	public String getIouNo() {
		return iouNo;
	}
	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}
}
