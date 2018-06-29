package net.engining.sccc.config.props;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * 工程通用配置
 * 
 * @author luxue
 *
 */
@ConfigurationProperties(prefix = "sccc.common")
public class CommonProperties implements EnvironmentAware {

	private static final String UNKNOWN = "unknown";
	private String appname = UNKNOWN;

	/**
	 * 缓存过期时间值
	 */
	private long expireDuration = 5;

	/**
	 * 缓存过期时间单位
	 */
	private TimeUnit expireTimeUnit = TimeUnit.MINUTES;

	/**
	 * 是否使用RedisCache的开关, 默认false
	 */
	private boolean enableRedisCache = false;

	/**
	 * Swagger扫描包
	 */
	private String swaggerBasePackage;

	/**
	 * 默认Org
	 */
	private String defaultOrgId;

	/**
	 * JWT 签名Key
	 */
	private String jwtSignKey;

	/**
	 * JWT过期毫秒数
	 */
	private long jwtExpirationMills;

	/**
	 * 默认管理控制台密码
	 */
	private String defaultPassword;

	/**
	 * 定时平行任务线程池
	 */
	private int scheduledThreadPool;

	private String businessDate;

	private String lastProcessDate;

	private String processDate;
	
	private String tranSubjectNo;
	
	public String getTranSubjectNo() {
		return tranSubjectNo;
	}

	public void setTranSubjectNo(String tranSubjectNo) {
		this.tranSubjectNo = tranSubjectNo;
	}

	private String defaultOutputDir;

	public String getDefaultOutputDir() {
		return defaultOutputDir;
	}

	public void setDefaultOutputDir(String defaultOutputDir) {
		this.defaultOutputDir = defaultOutputDir;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getLastProcessDate() {
		return lastProcessDate;
	}

	public void setLastProcessDate(String lastProcessDate) {
		this.lastProcessDate = lastProcessDate;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public boolean isEnableRedisCache() {
		return enableRedisCache;
	}

	public void setEnableRedisCache(boolean enableRedisCache) {
		this.enableRedisCache = enableRedisCache;
	}

	public long getExpireDuration() {
		return expireDuration;
	}

	public void setExpireDuration(long expireDuration) {
		this.expireDuration = expireDuration;
	}

	public TimeUnit getExpireTimeUnit() {
		return expireTimeUnit;
	}

	public void setExpireTimeUnit(TimeUnit expireTimeUnit) {
		this.expireTimeUnit = expireTimeUnit;
	}

	public String getSwaggerBasePackage() {
		return swaggerBasePackage;
	}

	public void setSwaggerBasePackage(String swaggerBasePackage) {
		this.swaggerBasePackage = swaggerBasePackage;
	}

	public String getDefaultOrgId() {
		return defaultOrgId;
	}

	public void setDefaultOrgId(String defaultOrgId) {
		this.defaultOrgId = defaultOrgId;
	}

	public String getJwtSignKey() {
		return jwtSignKey;
	}

	public void setJwtSignKey(String jwtSignKey) {
		this.jwtSignKey = jwtSignKey;
	}

	public long getJwtExpirationMills() {
		return jwtExpirationMills;
	}

	public void setJwtExpirationMills(long jwtExpirationMills) {
		this.jwtExpirationMills = jwtExpirationMills;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	public int getScheduledThreadPool() {
		return scheduledThreadPool;
	}

	public void setScheduledThreadPool(int scheduledThreadPool) {
		this.scheduledThreadPool = scheduledThreadPool;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	@Override
	public void setEnvironment(Environment environment) {
		// set some defaults from the environment, but allow the defaults to use
		// relaxed binding
		RelaxedPropertyResolver springPropertyResolver = new RelaxedPropertyResolver(environment,
				"spring.application.");
		String springAppName = springPropertyResolver.getProperty("name");
		if (org.springframework.util.StringUtils.hasText(springAppName)) {
			if (StringUtils.isBlank(this.appname)) {
				setAppname(springAppName);
			}

		}

	}

}
