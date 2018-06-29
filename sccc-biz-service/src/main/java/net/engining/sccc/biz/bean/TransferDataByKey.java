package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.List;

import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;

public class TransferDataByKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TxnDetailType txnDetailType;
	
	private List<String> keys;

	public TxnDetailType getTxnDetailType() {
		return txnDetailType;
	}

	public void setTxnDetailType(TxnDetailType txnDetailType) {
		this.txnDetailType = txnDetailType;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
	
	

}
