package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;

public class VodtlAssSumHstDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date postDate;
	private String txnDetailSeq;
	private Date transDate;
	private BigDecimal subjAmount;
	private TxnDirection txnDirection;//借贷方向
	private Integer volSeq;
	private String volDesc;
	private RedBlueInd redBlueInd;//红蓝字标识
	private String refNo;
	public RedBlueInd getRedBlueInd() {
		return redBlueInd;
	}
	public void setRedBlueInd(RedBlueInd redBlueInd) {
		this.redBlueInd = redBlueInd;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getTxnDetailSeq() {
		return txnDetailSeq;
	}
	public void setTxnDetailSeq(String txnDetailSeq) {
		this.txnDetailSeq = txnDetailSeq;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public BigDecimal getSubjAmount() {
		return subjAmount;
	}
	public void setSubjAmount(BigDecimal subjAmount) {
		this.subjAmount = subjAmount;
	}
	public TxnDirection getTxnDirection() {
		return txnDirection;
	}
	public void setTxnDirection(TxnDirection txnDirection) {
		this.txnDirection = txnDirection;
	}
	public Integer getVolSeq() {
		return volSeq;
	}
	public void setVolSeq(Integer volSeq) {
		this.volSeq = volSeq;
	}
	public String getVolDesc() {
		return volDesc;
	}
	public void setVolDesc(String volDesc) {
		this.volDesc = volDesc;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
}
