package net.engining.sccc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.engining.pg.web.filter.Log4jMappedDiagnosticContextFilter;

/**
 * Web.xml的扩展配置，主要包含自定义Filter，Listener，servlet；
 * @author Eric Lu
 *
 */
@Configuration
public class WebContextConfig{
	
	@Bean
	public Log4jMappedDiagnosticContextFilter log4jMappedDiagnosticContextFilter(){
		return new Log4jMappedDiagnosticContextFilter();
	}
	
}
