package net.engining.sccc.batch.sccc1101;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;
import net.engining.pcx.cc.infrastructure.shared.model.CactTxnPost;
@Service
@StepScope
public class Sccc1101P10OnlineSUB implements ItemProcessor<CactTxnPost, CactTxnPost>{
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 一条条处理超出时间期限的cacttxnpost的数据
	 */
	@Override
	public CactTxnPost process(CactTxnPost item) throws Exception {

		CactTxnPost cactTxnPost = em.find(CactTxnPost.class, item.getTxnSeq());
		cactTxnPost.setCheckAccountStatus(CheckAccountStatusDef.SUB);
		
		return cactTxnPost;
	}

}