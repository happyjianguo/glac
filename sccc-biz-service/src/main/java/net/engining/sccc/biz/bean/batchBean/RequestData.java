package net.engining.sccc.biz.bean.batchBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import net.engining.sccc.biz.enums.AccountTradingDef;
import net.engining.sccc.biz.enums.SysInternalAcctionCdDef;

public class RequestData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 产品参数唯一标识
	 */
	@NotBlank
	private String productId;
	/**
	 * 客户唯一标识
	 */
	@NotBlank
	private String custId;
	/**
	 * 联机对账交易
	 */
	private AccountTradingDef accountTrading;  
	/**
	 * 账户编号
	 */
	private Integer acctSeq;
	/**
	 * 借据号
	 */
	private String iouNo;
	
	  /**
     *交易总金额
     */
    private BigDecimal totalAmt;
    
    /**
     * 信用额度
     */
    private BigDecimal creditLimitAmt;
    /**
     * 货币类型
     */
    @NotBlank
    private String currency;
    /**
     * 当前结转日期
     */
    private Date carryOverDate;
  
    /**
     * 贷款总期数
     */
    @NotBlank
    private Integer totalPeriod;
    /**
     * 上次结转日期
     */
    private Date lastCarryOverDate;
    /**
     * 当前最高账龄
     */
    private Integer maxAgeCd;
    /**
     * 历史最高账龄
     */
    private Integer hisMaxAgeCd;
    /**
     * 当前账龄
     */
    private Integer ageCd;
    /**
     * 交易前账龄
     */
    private Integer ageCdBefore;
    /**
     * 内部账交易代码
     */
    private SysInternalAcctionCdDef sysInternalAcctActionCd;
    /**
     * 是否联合贷
     */
    @NotBlank
    private Boolean isUnion;
	/**
	 * 交易前余额成分
	 */
	private List<SubAcctData> beforeSubAcctData;
	/**
	 * 余额成分
	 */
	@NotEmpty
	private List<SubAcctData> subAcctData;
	/**
	 * 业务字段
	 */
	private List<BizData> bizData;
	/**
	 * 联合贷数据
	 */
	private List<UnionData> unionData;

	public Integer getTotalPeriod() {
		return totalPeriod;
	}
	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public Integer getAcctSeq() {
		return acctSeq;
	}
	public void setAcctSeq(Integer acctSeq) {
		this.acctSeq = acctSeq;
	}
	public String getIouNo() {
		return iouNo;
	}
	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public BigDecimal getCreditLimitAmt() {
		return creditLimitAmt;
	}
	public void setCreditLimitAmt(BigDecimal creditLimitAmt) {
		this.creditLimitAmt = creditLimitAmt;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getCarryOverDate() {
		return carryOverDate;
	}
	public void setCarryOverDate(Date carryOverDate) {
		this.carryOverDate = carryOverDate;
	}
	public Date getLastCarryOverDate() {
		return lastCarryOverDate;
	}
	public void setLastCarryOverDate(Date lastCarryOverDate) {
		this.lastCarryOverDate = lastCarryOverDate;
	}
	public Integer getMaxAgeCd() {
		return maxAgeCd;
	}
	public void setMaxAgeCd(Integer maxAgeCd) {
		this.maxAgeCd = maxAgeCd;
	}
	public Integer getHisMaxAgeCd() {
		return hisMaxAgeCd;
	}
	public void setHisMaxAgeCd(Integer hisMaxAgeCd) {
		this.hisMaxAgeCd = hisMaxAgeCd;
	}
	public Integer getAgeCd() {
		return ageCd;
	}
	public void setAgeCd(Integer ageCd) {
		this.ageCd = ageCd;
	}
	public Integer getAgeCdBefore() {
		return ageCdBefore;
	}
	public void setAgeCdBefore(Integer ageCdBefore) {
		this.ageCdBefore = ageCdBefore;
	}
	public Boolean getIsUnion() {
		return isUnion;
	}
	public void setIsUnion(Boolean isUnion) {
		this.isUnion = isUnion;
	}
	public SysInternalAcctionCdDef getSysInternalAcctActionCd() {
		return sysInternalAcctActionCd;
	}
	public void setSysInternalAcctActionCd(SysInternalAcctionCdDef sysInternalAcctActionCd) {
		this.sysInternalAcctActionCd = sysInternalAcctActionCd;
	}
	public AccountTradingDef getAccountTrading() {
		return accountTrading;
	}
	public void setAccountTrading(AccountTradingDef accountTrading) {
		this.accountTrading = accountTrading;
	}
	public List<SubAcctData> getBeforeSubAcctData() {
		return beforeSubAcctData;
	}
	public void setBeforeSubAcctData(List<SubAcctData> beforeSubAcctData) {
		this.beforeSubAcctData = beforeSubAcctData;
	}
	public List<SubAcctData> getSubAcctData() {
		return subAcctData;
	}
	public void setSubAcctData(List<SubAcctData> subAcctData) {
		this.subAcctData = subAcctData;
	}
	public List<BizData> getBizData() {
		return bizData;
	}
	public void setBizData(List<BizData> bizData) {
		this.bizData = bizData;
	}
	public List<UnionData> getUnionData() {
		return unionData;
	}
	public void setUnionData(List<UnionData> unionData) {
		this.unionData = unionData;
	}
	
	
}
