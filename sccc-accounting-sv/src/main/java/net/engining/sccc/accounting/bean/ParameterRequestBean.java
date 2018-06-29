/**
 * 
 */
package net.engining.sccc.accounting.bean;

import net.engining.pg.web.BaseRequestBean;
import net.engining.profile.param.SecurityControl;

/**
 * @author luxue
 *
 */
public class ParameterRequestBean extends BaseRequestBean {

	private static final long serialVersionUID = 1L;
	
	public int oper;
	
	SecurityControl securityControl;

	public int getOper() {
		return oper;
	}

	public void setOper(int oper) {
		this.oper = oper;
	}

	public SecurityControl getSecurityControl() {
		return securityControl;
	}

	public void setSecurityControl(SecurityControl securityControl) {
		this.securityControl = securityControl;
	}
	
	

}
