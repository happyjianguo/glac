package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.engining.pcx.cc.infrastructure.shared.enums.PostTxnTypeDef;

public class AccountDetailRes implements Serializable{

	/**
	 *记账明细查询
	 */
	private static final long serialVersionUID = 1L;

	private Date txnDate;//交易日期
	private String txnTime;//交易时间
	private Date postDate;//记账日期
	private PostTxnTypeDef txnType;//交易类型
	private String typeDesc;//交易描述
	private String txnSeq;//交易流水号
	private String postSeq;//入账流水号
	private String productCode;//产品代码
	private String iouNo;//借据号
	private String custNo;//客户号
	private BigDecimal postAmount;//记账金额
	
	
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public PostTxnTypeDef getTxnType() {
		return txnType;
	}
	public void setTxnType(PostTxnTypeDef txnType) {
		this.txnType = txnType;
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
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getIouNo() {
		return iouNo;
	}
	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public BigDecimal getPostAmount() {
		return postAmount;
	}
	public void setPostAmount(BigDecimal postAmount) {
		this.postAmount = postAmount;
	}
	
}
