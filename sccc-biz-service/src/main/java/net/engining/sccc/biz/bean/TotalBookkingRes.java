package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckFlagDef;

public class TotalBookkingRes implements Serializable{

	/**
	 * 综合记账响应bean
	 */
	private static final long serialVersionUID = 1L;

	private String txnDate;//交易日期
	private String postDate;//记账日期
	private String txnSeq;//交易流水号
	private String postSeq;//记账流水号
	private String postDesc;//记账摘要
	private BigDecimal postAmount;//记账金额
	private CheckFlagDef status;//状态
	private String refuseReason;//拒绝原因
	private String operaId;//录入号
	private String checkerId;//复核号
	private String printVoucherCount;
	private String txnType;//交易类型
	
	
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
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
	public String getPostDesc() {
		return postDesc;
	}
	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}
	public BigDecimal getPostAmount() {
		return postAmount;
	}
	public void setPostAmount(BigDecimal postAmount) {
		this.postAmount = postAmount;
	}
	public CheckFlagDef getStatus() {
		return status;
	}
	public void setStatus(CheckFlagDef status) {
		this.status = status;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	public String getOperaId() {
		return operaId;
	}
	public void setOperaId(String operaId) {
		this.operaId = operaId;
	}
	public String getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}
	public String getPrintVoucherCount() {
		return printVoucherCount;
	}
	public void setPrintVoucherCount(String printVoucherCount) {
		this.printVoucherCount = printVoucherCount;
	}
}
