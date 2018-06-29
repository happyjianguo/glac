package net.engining.sccc.batch.sccc1101;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.CactTxnPost;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
@Service
@StepScope
public class sccc1101P09OnlineUntreated implements ItemProcessor<CactTxnPost, CactTxnPost>{
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 一条条处理未对账和状态不明的cacttxnpost的数据
	 */
	@Override
	public CactTxnPost process(CactTxnPost item) throws Exception {

		CactTxnPost cactTxnPost = em.find(CactTxnPost.class, item.getTxnSeq());
		cactTxnPost.setCheckAccountStatus(CheckAccountStatusDef.UNCHK);
		
		QApGlTxn qApGlTxn = QApGlTxn.apGlTxn;
		ApGlTxn apGlTxn = new JPAQueryFactory(em).select(qApGlTxn).from(qApGlTxn)
				.where(qApGlTxn.txnDetailSeq.eq(cactTxnPost.getTxnSeq().toString())).fetchOne();
		apGlTxn.setPostGlInd(PostGlInd.Unknow);
		
		QApGlVolDtl qApGlVolDtl = QApGlVolDtl.apGlVolDtl;
		List<ApGlVolDtl> list = new JPAQueryFactory(em).select(qApGlVolDtl).from(qApGlVolDtl)
				.where(qApGlVolDtl.txnDetailSeq.eq(apGlTxn.getGltSeq().toString())).fetch();
		for(ApGlVolDtl apGlVolDtl : list){
			em.find(ApGlVolDtl.class, apGlVolDtl.getGlvSeq());
			apGlVolDtl.setPostGlInd(PostGlInd.Unknow);
		}
		
		
		
		return cactTxnPost;
	}

}