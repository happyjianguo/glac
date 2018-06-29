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

import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pg.batch.sdk.KeyBasedStreamReader;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.batch.service.DataBatchProcessService;
import net.engining.sccc.config.props.BatchTaskProperties;
@Service
@StepScope
public class Sccc999903R extends KeyBasedStreamReader<Range, List<String>> implements InitializingBean{

	@PersistenceContext
	private EntityManager em;

	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;
	
	@Autowired
	private BatchTaskProperties batchTaskProperties;
	
	@Autowired
	private DataBatchProcessService dataBatchProcess;

	@Override
	protected List<Range> loadKeys() {
		
		int size = batchTaskProperties.getDataHandleSizeOfApGlVolDtl();
		QApGlVolDtl qApGlVolDtl = QApGlVolDtl.apGlVolDtl;
		long db = new JPAQueryFactory(em).select(qApGlVolDtl.glvSeq.count().as("count")).from(qApGlVolDtl)
				.where(qApGlVolDtl.bizDate.eq(batchDate), qApGlVolDtl.postGlInd.notIn(PostGlInd.Unknow,PostGlInd.OddSuspend)).fetchCount();
		
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
		FetchResponse<String> result = dataBatchProcess.getApGlVolDtl(range, batchDate);
		List<String> list = result.getData();
		return list;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setMinPartitionSize(100);
		// TODO Auto-generated method stub
		
	}

}
