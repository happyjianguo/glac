package net.engining.sccc.mgm.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.profile.sdk.service.ProfilePasswordService;
import net.engining.sccc.mgm.bean.profile.ChangePasswordForm;

@RequestMapping("/profile")
@Controller
public class ProfilePasswordController {
	
	@Autowired
	ProfilePasswordService profilePasswordService;
	
	@PreAuthorize("hasAuthority('ProfileRole')")
	@ApiOperation(value="修改密码", notes="")
	@RequestMapping(value="/changePwdByAdmin/{puId}", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> changePasswordByAdmin(@PathVariable String puId, @RequestBody ChangePasswordForm changePasswordForm) {
		
			String opUser = SecurityContextHolder.getContext().getAuthentication().getName();
			profilePasswordService.changePassword(puId, changePasswordForm.getOldPassword(), changePasswordForm.getNewPassword(), opUser);
		
		return new WebCommonResponseBuilder<Void>().build()
				.setStatusCode(WebCommonResponse.CODE_OK);
	}
	/**
	 * 重置登陆密码
	 * @param puId
	 * @return
	 */
	@PreAuthorize("hasAuthority('ProfileRole')")
	@ApiOperation(value="resetPwdByAdmin", notes="")
	@RequestMapping(value="/resetPwdByAdmin/{puId}", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> resetPasswordByAdmin(@PathVariable String puId) {
			String opUser = SecurityContextHolder.getContext().getAuthentication().getName();
			profilePasswordService.resetPassword(puId, opUser);
		return new WebCommonResponseBuilder<Void>().build()
				.setStatusCode(WebCommonResponse.CODE_OK);
	}
}