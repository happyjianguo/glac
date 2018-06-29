/**
 * 
 */
package net.engining.sccc.accounting.sharding.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import io.shardingjdbc.core.api.algorithm.sharding.ListShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.ShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

/**
 * 属于ComplexShardingStrategy，支持多分片键；
 * 针对AP_GL_VOL_DTL_HST表。对SUBJECT_CD按其hash code，按5取模作为分片策略；表里有2个字段表示科目，分别是DBSUBJECT_CD(借方科目)，CRSUBJECT_CD(贷方科目)
 * 数据的规则是只有一个科目字段有值，查询时采用二次分段式查询，即每次查询只有一个科目字段作为条件，分两次查询处理；
 * @author luxue
 *
 */
public class SubjectCdModuloComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm{
	private static final Logger log = LoggerFactory.getLogger(SubjectCdModuloComplexKeysShardingAlgorithm.class);

	/* (non-Javadoc)
	 * @see io.shardingjdbc.core.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm#doSharding(java.util.Collection, java.util.Collection)
	 */
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
		log.info("collection:" + JSON.toJSONString(availableTargetNames) + ",shardingValues:" + JSON.toJSONString(shardingValues));
		
		List<String> tableNames = Lists.newArrayList();
		
		Collection<String> dbSubjectCdValues = getShardingValue(shardingValues, "DBSUBJECT_CD");
        Collection<String> crSubjectCdValues = getShardingValue(shardingValues, "CRSUBJECT_CD");
        
        int n = 0;
        
        //借方科目作为条件时
        if(Optional.fromNullable(dbSubjectCdValues).isPresent()){
        	for(String dbSubjectCd : dbSubjectCdValues){
        		if(StringUtils.isNoneBlank(dbSubjectCd)){
        			//对SUBJECT_CD按其hash code，按5取模
            		n = Math.abs(dbSubjectCd.hashCode()) % 5;
            		for (String tableName : availableTargetNames) {
            			if (tableName.endsWith(String.valueOf(n))) {
            				log.info("return tableName:{}", tableName);
            				tableNames.add(tableName);
            				
            			}
            		}
        		}
        	}
        }
        
        if(Optional.fromNullable(crSubjectCdValues).isPresent()){
        	for(String crSubjectCd : crSubjectCdValues){
        		if(StringUtils.isNoneBlank(crSubjectCd)){
        			//对SUBJECT_CD按其hash code，按5取模
            		n = Math.abs(crSubjectCd.hashCode()) % 5;
            		for (String tableName : availableTargetNames) {
            			if (tableName.endsWith(String.valueOf(n))) {
            				log.info("return tableName:{}", tableName);
            				tableNames.add(tableName);
            			}
            		}
        		}
        	}
        }
		
        return tableNames;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Collection<String> getShardingValue(Collection<ShardingValue> shardingValues, final String key) {
        Collection<String> valueSet = new ArrayList<>();
        Iterator<ShardingValue> iterator = shardingValues.iterator();
        while (iterator.hasNext()) {
            ShardingValue next = iterator.next();
            if (next instanceof ListShardingValue) {
                ListShardingValue value = (ListShardingValue) next;
                if (value.getColumnName().equals(key)) {
                    return value.getValues();
                }
            }
        }
        return valueSet;
    }

}
