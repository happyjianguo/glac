package net.engining.sccc.biz.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlHst;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;
import net.engining.sccc.biz.bean.SummaryBySubject;

@Service
public class LegerVolService {

	@PersistenceContext
	private EntityManager em;
	
	public SummaryBySubject queryApVolDtl(Date batchDate, String key) {
		QApGlVolDtlHst dbApGlVolDtl = QApGlVolDtlHst.apGlVolDtlHst;
		SummaryBySubject summaryBySubCd =new SummaryBySubject();
		//借方红字金额，条数
				Tuple reddb = new JPAQueryFactory(em)
						.select(dbApGlVolDtl.dbsubjectCd,dbApGlVolDtl.subjAmount,dbApGlVolDtl.org,dbApGlVolDtl.branch,
								dbApGlVolDtl.dbsubjectCd.count().as("redCount"),dbApGlVolDtl.subjAmount.sum().as("redSum"))
						.from(dbApGlVolDtl).where(dbApGlVolDtl.volDt.eq(batchDate),dbApGlVolDtl.postGlInd.in(PostGlInd.Normal,PostGlInd.OddSuspend),dbApGlVolDtl.redBlueInd.eq(RedBlueInd.R)).groupBy(dbApGlVolDtl.dbsubjectCd)
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
