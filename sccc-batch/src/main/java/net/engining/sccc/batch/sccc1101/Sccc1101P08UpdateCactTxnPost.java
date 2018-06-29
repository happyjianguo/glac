package net.engining.sccc.batch.sccc1101;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.IntoGlStatusDef;
import net.engining.pcx.cc.infrastructure.shared.model.CactTxnPost;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnPost;
import net.engining.sccc.biz.bean.batchBean.DataTransBean;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
@Service
@StepScope
public class Sccc1101P08UpdateCactTxnPost implements ItemProcessor<DataTransBean, DataTransBean>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public DataTransBean process(DataTransBean bean) throws Exception {
		
		EveryDayAccountingBean item = bean.getEveryDayAccountingBean();
		QCactTxnPost qCactTxnPost = QCactTxnPost.cactTxnPost;
		CactTxnPost cactTxnPost = new JPAQueryFactory(em).select(qCactTxnPost).from(qCactTxnPost)
				.where(qCactTxnPost.txnDetailSeq.eq(item.getTxnSerialNo())).fetchOne();
		
		//更新CactTxnPost表对账状态
		cactTxnPost.setIntoGlStatus(IntoGlStatusDef.S);
		return bean;
	}

}
