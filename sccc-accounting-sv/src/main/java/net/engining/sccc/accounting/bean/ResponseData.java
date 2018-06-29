package net.engining.sccc.accounting.bean;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pg.web.BaseResponseBean;

public class ResponseData extends BaseResponseBean {

	/**
	 * 还款
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 返回结果码
	 */
	@NotBlank
	private String returnCode;
	/**
	 * 返回结果描述
	 */
	@NotBlank
	private String returnDesc;
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnDesc() {
		return returnDesc;
	}
	public void setReturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
	}
	
}
