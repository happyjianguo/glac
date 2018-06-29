package net.engining.sccc.biz.bean;

import java.util.Date;

import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;
import net.engining.pg.support.db.querydsl.Range;

public class ApGlVoldtlSumReq{


	private Range range;

	/**
	 * 开始时间
	 */
	private Date volStartDt;
	/**
	 * 结束时间
	 */
	private Date volEndDt;

	/**
	 * 科目
	 */
	private String subjectCd;
	/**
	 * 辅助核算项类型
	 */
	private AssistAccountingType assistType;
	/**
	 * 排序方式
	 */
	// @NotBlank
	private SortDef sortDef;

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public Date getVolStartDt() {
		return volStartDt;
	}

	public void setVolStartDt(Date volStartDt) {
		this.volStartDt = volStartDt;
	}

	public Date getVolEndDt() {
		return volEndDt;
	}

	public void setVolEndDt(Date volEndDt) {
		this.volEndDt = volEndDt;
	}

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	public AssistAccountingType getAssistType() {
		return assistType;
	}

	public void setAssistType(AssistAccountingType assistType) {
		this.assistType = assistType;
	}

	public SortDef getSortDef() {
		return sortDef;
	}

	public void setSortDef(SortDef sortDef) {
		this.sortDef = sortDef;
	}

}
