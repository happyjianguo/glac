package net.engining.sccc.mgm.controller.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.profile.sdk.service.ProfileMgmService;
import net.engining.profile.sdk.service.ProfileRoleService;
import net.engining.sccc.mgm.bean.profile.ProfileRoleBranch;
import net.engining.sccc.mgm.bean.profile.ProfileRoleDelDisForm;
import net.engining.sccc.mgm.bean.profile.ProfileRoleSaveUpdateForm;
import net.engining.sccc.mgm.bean.profile.ProfileUserBranchForm;
import net.engining.sccc.mgm.bean.profile.ProfileUserRoleForm;

@RequestMapping("/profileMgm")
@Controller
public class ProfileMgmController {

	@Autowired
	private ProfileMgmService profileMgmService;

	@Autowired
	private ProfileRoleService profileRoleService;

	@Autowired
	Provider4Organization provider4Organization;

	/**
	 * 分页查询用户角色
	 * 
	 * @param profileUserForm 
	 * @return 
	 */
	@RequestMapping(value = "/fetchProfileUser", method = RequestMethod.POST)
	@ApiOperation(value = "根据用户信息查询其角色", notes = "")
	@PreAuthorize("hasAuthority('ProfileUser')")
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> fetchProfileUser(
			@RequestBody ProfileUserBranchForm profileUserBranchForm) {

		FetchResponse<Map<String, Object>> fetchResponse = profileMgmService.fetchProfileUser(
				profileUserBranchForm.getBranchId(), provider4Organization.getCurrentOrganizationId(),
				profileUserBranchForm.getName(), profileUserBranchForm.getRange());

		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(fetchResponse);
	}

	/**
	 * 获取所有的角色
	 * 
	 */
	@RequestMapping(value = "/fetchAllProfileRole", method = RequestMethod.POST)
	@ApiOperation(value = " 获取所有的角色", notes = "")
	@PreAuthorize("hasAuthority('ProfileUser')")
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> fetchAllProfileRole() {
		FetchResponse<Map<String, Object>> fetchResponse = profileMgmService.fetchAllProfileRole();
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(fetchResponse);
	}

	/**
	 * 保存用户所对应的权限
	 * 
	 * @param profileUserForm
	 * @return
	 */
	@RequestMapping(value = "/saveProfileUserAndRole", method = RequestMethod.POST)
	@ApiOperation(value = "为用户分配角色", notes = "")
	@PreAuthorize("hasAuthority('ProfileUser')")
	public @ResponseBody WebCommonResponse<Void> saveProfileUserAndRole(
			@RequestBody ProfileUserRoleForm profileUserRoleForm) {
		profileMgmService.saveProfileUserAndRole(profileUserRoleForm.getRoleStr(), profileUserRoleForm.getPuId());
		;
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}

	/**
	 * 获取用户对应的角色
	 * 
	 * @param puId
	 * @return
	 */
	@RequestMapping(value = "/fetchUserRole", method = RequestMethod.POST)
	@ApiOperation(value = "获取用户对应的角色", notes = "")
	@PreAuthorize("hasAuthority('ProfileUser')")
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> fetchUserRole(
			@RequestBody ProfileUserRoleForm profileUserRoleForm) {
		FetchResponse<Map<String, Object>> fetchResponse = profileMgmService
				.fetchUserRoleByPuId(profileUserRoleForm.getPuId());
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(fetchResponse);
	}

	/**
	 * 获取所有的分支下拉框
	 * 
	 */
	@RequestMapping(value = "/fetchAllProfileBranch", method = RequestMethod.POST)
	@ApiOperation(value = "获取所有的分支下拉框", notes = "")
	@PreAuthorize("hasAuthority('ProfileUser')")
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> fetchAllProfileBranch() {
		FetchResponse<Map<String, Object>> fetchResponse = profileMgmService.fetchAllProfileBranch();
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(fetchResponse);
	}

