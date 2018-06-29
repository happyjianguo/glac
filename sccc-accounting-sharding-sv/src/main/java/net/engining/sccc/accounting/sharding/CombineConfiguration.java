package net.engining.sccc.accounting.sharding;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.engining.gm.facility.SystemStatusFacility;
import net.engining.pcx.cc.process.service.support.OnlineProvider;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.sccc.config.GeneralContextConfig;
import net.engining.sccc.config.JPAContextConfig;
import net.engining.sccc.config.Jdbc4QuerydslContextConfig;
import net.engining.sccc.config.Swagger2ContextConfig;
import net.engining.sccc.config.ValidatorContextConfig;
import net.engining.sccc.config.WebContextConfig;
import net.engining.sccc.config.WebMvcExtContextConfig;
import net.engining.sccc.config.props.CommonProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 这个类用来组装需要的配置，根据不同的项目组装需要的配置项 <br>
 * 使用@Import用来导入@Configuration注解的config类(也可以通过@SpringBootApplication指定scanBasePackages来扫描@Configuration) <br>
 * 使用@ImportResource用来加载传统的xml配置
 * 
 * @author Eric Lu
 *
 */
@Configuration
@EnableSwagger2//FIXME 打开swagger，访问/swagger-ui.html; 暂时加在顶层config上，解决swagger报错(Unable to infer base url)的坑，未找到正真原因，一度加在Swagger2ContextConfig是可行的，可能是与某个包冲突;
@EnableRabbit //使@RabbitListener注释的端点可以在RabbitListenerContainerFactory的覆盖下创建;
//显式的指定具体的Properties类，不通过扫描的方式，更清晰；
//另也不需要在@ConfigurationProperties注解的自定义Properties类上加@Component
@EnableConfigurationProperties(value = { 
		CommonProperties.class
		})
//显式的指定具体的@Configuration类，不通过扫描的方式，更清晰；
@Import(value = {
		GeneralContextConfig.class,
		JPAContextConfig.class,
		Jdbc4QuerydslContextConfig.class,
		ValidatorContextConfig.class,
		Swagger2ContextConfig.class,
		WebContextConfig.class,
		WebMvcExtContextConfig.class
		})
@ComponentScan(
		basePackages = {
				"net.engining.sccc.biz.service"
		})
@EntityScan(basePackages = {
		"net.engining.pg.parameter.entity",
		"net.engining.pcx.cc.infrastructure"
	})
public class CombineConfiguration {

	@Bean
	public SystemStatusFacility systemStatusFacility(){
		SystemStatusFacility systemStatusFacility = new SystemStatusFacility();
		return systemStatusFacility;
		
	}
	
	@Bean
	@ConditionalOnClass(value={RemoteIpFilter.class})
	public RemoteIpFilter remoteIpFilter(){
		return new RemoteIpFilter();
	}
	
	@Bean
	public Provider7x24 provider7x24(){
		OnlineProvider provider7x24 = new OnlineProvider();
		return provider7x24;
	}
}
