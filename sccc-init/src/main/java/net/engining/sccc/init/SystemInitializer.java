package net.engining.sccc.init;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import net.engining.pg.support.core.context.ApplicationContextHolder;
import net.engining.pg.support.init.ParameterInitializer;
import net.engining.pg.support.init.TableDataInitializer;

/**
 * 
 * Spring Boot应用程序在启动后，会遍历CommandLineRunner接口的实例并运行它们的run方法;
 * 该方法在SpringApplication.run之时，在ApplicationContext容器加载完成后被调用;
 * 也可以利用@Order注解（或者实现Order接口）来规定所有CommandLineRunner实例的运行顺序;
 * 区别于{@link ApplicationRunner}，接收{@link ApplicationArguments}作为参数，接收简单的String参数;
 * 
 * @author luxue
 *
 */
@Component
public class SystemInitializer implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SystemInitializer.class);

	private void init() throws Exception {

		// 执行所有参数初始化器
		log.info("执行所有参数初始化器");
		Map<String, ParameterInitializer> parameterInitializers = ApplicationContextHolder
				.getBeansOfType(ParameterInitializer.class);
		for (ParameterInitializer init : parameterInitializers.values()) {
			init.init();
		}

		// 执行所有数据初始化器
		log.info("执行所有数据初始化器");
		Map<String, TableDataInitializer> tableDataInitializer = ApplicationContextHolder
				.getBeansOfType(TableDataInitializer.class);
		for (TableDataInitializer init : tableDataInitializer.values()) {
			init.init();
		}

	}

	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		if (args.length < 1) {
			System.err.println("请指定 init 或  patch 参数");
			return;
		}

		ArrayList<String> argsLs = Lists.newArrayList(args);
		//lambda表达式无法跳出循环,不适合这里的逻辑
//		argsLs.forEach((item)->{
//			log.debug(String.format("传入系统参数:%s", item));
//			if("init".equals(item)){
//				systemInitializer.init();
//				
//			}
//		});
		
		for(String item : argsLs){
			log.debug(String.format("传入系统参数:%s", item));
			if("init".equals(item)){
				init();
				break;
			}
		}

	}

}
