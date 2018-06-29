package net.engining.sccc.batch.sccc5701.bean;

import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;

public class AssistTypeAndData {
	/**
	 * 辅助核算项类型
	 */
	private AssistAccountingType type;
	/**
	 * 辅助核算项数据
	 */
	private String data;
	public AssistAccountingType getType() {
		return type;
	}
	public void setType(AssistAccountingType type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
