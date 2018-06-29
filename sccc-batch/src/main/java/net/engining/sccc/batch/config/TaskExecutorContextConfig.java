/**
 * 
 */
package net.engining.sccc.batch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 配置批量Step需要的task:executor
 * @author luxue
 *
 */
@Configuration
public class TaskExecutorContextConfig {

	@Autowired
	BatchTaskProperties batchTaskProperties;
	
	@Bean("dataMigrationExecutor")
	public ThreadPoolTaskExecutor dataMigrationExecutor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getDefaultExecutorSize());
		return taskExecutor;
	}
	
	@Bean("endOfDayExecutor")
	public ThreadPoolTaskExecutor endOfDayExecutor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getDefaultExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc0810Executor")
	public ThreadPoolTaskExecutor sccc0810Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getSccc0810ExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc0820Executor")
	public ThreadPoolTaskExecutor sccc0820Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getSccc0820ExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc0830Executor")
	public ThreadPoolTaskExecutor sccc0830Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getSccc0830ExecutorSize());
		return taskExecutor;
	}
	
	@Bean("scccPrev4EODExecutor")
	public ThreadPoolTaskExecutor scccPrev4EODExecutor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getDefaultExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc0901Executor")
	public ThreadPoolTaskExecutor sccc0901Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getSccc0901ExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc5800Executor")
	public ThreadPoolTaskExecutor sccc5800Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getDefaultExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc0902Executor")
	public ThreadPoolTaskExecutor sccc0902Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getSccc0902ExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc1000Executor")
	public ThreadPoolTaskExecutor sccc1000Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getDefaultExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc1101Executor")
	public ThreadPoolTaskExecutor sccc1101Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getSccc1101ExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc5701Executor")
	public ThreadPoolTaskExecutor sccc5701Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getSccc5701ExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc5801Executor")
	public ThreadPoolTaskExecutor sccc5801Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getSccc5801ExecutorSize());
		return taskExecutor;
	}
	
	@Bean("sccc9999Executor")
	public ThreadPoolTaskExecutor sccc9999Executor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(batchTaskProperties.getSccc9999ExecutorSize());
		return taskExecutor;
	}
	
}
