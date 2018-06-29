package net.engining.sccc.biz.bean;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;


import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.biz.enums.DateTypeDef;

public class HistoricalaccountingRecordReq extends AccountingRecordReq{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	private Date beginDate;//开始日期
	@NotBlank
	private Date endDate;//结束日期
	
	private DateTypeDef dateType;//日期类型
	
	private Range range;
	
	private List<AcctIouNo> list;
	
	public List<AcctIouNo> getList() {
		return list;
	}
	public void setList(List<AcctIouNo> list) {
		this.list = list;
	}
	
	public DateTypeDef getDateType() {
		return dateType;
	}
	public void setDateType(DateTypeDef dateType) {
		this.dateType = dateType;
	}
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
}

