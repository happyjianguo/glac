package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckFlagDef;

public class TransOprHstDetailRes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  交易日期
	 */
	private Date operDate;
	/**
	 * 记账日期
	 */
	private Date checkDate;
	/**
	 * 记账说明
	 */
	private String accountDesc;
	/**
	 * 拒绝原因
	 */
	private String refuseReason;
	/**
	 * 状态
	 * 
	 */
	private CheckFlagDef checkFlagDef;
	/**
	 * 操作员
	 */
	private String operaId;
	/**
	 * 复核员
	 */
	private String checkerId;
	/**
	 * 凭证次数
	 */
	private Integer sum;
	
	private List<TransOprHstDetail> list;
	
	public Date getOperDate() {
		return operDate;
	}
	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getAccountDesc() {
		return accountDesc;
	}
	public void setAccountDesc(String accountDesc) {
		this.accountDesc = accountDesc;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	public CheckFlagDef getCheckFlagDef() {
		return checkFlagDef;
	}
	public void setCheckFlagDef(CheckFlagDef checkFlagDef) {
		this.checkFlagDef = checkFlagDef;
	}
	public String getOperaId() {
		return operaId;
	}
	public void setOperaId(String operaId) {
		this.operaId = operaId;
	}
	public String getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	public List<TransOprHstDetail> getList() {
		return list;
	}
	public void setList(List<TransOprHstDetail> list) {
		this.list = list;
	}
}
