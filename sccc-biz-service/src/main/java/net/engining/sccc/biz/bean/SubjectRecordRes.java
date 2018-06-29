package net.engining.sccc.biz.bean;

import java.util.List;

import net.engining.pcx.cc.param.model.enums.AmtDbCrFlag;
import net.engining.pcx.cc.param.model.enums.BalDbCrFlag;
import net.engining.pcx.cc.param.model.enums.SubjectType;

public class SubjectRecordRes {
    private String subjectCd;
    private String name;
    private String description;
    private BalDbCrFlag balDbCrFlag;
    private AmtDbCrFlag amtDbCrFlag;
    private SubjectType type;
    private String currCd;
    private String parentSubjectCd;
    private Boolean enable;
    private String subjectCode;
    private Boolean isOverdraft;
    private String subjectHierarchy;
    private Boolean isLast;
    private List<String> assistList;
	private Boolean isAccount;

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BalDbCrFlag getBalDbCrFlag() {
		return balDbCrFlag;
	}

	public void setBalDbCrFlag(BalDbCrFlag balDbCrFlag) {
		this.balDbCrFlag = balDbCrFlag;
	}

	public AmtDbCrFlag getAmtDbCrFlag() {
		return amtDbCrFlag;
	}

	public void setAmtDbCrFlag(AmtDbCrFlag amtDbCrFlag) {
		this.amtDbCrFlag = amtDbCrFlag;
	}

	public SubjectType getType() {
		return type;
	}

	public void setType(SubjectType type) {
		this.type = type;
	}

	public String getCurrCd() {
		return currCd;
	}

	public void setCurrCd(String currCd) {
		this.currCd = currCd;
	}

	public String getParentSubjectCd() {
		return parentSubjectCd;
	}

	public void setParentSubjectCd(String parentSubjectCd) {
		this.parentSubjectCd = parentSubjectCd;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Boolean getIsOverdraft() {
		return isOverdraft;
	}

	public void setIsOverdraft(Boolean isOverdraft) {
		this.isOverdraft = isOverdraft;
	}

	public String getSubjectHierarchy() {
		return subjectHierarchy;
	}

	public void setSubjectHierarchy(String subjectHierarchy) {
		this.subjectHierarchy = subjectHierarchy;
	}

	public Boolean getIsLast() {
		return isLast;
	}

	public void setIsLast(Boolean isLast) {
		this.isLast = isLast;
	}

	public List<String> getAssistList() {
		return assistList;
	}

	public void setAssistList(List<String> assistList) {
		this.assistList = assistList;
	}

	public Boolean getIsAccount() {
		return isAccount;
	}

	public void setIsAccount(Boolean isAccount) {
		this.isAccount = isAccount;
	}
	
	
}
