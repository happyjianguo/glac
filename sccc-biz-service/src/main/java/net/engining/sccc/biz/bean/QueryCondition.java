package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import net.engining.pcx.cc.infrastructure.shared.enums.InOutFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.PostTxnTypeDef;
import net.engining.sccc.biz.enums.DateTypeDef;

/**
 * Trans报表查询条件
 * 
 * @author liqingfeng
 *
 */
public class QueryCondition implements Serializable {

	/**
	 *      
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 日期类型
	 */
	@NotBlank
	private DateTypeDef dateType;

	/**
	 * 交易类型
	 * tradeType
	 */
	private PostTxnTypeDef postTxnTypeDef;

	/**
	 * 科目
	 */
	private String subjectNumber;

	/**
	 * 表内外标志
	 */
	private InOutFlagDef inOutFlag;

	/**
	 * 开始时间
	 */
	@NotBlank
	private Date startDate;

	/**
	 * 结束时间
	 */
	@NotBlank
	private Date endDate;

	public String getSubjectNumber() {
		return subjectNumber;
	}

	public void setSubjectNumber(String subjectNumber) {
		this.subjectNumber = subjectNumber;
	}

	public DateTypeDef getDateType() {
		return dateType;
	}

	public void setDateType(DateTypeDef dateType) {
		this.dateType = dateType;
	}

	public PostTxnTypeDef getPostTxnTypeDef() {
		return postTxnTypeDef;
	}

	public void setPostTxnTypeDef(PostTxnTypeDef postTxnTypeDef) {
		this.postTxnTypeDef = postTxnTypeDef;
	}

	public InOutFlagDef getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(InOutFlagDef inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
