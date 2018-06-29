package net.engining.sccc.accounting.bean;

import net.engining.pg.web.BaseResponseBean;
import net.engining.profile.param.SecurityControl;

/**
 * @author luxue
 *
 */
public class ParameterResponseBean extends BaseResponseBean {
	
	private static final long serialVersionUID = 1L;
	
	SecurityControl securityControl;

	public SecurityControl getSecurityControl() {
		return securityControl;
	}

	public void setSecurityControl(SecurityControl securityControl) {
		this.securityControl = securityControl;
	}

}
