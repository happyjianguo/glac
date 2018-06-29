package net.engining.sccc.batch.sccc1100;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.engining.pg.batch.sdk.KeyBasedStreamReader;

/**
 * 从核心导入产品参数
 * @author luxue
 *
 */
@Service("sccc1100R")
@StepScope
public class Sccc1100R extends KeyBasedStreamReader<String, Map<String, Object>>{
	
	private static final Logger log = LoggerFactory.getLogger(Sccc1100R.class);

	@Override
	protected List<String> loadKeys() {
		log.debug("##################Sccc1100R load keys ");
		List<String> keyLs = Lists.newArrayList();
		//TODO load keys
		return keyLs;
	}

	@Override
	protected Map<String, Object> loadItemByKey(String key) {
		log.debug("##################Sccc1100R load Item By Key ");
		Map<String, Object> map =Maps.newHashMap();
		//TODO load data by key
		return map;
	}

}
