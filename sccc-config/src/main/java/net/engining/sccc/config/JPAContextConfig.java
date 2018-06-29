package net.engining.sccc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA 相关的context配置
 * @author Administrator
 *
 */
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)//等同于<tx:annotation-driven mode="aspectj" transaction-manager="transactionManager" />
@Import(value = {
		JPA4H2ContextConfig.class
		})
public class JPAContextConfig {
	
	@Autowired
	DataSource dataSource;

	/**
	 * 保留纯jdbc方式操作数据库的能力
	 * @return
	 */
	@Bean
	public JdbcTemplate jdbcTemplate(){
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		return jdbcTemplate;
		
	}
	
}
