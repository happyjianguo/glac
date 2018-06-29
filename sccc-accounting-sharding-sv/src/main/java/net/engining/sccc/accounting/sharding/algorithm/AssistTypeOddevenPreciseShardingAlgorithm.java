package net.engining.sccc.accounting.sharding.algorithm;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;

/**
 * 属于StandardShardingStrategy，只支持单分片键；PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。
 * 对ASSIST_TYPE按其hash code的奇偶数作为分片策略
 * @author luxue
 *
 */
public class AssistTypeOddevenPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String>{
	
	private static final Logger log = LoggerFactory.getLogger(AssistTypeOddevenPreciseShardingAlgorithm.class);


	/* (non-Javadoc)
	 * @see io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm#doSharding(java.util.Collection, io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue)
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
		log.info("availableTargetNames:{}, shardingValue:{}", JSON.toJSONString(availableTargetNames), JSON.toJSONString(shardingValue));
		
		String assistType = shardingValue.getValue();
		//对天数求奇偶
		int n = assistType.hashCode() % 2;
		for (String dbName : availableTargetNames) {
			if (dbName.endsWith(String.valueOf(n))) {
				log.info("return db name:{}", dbName);
				return dbName;
			}
		}
        return null;
	}


}
