/**
 * 
 */
package net.engining.sccc.accounting.sharding.controller.bean;

import java.util.Date;

import net.engining.pg.support.db.querydsl.Range;
import net.engining.pg.web.BaseRequestBean;

/**
 * @author luxue
 *
 */
public class ApGlTxnRequestBean extends BaseRequestBean {

	private static final long serialVersionUID = 1L;

	Integer acctSeq;
	Date postDate;
	Range range;
 
	public Integer getAcctSeq() {
		return acctSeq;
	}

	public void setAcctSeq(Integer acctSeq) {
		this.acctSeq = acctSeq;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

}
