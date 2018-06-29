package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckFlagDef;
import net.engining.pg.support.db.querydsl.Range;

public class TransOprHstReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  交易日期
	 */
	@NotBlank
	private Date operDate;
	/**
	 * 记账流水号
	 */
	private String txnDetailSeq;
	//复核标志
	private CheckFlagDef checkFlagDef;
	
	private Range range;
	
	public Range getRange() {
		return range;
	}
	public void setRange(Range range) {
		this.range = range;
	}
	public Date getOperDate() {
		return operDate;
	}
	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}
	public CheckFlagDef getCheckFlagDef() {
		return checkFlagDef;
	}
	public void setCheckFlagDef(CheckFlagDef checkFlagDef) {
		this.checkFlagDef = checkFlagDef;
	}
	public String getTxnDetailSeq() {
		return txnDetailSeq;
	}
	public void setTxnDetailSeq(String txnDetailSeq) {
		this.txnDetailSeq = txnDetailSeq;
	}

}
