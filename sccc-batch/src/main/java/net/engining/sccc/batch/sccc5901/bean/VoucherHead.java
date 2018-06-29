package net.engining.sccc.batch.sccc5901.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "voucher_head")
@XmlAccessorType(XmlAccessType.FIELD)
public class VoucherHead {
	/**
	 * 凭证主键  如果没有那就是新增，有就是修改 可空
	 */
	@XmlElement(name = "pk_voucher")
	private String pkVoucher;
	/**
	 * 凭证类别 非空   （凭证类别）
	 */
	@XmlElement(name = "pk_vouchertype")
	private String pkVoucherType;
	/**
	 * 会计年度
	 */
	@XmlElement(name = "year")
	private String year;
	/**
	 * 来源系统
	 */
	@XmlElement(name = "pk_system")
	private String pkSystem;
	/**
	 * 凭证类型值 0：正常凭证 3：数量调整凭证
	 */
	@XmlElement(name = "voucherkind")
	private String voucherKind;
	/**
	 * 核算账簿
	 */
	@XmlElement(name = "pk_accountingbook")
	private String pkAccountIngBook;
	/**
	 * 作废标志
	 */
	@XmlElement(name = "discardflag")
	private String disCardFlag;
	/**
	 * 会计期间
	 */
	@XmlElement(name = "period")
	private String period;
	/**
	 * 凭证号
	 */
	@XmlElement(name = "no")
	private String NO;
	/**
	 * 附单据数
	 */
	@XmlElement(name = "attachment")
	private String attachment;
	/**
	 * 制单日期
	 */
	@XmlElement(name = "prepareddate")
	private Date preparedDate;
	/**
	 * 制单人
	 */
	@XmlElement(name = "pk_prepared")
	private String pkPrepared;
	/**
	 * 出纳
	 */
	@XmlElement(name = "pk_casher")
	private String pkCasher;
	/**
	 * 签字标志
	 */
	@XmlElement(name = "signflag")
	private String signFlag;
	/**
	 * 审核人
	 */
	@XmlElement(name = "pk_checked")
	private String pkChecked;
	/**
	 * 记账日期
	 */
	@XmlElement(name = "tallydate")
	private String tallyDate;
	/**
	 * 记账人
	 */
	@XmlElement(name = "pk_manager")
	private String pkManager;
	/**
	 * 
	 */
	@XmlElement(name = "memo1")
	private String memo1;
	/**
	 * 
	 */
	@XmlElement(name = "memo2")
	private String memo2;
	/**
	 * 
	 */
	@XmlElement(name = "reserve1")
	private String reserve1;
	/**
	 * 
	 */
	@XmlElement(name = "reserve2")
	private String reserve2;
	/**
	 * 所属组织
	 */
	@XmlElement(name = "pk_org")
	private String pkOrg;
	/**
	 * 所属组织版本
	 */
	@XmlElement(name = "pk_org_v")
	private String pkOrgV;
	/**
	 * 所集团
	 */
	@XmlElement(name = "pk_group")
	private String pkGroup;
	
	@XmlElement(name = "details")
	private Details details;
	
	

	public VoucherHead() {
		super();
		this.pkVoucher = "";
		this.pkVoucherType = "99";
		this.year = new SimpleDateFormat("yyyy").format(new Date());
		this.pkSystem = "1221";
		this.voucherKind = "0";
		this.pkAccountIngBook = "10-0001";
		this.disCardFlag = "N";
		this.period = new SimpleDateFormat("MM").format(new Date());
		this.NO = "";
		this.attachment = "";
		this.preparedDate = new Date();
		this.pkPrepared = "hx";
		this.pkCasher = "";
		this.signFlag = "";
		this.pkChecked = "";
		this.tallyDate = "";
		this.pkManager = "";
		this.memo1 = "";
		this.memo2 = "";
		this.reserve1 = "";
		this.reserve2 = "";
		this.pkOrg = "10";
		this.pkOrgV = "";
		this.pkGroup = "0001";
		this.details = new Details();
	}

	public String getPkVoucher() {
		return pkVoucher;
	}

	public void setPkVoucher(String pkVoucher) {
		this.pkVoucher = pkVoucher;
	}

	public String getPkVoucherType() {
		return pkVoucherType;
	}

	public void setPkVoucherType(String pkVoucherType) {
		this.pkVoucherType = pkVoucherType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPkSystem() {
		return pkSystem;
	}

	public void setPkSystem(String pkSystem) {
		this.pkSystem = pkSystem;
	}

	public String getVoucherKind() {
		return voucherKind;
	}

	public void setVoucherKind(String voucherKind) {
		this.voucherKind = voucherKind;
	}

	public String getPkAccountIngBook() {
		return pkAccountIngBook;
	}

	public void setPkAccountIngBook(String pkAccountIngBook) {
		this.pkAccountIngBook = pkAccountIngBook;
	}

	public String getDisCardFlag() {
		return disCardFlag;
	}

	public void setDisCardFlag(String disCardFlag) {
		this.disCardFlag = disCardFlag;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getNO() {
		return NO;
	}

	public void setNO(String nO) {
		NO = nO;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Date getPreparedDate() {
		return preparedDate;
	}

	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	public String getPkPrepared() {
		return pkPrepared;
	}

	public void setPkPrepared(String pkPrepared) {
		this.pkPrepared = pkPrepared;
	}

	public String getPkCasher() {
		return pkCasher;
	}

	public void setPkCasher(String pkCasher) {
		this.pkCasher = pkCasher;
	}

	public String getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}

	public String getPkChecked() {
		return pkChecked;
	}

	public void setPkChecked(String pkChecked) {
		this.pkChecked = pkChecked;
	}

	public String getTallyDate() {
		return tallyDate;
	}

	public void setTallyDate(String tallyDate) {
		this.tallyDate = tallyDate;
	}

	public String getPkManager() {
		return pkManager;
	}

	public void setPkManager(String pkManager) {
		this.pkManager = pkManager;
	}

	public String getMemo1() {
		return memo1;
	}

	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}

	public String getMemo2() {
		return memo2;
	}

	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getPkOrg() {
		return pkOrg;
	}

	public void setPkOrg(String pkOrg) {
		this.pkOrg = pkOrg;
	}

	public String getPkOrgV() {
		return pkOrgV;
	}

	public void setPkOrgV(String pkOrgV) {
		this.pkOrgV = pkOrgV;
	}

	public String getPkGroup() {
		return pkGroup;
	}

	public void setPkGroup(String pkGroup) {
		this.pkGroup = pkGroup;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	
	
}
