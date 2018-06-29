package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;

public class HistoricalSubjectDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date postDate;//记账日期
	private String gltSeq;//记账流水号
	private String postDesc;//记账摘要
	private RedBlueInd redBlueInd;
	private TxnDirection txnDirection;
	private BigDecimal postAmount;
	private String acctSeq;
	private String assistAccountData;
	private String opratorId;
	private String checkedId;
	
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getGltSeq() {
		return gltSeq;
	}
	public void setGltSeq(String gltSeq) {
		this.gltSeq = gltSeq;
	}
	public String getPostDesc() {
		return postDesc;
	}
	public void setPostDesc(String volDesc) {
		this.postDesc = volDesc;
	}
	public RedBlueInd getRedBlueInd() {
		return redBlueInd;
	}
	public void setRedBlueInd(RedBlueInd redBlueInd) {
		this.redBlueInd = redBlueInd;
	}
	public TxnDirection getTxnDirection() {
		return txnDirection;
	}
	public void setTxnDirection(TxnDirection txnDirection) {
		this.txnDirection = txnDirection;
	}
	public BigDecimal getPostAmount() {
		return postAmount;
	}
	public void setPostAmount(BigDecimal subjAmount) {
		this.postAmount = subjAmount;
	}
	public String getAcctSeq() {
		return acctSeq;
	}
	public void setAcctSeq(String acctSeq) {
		this.acctSeq = acctSeq;
	}
	public String getAssistAccountData() {
		return assistAccountData;
	}
	public void setAssistAccountData(String assistAccountData) {
		this.assistAccountData = assistAccountData;
	}
	public String getOpratorId() {
		return opratorId;
	}
	public void setOpratorId(String operaId) {
		this.opratorId = operaId;
	}
	public String getCheckedId() {
		return checkedId;
	}
	public void setCheckedId(String checkedId) {
		this.checkedId = checkedId;
	}
	
}
