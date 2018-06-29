package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;


public class SummaryBySubject implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 科目
	 */
	private String subjectCd;
	/**
	 * 借方红字金额
	 */
	private BigDecimal dbRedAmt;
	/**
	 * 借方正常金额
	 */
	private BigDecimal dbNorAmt;
	/**
	 * 借方蓝字金额
	 */
	private BigDecimal dbBlueAmt;
	/**
	 * 借方红字笔数
	 */
	private Integer dbRedCount;
	/**
	 * 借方正常笔数
	 */
	private Integer dbNorCount;
	/**
	 * 借方蓝字笔数
	 */
	private Integer dbBlueCount;
	/**
	 * 贷方红字金额
	 */
	private BigDecimal crRedAmt;
	/**
	 * 贷方正常金额
	 */
	private BigDecimal crNorAmt;
	/**
	 * 贷方蓝字金额
	 */
	private BigDecimal crBlueAmt;
	/**
	 * 贷方红字笔数
	 */
	private Integer crRedCount;
	/**
	 * 贷方正常笔数
	 */
	private Integer crNorCount;
	/**
	 * 贷方蓝字笔数
	 */
	private Integer crBlueCount;

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	public BigDecimal getDbRedAmt() {
		return dbRedAmt;
	}

	public void setDbRedAmt(BigDecimal dbRedAmt) {
		this.dbRedAmt = dbRedAmt;
	}

	public BigDecimal getDbNorAmt() {
		return dbNorAmt;
	}

	public void setDbNorAmt(BigDecimal dbNorAmt) {
		this.dbNorAmt = dbNorAmt;
	}

	public BigDecimal getDbBlueAmt() {
		return dbBlueAmt;
	}

	public void setDbBlueAmt(BigDecimal dbBlueAmt) {
		this.dbBlueAmt = dbBlueAmt;
	}

	public Integer getDbRedCount() {
		return dbRedCount;
	}

	public void setDbRedCount(Integer dbRedCount) {
		this.dbRedCount = dbRedCount;
	}

	public Integer getDbNorCount() {
		return dbNorCount;
	}

	public void setDbNorCount(Integer dbNorCount) {
		this.dbNorCount = dbNorCount;
	}

	public Integer getDbBlueCount() {
		return dbBlueCount;
	}

	public void setDbBlueCount(Integer dbBlueCount) {
		this.dbBlueCount = dbBlueCount;
	}

	public BigDecimal getCrRedAmt() {
		return crRedAmt;
	}

	public void setCrRedAmt(BigDecimal crRedAmt) {
		this.crRedAmt = crRedAmt;
	}

	public BigDecimal getCrNorAmt() {
		return crNorAmt;
	}

	public void setCrNorAmt(BigDecimal crNorAmt) {
		this.crNorAmt = crNorAmt;
	}

	public BigDecimal getCrBlueAmt() {
		return crBlueAmt;
	}

	public void setCrBlueAmt(BigDecimal crBlueAmt) {
		this.crBlueAmt = crBlueAmt;
	}

	public Integer getCrRedCount() {
		return crRedCount;
	}

	public void setCrRedCount(Integer crRedCount) {
		this.crRedCount = crRedCount;
	}

	public Integer getCrNorCount() {
		return crNorCount;
	}

	public void setCrNorCount(Integer crNorCount) {
		this.crNorCount = crNorCount;
	}

	public Integer getCrBlueCount() {
		return crBlueCount;
	}

	public void setCrBlueCount(Integer crBlueCount) {
		this.crBlueCount = crBlueCount;
	}
	
}
