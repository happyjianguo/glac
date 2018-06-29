/**
 * 
 */
package net.engining.sccc.accounting.sharding.algorithm;

import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

/**
 * 属于StandardShardingStrategy，只支持单分片键；PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。
 * 对日期型数据按天的奇偶数作为分片策略
 * @author luxue
 *
 */
public class DateOddevenPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date>{
	
	private static final Logger log = LoggerFactory.getLogger(DateOddevenPreciseShardingAlgorithm.class);


	/* (non-Javadoc)
	 * @see io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm#doSharding(java.util.Collection, io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue)
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
		log.info("availableTargetNames:{}, shardingValue:{}", JSON.toJSONString(availableTargetNames), JSON.toJSONString(shardingValue));
		LocalDate partDate = new LocalDate(shardingValue.getValue());
		//对天数求奇偶
		int n = partDate.getDayOfYear() % 2;
		for (String tableOrDbName : availableTargetNames) {
			if (tableOrDbName.endsWith(String.valueOf(n))) {
				log.info("return table or db Name:{}", tableOrDbName);
				return tableOrDbName;
			}
		}
        return null;
	}


}
