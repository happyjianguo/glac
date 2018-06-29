package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.List;

import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.enums.SubjectType;
import net.engining.pg.support.db.querydsl.Range;

public class SubjectRecordReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String subjectCd;
	private SubjectType type;
	private Boolean status;
	private Range range;
	
	public Range getRange() {
		return range;
	}
	public void setRange(Range range) {
		this.range = range;
	}
	public String getSubjectCd() {
		return subjectCd;
	}
	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}
	public SubjectType getType() {
		return type;
	}
	public void setType(SubjectType type) {
		this.type = type;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
