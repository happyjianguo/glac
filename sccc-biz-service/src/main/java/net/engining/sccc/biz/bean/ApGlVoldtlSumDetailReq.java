package net.engining.sccc.biz.bean;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;


public class ApGlVoldtlSumDetailReq  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  科目
	 */
	@NotBlank
	private String subjectCd;
	
	/**
	 * 辅助核算类型
	 */
	@NotBlank
	private AssistAccountingType assistType;
	
	
	@NotBlank
	private String assistAccountData;
	
	public AssistAccountingType getAssistType() {
		return assistType;
	}

	public void setAssistType(AssistAccountingType assistType) {
		this.assistType = assistType;
	}

	public String getAssistAccountData() {
		return assistAccountData;
	}

	public void setAssistAccountData(String assistAccountData) {
		this.assistAccountData = assistAccountData;
	}

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}
	
}
