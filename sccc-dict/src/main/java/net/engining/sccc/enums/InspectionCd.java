package net.engining.sccc.enums;

import net.engining.pg.support.meta.EnumInfo;

@EnumInfo({
	"CheckTrade|对账交易检查",
	"NQHAcct|拿去花记账交易检查",
	"ZYAcct|中银记账交易检查",
	"InterestProvision|利息计提记账交易检查",
	"PaymentRecord|罚息计收记账交易检查",
	"BalCarriedForward|余额成分结转记账交易检查",
	"RepaymentAcct|批量还款记账交易检查"
})
public enum InspectionCd {
	/**
	 * 对账交易检查
	 */
	CheckTrade, 
	/**
	 * 拿去花记账交易检查
	 */
	NQHAcct,
	/**
	 * 中银记账交易检查
	 */
	ZYAcct, 
	/**
	 * 利息计提记账交易检查
	 */
	InterestProvision,
	/**
	 * 罚息计收记账交易检查
	 */
	PaymentRecord, 
	/**
	 * 余额成分结转记账交易检查
	 */
	BalCarriedForward,
	/**
	 * 批量还款记账交易检查
	 */
	RepaymentAcct
}
