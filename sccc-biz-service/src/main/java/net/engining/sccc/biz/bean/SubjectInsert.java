package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pcx.cc.param.model.enums.AmtDbCrFlag;
import net.engining.pcx.cc.param.model.enums.BalDbCrFlag;
import net.engining.pcx.cc.param.model.enums.SubjectType;
import net.engining.pg.support.meta.PropertyInfo;

public class SubjectInsert implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 科目编号
     */
	@NotBlank
    private String subjectCd;

    /**
     * 科目名称
     */
	@NotBlank
    private String name;
    
    /**
     * 描述
     */
    private String description;

    /**
     * 余额方向
     * D - 借方余额
     * C - 贷方余额
     * B - 按轧差金额
     * D - 双向余额
     */
    @NotBlank
    private BalDbCrFlag balDbCrFlag;

    /**
     * 交易允许发生方向
     * D - 只允许借记发生额
     * C - 只允许贷记发生额
     * B - 双向发生额
     */
    @NotBlank
    private AmtDbCrFlag amtDbCrFlag;

    /**
     * 科目类型
     * A - 资产类
     * B - 负债类
     * C - 损益类
     * D - 共同类
     * E - 所有者权益
     * F - 表外类(账户呆账类)
     */
    @NotBlank
    private SubjectType type;
    
    /**
     * 币种
     */
    private String currCd;
    
    /**
     * 父科目编号
     */
    private String parentSubjectCd;
    
    /**
     * 科目是否启用
     */
    private Boolean enable;
    
    /**
     * 科目表中的编号，一般用于保存行内的科目号，以便以后按照行内的科目表出会计流水
     */
    private String subjectCode;
    
    /**
     * 是否允许透支
     */
    @NotBlank
    private Boolean isOverdraft;
    
    /**
     * 科目层级
     */
    @NotBlank
    private String subjectHierarchy;
    
    /**
     * 是否末级
     */
    @NotBlank
    private Boolean isLast;
    
    /**
     * 辅助核算项
     */
    private List<String> auxiliaryAssist;
    
    /**
     * 是否参与对账
     */
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

	public List<String> getAuxiliaryAssist() {
		return auxiliaryAssist;
	}

	public void setAuxiliaryAssist(List<String> auxiliaryAssist) {
		this.auxiliaryAssist = auxiliaryAssist;
	}

	public Boolean getIsAccount() {
		return isAccount;
	}

	public void setIsAccount(Boolean isAccount) {
		this.isAccount = isAccount;
	}
    
    
}
