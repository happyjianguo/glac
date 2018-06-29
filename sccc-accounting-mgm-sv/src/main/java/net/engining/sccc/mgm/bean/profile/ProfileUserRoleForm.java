package net.engining.sccc.mgm.bean.profile;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author liqingfeng
 *
 */
public class ProfileUserRoleForm {

	@NotBlank
	private String puId;
	
	private String roleStr;

	public String getPuId() {
		return puId;
	}

	public void setPuId(String puId) {
		this.puId = puId;
	}

	public String getRoleStr() {
		return roleStr;
	}

	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}

}
