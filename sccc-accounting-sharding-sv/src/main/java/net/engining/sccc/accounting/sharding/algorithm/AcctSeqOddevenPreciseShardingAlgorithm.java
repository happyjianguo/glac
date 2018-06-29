package net.engining.sccc.accounting.sharding.algorithm;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

/**
 * 属于StandardShardingStrategy，只支持单分片键；PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。
 * 对AccSeq的奇偶数作为分片策略
 * @author luxue
 *
 */
public class AcctSeqOddevenPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer>{
	
	private static final Logger log = LoggerFactory.getLogger(AcctSeqOddevenPreciseShardingAlgorithm.class);


	/* (non-Javadoc)
	 * @see io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm#doSharding(java.util.Collection, io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue)
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
		log.info("availableTargetNames:{}, shardingValue:{}", JSON.toJSONString(availableTargetNames), JSON.toJSONString(shardingValue));
		
		Integer acctSeq = shardingValue.getValue();
		//对AccSeq求奇偶
		int n = acctSeq % 2;
		for (String tableOrDbName : availableTargetNames) {
			if (tableOrDbName.endsWith(String.valueOf(n))) {
				log.info("return table or db Name:{}", tableOrDbName);
				return tableOrDbName;
			}
		}
        return null;
	}


}
