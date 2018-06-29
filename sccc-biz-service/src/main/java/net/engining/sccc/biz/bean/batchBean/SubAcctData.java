package net.engining.sccc.biz.bean.batchBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.engining.sccc.biz.enums.SubAcctTypeDef;

public class SubAcctData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 子账户类型
	 */
	private SubAcctTypeDef subAcctType;
	/**
	 * 业务类型
	 */
	private String businessTyoe;
	/**
	 * 币种
	 */
	private String currCd;
	/**
	 * 账期
	 */
	private Integer stmtHist;
	/**
	 * 期初余额
	 */
	private BigDecimal beginBal;
	/**
	 * 当前余额
	 */
	private BigDecimal currBal;
	/**
	 * 最小还款额
	 */
	private BigDecimal totDueAmt;
	/**
	 * 未决利息
	 */
	private BigDecimal intPending;
	/**
	 * 应收利息
	 */
	private BigDecimal intReceivable;
	/**
	 * 上次计息日期
	 */
	private Date lastComputingInterestDate;
	/**
	 * 上次利息计提日期
	 */
	private Date lastAccrualInterestDate;
	/**
	 * 计提利息
	 */
	private BigDecimal intAccrual;
	/**
	 * 上次罚息计息日期
	 */
	private Date lastPenalizedInterestDate;
	/**
	 * 批量前余额
	 */
	private BigDecimal endDayBal;
	/**
	 * 罚息余额
	 */
	private BigDecimal penalizedAmt;
	/**
	 * 罚息计提
	 */
	private BigDecimal intPenaltyAccrual;
	/**
	 * 上次罚息计提日期
	 */
	private Date lastAccrualintepenaltyDate;

	public SubAcctTypeDef getSubAcctType() {
		return subAcctType;
	}

	public void setSubAcctType(SubAcctTypeDef subAcctType) {
		this.subAcctType = subAcctType;
	}

	public String getBusinessTyoe() {
		return businessTyoe;
	}

	public void setBusinessTyoe(String businessTyoe) {
		this.businessTyoe = businessTyoe;
	}

	public String getCurrCd() {
		return currCd;
	}

	public void setCurrCd(String currCd) {
		this.currCd = currCd;
	}

	public Integer getStmtHist() {
		return stmtHist;
	}

	public void setStmtHist(Integer stmtHist) {
		this.stmtHist = stmtHist;
	}

	public BigDecimal getBeginBal() {
		return beginBal;
	}

	public void setBeginBal(BigDecimal beginBal) {
		this.beginBal = beginBal;
	}

	public BigDecimal getCurrBal() {
		return currBal;
	}

	public void setCurrBal(BigDecimal currBal) {
		this.currBal = currBal;
	}

	public BigDecimal getTotDueAmt() {
		return totDueAmt;
	}

	public void setTotDueAmt(BigDecimal totDueAmt) {
		this.totDueAmt = totDueAmt;
	}

	public BigDecimal getIntPending() {
		return intPending;
	}

	public void setIntPending(BigDecimal intPending) {
		this.intPending = intPending;
	}

	public BigDecimal getIntReceivable() {
		return intReceivable;
	}

	public void setIntReceivable(BigDecimal intReceivable) {
		this.intReceivable = intReceivable;
	}

	public Date getLastComputingInterestDate() {
		return lastComputingInterestDate;
	}

	public void setLastComputingInterestDate(Date lastComputingInterestDate) {
		this.lastComputingInterestDate = lastComputingInterestDate;
	}

	public Date getLastAccrualInterestDate() {
		return lastAccrualInterestDate;
	}

	public void setLastAccrualInterestDate(Date lastAccrualInterestDate) {
		this.lastAccrualInterestDate = lastAccrualInterestDate;
	}

	public BigDecimal getIntAccrual() {
		return intAccrual;
	}

	public void setIntAccrual(BigDecimal intAccrual) {
		this.intAccrual = intAccrual;
	}

	public Date getLastPenalizedInterestDate() {
		return lastPenalizedInterestDate;
	}

	public void setLastPenalizedInterestDate(Date lastPenalizedInterestDate) {
		this.lastPenalizedInterestDate = lastPenalizedInterestDate;
	}

	public BigDecimal getEndDayBal() {
		return endDayBal;
	}

	public void setEndDayBal(BigDecimal endDayBal) {
		this.endDayBal = endDayBal;
	}

	public BigDecimal getPenalizedAmt() {
		return penalizedAmt;
	}

	public void setPenalizedAmt(BigDecimal penalizedAmt) {
		this.penalizedAmt = penalizedAmt;
	}

	public BigDecimal getIntPenaltyAccrual() {
		return intPenaltyAccrual;
	}

	public void setIntPenaltyAccrual(BigDecimal intPenaltyAccrual) {
		this.intPenaltyAccrual = intPenaltyAccrual;
	}

	public Date getLastAccrualintepenaltyDate() {
		return lastAccrualintepenaltyDate;
	}

	public void setLastAccrualintepenaltyDate(Date lastAccrualintepenaltyDate) {
		this.lastAccrualintepenaltyDate = lastAccrualintepenaltyDate;
	}

}
