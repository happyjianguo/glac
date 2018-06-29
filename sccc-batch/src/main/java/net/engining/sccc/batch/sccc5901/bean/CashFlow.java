package net.engining.sccc.batch.sccc5901.bean;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CashFlow {
	/**
	 * 币种
	 */
	@XmlElement(name = "m_pk_currtype")
	private String mPkCurrType;
	/**
	 * 原币
	 */
	@XmlElement(name = "money")
	private BigDecimal money;
	/**
	 * 全局本币
	 */
	@XmlElement(name = "moneyglobal")
	private Double moneyGlobal;
	/**
	 * 集团本币
	 */
	@XmlElement(name = "moneygroup")
	private Double moneyGroup;
	/**
	 * 本币
	 */
	@XmlElement(name = "moneymain")
	private BigDecimal moneyMain;
	/**
	 * 现金主键
	 */
	@XmlElement(name = "pk_cashflow")
	private String pkCashFlow;
	/**
	 * 内部单位主键
	 */
	@XmlElement(name = "pk_innercorp")
	private String pkInnercorp;
	
	
	public CashFlow() {
		super();
		this.mPkCurrType = "";
		this.money = BigDecimal.ZERO;
		this.moneyGlobal = 0.0;
		this.moneyGroup = 0.0;
		this.moneyMain = BigDecimal.ZERO;
		this.pkCashFlow = "";
		this.pkInnercorp = "";
	}
	public String getmPkCurrType() {
		return mPkCurrType;
	}
	public void setmPkCurrType(String mPkCurrType) {
		this.mPkCurrType = mPkCurrType;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Double getMoneyGlobal() {
		return moneyGlobal;
	}
	public void setMoneyGlobal(Double moneyGlobal) {
		this.moneyGlobal = moneyGlobal;
	}
	public Double getMoneyGroup() {
		return moneyGroup;
	}
	public void setMoneyGroup(Double moneyGroup) {
		this.moneyGroup = moneyGroup;
	}
	public BigDecimal getMoneyMain() {
		return moneyMain;
	}
	public void setMoneyMain(BigDecimal moneyMain) {
		this.moneyMain = moneyMain;
	}
	public String getPkCashFlow() {
		return pkCashFlow;
	}
	public void setPkCashFlow(String pkCashFlow) {
		this.pkCashFlow = pkCashFlow;
	}
	public String getPkInnercorp() {
		return pkInnercorp;
	}
	public void setPkInnercorp(String pkInnercorp) {
		this.pkInnercorp = pkInnercorp;
	}
	
	
}
