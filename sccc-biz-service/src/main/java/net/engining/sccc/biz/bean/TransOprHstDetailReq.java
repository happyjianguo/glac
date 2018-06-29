package net.engining.sccc.biz.bean;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;

public class TransOprHstDetailReq implements Serializable{

	/**
	 *   
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Length(max = 64)
	private String gltSeq;
	
	@NotBlank
	@Length(max = 64)
	private String seq;
	
	@NotBlank
	@Length(max = 1)
	private TxnDetailType txnDetailType;
	
	public TxnDetailType getTxnDetailType() {
		return txnDetailType;
	}

	public void setTxnDetailType(TxnDetailType txnDetailType) {
		this.txnDetailType = txnDetailType;
	}

	public String getGltSeq() {
		return gltSeq;
	}

	public void setGltSeq(String gltSeq) {
		this.gltSeq = gltSeq;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
}
