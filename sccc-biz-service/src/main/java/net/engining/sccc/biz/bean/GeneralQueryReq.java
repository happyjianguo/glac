package net.engining.sccc.biz.bean;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import net.engining.pcx.cc.infrastructure.shared.enums.InOutFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.SubjectLevelDef;
import net.engining.pg.support.db.querydsl.Range;

public class GeneralQueryReq {
	@NotBlank
	@Length(max=1)
	private SubjectLevelDef subjectLevel;
	@NotBlank
	@Length(max=1)
	private InOutFlagDef inOutFlag;
	private Date beginDate;
	private Date endDate;
	private Range range;
	
	public Range getRange() {
		return range;
	}
	public void setRange(Range range) {
		this.range = range;
	}
	public SubjectLevelDef getSubjectLevel() {
		return subjectLevel;
	}
	public void setSubjectLevel(SubjectLevelDef subjectLevel) {
		this.subjectLevel = subjectLevel;
	}
	public InOutFlagDef getInOutFlag() {
		return inOutFlag;
	}
	public void setInOutFlag(InOutFlagDef inOutFlag) {
		this.inOutFlag = inOutFlag;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
