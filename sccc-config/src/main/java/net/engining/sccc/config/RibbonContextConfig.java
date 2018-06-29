package net.engining.sccc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.PingUrl;

/**
 * 通过该配置可以改变Ribbon的默认配置
 * @author luxue
 *
 */
@Configuration
public class RibbonContextConfig {
	
	@Bean
	public IPing ribbonPing(IClientConfig config){
		return new PingUrl();
	}
}
