package net.engining.sccc.config;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Channel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * RabbitMQ自定义配置；<br> 
 * 要使用此配置，需要排除自动配置；@SpringBootApplication(exclude = RabbitAutoConfiguration.class)
 * 
 * @author luxue
 *
 */
@Configuration
@ConditionalOnClass({ RabbitTemplate.class, Channel.class })
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitMQContextConfig {

	@Autowired
	private RabbitProperties rabbitProperties;

	@Autowired
	BatchTaskProperties batchTaskProperties;

	/**
	 * 创建连接;
	 * 参考{@link org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration}
	 * 
	 * @param amqpConnectionTaskExecutor
	 * @return
	 * @throws Exception
	 */
	@Bean
	public CachingConnectionFactory rabbitConnectionFactory(ThreadPoolTaskExecutor amqpConnectionTaskExecutor) throws Exception {

		RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
		if (rabbitProperties.determineHost() != null) {
			factory.setHost(rabbitProperties.determineHost());
		}
		factory.setPort(rabbitProperties.determinePort());
		if (rabbitProperties.determineUsername() != null) {
			factory.setUsername(rabbitProperties.determineUsername());
		}
		if (rabbitProperties.determinePassword() != null) {
			factory.setPassword(rabbitProperties.determinePassword());
		}
		if (rabbitProperties.determineVirtualHost() != null) {
			factory.setVirtualHost(rabbitProperties.determineVirtualHost());
		}
		if (rabbitProperties.getRequestedHeartbeat() != null) {
			factory.setRequestedHeartbeat(rabbitProperties.getRequestedHeartbeat());
		}
		RabbitProperties.Ssl ssl = rabbitProperties.getSsl();
		if (ssl.isEnabled()) {
			factory.setUseSSL(true);
			if (ssl.getAlgorithm() != null) {
				factory.setSslAlgorithm(ssl.getAlgorithm());
			}
			factory.setKeyStore(ssl.getKeyStore());
			factory.setKeyStorePassphrase(ssl.getKeyStorePassword());
			factory.setTrustStore(ssl.getTrustStore());
			factory.setTrustStorePassphrase(ssl.getTrustStorePassword());
		}
		if (rabbitProperties.getConnectionTimeout() != null) {
			factory.setConnectionTimeout(rabbitProperties.getConnectionTimeout());
		}
		
		//这里设置创建多线程RabbitMQ连接
		int nThreads = batchTaskProperties.getDefaultExecutorSize();
		factory.setSharedExecutor(new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(nThreads*100)));
		factory.afterPropertiesSet();

		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(factory.getObject());
		cachingConnectionFactory.setAddresses(rabbitProperties.determineAddresses());
		cachingConnectionFactory.setPublisherConfirms(rabbitProperties.isPublisherConfirms());
		cachingConnectionFactory.setPublisherReturns(rabbitProperties.isPublisherReturns());
		if (rabbitProperties.getCache().getChannel().getSize() != null) {
			cachingConnectionFactory.setChannelCacheSize(rabbitProperties.getCache().getChannel().getSize());
		}
		if (rabbitProperties.getCache().getConnection().getMode() != null) {
			cachingConnectionFactory.setCacheMode(rabbitProperties.getCache().getConnection().getMode());
		}
		if (rabbitProperties.getCache().getConnection().getSize() != null) {
			cachingConnectionFactory.setConnectionCacheSize(rabbitProperties.getCache().getConnection().getSize());
		}
		if (rabbitProperties.getCache().getChannel().getCheckoutTimeout() != null) {
			cachingConnectionFactory.setChannelCheckoutTimeout(rabbitProperties.getCache().getChannel().getCheckoutTimeout());
		}
		return cachingConnectionFactory;

	}

}
