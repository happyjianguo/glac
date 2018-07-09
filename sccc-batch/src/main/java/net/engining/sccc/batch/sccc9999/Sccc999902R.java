package net.engining.sccc.batch.sccc9999;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.batch.service.DataBatchProcessService;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 
 * 数据治理，当日总帐数据迁移到相应历史表；<br>
 * AP_GL_TXN -> AP_GL_TXN_HST;<br>
 *
 */
@Service
@StepScope
public class Sccc999902R extends AbstractKeyBasedStreamReader<Range, List<String>> implements InitializingBean{

	@PersistenceContext
	private EntityManager em;

	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Autowired
	private BatchTaskProperties batchTaskProperties;
	
	@Autowired
	private DataBatchProcessService dataBatchProcessService;
	
	@Override
	protected List<Range> loadKeys() {
		
		int size = batchTaskProperties.getDataHandleSizeOfApGlTxn();
		QApGlTxn qApGlTxn = QApGlTxn.apGlTxn;
		long db = new JPAQueryFactory(em).select(qApGlTxn.gltSeq.count().as("count")).from(qApGlTxn)
				.where(qApGlTxn.bizDate.eq(batchDate), qApGlTxn.postGlInd.notIn(PostGlInd.Unknow)).fetchCount();
		
		long fre = 0;
		long merchant  = db % size;
		if(merchant != 0){
			fre = db / size +1;
		}else{
			fre = db / size;
		}
		
		int start=0;
		Range range = null;
		List<Range> listFinal=Lists.newArrayList();
		//构造Range的Json串，作为key；反过来通过Json串转为Range类作为item
		for(int i = 0;i < fre;i++){
			if(start == db){
				break;
			}
			range = new Range(start,size);
			listFinal.add(range);
			start= start+size;
		}
		return listFinal;
	}

	@Override
	protected List<String> loadItemByKey(Range range) {
		FetchResponse<String> result = dataBatchProcessService.getApGlTxn(range,batchDate);
		List<String> list = result.getData();
		return list;
	}

	/* 
	 * 该表对应的主键list太大时，会造成KEY_LIST字段超长，需要减小默认最小分片的大小
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.setMinPartitionSize(100);
	}
}
