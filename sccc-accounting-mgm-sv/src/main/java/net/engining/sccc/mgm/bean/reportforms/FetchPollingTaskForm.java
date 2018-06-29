package net.engining.sccc.mgm.bean.reportforms;

import java.io.Serializable;

import net.engining.pcx.cc.infrastructure.shared.enums.ReportTypeDef;
import net.engining.pg.support.db.querydsl.Range;

/**
 * 
 * @author liqingfeng
 *
 */
public class FetchPollingTaskForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 报表类型
	 */
	private ReportTypeDef reportTypeDef;

	/**
	 * 分页查询范围
	 */
	private Range range;

	public ReportTypeDef getReportTypeDef() {
		return reportTypeDef;
	}

	public void setReportTypeDef(ReportTypeDef reportTypeDef) {
		this.reportTypeDef = reportTypeDef;
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

}
