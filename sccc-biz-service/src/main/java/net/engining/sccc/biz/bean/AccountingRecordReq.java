package net.engining.sccc.biz.bean;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import net.engining.pcx.cc.infrastructure.shared.enums.PostTxnTypeDef;
import net.engining.pg.support.db.querydsl.Range;

public class AccountingRecordReq implements Serializable{
	
	/**
	 * 当日会计分录查询
	 */
	private static final long serialVersionUID = 1L;
	@Length(max=36)
	private String iouNo;//借据号
	@Length(max=64)
	private String custId;//客户号
	
	private PostTxnTypeDef type;//交易类型
	private String postSeq;
	private String txnSeq;
	private Range range;
	
	
	public PostTxnTypeDef getType() {
		return type;
	}

	public void setType(PostTxnTypeDef type) {
		this.type = type;
	}

	public String getPostSeq() {
		return postSeq;
	}

	public void setPostSeq(String postSeq) {
		this.postSeq = postSeq;
	}

	public String getTxnSeq() {
		return txnSeq;
	}

	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
	}

	public String getIouNo() {
		return iouNo;
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}


	
}

