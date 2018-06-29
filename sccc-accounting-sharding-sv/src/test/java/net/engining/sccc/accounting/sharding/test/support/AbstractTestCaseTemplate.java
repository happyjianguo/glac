package net.engining.sccc.accounting.sharding.test.support;

import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import net.engining.pg.support.core.context.ApplicationContextHolder;
import net.engining.pg.support.testcase.AbstractJUnit4SpringContextTestsWithServlet;
import net.engining.sccc.accounting.sharding.AccountingShardingApplication;

/**
 * 
 * 单元测试模版类，可作为抽象类继承，也可作为模版；
 * @author luxue
 *
 */
@ActiveProfiles(value={"autotest","shardingjdbc"})
@SpringBootTest(classes = AccountingShardingApplication.class)
public abstract class AbstractTestCaseTemplate extends AbstractJUnit4SpringContextTestsWithServlet {

	private static final Logger log = LoggerFactory.getLogger(AbstractTestCaseTemplate.class);

	/**
	 * 由实现该接口的抽象类实现，用于统一的进行整体测试数据初始化，通常结合init项目
	 * 
	 * @throws Exception
	 */
	@BeforeClass // 针对所有测试，只执行一次，且必须为static void
	@Transactional
	public static void init4Test() throws Exception {

		// TODO call init
	}
	
	@AfterClass // 针对所有测试，只执行一次，且必须为static void 
	public static void tearDown4Test() throws Exception {
		Server h2tcp = ApplicationContextHolder.getBean("h2tcp");
		h2tcp.stop();
		log.warn("H2 TCP server is closed");
	}

}
