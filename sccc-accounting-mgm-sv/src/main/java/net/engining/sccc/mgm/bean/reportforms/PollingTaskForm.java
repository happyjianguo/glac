package net.engining.sccc.mgm.bean.reportforms;

import java.io.Serializable;

import net.engining.pcx.cc.infrastructure.shared.enums.ReportTypeDef;
import net.engining.sccc.biz.bean.QueryCondition;
/**
 * 
 * @author liqingfeng
 *
 */
public class PollingTaskForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	/**
	 * 查询条件
	 */
	private QueryCondition queryCondition;

	/**
	 * 报表类型
	 */
	private ReportTypeDef reportTypeDef;

	public QueryCondition getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}

	public ReportTypeDef getReportTypeDef() {
		return reportTypeDef;
	}

	public void setReportTypeDef(ReportTypeDef reportTypeDef) {
		this.reportTypeDef = reportTypeDef;
	}

}
