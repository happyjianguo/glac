package net.engining.sccc.batch.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAss;
import net.engining.pcx.cc.infrastructure.shared.model.QBtDataMigrationTempdtl;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;

@Service
public class DataBatchProcessService {
	
	@PersistenceContext
	private EntityManager em;
	
	public FetchResponse<String> getApGlTxn(Range range,Date batchDate) {
		
//		QBtDataMigrationTempdtl qApGlTxn = QBtDataMigrationTempdtl.btDataMigrationTempdtl;
//		JPAQuery<String> query = new JPAQueryFactory(em)
//		.select(qApGlTxn.branchNo)
//		.from(qApGlTxn).where(qApGlTxn.bizDate.eq(batchDate));
		
		QApGlTxn qApGlTxn = QApGlTxn.apGlTxn;
		JPAQuery<String> query =  new JPAQueryFactory(em)
				.select(qApGlTxn.gltSeq)
				.from(qApGlTxn).where(qApGlTxn.bizDate.eq(batchDate), qApGlTxn.postGlInd.notIn(PostGlInd.Unknow));
		
		return new JPAFetchResponseBuilder<String>()
			.range(range)
			.build(query);
	}
	
	
	public FetchResponse<String> getApGlVolDtl(Range range,Date batchDate) {
		
		QApGlVolDtl qApGlVolDtl = QApGlVolDtl.apGlVolDtl;
		JPAQuery<String> query = new JPAQueryFactory(em)
				.select(qApGlVolDtl.glvSeq)
				.from(qApGlVolDtl).where(qApGlVolDtl.bizDate.eq(batchDate), qApGlVolDtl.postGlInd.notIn(PostGlInd.Unknow,PostGlInd.OddSuspend));
		
		return new JPAFetchResponseBuilder<String>()
			.range(range)
			.build(query);
	}
	
	public FetchResponse<String> getApGlVolDtlAss(Range range,Date batchDate) {
		
		QApGlVolDtlAss qApGlVolDtlAss = QApGlVolDtlAss.apGlVolDtlAss;
		JPAQuery<String> query = new JPAQueryFactory(em)
				.select(qApGlVolDtlAss.assSeq)
				.from(qApGlVolDtlAss).where(qApGlVolDtlAss.bizDate.eq(batchDate));
		
		return new JPAFetchResponseBuilder<String>()
			.range(range)
			.build(query);
	}
}
