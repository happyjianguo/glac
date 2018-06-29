package net.engining.sccc.accounting.sharding.controller.bean;

import java.util.Date;

import net.engining.pg.support.db.querydsl.Range;
import net.engining.pg.web.BaseRequestBean;

/**
 * @author luxue
 *
 */
public class ApGlVolDtlHstRequestBean extends BaseRequestBean {

	private static final long serialVersionUID = 1L;

	String crSubjectCd;
	String dbSubjectCd;
	Date volDate;
	Range range;

	public Date getVolDate() {
		return volDate;
	}

	public void setVolDate(Date volDate) {
		this.volDate = volDate;
	}

	public String getCrSubjectCd() {
		return crSubjectCd;
	}

	public void setCrSubjectCd(String crSubjectCd) {
		this.crSubjectCd = crSubjectCd;
	}

	public String getDbSubjectCd() {
		return dbSubjectCd;
	}

	public void setDbSubjectCd(String dbSubjectCd) {
		this.dbSubjectCd = dbSubjectCd;
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

}
