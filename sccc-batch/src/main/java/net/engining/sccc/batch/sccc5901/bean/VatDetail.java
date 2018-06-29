package net.engining.sccc.batch.sccc5901.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class VatDetail {
	/**
	 * 交易代码
	 */
	@XmlElement(name = "businesscode")
	private String businessCode;
	/**
	 * 收货国家
	 */
	@XmlElement(name = "pk_receivecountry")
	private String pkReceiveCountry;
	/**
	 * 供应商VAT码
	 */
	@XmlElement(name = "pk_suppliervatcode")
	private String pkSuppliervatCode;
	/**
	 * 税码
	 */
	@XmlElement(name = "pk_taxcode")
	private String pkTaxCode;
	/**
	 * 客户VAT码
	 */
	@XmlElement(name = "pk_clientvatcode")
	private String pkClientvatCode;
	/**
	 * 方向
	 */
	@XmlElement(name = "direction")
	private String direction;
	/**
	 * 税额
	 */
	@XmlElement(name = "moneyamount")
	private Double moneyAmount;
	/**
	 * 报税国家
	 */
	@XmlElement(name = "pk_vatcountry")
	private String pkVatCountry;
	/**
	 * 税额
	 */
	@XmlElement(name = "taxamount")
	private Double taxAmount;
	
	
	public VatDetail() {
		super();
		this.businessCode = "";
		this.pkReceiveCountry = "";
		this.pkSuppliervatCode = "";
		this.pkTaxCode = "";
		this.pkClientvatCode = "";
		this.direction = "";
		this.moneyAmount = 0.0;
		this.pkVatCountry = "";
		this.taxAmount = 0.0;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getPkReceiveCountry() {
		return pkReceiveCountry;
	}
	public void setPkReceiveCountry(String pkReceiveCountry) {
		this.pkReceiveCountry = pkReceiveCountry;
	}
	public String getPkSuppliervatCode() {
		return pkSuppliervatCode;
	}
	public void setPkSuppliervatCode(String pkSuppliervatCode) {
		this.pkSuppliervatCode = pkSuppliervatCode;
	}
	public String getPkTaxCode() {
		return pkTaxCode;
	}
	public void setPkTaxCode(String pkTaxCode) {
		this.pkTaxCode = pkTaxCode;
	}
	public String getPkClientvatCode() {
		return pkClientvatCode;
	}
	public void setPkClientvatCode(String pkClientvatCode) {
		this.pkClientvatCode = pkClientvatCode;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getPkVatCountry() {
		return pkVatCountry;
	}
	public void setPkVatCountry(String pkVatCountry) {
		this.pkVatCountry = pkVatCountry;
	}
	public Double getMoneyAmount() {
		return moneyAmount;
	}
	public void setMoneyAmount(Double moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	public Double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	
	
}
