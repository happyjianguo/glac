package net.engining.sccc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.engining.pg.web.handler.GlobalControllerExceptionHandler;

/**
 * Spring Mvc的扩展配置
 * @author Eric Lu
 *
 */
@Configuration
//如果需要保持Spring boot MVC的功能，但是需要添加额外MVC configuration，可以通过@Configuration注解继承WebMvcConfigurerAdapter的类，但是不要添加@EnableWebMvc注解;
//*ConfigurerAdapter通常是重载默认配置或附加额外配置的入口
//如果想要完全控制Spring MVC，可以定义注解@Configuration并加上注解@EnableWebMvc;
@ComponentScan(basePackageClasses={GlobalControllerExceptionHandler.class})//这里显示的指定扫描类所在的包，避免其他不需要的组件被扫描
public class WebMvcExtContextConfig extends WebMvcConfigurerAdapter{
	

}
