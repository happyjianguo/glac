package net.engining.sccc.batch.sccc5901.bean;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "voucher")
public class Voucher {
	@XmlElement(name = "voucher_head")
	private VoucherHead head;

	public VoucherHead getHead() {
		return head;
	}

	public void setHead(VoucherHead head) {
		this.head = head;
	}
	
}
