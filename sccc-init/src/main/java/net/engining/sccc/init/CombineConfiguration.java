package net.engining.sccc.init;

import java.security.SecureRandom;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.engining.sccc.config.GeneralContextConfig;
import net.engining.sccc.config.JPAContextConfig;
import net.engining.sccc.config.props.CommonProperties;

/**
 * 这个类用来组装需要的配置，根据不同的项目组装需要的配置项 <br>
 * 使用@Import用来导入@Configuration注解的config类(也可以通过@SpringBootApplication指定scanBasePackages来扫描@Configuration) <br>
 * 使用@ImportResource用来加载传统的xml配置
 * 
 * @author Eric Lu
 *
 */
@Configuration
//@Profile("init")
//显式的指定具体的Properties类，不通过扫描的方式，更清晰；
//另也不需要在@ConfigurationProperties注解的自定义Properties类上加@Component
@EnableConfigurationProperties(value = { 
		CommonProperties.class
		})
//显式的指定具体的@Configuration类，不通过扫描的方式，更清晰；
@Import(value = {
		GeneralContextConfig.class,
		JPAContextConfig.class
		})
@EntityScan(basePackages = {
			"net.engining.pg.parameter.entity",
			"net.engining.pg.batch.entity",
			"net.engining.gm.entity",
			"net.engining.pcx.cc.infrastructure",
			"net.engining.profile.entity"
		})
public class CombineConfiguration {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(666);
		return new BCryptPasswordEncoder(10,secureRandom);
	}
}
