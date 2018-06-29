package net.engining.sccc.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 系统初始化项目的启动类
 * @author luxue
 *
 */
//默认扫描启动类的package所在路径下的@Configuration; 也可以指定扫描指定package
@SpringBootApplication
@EnableConfigurationProperties
public class InitApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(InitApplication.class, args);
	}
	
}
