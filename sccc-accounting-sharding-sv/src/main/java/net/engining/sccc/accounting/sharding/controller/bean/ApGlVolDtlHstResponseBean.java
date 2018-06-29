/**
 * 
 */
package net.engining.sccc.accounting.sharding.controller.bean;

import java.util.List;
import java.util.Map;

import net.engining.pg.web.BaseResponseBean;

/**
 * @author luxue
 *
 */
public class ApGlVolDtlHstResponseBean extends BaseResponseBean {
	
	private static final long serialVersionUID = 1L;

	List<Map<String, Object>> apGlVolDtlHst;

	public List<Map<String, Object>> getApGlVolDtlHst() {
		return apGlVolDtlHst;
	}

	public void setApGlVolDtlHst(List<Map<String, Object>> apGlVolDtlHst) {
		this.apGlVolDtlHst = apGlVolDtlHst;
	}

}
