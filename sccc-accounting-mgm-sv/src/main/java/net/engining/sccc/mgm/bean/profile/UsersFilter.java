package net.engining.sccc.mgm.bean.profile;

import java.io.Serializable;

import net.engining.pg.support.db.querydsl.Range;

public class UsersFilter implements Serializable{

	private static final long serialVersionUID = 4603644745622122258L;
	private String branchId;
	private Range range;
	
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public Range getRange() {
		return range;
	}
	public void setRange(Range range) {
		this.range = range;
	}
	
}
