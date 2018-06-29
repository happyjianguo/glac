package net.engining.sccc.accounting.sharding.algorithm;

import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

/**
 * 属于StandardShardingStrategy，只支持单分片键；RangeShardingAlgorithm用于处理BETWEEN AND分片。
 * 对日期型数据按天的奇偶数作为分片策略
 * @author luxue
 *
 */
public class DateOddevenRangeShardingAlgorithm implements RangeShardingAlgorithm<Date>{
	
	
	private static final Logger log = LoggerFactory.getLogger(DateOddevenRangeShardingAlgorithm.class);

	/* (non-Javadoc)
	 * @see io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm#doSharding(java.util.Collection, io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue)
	 */
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
		log.info("availableTargetNames:{}, shardingValue:{}", JSON.toJSONString(availableTargetNames), JSON.toJSONString(shardingValue));
		
		Collection<String> tableOrDbNames = Lists.newArrayList();
		
		Range<Date> valueRange = shardingValue.getValueRange();
		LocalDate startDate = new LocalDate(valueRange.lowerEndpoint());
		LocalDate endDate = new LocalDate(valueRange.upperEndpoint());
		for (int i = startDate.getDayOfYear(); i <= endDate.getDayOfYear(); i++) {
			for (String tableOrDbName : availableTargetNames) {
				//对范围内包含的每个天数求奇偶
				int n = i % 2;
				if (tableOrDbName.endsWith(String.valueOf(n))) {
					if(!tableOrDbNames.contains(tableOrDbName)){
						log.info("return table or db Name:{}", tableOrDbName);
						tableOrDbNames.add(tableOrDbName);
					}
				}
			}
		}
		
		return tableOrDbNames;
	}

}
