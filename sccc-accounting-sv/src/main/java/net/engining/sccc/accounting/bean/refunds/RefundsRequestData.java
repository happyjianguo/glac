package net.engining.sccc.accounting.bean.refunds;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import net.engining.pg.web.BaseResponseBean;
import net.engining.sccc.biz.bean.batchBean.BizData;
import net.engining.sccc.biz.bean.batchBean.SubAcctData;

public class RefundsRequestData extends BaseResponseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 产品参数唯一标识
	 */
	@NotBlank
	private String productId;
	/**
	 * 客户唯一标识
	 */
	@NotBlank
	private String custId;
	/**
	 * 账户编号
	 */
	private Integer acctSeq;
	/**
	 * 借据号
	 */
	private String iouNo;
	
	  /**
     *交易总金额
     */
    private BigDecimal totalAmt;
    
	/**
	 * 交易前余额成分
	 */
	private List<SubAcctData> beforeSubAcctData;
	/**
	 * 余额成分
	 */
	@NotEmpty
	private List<SubAcctData> subAcctData;
	/**
	 * 业务字段
	 */
	private List<BizData> bizData;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public Integer getAcctSeq() {
		return acctSeq;
	}
	public void setAcctSeq(Integer acctSeq) {
		this.acctSeq = acctSeq;
	}
	public String getIouNo() {
		return iouNo;
	}
	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public List<SubAcctData> getBeforeSubAcctData() {
		return beforeSubAcctData;
	}
	public void setBeforeSubAcctData(List<SubAcctData> beforeSubAcctData) {
		this.beforeSubAcctData = beforeSubAcctData;
	}
	public List<SubAcctData> getSubAcctData() {
		return subAcctData;
	}
	public void setSubAcctData(List<SubAcctData> subAcctData) {
		this.subAcctData = subAcctData;
	}
	public List<BizData> getBizData() {
		return bizData;
	}
	public void setBizData(List<BizData> bizData) {
		this.bizData = bizData;
	}
	
}
