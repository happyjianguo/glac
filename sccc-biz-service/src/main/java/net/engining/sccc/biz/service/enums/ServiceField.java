package net.engining.sccc.biz.service.enums;

import net.engining.pg.support.meta.EnumInfo;
/**
 * @author wanglidong
 *  业务字段
 */
@EnumInfo({
	"ClearDate|清算日期",
	"TransSeq|交易流水号",
	"TransType|交易类型",
	"IsCunLoan|是否联合贷",
	"OrgNo|合作机构商户号",
	"AcctAmt|记账金额",
	"CunLoanPartiesNo|联合贷参与方商户号",
	"CunLoanAcctAmt|联合贷记账金额"
})
public enum ServiceField {
	/**
	 * 清算日期
	 */
	ClearDate, 
	/**
	 * 交易流水号
	 */
	TransSeq,
	/**
	 * 交易类型
	 */
	TransType,
	/**
	 * 是否联合贷
	 */
	IsCunLoan,
	/**
	 * 合作机构商户号
	 */
	OrgNo,
	/**
	 * 记账金额
	 */
	AcctAmt,
	/**
	 * 联合贷参与方商户号
	 */
	CunLoanPartiesNo,
	/**
	 * 联合贷记账金额
	 */
	CunLoanAcctAmt
}