	/**
	 * 根据角色名称查询角色信息
	 * 
	 * @param profileRoleForm
	 * @return
	 */
	@RequestMapping(value = "/fetchProfileRole", method = RequestMethod.POST)
	@ApiOperation(value = "根据角色名称查询角色信息", notes = "")
	@PreAuthorize("hasAuthority('ProfileRole')")
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> fetchProfileRole(
			@RequestBody ProfileRoleBranch profileRoleBranch) {
		FetchResponse<Map<String, Object>> fetchResponse = profileMgmService
				.fetchProfileRole(profileRoleBranch.getRoleName(), profileRoleBranch.getRange());
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(fetchResponse);
	}

	/**
	 * 角色新增
	 * 
	 * @param profileRoleForm
	 * @return
	 */
	@RequestMapping(value = "/saveProfileRole", method = RequestMethod.POST)
	@ApiOperation(value = "角色新增", notes = "")
	@PreAuthorize("hasAuthority('ProfileUser')")
	public @ResponseBody WebCommonResponse<Void> saveProfileRole(
			@RequestBody ProfileRoleSaveUpdateForm profileRoleSaveForm) {
		profileMgmService.saveProfileRole(profileRoleSaveForm.getRoleId(), profileRoleSaveForm.getBranchId(),
				profileRoleSaveForm.getRoleName(), provider4Organization.getCurrentOrganizationId());
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}

	/**
	 * 角色修改
	 * 
	 * @param profileRoleForm
	 * @return
	 */
	@RequestMapping(value = "/updateProfileRole", method = RequestMethod.POST)
	@ApiOperation(value = "角色修改", notes = "")
	@PreAuthorize("hasAuthority('ProfileUser')")
	public @ResponseBody WebCommonResponse<Void> updateProfileRole(
			@RequestBody ProfileRoleSaveUpdateForm profileRoleSaveUpdateForm) {
		profileMgmService.updateProfileRole(profileRoleSaveUpdateForm.getRoleId(),
				profileRoleSaveUpdateForm.getBranchId(), profileRoleSaveUpdateForm.getRoleName());
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}

	/**
	 * 角色删除
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/deleteProfileRole", method = RequestMethod.POST)
	@ApiOperation(value = "角色删除", notes = "")
	@PreAuthorize("hasAuthority('ProfileUser')")
	public @ResponseBody WebCommonResponse<Void> deleteProfileRole(
			@RequestBody ProfileRoleDelDisForm profileRoleDelDisForm) {
		List<String> list = new ArrayList<String>();
		list.add(profileRoleDelDisForm.getRoleId());
		profileRoleService.deleteProfileRoles(list);
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}

	/**
	 * 角色权限分配
	 * 
	 * @param profileRoleForm
	 * @return
	 */
	@RequestMapping(value = "/distributionProfileRole", method = RequestMethod.POST)
	@ApiOperation(value = "角色权限分配", notes = "")
	@PreAuthorize("hasAuthority('ProfileUser')")
	public @ResponseBody WebCommonResponse<Void> distributionProfileRole(
			@RequestBody ProfileRoleDelDisForm profileRoleDelDisForm) {
				profileMgmService.distributionProfileRole(profileRoleDelDisForm.getRoleId(),
				profileRoleDelDisForm.getAuthStr());
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}

	/**
	 * 获取角色对应的权限
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/fetchRoleAuth", method = RequestMethod.POST)
	@ApiOperation(value = "获取角色对应的权限", notes = "")
	@PreAuthorize("hasAuthority('ProfileUser')") 
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> fetchRoleAuth(
			@RequestBody ProfileRoleDelDisForm profileRoleDelDisForm) {
		FetchResponse<Map<String, Object>> fetchResponse = profileMgmService
				.fetchRoleAuthByRoleId(profileRoleDelDisForm.getRoleId());
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK).setResponseData(fetchResponse);
	}
	
}
