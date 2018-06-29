package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pg.support.db.querydsl.Range;

public class TransQueryReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 交易日期
	 */
	private Date transDate;

	/*
	 * 录入操作员
	 */
	private String checkerId;

	/**
	 * 当前操作员
	 */
	@NotBlank
	private String currOperateId;

	@NotBlank
	private Range range;

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}

	public String getCurrOperateId() {
		return currOperateId;
	}

	public void setCurrOperateId(String currOperateId) {
		this.currOperateId = currOperateId;
	}

}
