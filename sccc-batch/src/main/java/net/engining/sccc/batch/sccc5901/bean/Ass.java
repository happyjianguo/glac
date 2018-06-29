package net.engining.sccc.batch.sccc5901.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Ass {
	/**
	 * 辅助核算类型
	 */
	@XmlElement(name = "pk_Checktype")
	private String pkCheckType;
	/**
	 * 辅助核算值
	 */
	@XmlElement(name = "pk_Checkvalue")
	private String pkCheckValue;
	
	
	public Ass() {
		super();
		this.pkCheckType = "";
		this.pkCheckValue = "";
	}
	public String getPkCheckType() {
		return pkCheckType;
	}
	public void setPkCheckType(String pkCheckType) {
		this.pkCheckType = pkCheckType;
	}
	public String getPkCheckValue() {
		return pkCheckValue;
	}
	public void setPkCheckValue(String pkCheckValue) {
		this.pkCheckValue = pkCheckValue;
	}
	
	
}
