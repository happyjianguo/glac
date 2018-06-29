package net.engining.sccc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.engining.pg.support.db.id.generator.SnowflakeSequenceID;

/**
 * SnowflakeSequence 相关的context配置
 * 
 * @author Administrator
 *
 */
@Configuration
public class SnowflakeSequenceIDContextConfig {

	@Autowired
	JpaProperties jpaProperties;

	@Bean
	public SnowflakeSequenceID snowflakeSequenceId() {
		String workerId = jpaProperties.getProperties().get("pg.snowflake.workerId");
		String dataCenterId = jpaProperties.getProperties().get("pg.snowflake.dataCenterId");
		SnowflakeSequenceID snowflakeSequenceIDUtils = new SnowflakeSequenceID(
				Long.parseLong(workerId),
				Long.parseLong(dataCenterId));
		return snowflakeSequenceIDUtils;

	}

}
