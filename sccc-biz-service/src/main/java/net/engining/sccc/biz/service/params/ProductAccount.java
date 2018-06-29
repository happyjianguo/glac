package net.engining.sccc.biz.service.params;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.gm.infrastructure.enums.BusinessType;
import net.engining.pcx.cc.param.model.enums.Deadline;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 长短期类型
	 */
	private Deadline deadline;
	/**
	 * 账户ID
	 */
	private String accountId;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Deadline getDeadline() {
		return deadline;
	}
	public void setDeadline(Deadline deadline) {
		this.deadline = deadline;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getKey() {
    	return key(productId, deadline);
    }
    
    public static String key(String productId, Deadline deadline) {
    	return productId + "|" + deadline ;
    }
}
