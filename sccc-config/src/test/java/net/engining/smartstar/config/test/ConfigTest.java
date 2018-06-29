package net.engining.smartstar.config.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.transaction.annotation.Transactional;

import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.db.id.generator.SnowflakeSequenceID;
import net.engining.smartstar.config.test.entity.SsUsrAddinf;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//整个测试项目只能有一个,默认扫描启动类的package所在路径下的@Configuration
//@SpringBootApplication(scanBasePackageClasses=CombineConfiguration.class)//(scanBasePackages="net.engining.smartstar.config")
//@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
public class ConfigTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Autowired
	private SnowflakeSequenceID snowflakeSequenceID;
	
	@Autowired
	private ParameterFacility parameterFacility;
	
	/**
	 * 
	 */
//	@Test
	public void commonConfigTest(){
		logger.debug("snowflakeWorkerId={}",jpaProperties.getProperties().get("pg.snowflake.workerId"));
		assertThat(jpaProperties.getProperties().get("pg.snowflake.workerId"), equalTo(0L));
		logger.debug("snow flak id = {}", snowflakeSequenceID.nextIdString());
		logger.debug("parameterFacility ={}", parameterFacility.getClass());
	}
	
	/**
	 * 
	 */
//	@Test
	@Transactional
	public void initData() {
		
		// 初始化必要的测试数据
		SsUsrAddinf ssUsrAddinf = new SsUsrAddinf();
		ssUsrAddinf.setBizDate(new Date());
		ssUsrAddinf.setCustId(UUID.randomUUID().toString());
		ssUsrAddinf.setHeight(165);
		ssUsrAddinf.setMailVailFlag(false);
		ssUsrAddinf.setMobileVailFlag(false);
		ssUsrAddinf.setNickName("testredis");
		ssUsrAddinf.setPuId(UUID.randomUUID().toString());
		ssUsrAddinf.setQqId("123654789");
		ssUsrAddinf.setRegTime(new Date());
		ssUsrAddinf.setSecScore(100);
		ssUsrAddinf.setSellerId(123);
		ssUsrAddinf.setUpdateTime(new Date());
		ssUsrAddinf.setUpdateUser("Test");
		ssUsrAddinf.setWchatId("123654789");
		ssUsrAddinf.setWeiboId("123654789@qq.com");
		ssUsrAddinf.setWeight(102);
		em.persist(ssUsrAddinf);
		ssUsrAddinf.equals(ssUsrAddinf);
		ssUsrAddinf.hashCode();
		
	}
}
