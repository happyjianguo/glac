package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.engining.pcx.cc.infrastructure.shared.enums.PostTxnTypeDef;
import net.engining.pg.support.db.querydsl.Range;

public class HistoricalCondition implements Serializable{

	/**
	 * 历史记账明细查询第二部分查询条件
	 */
	private static final long serialVersionUID = 1L;
	private Date endDate;
	private String postSeq;
	private String txnSeq;
	private PostTxnTypeDef type;
	private Range range;
	private List<Map<String,Object>> list;
	
	
	
	public PostTxnTypeDef getType() {
		return type;
	}
	public void setType(PostTxnTypeDef type) {
		this.type = type;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public Range getRange() {
		return range;
	}
	public void setRange(Range range) {
		this.range = range;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	
}
