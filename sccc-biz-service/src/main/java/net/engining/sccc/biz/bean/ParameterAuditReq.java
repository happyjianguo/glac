package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.Date;

import net.engining.pg.support.db.querydsl.Range;

public class ParameterAuditReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date startDate;
	private Date endDate;
	private String mtnId;
	private String mtnUser;
	private Range range;
	public Range getRange() {
		return range;
	}
	public void setRange(Range range) {
		this.range = range;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getMtnId() {
		return mtnId;
	}
	public void setMtnId(String mtnId) {
		this.mtnId = mtnId;
	}
	public String getMtnUser() {
		return mtnUser;
	}
	public void setMtnUser(String mtnUser) {
		this.mtnUser = mtnUser;
	}

}
