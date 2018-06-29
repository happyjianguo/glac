package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import net.engining.pcx.cc.infrastructure.shared.enums.InOutFlagDef;

public class TxnOperateReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 操作员Id
	 */
	@NotBlank
	@Length(max = 40)
	private String operaId;
	
	/**
	 *  表内外标志
	 */
	@NotNull
	@Length(max = 1)
	private InOutFlagDef inOutFlag;
	
	/*
	 * 记账说明
	 */
	@NotBlank
	@Length(max = 80)
	private String accountDesc;
	
	/*
	 * 记账摘要/入账描述
	 */
	@NotBlank
	@Length(max = 80)
	private String postDesc;
	
	private List<TxnOperate> list;
	
	public String getOperaId() {
		return operaId;
	}

	public void setOperaId(String operaId) {
		this.operaId = operaId;
	}

	public InOutFlagDef getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(InOutFlagDef inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	public String getAccountDesc() {
		return accountDesc;
	}

	public void setAccountDesc(String accountDesc) {
		this.accountDesc = accountDesc;
	}

	public String getPostDesc() {
		return postDesc;
	}

	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}

	public List<TxnOperate> getList() {
		return list;
	}

	public void setList(List<TxnOperate> list) {
		this.list = list;
	}
}
