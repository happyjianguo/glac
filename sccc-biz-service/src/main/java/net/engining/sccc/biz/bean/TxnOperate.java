package net.engining.sccc.biz.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.enums.InOutFlagDef;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;

public class TxnOperate {

	
	/**
	 * 复核员Id
	 */
	@NotBlank
	@Length(max=40)
	private String checkerId;
	
	/**
	 * 表内外标志
	 */
	@NotBlank
	@Length(max = 1)
	private InOutFlagDef inOutFlag;

	/*
	 * 借方科目号
	 */
	@Length(max = 40)
	private String subjectCd;

	/*
	 * 红蓝字
	 */
	@NotBlank
	@Length(max = 1)
	private RedBlueInd redBlueInd;

	/*
	 * 记账金额
	 */
	@NotBlank
	private BigDecimal postAmount;

	/*
	 * 借贷标志
	 */
	@NotBlank
	@Length(max = 1)
	private TxnDirection txnDirection;

	/*
	 * 辅助核算项
	 */
	@NotNull
	private List<AssistAccountData> assistAccountData;

	public InOutFlagDef getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(InOutFlagDef inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	public RedBlueInd getRedBlueInd() {
		return redBlueInd;
	}

	public void setRedBlueInd(RedBlueInd redBlueInd) {
		this.redBlueInd = redBlueInd;
	}

	public BigDecimal getPostAmount() {
		return postAmount;
	}

	public void setPostAmount(BigDecimal postAmount) {
		this.postAmount = postAmount;
	}

	public TxnDirection getTxnDirection() {
		return txnDirection;
	}

	public void setTxnDirection(TxnDirection txnDirection) {
		this.txnDirection = txnDirection;
	}

	public List<AssistAccountData> getAssistAccountData() {
		return assistAccountData;
	}

	public void setAssistAccountData(List<AssistAccountData> assistAccountData) {
		this.assistAccountData = assistAccountData;
	}

}
