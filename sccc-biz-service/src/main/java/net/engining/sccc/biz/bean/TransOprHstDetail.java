package net.engining.sccc.biz.bean;

import java.math.BigDecimal;
import java.sql.Blob;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;

public class TransOprHstDetail {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
	  *  红蓝字
	  */
    private RedBlueInd redBlueInd;
    
    /**
     * 借贷方向
     */
    private TxnDirection txnDirection;
    /**
     * 科目号
     */
    private String dbsubject;
    
    private String subjectName;
   
	/**
     * 记账金额
     */
    private BigDecimal postAmount;
    
    /*
     * 账务核算项
     */
    private String assistAccountData;
    
    public String getAssistAccountData() {
		return assistAccountData;
	}
	public void setAssistAccountData(String assistAccountData) {
		this.assistAccountData = assistAccountData;
	}
	
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
	public TxnDirection getTxnDirection() {
		return txnDirection;
	}
	public void setTxnDirection(TxnDirection txnDirection) {
		this.txnDirection = txnDirection;
	}
	public String getDbsubject() {
		return dbsubject;
	}
	public void setDbsubject(String dbsubject) {
		this.dbsubject = dbsubject;
	}

	public BigDecimal getPostAmount() {
		return postAmount;
	}
	public void setPostAmount(BigDecimal postAmount) {
		this.postAmount = postAmount;
	}
}
