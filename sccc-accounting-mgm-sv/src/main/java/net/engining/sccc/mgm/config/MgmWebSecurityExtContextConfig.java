package net.engining.sccc.mgm.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import net.engining.profile.security.ProfileUserDetailsService;
import net.engining.profile.security.handler.JsonAuthFailureHandler;
import net.engining.profile.security.listener.ClearPasswordTriesListener;
import net.engining.profile.security.listener.PasswordTriesListener;
import net.engining.sccc.config.props.CommonProperties;
import net.engining.sccc.mgm.filter.JWTBasicAuthenticationFilter;
import net.engining.sccc.mgm.filter.RESTfulUsernamePasswordAuthenticationFilter;
import net.engining.sccc.mgm.handler.JWTAuthSuccessHandler;

/**
 * 每个项目的安全配置大都不同，不考虑统一了
 * @author luxue
 *
 */
@Configuration
@EnableWebSecurity
//开启支持方法注解的权限控制：
//@PreAuthorize：该注解用来确定一个方法是否应该被执行。该注解后面跟着的是一个表达式，如果表达式的值为真，则该方法会被执行。如 @PreAuthorize("hasRole('ROLE_USER')")就说明只有当前用户具有角色 ROLE_USER的时候才会执行。
//@PostAuthorize：该注解用来在方法执行完之后进行访问控制检查。
//@PostFilter：该注解用来对方法的返回结果进行过滤。从返回的集合中过滤掉表达式值为假的元素。如@PostFilter("hasPermission(filterObject, 'read')")说明返回的结果中只保留当前用户有读权限的元素。
//@PreFilter：该注解用来对方法调用时的参数进行过滤。	
//等同于<security:global-method-security pre-post-annotations="enabled" mode="aspectj"/>
@EnableGlobalMethodSecurity(prePostEnabled = true, mode = AdviceMode.ASPECTJ)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)//如果不想覆盖执行器的访问规则,否则使用@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class MgmWebSecurityExtContextConfig extends WebSecurityConfigurerAdapter 
{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	CommonProperties commonProperties;
	
	@Bean
	public ProfileUserDetailsService profileUserDetailsService(){
		return new ProfileUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(666);
		return new BCryptPasswordEncoder(10,secureRandom);
	}
	
	@Bean
	public PasswordTriesListener passwordTriesListener(){
		return new PasswordTriesListener();
	}
	
	@Bean
	public ClearPasswordTriesListener clearPasswordTriesListener(){
		return new ClearPasswordTriesListener();
	}
	
	@Bean
	public JWTAuthSuccessHandler jsonAuthSuccessHandler(){
		return new JWTAuthSuccessHandler();
	}
	
	@Bean
	public JsonAuthFailureHandler JsonAuthFailureHandler(){
		return new JsonAuthFailureHandler();
	}

	@Override
    public void configure(HttpSecurity http) throws Exception {
//		  http.headers().frameOptions().disable();
        http
        	.cors().and()//打开跨域请求支持，默认浏览器不能进行跨域请求；FIXME
        	.csrf().disable()//关闭跨站请求伪造保护功能，FIXME
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            // 所有xx 的POST、GET请求 都放行
            .antMatchers(HttpMethod.POST, "/login").permitAll()
//            .antMatchers(HttpMethod.GET, "/*").permitAll()
            .anyRequest().authenticated()  // 所有请求需要身份认证
            .and()
            .addFilter(new RESTfulUsernamePasswordAuthenticationFilter(authenticationManager(), authenticationSuccessHandler, authenticationFailureHandler))//登录
            .addFilter(new JWTBasicAuthenticationFilter(authenticationManager(), commonProperties.getJwtSignKey()));//验证是否登录
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 设置不拦截规则
		//TODO 对各监控资源是否放开拦截
		web.ignoring().antMatchers(
				"/webjars/springfox-swagger-ui/**",
				"/v2/api-docs", // swagger api json
				"/swagger-resources", // 用来获取api-docs的URI
				"/swagger-resources/configuration/ui/**", // 用来获取支持的动作
				"/swagger-resources/configuration/security/**", // 安全选项
				"/swagger-ui.html",
				"/error",
				"/**/favicon.ico",
				"/druid/**"//druid监控
				);
	}
	
	/**
	 * 配置authenticationManager
	 */
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        auth.eraseCredentials(true);
    }
	
}
