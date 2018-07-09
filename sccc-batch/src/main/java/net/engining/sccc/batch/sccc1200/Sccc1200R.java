package net.engining.sccc.batch.sccc1200;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;

/**
 * @author luxue
 *
 */
@Service("sccc1200R")
@StepScope
public class Sccc1200R extends AbstractKeyBasedStreamReader<String, Map<String, Object>>{
	
	private static final Logger log = LoggerFactory.getLogger(Sccc1200R.class);
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;
	
	public Sccc1200R(){
		//设置批量Step的Reader，在进行分片处理时的最小分片数据量
		setMinPartitionSize(100);
	}

	@Override
	protected List<String> loadKeys() {
		log.debug("##################Sccc1200R load keys , batchDate={}",batchDate);
		List<String> keyLs = Lists.newArrayList();
		//TODO load keys
		return keyLs;
	}

	@Override
	protected Map<String, Object> loadItemByKey(String key) {
		log.debug("##################Sccc1200R load Item By Key ");
		Map<String, Object> map =Maps.newHashMap();
		//TODO load data by key
		return map;
	}

}
