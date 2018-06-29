package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.Date;
import net.engining.pg.support.db.querydsl.Range;
import org.hibernate.validator.constraints.NotBlank;

public class TrialReqBean implements Serializable{
	/**
	 * 试算处理请求bean
	 */
	private static final long serialVersionUID = 1L;
	private Date beginDate;//开始日期
	@NotBlank
	private Date endDate;//结束日期
	private String subjectNo;//科目号
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
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	
}
