package net.engining.sccc.biz.bean;

import java.math.BigDecimal;
import java.sql.Blob;


import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;

public class AccountRecord {

	private RedBlueInd redBlueInd;//红蓝字标识
	private String crsubjectCd;//科目号
	private String dbsubjectCd;
	private String subjectName;//科目名称
	private TxnDirection txnDirection;//借贷方向
	private BigDecimal postAmount;
	private String assistAccountData;
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public RedBlueInd getRedBlueInd() {
		return redBlueInd;
	}
	public void setRedBlueInd(RedBlueInd redBlueInd) {
		this.redBlueInd = redBlueInd;
	}
	public String getCrsubjectCd() {
		return crsubjectCd;
	}
	public void setCrsubjectCd(String crsubjectCd) {
		this.crsubjectCd = crsubjectCd;
	}
	public String getDbsubjectCd() {
		return dbsubjectCd;
	}
	public void setDbsubjectCd(String dbsubjectCd) {
		this.dbsubjectCd = dbsubjectCd;
	}
	public TxnDirection getTxnDirection() {
		return txnDirection;
	}
	public void setTxnDirection(TxnDirection txnDirection) {
		this.txnDirection = txnDirection;
	}
	public BigDecimal getPostAmount() {
		return postAmount;
	}
	public void setPostAmount(BigDecimal postAmount) {
		this.postAmount = postAmount;
	}
	public String getAssistAccountData() {
		return assistAccountData;
	}
	public void setAssistAccountData(String assistAccountData) {
		this.assistAccountData = assistAccountData;
	}
}
