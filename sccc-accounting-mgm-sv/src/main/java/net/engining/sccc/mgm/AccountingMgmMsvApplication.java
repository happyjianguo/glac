package net.engining.sccc.mgm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 会计核算微服务SpringBoot启动类;
 * 这里继承了SpringBootServletInitializer，是为了可以支持WAR包部署的方式；如果只考虑用jar方式启动内置Tomcat，则不需要继承；
 * 
 * @author luxue
 *
 */
@SpringBootApplication
//@EnableEurekaClient //用@EnableDiscoveryClient代替，这样可以同时支持其他注册服务器如：Consul
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
//@RefreshScope//用于支持动态刷新配置，但有坑，需要注意
public class AccountingMgmMsvApplication extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AccountingMgmMsvApplication.class);
    }

    public static void main(String[] args) {
//        new SpringApplicationBuilder(AccountingMsvApplication.class).web(true).run(args);
    	SpringApplication.run(AccountingMgmMsvApplication.class, args);
    }
}
