package net.engining.sccc.batch.sccc1100;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import net.engining.sccc.batch.sccc1100.bean.SampleBean;

/**
 * 
 * @author luxue
 *
 */
@Service
@StepScope
public class Sccc1100P10ProductDataIncoming implements ItemProcessor<Map<String, Object>, SampleBean> {

	private static final Logger log = LoggerFactory.getLogger(Sccc1100P10ProductDataIncoming.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public SampleBean process(Map<String, Object> item) throws Exception {
		// TODO do biz logic
		log.debug("##################Sccc1100P10ProductDataIncoming process ");
		
		return null;
	}

}
