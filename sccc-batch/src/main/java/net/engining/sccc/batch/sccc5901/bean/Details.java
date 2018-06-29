package net.engining.sccc.batch.sccc5901.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class Details {
	/**
	 * 借方分录
	 */
	@XmlElement(name = "item")
	private Debit debit;
	/**
	 * 贷方分录
	 */
	@XmlElement(name = "item")
	private Credit credit;
	
	
	public Details() {
		super();
		this.debit = new Debit();
		this.credit = new Credit();
	}
	public Debit getDebit() {
		return debit;
	}
	public void setDebit(Debit debit) {
		this.debit = debit;
	}
	public Credit getCredit() {
		return credit;
	}
	public void setCredit(Credit credit) {
		this.credit = credit;
	}
	
	
}
