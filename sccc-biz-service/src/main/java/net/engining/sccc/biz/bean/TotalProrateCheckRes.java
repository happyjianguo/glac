package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.engining.gm.infrastructure.enums.TxnDirection;

public class TotalProrateCheckRes implements Serializable{

	/**
	 * 试算平衡功能中的总分核对
	 */
	private static final long serialVersionUID = 1L;
	
	private Date bizDate;//业务日期
	private String subjectNo;//科目号
	private String subjectName;//科目名称
	private String beginDirection;//期初余额方向
	private BigDecimal beginAmount;//总账期初金额
	private BigDecimal crAmt;//总账贷方金额
	private BigDecimal dbAmt;//总账借方金额
	private BigDecimal detailDbAmt;//明细账借方金额
	private BigDecimal detailcrAmt;//明细账贷方金额
	private String endDirection;//期末余额方向
	private BigDecimal endAmount;//总账期末余额
	private BigDecimal factEndAmount;//实际期末余额
	private BigDecimal difference;//差额
	public Date getBizDate() {
		return bizDate;
	}
	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getBeginDirection() {
		return beginDirection;
	}
	public void setBeginDirection(String beginDirection) {
		this.beginDirection = beginDirection;
	}
	public BigDecimal getBeginAmount() {
		return beginAmount;
	}
	public void setBeginAmount(BigDecimal beginAmount) {
		this.beginAmount = beginAmount;
	}
	public BigDecimal getCrAmt() {
		return crAmt;
	}
	public void setCrAmt(BigDecimal crAmt) {
		this.crAmt = crAmt;
	}
	public BigDecimal getDbAmt() {
		return dbAmt;
	}
	public void setDbAmt(BigDecimal dbAmt) {
		this.dbAmt = dbAmt;
	}
	public BigDecimal getDetailDbAmt() {
		return detailDbAmt;
	}
	public void setDetailDbAmt(BigDecimal detailDbAmt) {
		this.detailDbAmt = detailDbAmt;
	}
	public BigDecimal getDetailcrAmt() {
		return detailcrAmt;
	}
	public void setDetailcrAmt(BigDecimal detailcrAmt) {
		this.detailcrAmt = detailcrAmt;
	}
	public String getEndDirection() {
		return endDirection;
	}
	public void setEndDirection(String endDirection) {
		this.endDirection = endDirection;
	}
	public BigDecimal getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}
	public BigDecimal getFactEndAmount() {
		return factEndAmount;
	}
	public void setFactEndAmount(BigDecimal factEndAmount) {
		this.factEndAmount = factEndAmount;
	}
	public BigDecimal getDifference() {
		return difference;
	}
	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}
	
}
