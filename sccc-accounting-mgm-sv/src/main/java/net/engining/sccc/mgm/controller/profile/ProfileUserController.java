package net.engining.sccc.mgm.controller.profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.profile.entity.model.ProfileUser;
import net.engining.profile.sdk.service.ProfileUserService;
import net.engining.profile.security.ProfileSecurityService;
import net.engining.sccc.mgm.bean.profile.MgmWebUser;
import net.engining.sccc.mgm.bean.profile.ProfileUserForm;
import net.engining.sccc.mgm.bean.profile.UsersFilter;
import net.engining.sccc.mgm.service.UserService;

@RequestMapping("/proUser")
@RestController
@Api(value="ProfileUserController",description="用户管理")
public class ProfileUserController {
	
	@Autowired
	private ProfileUserService profileUserService;
	
	@Autowired
	private ProfileSecurityService profileSecurityService;
	
	@Autowired
	private Provider4Organization provider4Organization;

	@Autowired 
	private UserService userService;
	
	@PreAuthorize("hasAuthority('ProfileUser')")
	@ApiOperation(value="根据机构和分部获取用户列表", notes="")
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> getUsers(@RequestBody UsersFilter usersFilter) {
		FetchResponse<Map<String, Object>> rsp = profileUserService.fetchUsers4Branch(usersFilter.getBranchId(), provider4Organization.getCurrentOrganizationId(), usersFilter.getRange());
		
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(rsp); 
	}
	
	@PreAuthorize("hasAuthority('ProfileUser')")
	@ApiOperation(value="根据用户userId获取用户信息", notes="")
	@RequestMapping(value="/userId", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> getUserByName(@RequestBody ProfileUserForm user) {
		FetchResponse<Map<String, Object>> rsp = profileUserService.getUserInfoByUserId(user.getUserId());
		
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(rsp); 
	}
	
	
	@PreAuthorize("hasAuthority('ProfileUser')")
	@ApiOperation(value="获取用户信息", notes="")
	@RequestMapping(value="/puId", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<MgmWebUser> getUser(@RequestBody ProfileUserForm user) {
		ProfileUser profileUser = profileSecurityService.getUserInfo(user.getPuId());
		MgmWebUser mgmWebUser = userService.mgmWebUser(profileUser);
		
		return new WebCommonResponseBuilder<MgmWebUser>().build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(mgmWebUser); 
	}
	
	@PreAuthorize("hasAuthority('ProfileUser')")
	@ApiOperation(value="添加用户", notes="")
    @RequestMapping(value="/addUser", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> addUser(@RequestBody MgmWebUser user) {
		userService.validateUser(user.getUserId());
		ProfileUser profileUser = userService.profileUserForm(user);
		
		return new WebCommonResponseBuilder<Void>().build()
				.setStatusCode(WebCommonResponse.CODE_OK);
		
	}
	
	
	@PreAuthorize("hasAuthority('ProfileUser')")
	@ApiOperation(value="删除某个用户", notes="")
    @RequestMapping(value="/removeUserByPuId", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> removeUser(@RequestBody ProfileUserForm user) {
		List<String> usrs = new ArrayList<String>();
		usrs.add(user.getPuId());
		profileUserService.deleteProfileUsers(usrs);
		
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
	
	@PreAuthorize("hasAuthority('ProfileUser')")
	@ApiOperation(value="删除多个用户", notes="")
    @RequestMapping(value="/removeUsers", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> removeUser(@RequestBody String[] puIds) {
		List<String> usrs = Arrays.asList(puIds);
		profileUserService.deleteProfileUsers(usrs);

		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
	
	@PreAuthorize("hasAuthority('ProfileUser')")
	@ApiOperation(value="更新某个用户", notes="")
    @RequestMapping(value="/updateUser", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> updateUser(@RequestBody ProfileUserForm user) {
			ProfileUser profileUser = userService.profileUser(user);
			
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
	
}