package net.engining.sccc.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;

import net.engining.pg.support.utils.H2MvccConfigurer;

@Configuration
public class DataSource4H2ContextConfig {

	/**
	 * 使用内存H2数据库作为数据源，数据库名为mem:memdb,用户名sa，无密码，可以使用jdbc:h2:localhost:mem:memdb;LOCK_MODE=0来连接。LOCK_MODE=0为指定脏读;
	 * 这里使用自定义Configurer而不是使用<jdbc:embedded-database/>，目的是为了加上MVCC=true参数，不然H2会用锁表来代替，造成一堆锁表问题，以及锁表造成的暴露不出的问题;
	 * @return
	 */
	@Bean
	public EmbeddedDatabase dataSource() {
		EmbeddedDatabaseFactoryBean embeddedDatabaseFactoryBean = new EmbeddedDatabaseFactoryBean();
		embeddedDatabaseFactoryBean.setDatabaseName("memdb");
		embeddedDatabaseFactoryBean.setDatabaseConfigurer(new H2MvccConfigurer());
		return embeddedDatabaseFactoryBean.getDatabase();
	}
	
	@Bean(name="h2tcp", initMethod="start", destroyMethod="stop")
	public Server tcpServver4H2() throws Exception{
		Server server = Server.createTcpServer("-tcp","-tcpAllowOthers","-tcpPort","9092");
		return server;
		
	}
	
}
