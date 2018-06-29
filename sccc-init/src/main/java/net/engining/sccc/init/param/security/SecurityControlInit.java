package net.engining.sccc.init.param.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.init.ParameterInitializer;
import net.engining.profile.param.PasswordPattern;
import net.engining.profile.param.SecurityControl;
import net.engining.profile.param.UsernamePattern;

@Service
public class SecurityControlInit implements ParameterInitializer {

	@Autowired
	private ParameterFacility facility;
	
	@Override
	public void init() throws Exception {
		if (facility.getParameterMap(SecurityControl.class).size() > 0){
			facility.removeParameter(SecurityControl.class, ParameterFacility.UNIQUE_PARAM_KEY);
		}
		
		SecurityControl securityControl = new SecurityControl();
		securityControl.complexPwdInd = true;
		securityControl.passwordPatterns = new ArrayList<PasswordPattern>();
		PasswordPattern passwordPattern1 = new PasswordPattern();
		passwordPattern1.mustMatch = true;
		passwordPattern1.message = "密码必须包含小写字母";
		passwordPattern1.pattern = ".*[a-z].*";
		passwordPattern1.weights = 100;
		PasswordPattern passwordPattern2 = new PasswordPattern();
		passwordPattern2.mustMatch = true;
		passwordPattern2.message = "密码必须包含大写字母";
		passwordPattern2.pattern = ".*[A-Z].*";
		passwordPattern2.weights = 100;
		PasswordPattern passwordPattern3 = new PasswordPattern();
		passwordPattern3.mustMatch = true;
		passwordPattern3.message = "密码必须包含数字";
		passwordPattern3.pattern = ".*[0-9].*";
		passwordPattern3.weights = 100;
		PasswordPattern passwordPattern4 = new PasswordPattern();
		passwordPattern4.mustMatch = true;
		passwordPattern4.message = "密码长度必须大于8位";
		passwordPattern4.pattern = ".{8,}";
		passwordPattern4.weights = 100;
		securityControl.passwordPatterns.add(passwordPattern1);
		securityControl.passwordPatterns.add(passwordPattern2);
		securityControl.passwordPatterns.add(passwordPattern3);
		securityControl.passwordPatterns.add(passwordPattern4);
		securityControl.pwdCycleCnt = 3;
		securityControl.pwdExpireDays = 30;
		securityControl.pwdFirstLoginChgInd = false;
		securityControl.pwdTries = 5;
		securityControl.usernamePattern = new ArrayList<UsernamePattern>();
		UsernamePattern usernamePattern1 = new UsernamePattern();
		usernamePattern1.pattern = "[a-z,A-Z,0-9,_]*";
		usernamePattern1.message = "用户名只能包含字母、数字或下划线";
		UsernamePattern usernamePattern2 = new UsernamePattern();
		usernamePattern2.pattern = ".{6,12}";
		usernamePattern2.message = "用户名长度必须为6-12位";
		securityControl.usernamePattern.add(usernamePattern1);
		securityControl.usernamePattern.add(usernamePattern2);
		facility.addParameter(ParameterFacility.UNIQUE_PARAM_KEY, securityControl);
	}
	
}
