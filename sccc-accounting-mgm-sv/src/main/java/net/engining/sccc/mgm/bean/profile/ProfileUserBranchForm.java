package net.engining.sccc.mgm.bean.profile;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pg.support.db.querydsl.Range;

/**
 * 
 * @author liqingfeng
 *
 */
public class ProfileUserBranchForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户姓名
	 */
	private String name;
	@NotBlank
	private String branchId;

	private Range range;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
