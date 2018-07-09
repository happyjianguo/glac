package net.engining.sccc.batch.sccc1101;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;
import net.engining.pcx.cc.infrastructure.shared.model.CactTxnPost;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnPost;
import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;
@Service
@StepScope
public class Sccc1101R02 extends AbstractKeyBasedStreamReader<Integer, CactTxnPost>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	protected List<Integer> loadKeys() {
		QCactTxnPost qCactTxnPost = QCactTxnPost.cactTxnPost;
		return new JPAQueryFactory(em).select(qCactTxnPost.txnSeq)
				.from(qCactTxnPost).where(qCactTxnPost.checkAccountStatus.notIn(CheckAccountStatusDef.RIGHT, CheckAccountStatusDef.ADD)).fetch();
	}

	@Override
	protected CactTxnPost loadItemByKey(Integer key) {
		return em.find(CactTxnPost.class, key);
	}

}
