package net.engining.sccc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import net.engining.pg.parameter.LocalCachedParameterFacility;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.ProcessesProvider4Organization;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.parameter.RedisCachedParameterFacility;
import net.engining.pg.support.core.context.ApplicationContextHolder;
import net.engining.sccc.config.props.CommonProperties;

/**
 * 通用Context配置
 * @author Eric Lu
 *
 */
@Configuration
public class GeneralContextConfig {

	@Autowired
	CommonProperties commonProperties;
	
	/**
	 * ApplicationContext的静态辅助Bean，建议项目必须注入
	 * @return
	 */
	@Bean
	@Lazy(value=false)
	public ApplicationContextHolder applicationContextHolder(){
		return new ApplicationContextHolder();
	}
	
	/**
	 * 参数体系辅助Bean，建议项目必须注入
	 * @return
	 */
	@Bean
	public ParameterFacility parameterFacility(){
		if (commonProperties.isEnableRedisCache()) {
			RedisCachedParameterFacility redisCachedParameterFacility = new RedisCachedParameterFacility();
			redisCachedParameterFacility.setExpireDuration(commonProperties.getExpireDuration());
			redisCachedParameterFacility.setExpireTimeUnit(commonProperties.getExpireTimeUnit());
			return redisCachedParameterFacility;
		}
		LocalCachedParameterFacility localCachedParameterFacility = new LocalCachedParameterFacility();
		localCachedParameterFacility.setExpireDuration(commonProperties.getExpireDuration());
		localCachedParameterFacility.setExpireTimeUnit(commonProperties.getExpireTimeUnit());
		return localCachedParameterFacility;
	}
	
	@Bean
	public Provider4Organization provider4Organization(){
		ProcessesProvider4Organization processesProvider4Organization = new ProcessesProvider4Organization();
		//从配置文件获取默认机构ID
		processesProvider4Organization.setCurrentOrganizationId(commonProperties.getDefaultOrgId());
		return processesProvider4Organization;
	}
}
