package net.engining.sccc.batch.sccc5400;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.PostTypeDef;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QGlTransOprHst;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;
import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;
import net.engining.sccc.batch.sccc5400.bean.SummaryBySubject;

@Service
@StepScope
public class Sccc5400R extends AbstractKeyBasedStreamReader<String, SummaryBySubject>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;
	
	@Override
	protected List<String> loadKeys() {
//		List<String> key = new ArrayList<>();
		QApGlVolDtl q = QApGlVolDtl.apGlVolDtl;
		QApGlTxn glTxn=QApGlTxn.apGlTxn;
		QGlTransOprHst oprHst=QGlTransOprHst.glTransOprHst;
			List<String> menudb=new JPAQueryFactory(em)
			.select(q.dbsubjectCd)
			.from(q,oprHst).groupBy(q.dbsubjectCd).where(oprHst.txnDetailSeq.eq(q.txnDetailSeq),oprHst.checkFlag.eq(CheckFlagDef.A),q.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),q.bizDate.eq(batchDate))
			.having(q.dbsubjectCd.isNotEmpty()).fetch();
			
			List<String> menucr = new JPAQueryFactory(em)
					.select(q.crsubjectCd)
					.from(q,oprHst).groupBy(q.crsubjectCd).where(oprHst.txnDetailSeq.eq(q.txnDetailSeq),oprHst.checkFlag.eq(CheckFlagDef.A),q.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),q.bizDate.eq(batchDate))
					.having(q.crsubjectCd.isNotEmpty()).fetch();
			
		List<String> sysmGltSeqList = new JPAQueryFactory(em).select(glTxn.gltSeq).from(glTxn).where(glTxn.postType.eq(PostTypeDef.SYSM)).fetch();
		List<String> sysmdb=new JPAQueryFactory(em)
			.select(q.dbsubjectCd)
			.from(q).groupBy(q.dbsubjectCd).where(q.txnDetailSeq.in(sysmGltSeqList),q.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),q.bizDate.eq(batchDate))
			.having(q.dbsubjectCd.isNotEmpty()).fetch();
		
		List<String> sysmcr=new JPAQueryFactory(em)
				.select(q.crsubjectCd)
				.from(q).groupBy(q.crsubjectCd).where(q.txnDetailSeq.in(sysmGltSeqList),q.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),q.bizDate.eq(batchDate))
				.having(q.crsubjectCd.isNotEmpty()).fetch();
		
		sysmdb.addAll(sysmcr);
		sysmdb.addAll(menudb);
		sysmdb.addAll(menucr);
		
     return sysmdb;
	}

	@Override
	protected SummaryBySubject loadItemByKey(String key) {
		QApGlVolDtl dbApGlVolDtl = QApGlVolDtl.apGlVolDtl;
		SummaryBySubject summaryBySubCd =new SummaryBySubject();
		
		//借方红字金额，条数
		Tuple reddb = new JPAQueryFactory(em)
				.select(dbApGlVolDtl.dbsubjectCd,dbApGlVolDtl.subjAmount,dbApGlVolDtl.org,dbApGlVolDtl.branch,
						dbApGlVolDtl.dbsubjectCd.count().as("redCount"),dbApGlVolDtl.subjAmount.sum().as("redSum"))
				.from(dbApGlVolDtl).where(dbApGlVolDtl.bizDate.eq(batchDate),dbApGlVolDtl.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),dbApGlVolDtl.redBlueInd.eq(RedBlueInd.R)).groupBy(dbApGlVolDtl.dbsubjectCd)
				.having(dbApGlVolDtl.dbsubjectCd.eq(key)).fetchOne();
		
		if(reddb !=null){
			summaryBySubCd.setSubjectCd(reddb.get(dbApGlVolDtl.dbsubjectCd));
			summaryBySubCd.setDbRedAmt(reddb.get(dbApGlVolDtl.subjAmount.sum().as("redSum")));
			summaryBySubCd.setDbRedCount(reddb.get(dbApGlVolDtl.dbsubjectCd.count().as("redCount")).intValue());
		}
		
		//借方正常金额，条数
		Tuple norDb = new JPAQueryFactory(em)
				.select(dbApGlVolDtl.dbsubjectCd,dbApGlVolDtl.subjAmount,dbApGlVolDtl.org,dbApGlVolDtl.branch,
						dbApGlVolDtl.dbsubjectCd.count().as("norCount"),dbApGlVolDtl.subjAmount.sum().as("norSum"))
				.from(dbApGlVolDtl).where(dbApGlVolDtl.bizDate.eq(batchDate),dbApGlVolDtl.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),dbApGlVolDtl.redBlueInd.eq(RedBlueInd.N)).groupBy(dbApGlVolDtl.dbsubjectCd)
				.having(dbApGlVolDtl.dbsubjectCd.eq(key)).fetchOne();
		if(norDb !=null){
			summaryBySubCd.setSubjectCd(norDb.get(dbApGlVolDtl.dbsubjectCd));
			summaryBySubCd.setDbNorAmt(norDb.get(dbApGlVolDtl.subjAmount.sum().as("norSum")));
			summaryBySubCd.setDbNorCount(norDb.get(dbApGlVolDtl.dbsubjectCd.count().as("norCount")).intValue());
		}
		
		//借方蓝字金额，条数
		Tuple blueDb = new JPAQueryFactory(em)
				.select(dbApGlVolDtl.dbsubjectCd,dbApGlVolDtl.subjAmount,dbApGlVolDtl.org,dbApGlVolDtl.branch,
						dbApGlVolDtl.dbsubjectCd.count().as("blueCount"),dbApGlVolDtl.subjAmount.sum().as("blueSum"))
				.from(dbApGlVolDtl).where(dbApGlVolDtl.bizDate.eq(batchDate),dbApGlVolDtl.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),dbApGlVolDtl.redBlueInd.eq(RedBlueInd.B)).groupBy(dbApGlVolDtl.dbsubjectCd)
				.having(dbApGlVolDtl.dbsubjectCd.eq(key)).fetchOne();
		if(blueDb !=null){
			summaryBySubCd.setSubjectCd(blueDb.get(dbApGlVolDtl.dbsubjectCd));
			summaryBySubCd.setDbBlueAmt(blueDb.get(dbApGlVolDtl.subjAmount.sum().as("blueSum")));
			summaryBySubCd.setDbBlueCount(blueDb.get(dbApGlVolDtl.dbsubjectCd.count().as("blueCount")).intValue());
		}
		
		QApGlVolDtl crApGlVolDtl = QApGlVolDtl.apGlVolDtl;
		//贷方红字金额，条数
		Tuple redCr = new JPAQueryFactory(em)
				.select(crApGlVolDtl.crsubjectCd,crApGlVolDtl.subjAmount,crApGlVolDtl.org,crApGlVolDtl.branch,
						crApGlVolDtl.crsubjectCd.count().as("redCount"),crApGlVolDtl.subjAmount.sum().as("redSum"))
				.from(crApGlVolDtl).where(dbApGlVolDtl.bizDate.eq(batchDate),dbApGlVolDtl.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),dbApGlVolDtl.redBlueInd.eq(RedBlueInd.R)).groupBy(crApGlVolDtl.crsubjectCd)
				.having(crApGlVolDtl.crsubjectCd.eq(key)).fetchOne();
		if(redCr !=null){
			summaryBySubCd.setSubjectCd(redCr.get(crApGlVolDtl.crsubjectCd));
			summaryBySubCd.setCrRedAmt(redCr.get(crApGlVolDtl.subjAmount.sum().as("redSum")));
			summaryBySubCd.setCrRedCount(redCr.get(crApGlVolDtl.crsubjectCd.count().as("redCount")).intValue());
		}
		
		//贷方正常金额，条数
		Tuple norCr = new JPAQueryFactory(em)
				.select(crApGlVolDtl.crsubjectCd,crApGlVolDtl.subjAmount,crApGlVolDtl.org,crApGlVolDtl.branch,
						crApGlVolDtl.crsubjectCd.count().as("norCount"),crApGlVolDtl.subjAmount.sum().as("norSum"))
				.from(crApGlVolDtl).where(dbApGlVolDtl.bizDate.eq(batchDate),dbApGlVolDtl.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),dbApGlVolDtl.redBlueInd.eq(RedBlueInd.N)).groupBy(crApGlVolDtl.crsubjectCd)
				.having(crApGlVolDtl.crsubjectCd.eq(key)).fetchOne();
		if(norCr !=null){
			summaryBySubCd.setSubjectCd(norCr.get(crApGlVolDtl.crsubjectCd));
			summaryBySubCd.setCrNorAmt(norCr.get(crApGlVolDtl.subjAmount.sum().as("norSum")));
			summaryBySubCd.setCrNorCount(norCr.get(crApGlVolDtl.crsubjectCd.count().as("norCount")).intValue());
		}
		
		//贷方蓝字金额，条数
		Tuple blueCr = new JPAQueryFactory(em)
				.select(crApGlVolDtl.crsubjectCd,crApGlVolDtl.subjAmount,crApGlVolDtl.org,crApGlVolDtl.branch,
						crApGlVolDtl.crsubjectCd.count().as("blueCount"),crApGlVolDtl.subjAmount.sum().as("blueSum"))
				.from(crApGlVolDtl).where(dbApGlVolDtl.bizDate.eq(batchDate),dbApGlVolDtl.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),dbApGlVolDtl.redBlueInd.eq(RedBlueInd.B)).groupBy(crApGlVolDtl.crsubjectCd)
				.having(crApGlVolDtl.crsubjectCd.eq(key)).fetchOne();
		if(blueCr !=null){
			summaryBySubCd.setSubjectCd(blueCr.get(crApGlVolDtl.crsubjectCd));
			summaryBySubCd.setCrBlueAmt(blueCr.get(crApGlVolDtl.subjAmount.sum().as("blueSum")));
			summaryBySubCd.setCrBlueCount(blueCr.get(crApGlVolDtl.crsubjectCd.count().as("blueCount")).intValue());
		}
		return summaryBySubCd;
		
	}
}

