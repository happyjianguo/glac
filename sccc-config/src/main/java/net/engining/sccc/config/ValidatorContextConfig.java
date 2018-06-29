package net.engining.sccc.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 校验器相关的Context配置
 * @author Administrator
 *
 */
@Configuration
public class ValidatorContextConfig {
	
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		reloadableResourceBundleMessageSource.setBasenames(
				"classpath:i18n/messages",
				"classpath:org/hibernate/validator/ValidationMessages"
				);
		reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
		reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
		reloadableResourceBundleMessageSource.setCacheSeconds(600);
		return reloadableResourceBundleMessageSource;
		
	}
	
	@Bean
	public LocalValidatorFactoryBean validator(){
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
		localValidatorFactoryBean.setValidationMessageSource(messageSource());
		return localValidatorFactoryBean;
		
	}
	
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor(){
		MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
		methodValidationPostProcessor.setValidator(validator());
		return methodValidationPostProcessor;
		
	}
}
