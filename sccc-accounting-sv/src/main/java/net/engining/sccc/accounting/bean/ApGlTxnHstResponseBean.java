package net.engining.sccc.accounting.bean;

import java.util.List;
import java.util.Map;

import net.engining.pg.web.BaseResponseBean;

/**
 * @author luxue
 *
 */
public class ApGlTxnHstResponseBean extends BaseResponseBean {
	
	private static final long serialVersionUID = 1L;

	List<Map<String, Object>> apGlTxnHst;

	public List<Map<String, Object>> getApGlTxnHst() {
		return apGlTxnHst;
	}

	public void setApGlTxnHst(List<Map<String, Object>> apGlTxnHst) {
		this.apGlTxnHst = apGlTxnHst;
	}


}
