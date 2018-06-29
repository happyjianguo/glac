package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pcx.cc.infrastructure.shared.enums.InOutFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.SubjectLevelDef;
import net.engining.pg.support.db.querydsl.Range;

public class SubjectDetailForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SubjectLevelDef subjectLevel;
	private String subjectNo;
	@NotBlank
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
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
}
