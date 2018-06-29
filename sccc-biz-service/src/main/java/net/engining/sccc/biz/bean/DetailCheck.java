package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;

public class DetailCheck implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String txnDate;//交易日期
	private Date postDateInfo;//记账日期转化
	private String postDate;//记账日期
	private String txnSeq;//交易流水号
	private String cactPostSeq;//交易流水主键
	private String postSeq;//记账流水号
	private String txnType;//交易类型
	private BigDecimal txnAmount;//交易总金额
	private BigDecimal postAmount;//记账总金额
	private CheckAccountStatusDef errorReason;//差错原因
	private int acctSeq;//账户编号
	
	public String getCactPostSeq() {
		return cactPostSeq;
	}
	public void setCactPostSeq(String cactPostSeq) {
		this.cactPostSeq = cactPostSeq;
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
	public void setPostDateInfo(Date postDateInfo) {
		this.postDateInfo = postDateInfo;
	}
	public Date getPostDateInfo() {
		return postDateInfo;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public String getTxnSeq() {
		return txnSeq;
	}
	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
	}
	public String getPostSeq() {
		return postSeq;
	}
	public void setPostSeq(String postSeq) {
		this.postSeq = postSeq;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
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
	public CheckAccountStatusDef getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(CheckAccountStatusDef errorReason) {
		this.errorReason = errorReason;
	}
	public int getAcctSeq() {
		return acctSeq;
	}
	public void setAcctSeq(int acctSeq) {
		this.acctSeq = acctSeq;
	}
}
