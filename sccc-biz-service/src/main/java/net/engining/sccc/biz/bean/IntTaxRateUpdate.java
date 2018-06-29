package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.sccc.biz.service.enums.InvolceType;
import net.engining.sccc.biz.service.enums.TaxpayerAttribute;

public class IntTaxRateUpdate implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 币种数字代码
	 */
	@NotBlank
	private String taxCd;

    /**
	 * 应税项目
	 */
	@NotBlank
	private String taxableItem;

	 /**
     * 是否代扣利息税
     */
	@NotBlank
	private Boolean withHoldingInt;
	
	/**
	 * 是否开票
	 */
	@NotBlank
	private Boolean isInvoice;

	/**
	 * 开票类型
	 */
	@NotBlank
	private InvolceType involceType;

	/**
	 * 本方纳税人属性
	 */
	@NotBlank
	private TaxpayerAttribute taxpayerAttribute;

	/**
     * 折算本币汇率
     * 用于购汇和账务计算的汇率
     */
	@NotBlank
    private BigDecimal taxRt;
    
    /**
     * 科目编号
     */
	@NotBlank
    private String subjectCd;

	public String getTaxCd() {
		return taxCd;
	}

	public void setTaxCd(String taxCd) {
		this.taxCd = taxCd;
	}

	public String getTaxableItem() {
		return taxableItem;
	}

	public void setTaxableItem(String taxableItem) {
		this.taxableItem = taxableItem;
	}

	public Boolean getWithHoldingInt() {
		return withHoldingInt;
	}

	public void setWithHoldingInt(Boolean withHoldingInt) {
		this.withHoldingInt = withHoldingInt;
	}

	public Boolean getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(Boolean isInvoice) {
		this.isInvoice = isInvoice;
	}

	public InvolceType getInvolceType() {
		return involceType;
	}

	public void setInvolceType(InvolceType involceType) {
		this.involceType = involceType;
	}

	public TaxpayerAttribute getTaxpayerAttribute() {
		return taxpayerAttribute;
	}

	public void setTaxpayerAttribute(TaxpayerAttribute taxpayerAttribute) {
		this.taxpayerAttribute = taxpayerAttribute;
	}

	public BigDecimal getTaxRt() {
		return taxRt;
	}

	public void setTaxRt(BigDecimal taxRt) {
		this.taxRt = taxRt;
	}

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}
	
	
}
