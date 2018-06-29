package net.engining.sccc.accounting.bean.limit;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pg.web.BaseResponseBean;
import net.engining.sccc.biz.bean.batchBean.BizData;
import net.engining.sccc.biz.bean.batchBean.UnionData;
import net.engining.sccc.biz.enums.SysInternalAcctionCdDef;

public class AccountLimitRequestData extends BaseResponseBean {
	
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
     * 信用额度
     */
    private BigDecimal creditLimitAmt;
    /**
     * 货币类型
     */
    @NotBlank
    private String currency;
    /**
     * 是否联合贷
     */
    @NotBlank
    private Boolean isUnion;
    /**
     * 内部账交易代码
     */
    private SysInternalAcctionCdDef sysInternalAcctActionCd;
	/**
	 * 业务字段
	 */
	private List<BizData> bizData;
	/**
	 * 联合贷数据
	 */
	private List<UnionData> unionData;
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
	public Boolean getIsUnion() {
		return isUnion;
	}
	public void setIsUnion(Boolean isUnion) {
		this.isUnion = isUnion;
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
	public SysInternalAcctionCdDef getSysInternalAcctActionCd() {
		return sysInternalAcctActionCd;
	}
	public void setSysInternalAcctActionCd(SysInternalAcctionCdDef sysInternalAcctActionCd) {
		this.sysInternalAcctActionCd = sysInternalAcctActionCd;
	}
	
	

}
