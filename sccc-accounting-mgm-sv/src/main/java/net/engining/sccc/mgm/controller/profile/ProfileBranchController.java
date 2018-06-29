package net.engining.sccc.mgm.controller.profile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.profile.entity.model.ProfileBranch;
import net.engining.profile.sdk.service.ProfileBranchService;
import net.engining.sccc.mgm.bean.profile.BranchFilter;
import net.engining.sccc.mgm.bean.profile.ProfileBranchForm;
import net.engining.sccc.mgm.service.BranchService;

@RequestMapping("/profile")
@RestController
public class ProfileBranchController {
	
	@Autowired
	ProfileBranchService profileBranchService;
	
	@Autowired
	Provider4Organization provider4Organization;
	
	@Autowired
	BranchService branchService;
	
	@RequestMapping(value="/branchesBySuperid",method=RequestMethod.POST)
	@ApiOperation(value="fetchBranchesBySuperid", notes="")
	@PreAuthorize("hasAuthority('ProfileBranch')")
	public @ResponseBody WebCommonResponse<FetchResponse<ProfileBranchForm>> fetchBranchesBySuperid(@RequestBody BranchFilter branchFilter) {
		FetchResponse<ProfileBranch> fetchBranch = profileBranchService.fetchBranch(branchFilter.getRange(), branchFilter.getSuperiorId(), provider4Organization.getCurrentOrganizationId());
		FetchResponse<ProfileBranchForm> profileBranchForm = branchService.profileBranchForm(fetchBranch);
		return new WebCommonResponseBuilder<FetchResponse<ProfileBranchForm>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK)  
				.setResponseData(profileBranchForm);
	}
	
	@RequestMapping(value="/branches",method=RequestMethod.POST)
	@ApiOperation(value="fetchBranches", notes="")
	@PreAuthorize("hasAuthority('ProfileBranch')")
	public @ResponseBody WebCommonResponse<FetchResponse<ProfileBranchForm>> fetchBranches(@RequestBody Range range) {
		FetchResponse<ProfileBranch> fetchBranch = profileBranchService.fetchBranch(range);
		FetchResponse<ProfileBranchForm> profileBranchForm = branchService.profileBranchForm(fetchBranch);
		return new WebCommonResponseBuilder<FetchResponse<ProfileBranchForm>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(profileBranchForm);
	}
	
	@RequestMapping(value="/branch/{branchId}",method=RequestMethod.GET)
	@ApiOperation(value="getBranch", notes="")
	@PreAuthorize("hasAuthority('ProfileBranch')")
	public @ResponseBody WebCommonResponse<ProfileBranchForm> getBranch(@PathVariable String branchId) {
		ProfileBranch branch = profileBranchService.getBranch(provider4Organization.getCurrentOrganizationId(), branchId);
		ProfileBranchForm profileBranchForm = branchService.profileBranchForm(branch);
		return new WebCommonResponseBuilder<ProfileBranchForm>().build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(profileBranchForm);
	}
	
	@RequestMapping(value="/updateBranch",method=RequestMethod.POST)
	@ApiOperation(value="updateBranch", notes="")
	@PreAuthorize("hasAuthority('ProfileBranch')")
	public @ResponseBody WebCommonResponse<Void> updateBranch(@RequestBody ProfileBranchForm branch) {
		ProfileBranch profileBranch = branchService.profileBranch(branch);
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
	
	@RequestMapping(value="/getBranchNames",method=RequestMethod.GET)
	@ApiOperation(value="getBranchNames", notes="")
	@PreAuthorize("hasAuthority('ProfileBranch')")
	public @ResponseBody WebCommonResponse<Map<String, String>> getBranchNames() {
		Map<String, String> fetchBranchNamesByOrg = profileBranchService.fetchBranchNamesByOrg(provider4Organization.getCurrentOrganizationId());
		return new WebCommonResponseBuilder<Map<String, String>>().build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(fetchBranchNamesByOrg); 
	}
	
	@RequestMapping(value="/removeBranches",method=RequestMethod.POST)
	@ApiOperation(value="removeUser", notes="")
	@PreAuthorize("hasAuthority('ProfileBranch')")
	public @ResponseBody WebCommonResponse<Void> removeUser(@RequestBody String[] branchIds) {
			List<String> branches= Arrays.asList(branchIds);
			profileBranchService.deleteProfileBranch(branches, provider4Organization.getCurrentOrganizationId());
			return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
	
}