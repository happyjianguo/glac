package net.engining.sccc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//开启支持方法注解的权限控制：
//@PreAuthorize：该注解用来确定一个方法是否应该被执行。该注解后面跟着的是一个表达式，如果表达式的值为真，则该方法会被执行。如 @PreAuthorize("hasRole('ROLE_USER')")就说明只有当前用户具有角色 ROLE_USER的时候才会执行。
//@PostAuthorize：该注解用来在方法执行完之后进行访问控制检查。
//@PostFilter：该注解用来对方法的返回结果进行过滤。从返回的集合中过滤掉表达式值为假的元素。如@PostFilter("hasPermission(filterObject, 'read')")说明返回的结果中只保留当前用户有读权限的元素。
//@PreFilter：该注解用来对方法调用时的参数进行过滤。	
//等同于<security:global-method-security pre-post-annotations="enabled" mode="aspectj"/>
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, mode = AdviceMode.ASPECTJ)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityExtContextConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/home").permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
				.and().logout().permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//		设置不拦截规则；等同于以下：
		//		<security:http pattern="/static/js/**" security="none"/>
		//		<security:http pattern="/static/images/**" security="none"/>
		//		<security:http pattern="/static/css/**" security="none"/>
		//		<security:http pattern="/static/fonts/**" security="none"/>
		web.ignoring().antMatchers("/static/**");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}

}
