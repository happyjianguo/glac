package net.engining.sccc.accounting.bean.cost;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import net.engining.pg.web.BaseResponseBean;
import net.engining.sccc.biz.bean.batchBean.BizData;
import net.engining.sccc.biz.bean.batchBean.SubAcctData;
import net.engining.sccc.biz.bean.batchBean.UnionData;

public class CostAccountRequestData extends BaseResponseBean {
	
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
	 * 借据号
	 */
	private String iouNo;
	
	/**
	 * 账户编号
	 */
	private Integer acctSeq;
	
	/**
	 * 余额成分
	 */
	@NotEmpty
	private List<SubAcctData> subAcctData;
	/**
	 * 业务字段
	 */
	private List<BizData> bizData;
	/**
	 * 联合贷数据
	 */
	private List<UnionData> unionData;
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
	public String getIouNo() {
		return iouNo;
	}
	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}
	public Integer getAcctSeq() {
		return acctSeq;
	}
	public void setAcctSeq(Integer acctSeq) {
		this.acctSeq = acctSeq;
	}
	public List<SubAcctData> getSubAcctData() {
		return subAcctData;
	}
	public void setSubAcctData(List<SubAcctData> subAcctData) {
		this.subAcctData = subAcctData;
	}
	public List<UnionData> getUnionData() {
		return unionData;
	}
	public void setUnionData(List<UnionData> unionData) {
		this.unionData = unionData;
	}
	public List<BizData> getBizData() {
		return bizData;
	}
	public void setBizData(List<BizData> bizData) {
		this.bizData = bizData;
	}
	
}
