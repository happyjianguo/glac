package net.engining.sccc.biz.bean.batchBean;

import java.io.Serializable;

public class BizData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 业务字段Id
	 */
	private String keyId;
	/**
	 * 业务字段描述
	 */
	private String fieldDesc;
	/**
	 * 是否辅助核算项
	 */
	private boolean isAssisting;
	/**
	 * 值
	 */
	private String value;

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public boolean getIsAssisting() {
		return isAssisting;
	}

	public void setIsAssisting(boolean isAssisting) {
		this.isAssisting = isAssisting;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
