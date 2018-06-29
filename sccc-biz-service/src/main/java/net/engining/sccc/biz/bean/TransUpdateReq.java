package net.engining.sccc.biz.bean;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckFlagDef;

public class TransUpdateReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String operaId;
	
	public String getOperaId() {
		return operaId;
	}

	public void setOperaId(String operaId) {
		this.operaId = operaId;
	}
	
	// 复核标志
	@NotBlank
	private CheckFlagDef checkFlagDef;
	
	//总账交易操作历史表id
	@NotBlank
	private Integer seq;	
	
	//交易流水号
	@NotBlank
	private String gltSeq;
	
	//拒绝原因
	private String refuseReason;
	
	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getGltSeq() {
		return gltSeq;
	}


	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setGltSeq(String gltSeq) {
		this.gltSeq = gltSeq;
	}

	public CheckFlagDef getCheckFlagDef() {
		return checkFlagDef;
	}

	public void setCheckFlagDef(CheckFlagDef checkFlagDef) {
		this.checkFlagDef = checkFlagDef;
	}
	
}
