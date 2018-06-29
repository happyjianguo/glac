package net.engining.sccc.accounting.sharding.algorithm;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

/**
 * 属于StandardShardingStrategy，只支持单分片键；PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。
 * 对SUBJECT_CD按其hash code，按15取模作为分片策略
 * @author luxue
 *
 */
public class SubjectCdModuloPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String>{
	
	private static final Logger log = LoggerFactory.getLogger(SubjectCdModuloPreciseShardingAlgorithm.class);


	/* (non-Javadoc)
	 * @see io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm#doSharding(java.util.Collection, io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue)
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
		log.info("availableTargetNames:{}, shardingValue:{}", JSON.toJSONString(availableTargetNames), JSON.toJSONString(shardingValue));
		
		String subjectcd = shardingValue.getValue();
		//对SUBJECT_CD按其hash code，按15取模
		int n = Math.abs(subjectcd.hashCode()) % 15;
		for (String tableName : availableTargetNames) {
			if (tableName.endsWith(String.valueOf(n))) {
				log.info("return tableName:{}", tableName);
				return tableName;
			}
		}
        return null;
	}


}
