package net.engining.sccc.biz.bean.batchBean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class EveryDayAccountingBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 服务提供系统标识
	 * 
	 */
	@NotBlank
	private String svPrId;
	/**
	 * 渠道Id
	 */
	@NotBlank
	private String channelId;
	/**
	 * 渠道签名
	 */
	private String channelSign;
	/**
	 * 清算日期
	 * 
	 */
	private Date clearDate;

	/**
	 * 流水号
	 */
	@NotBlank
	private String txnSerialNo;

	/**
	 * 
	 * 交易日期
	 */
	@NotBlank
	private Date timestamp;
	/**
	 * 异步接口标识
	 */
	private String asynInd;

	/**
	 * 请求数据
	 */
	@NotNull
	private RequestData requestData;

	public String getSvPrId() {
		return svPrId;
	}

	public void setSvPrId(String svPrId) {
		this.svPrId = svPrId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelSign() {
		return channelSign;
	}

	public void setChannelSign(String channelSign) {
		this.channelSign = channelSign;
	}

	public Date getClearDate() {
		return clearDate;
	}

	public void setClearDate(Date clearDate) {
		this.clearDate = clearDate;
	}

	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getAsynInd() {
		return asynInd;
	}

	public void setAsynInd(String asynInd) {
		this.asynInd = asynInd;
	}

	public RequestData getRequestData() {
		return requestData;
	}

	public void setRequestData(RequestData requestData) {
		this.requestData = requestData;
	}


}
