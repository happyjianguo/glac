package net.engining.sccc.biz.bean;

import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;

public class AssistAccountData {

	/**
	 * 辅助核算类型
	 */
	private AssistAccountingType key;

	/**s
	 * 辅助核算描述
	 */
	private String assistAccountingDesc;

	public AssistAccountingType getKey() {
		return key;
	}

	public void setKey(AssistAccountingType key) {
		this.key = key;
	}

	public String getAssistAccountingDesc() {
		return assistAccountingDesc;
	}

	public void setAssistAccountingDesc(String assistAccountingDesc) {
		this.assistAccountingDesc = assistAccountingDesc;
	}

}
