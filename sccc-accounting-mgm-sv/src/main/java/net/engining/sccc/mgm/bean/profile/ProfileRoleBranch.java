package net.engining.sccc.mgm.bean.profile;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pg.support.db.querydsl.Range;

/**
 * 
 * @author liqingfeng
 *
 */
public class ProfileRoleBranch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	private String branchId;
	@NotBlank
	private String roleName;

	private Range range;

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

}
