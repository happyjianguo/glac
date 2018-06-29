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
public class Debit {
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
	 * 借方数量
	 */
	@XmlElement(name = "debitquantity")
	private int debitQuantity;
	/**
	 * 原币借方金额
	 */
	@XmlElement(name = "debitamount")
	private BigDecimal debitAmount;
	/**
	 * 本币借方金额
	 */
	@XmlElement(name = "localdebitamount")
	private BigDecimal localDebitAmount;
	/**
	 * 集团本币借方金额
	 */
	@XmlElement(name = "groupdebitamount")
	private BigDecimal groupDebitAmount;
	/**
	 * 全局本币借方金额
	 */
	@XmlElement(name = "globaldebitamount")
	private BigDecimal globalDebitAmount;
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
	 * 欧盟vat导入
	 */
	@XmlElement(name = "vatdetail")
	private VatDetail vatDetail;
	/**
	 * 资金流动
	 */
	@XmlElementWrapper(name = "cashFlow")
	@XmlElement(name = "item")
	private List<CashFlow> cashFlow;
	
	
	
	public Debit() {
		super();
		this.detailIndex = "";
		this.explanation = "";
		this.verifyDate = null;
		this.price = "";
		this.excrate2 = "";
		this.debitQuantity = 0;
		this.debitAmount = BigDecimal.ZERO;
		this.localDebitAmount = BigDecimal.ZERO;
		this.groupDebitAmount = BigDecimal.ZERO;
		this.globalDebitAmount = BigDecimal.ZERO;
		this.pkCurrType = "156";
		this.pkAccasoa = "";
		this.pkUnit = "";
		this.pkUnitV = "";
		//this.ass = new ArrayList<Ass>();
		//this.vatDetail = new VatDetail();
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
	public int getDebitQuantity() {
		return debitQuantity;
	}
	public void setDebitQuantity(int debitQuantity) {
		this.debitQuantity = debitQuantity;
	}
	public BigDecimal getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}
	public BigDecimal getLocalDebitAmount() {
		return localDebitAmount;
	}
	public void setLocalDebitAmount(BigDecimal localDebitAmount) {
		this.localDebitAmount = localDebitAmount;
	}
	public BigDecimal getGroupDebitAmount() {
		return groupDebitAmount;
	}
	public void setGroupDebitAmount(BigDecimal groupDebitAmount) {
		this.groupDebitAmount = groupDebitAmount;
	}
	public BigDecimal getGlobalDebitAmount() {
		return globalDebitAmount;
	}
	public void setGlobalDebitAmount(BigDecimal globalDebitAmount) {
		this.globalDebitAmount = globalDebitAmount;
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
	public VatDetail getVatDetail() {
		return vatDetail;
	}
	public void setVatDetail(VatDetail vatDetail) {
		this.vatDetail = vatDetail;
	}
	public List<CashFlow> getCashFlow() {
		return cashFlow;
	}
	public void setCashFlow(List<CashFlow> cashFlow) {
		this.cashFlow = cashFlow;
	}
	
	
}
