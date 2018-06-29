package net.engining.sccc.batch.sccc0901.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO 字段基本检查
 * @author luxue
 *
 */
public class DirectPostLedgerFileInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String postCode;
	
	BigDecimal postAmt;
	
	Date clearDate;
	
	String channeId;
	
	String prodId;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(postCode);
		buffer.append(postAmt.toString());
		buffer.append(clearDate.toString());
		buffer.append(channeId);
		buffer.append(prodId);
		return buffer.toString().hashCode();
	}
	
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public BigDecimal getPostAmt() {
		return postAmt;
	}

	public void setPostAmt(BigDecimal postAmt) {
		this.postAmt = postAmt;
	}

	public Date getClearDate() {
		return clearDate;
	}

	public void setClearDate(Date clearDate) {
		this.clearDate = clearDate;
	}

	public String getChanneId() {
		return channeId;
	}

	public void setChanneId(String channeId) {
		this.channeId = channeId;
	}

}
