package net.engining.sccc.batch.sccc5901.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
@XmlAccessorType(XmlAccessType.FIELD)
public class Credit {
	/**
	 * 分录号
	 */
	@XmlElement(name = "detailindex")
	private String detailIndex;
	/**
	 * 摘要
	 */
	@XmlElement(name = "explanation")
	private String explanation;
	/**
	 * 业务日期
	 */
	@XmlElement(name = "verifydate")
	private Date verifyDate;
	/**
	 * 单价
	 */
	@XmlElement(name = "price")
	private String price;
	/**
	 * 折本汇率
	 */
	@XmlElement(name = "excrate2")
	private String excrate2;
	/**
	 * 贷方数量
	 */
	@XmlElement(name = "creditquantity")
	private int creditQuantity;
	/**
	 * 原币贷方金额
	 */
	@XmlElement(name = "creditamount")
	private BigDecimal creditAmount;
	/**
	 * 本币贷方金额
	 */
	@XmlElement(name = "localcreditamount")
	private BigDecimal localCreditAmount;
	/**
	 * 集团本币贷方金额
	 */
	@XmlElement(name = "groupcreditamount")
	private BigDecimal groupCreditAmount;
	/**
	 * 全局本币贷方金额
	 */
	@XmlElement(name = "globalcreditamount")
	private BigDecimal globalCreditAmount;
	/**
	 * 币种
	 */
	@XmlElement(name = "pk_currtype")
	private String pkCurrType;
	/**
	 * 科目
	 */
	@XmlElement(name = "pk_accasoa")
	private String pkAccasoa;
	/**
	 * 所属二级核算单位
	 */
	@XmlElement(name = "pk_unit")
	private String pkUnit;
	/**
	 * 所属二级核算单位版本
	 */
	@XmlElement(name = "pk_unit_v")
	private String pkUnitV;
	/**
	 * 辅助核算
	 */
	@XmlElementWrapper(name = "ass")
	@XmlElement(name = "item")
	private List<Ass> ass;
	/**
	 * 资金流动
	 */
	@XmlElementWrapper(name = "cashFlow")
	@XmlElement(name = "item")
	private List<CashFlow> cashFlow;
	
	
	
	public Credit() {
		super();
		this.detailIndex = "";
		this.explanation = "";
		this.verifyDate = null;
		this.price = "";
		this.excrate2 = "";
		this.creditQuantity = 0;
		this.creditAmount = BigDecimal.ZERO;
		this.localCreditAmount = BigDecimal.ZERO;
		this.groupCreditAmount = BigDecimal.ZERO;
		this.globalCreditAmount = BigDecimal.ZERO;
		this.pkCurrType = "156";
		this.pkAccasoa = "";
		this.pkUnit = "";
		this.pkUnitV = "";
		//this.ass = new ArrayList<Ass>();
		//this.cashFlow = new ArrayList<CashFlow>();
	}
	public String getDetailIndex() {
		return detailIndex;
	}
	public void setDetailIndex(String detailIndex) {
		this.detailIndex = detailIndex;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public Date getVerifyDate() {
		return verifyDate;
	}
	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getExcrate2() {
		return excrate2;
	}
	public void setExcrate2(String excrate2) {
		this.excrate2 = excrate2;
	}
	public int getCreditQuantity() {
		return creditQuantity;
	}
	public void setCreditQuantity(int creditQuantity) {
		this.creditQuantity = creditQuantity;
	}
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	public BigDecimal getLocalCreditAmount() {
		return localCreditAmount;
	}
	public void setLocalCreditAmount(BigDecimal localCreditAmount) {
		this.localCreditAmount = localCreditAmount;
	}
	public BigDecimal getGroupCreditAmount() {
		return groupCreditAmount;
	}
	public void setGroupCreditAmount(BigDecimal groupCreditAmount) {
		this.groupCreditAmount = groupCreditAmount;
	}
	public BigDecimal getGlobalCreditAmount() {
		return globalCreditAmount;
	}
	public void setGlobalCreditAmount(BigDecimal globalCreditAmount) {
		this.globalCreditAmount = globalCreditAmount;
	}
	public String getPkCurrType() {
		return pkCurrType;
	}
	public void setPkCurrType(String pkCurrType) {
		this.pkCurrType = pkCurrType;
	}
	public String getPkAccasoa() {
		return pkAccasoa;
	}
	public void setPkAccasoa(String pkAccasoa) {
		this.pkAccasoa = pkAccasoa;
	}
	public String getPkUnit() {
		return pkUnit;
	}
	public void setPkUnit(String pkUnit) {
		this.pkUnit = pkUnit;
	}
	public String getPkUnitV() {
		return pkUnitV;
	}
	public void setPkUnitV(String pkUnitV) {
		this.pkUnitV = pkUnitV;
	}
	public List<Ass> getAss() {
		return ass;
	}
	public void setAss(List<Ass> ass) {
		this.ass = ass;
	}
	public List<CashFlow> getCashFlow() {
		return cashFlow;
	}
	public void setCashFlow(List<CashFlow> cashFlow) {
		this.cashFlow = cashFlow;
	}
	
	
}
