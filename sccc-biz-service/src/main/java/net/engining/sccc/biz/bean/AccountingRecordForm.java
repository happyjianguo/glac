package net.engining.sccc.biz.bean;

import java.util.Date;

import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pg.support.db.querydsl.Range;
/**
 * 当日会计分录查询
 * @author wenyeming
 *
 */
public class AccountingRecordForm {
	/**
	 * 科目号
	 */
	private String subject_cd;
	private String iouNo;//借据号
	
	private String custId;//客户号
	private Date beginDate;//开始日期
	private Date endDate;//结束日期
	private String dateType;//日期类型
	private Range range;
	private int acctSeq;//账户编号
	private String gltSeq;//入账流水号
	private TxnDetailType txnDetailType;//入账交易流水类型
	
	public TxnDetailType getTxnDetailType() {
		return txnDetailType;
	}
	public void setTxnDetailType(TxnDetailType txnDetailType) {
		this.txnDetailType = txnDetailType;
	}
	public int getAcctSeq() {
		return acctSeq;
	}
	public void setAcctSeq(int acctSeq) {
		this.acctSeq = acctSeq;
	}
	public String getGltSeq() {
		return gltSeq;
	}
	public void setGltSeq(String gltSeq) {
		this.gltSeq = gltSeq;
	}
	public Range getRange() {
		return range;
	}
	public void setRange(Range range) {
		this.range = range;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public String getSubject_cd() {
		return subject_cd;
	}
	public void setSubject_cd(String subject_cd) {
		this.subject_cd = subject_cd;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getIouNo() {
		return iouNo;
	}
	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	
}
