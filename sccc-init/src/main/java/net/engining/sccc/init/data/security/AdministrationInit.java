package net.engining.sccc.init.data.security;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.init.TableDataInitializer;
import net.engining.profile.entity.enums.StatusDef;
import net.engining.profile.entity.model.ProfileBranch;
import net.engining.profile.entity.model.ProfileRole;
import net.engining.profile.entity.model.ProfileRoleAuth;
import net.engining.profile.entity.model.ProfileUser;
import net.engining.profile.entity.model.ProfileUserRole;
import net.engining.profile.enums.DefaultRoleID;
import net.engining.profile.enums.SecurityAppAuthority;
import net.engining.sccc.config.props.CommonProperties;

/**
 * 初始化超级管理员极其默认权限
 * @author luxue
 *
 */
@Service
public class AdministrationInit implements TableDataInitializer{
	
	
	private static final Logger log = LoggerFactory.getLogger(AdministrationInit.class);

	@PersistenceContext 
	private EntityManager em;
	
	@Autowired
	Provider4Organization provider4Organization;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CommonProperties commonProperties;
	
	/**
	 * 缺省角色名
	 */
	private String roleName = "系统超级管理员";

	/**
	 * 缺省用户名
	 */
	private String userName = "admin";
	
	/* (non-Javadoc)
	 * @see net.engining.pg.support.init.TableDataInitializer#init()
	 */
	@Override
	@Transactional
	public void init() throws Exception {
		log.debug("正在执行初始化器 -> AdministrationInit");

		//Init Branch
		em.createNativeQuery("delete from PROFILE_BRANCH").executeUpdate();
		//该系统目前不区分机构与分部，所以都保持一致
		ProfileBranch branch = new ProfileBranch();
		branch.setOrgId(provider4Organization.getCurrentOrganizationId());
		branch.setBranchId(provider4Organization.getCurrentOrganizationId());
		branch.setBranchName("本机构总部");
		em.persist(branch);
		
		// Init Role
		em.createNativeQuery("delete from PROFILE_ROLE").executeUpdate();
		ProfileRole profileRole = new ProfileRole();
		profileRole.setRoleId(DefaultRoleID.SUPERADMIN.toString());
		profileRole.setOrgId(provider4Organization.getCurrentOrganizationId());
		profileRole.setBranchId(provider4Organization.getCurrentOrganizationId());
		profileRole.setRoleName(roleName);
		em.persist(profileRole);
		
		// Init Role Auth; 角色与权限
		em.createNativeQuery("delete from PROFILE_ROLE_AUTH").executeUpdate();
		ProfileRoleAuth profileRoleAuth = null;
		//后端管理预设权限全部赋给ADMIN
		for (SecurityAppAuthority auth : SecurityAppAuthority.values()){
			profileRoleAuth = new ProfileRoleAuth();
			profileRoleAuth.setRoleId(DefaultRoleID.SUPERADMIN.toString());
			profileRoleAuth.setAuthority(auth.toString());
			profileRoleAuth.setAutuUri("*");//该权限ID下的所有Uri
			em.persist(profileRoleAuth);
		}
		//额外增加ACTUATOR_MONITOR，用于微服务Actuator监控
		profileRoleAuth = new ProfileRoleAuth();
		profileRoleAuth.setRoleId(DefaultRoleID.SUPERADMIN.toString());
		profileRoleAuth.setAuthority("ACTUATOR_MONITOR");
		profileRoleAuth.setAutuUri("*");
		em.persist(profileRoleAuth);
		
		// Init User
		em.createNativeQuery("delete from PROFILE_USER").executeUpdate();
		ProfileUser user = new ProfileUser();
		user.setUserId(userName);
		user.setOrgId(provider4Organization.getCurrentOrganizationId());
		user.setBranchId(provider4Organization.getCurrentOrganizationId());
		user.setEmail("admin@admin.com");
		user.setMtnTimestamp(new Date());
		user.setMtnUser("Initializer");
		user.setName(userName);
		user.setPassword(passwordEncoder.encode(commonProperties.getDefaultPassword()));
		user.setPwdExpDate(DateUtils.addYears(new Date(), 10));
		user.setPwdTries(0);
		user.setStatus(StatusDef.A);
		em.persist(user);
		
		// Init User Role；用户与角色
		em.createNativeQuery("delete from PROFILE_USER_ROLE").executeUpdate();
		ProfileUserRole userRole = new ProfileUserRole();
		userRole.setRoleId(DefaultRoleID.SUPERADMIN.toString());
		userRole.setPuId(user.getPuId());
		em.persist(userRole);
		
	}

}
