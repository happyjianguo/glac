package net.engining.sccc.accounting.sharding.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import net.engining.sccc.accounting.sharding.service.BatchDataTransferService;
import net.engining.sccc.accounting.sharding.test.support.AbstractTestCaseTemplate;

/**
 * @author luxue
 *
 */
public class BatchDataTransferServiceTest extends AbstractTestCaseTemplate{
	
	@Autowired
	BatchDataTransferService batchDataTransferService;
	
	/* (non-Javadoc)
	 * @see net.engining.pg.support.testcase.TestCase#initTestData()
	 */
	@Override
	public void initTestData() throws Exception {
		List<String> keys = Lists.newArrayList();
		keys.add("123654");
		keys.add("325689");
		batchDataTransferService.insertApGlTxnHst(keys);
		
	}

	/* (non-Javadoc)
	 * @see net.engining.pg.support.testcase.TestCase#assertResult()
	 */
	@Override
	public void assertResult() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.engining.pg.support.testcase.TestCase#testProcess()
	 */
	@Override
	public void testProcess() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.engining.pg.support.testcase.TestCase#end()
	 */
	@Override
	public void end() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
