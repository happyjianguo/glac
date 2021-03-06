package net.engining.sccc.mgm.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.profile.entity.model.ProfileUser;
import net.engining.profile.sdk.service.ProfileUserService;
import net.engining.profile.security.ProfileSecurityService;
import net.engining.sccc.mgm.bean.profile.MgmWebUser;
import net.engining.sccc.mgm.bean.profile.ProfileUserForm;

@Service
public class UserService {

	@Autowired
	private Provider4Organization provider4Organization;
	
	@Autowired
	private ProfileSecurityService profileSecurityService;
	
	@Autowired
	private ProfileUserService profileUserService;
	public MgmWebUser mgmWebUser(ProfileUser profileUser){
		MgmWebUser mgmWebUser = new MgmWebUser();
		BeanUtils.copyProperties(profileUser, mgmWebUser, "password");
		return mgmWebUser;
	}
	
	
	public ProfileUser profileUserForm(MgmWebUser user){
		ProfileUser profileUser = new ProfileUser();
		BeanUtils.copyProperties(user, profileUser);
		profileUser.setPuId(null);
		//单机构默认赋值
		profileUser.setOrgId(provider4Organization.getCurrentOrganizationId());
		profileUser.setBranchId(provider4Organization.getCurrentOrganizationId());
		profileSecurityService.createNewUser(profileUser, user.getPassword());
		return profileUser;
	}
	
	public ProfileUser profileUser(ProfileUserForm user){
		ProfileUser profileUser = new ProfileUser();
		BeanUtils.copyProperties(user, profileUser);
//		profileUser.setPuId(user.getPuId());
//		profileUser.setName(user.getName());
		profileUser.fillDefaultValues();
		profileUserService.updateProfileUser(profileUser);
		return profileUser;
	}
	
	public void validateUser(String userId){
		ProfileUser userInfo = profileUserService.findProfileUserInfoByUserId(userId);
		if(null!=userInfo){
			throw new ErrorMessageException(ErrorCode.CheckError, "该用户已被添加，不能再次添加");
		}
	}
}