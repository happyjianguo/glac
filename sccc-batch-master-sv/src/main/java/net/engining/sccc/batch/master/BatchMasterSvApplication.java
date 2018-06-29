package net.engining.sccc.batch.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 会计核算微服务SpringBoot启动类
 * @author luxue
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
//@RefreshScope//用于支持动态刷新配置，但有坑，需要注意
//这里继承了SpringBootServletInitializer，是为了可以支持WAR包部署的方式；如果只考虑用jar方式启动内置Tomcat，则不需要继承；
public class BatchMasterSvApplication extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BatchMasterSvApplication.class);
    }

    public static void main(String[] args) {
//        new SpringApplicationBuilder(AccountingMsvApplication.class).web(true).run(args);
    	SpringApplication.run(BatchMasterSvApplication.class, args);
    	
    	
    }
}
