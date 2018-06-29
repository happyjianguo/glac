package net.engining.sccc.mgm.controller.profile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.profile.entity.model.ProfileRole;
import net.engining.profile.sdk.service.ProfileRoleService;
import net.engining.sccc.mgm.bean.profile.RoleForm;

@RequestMapping("/profile")
@Controller
public class ProfileRoleController {
	
	@Autowired
	private ProfileRoleService profileRoleService;

	@PreAuthorize("hasAuthority('ProfileRole')")
	@ApiOperation(value="获取所有角色", notes="")
	@RequestMapping(value="/fetchRoles",method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<FetchResponse<ProfileRole>> fetchRoles(@RequestBody Range range) {
		FetchResponse<ProfileRole> fetchResponse  = profileRoleService.fetchRoles(range);
		return new WebCommonResponseBuilder<FetchResponse<ProfileRole>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(fetchResponse);
	}
	
	@PreAuthorize("hasAuthority('ProfileRole')")
	@ApiOperation(value="添加角色", notes="")
	@RequestMapping(value="/addRole",method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> addRole(@RequestBody ProfileRole role) {
			profileRoleService.addProfileRole(role);
			return new WebCommonResponseBuilder<Void>().build()
					.setStatusCode(WebCommonResponse.CODE_OK);
	}
	
	@PreAuthorize("hasAuthority('ProfileRole')")
	@ApiOperation(value="removeRoles", notes="")
	@RequestMapping(value="/removeRoles", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> removeRole(@RequestBody String[] roles) {
			List<String> lroles= Arrays.asList(roles);
			profileRoleService.deleteProfileRoles(lroles);
			return new WebCommonResponseBuilder<Void>().build()
					.setStatusCode(WebCommonResponse.CODE_OK);
	}
	
	@PreAuthorize("hasAuthority('ProfileRole')")
	@ApiOperation(value="updateRole", notes="")
	@RequestMapping(value="/updateRole", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> updateRole(@RequestBody RoleForm roleForm) {
			List<String> lauth= Arrays.asList(roleForm.getAuthorities());
			profileRoleService.updateProfileRole(roleForm.getProfileRole(), lauth);
			return new WebCommonResponseBuilder<Void>().build()
					.setStatusCode(WebCommonResponse.CODE_OK);
	}
	
	@PreAuthorize("hasAuthority('ProfileRole')")
	@ApiOperation(value="getRole", notes="")
	@RequestMapping(value="/getRole/{roleId}",method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<ProfileRole> getRole(@PathVariable String roleId) {
		ProfileRole fetchResponse = profileRoleService.getProfileRoleInfo(roleId);
		return new WebCommonResponseBuilder<ProfileRole>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(fetchResponse);
	}
	
	@PreAuthorize("hasAuthority('ProfileRole')")
	@ApiOperation(value="getAuths4Role", notes="")
	@RequestMapping(value="/getAuths4Role/{roleId}",method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<List<String>> getAuthorities4Role(@PathVariable String roleId){
		List<String> fetchResponse =  profileRoleService.getProfileRoleAuths(roleId);
		return new WebCommonResponseBuilder<List<String>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(fetchResponse);
		
	}
	
	@PreAuthorize("hasAuthority('ProfileRole')")
	@ApiOperation(value="delRoleUsers", notes="")
	@RequestMapping(value="/delRoleUsers",method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> deleteRole2User(@RequestBody Map<String, String> roleUsers){
			profileRoleService.deleteProfileUserRoles(roleUsers);
			return new WebCommonResponseBuilder<Void>().build()
					.setStatusCode(WebCommonResponse.CODE_OK);
		
	}
	
}