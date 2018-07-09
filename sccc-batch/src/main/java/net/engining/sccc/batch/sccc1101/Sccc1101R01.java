package net.engining.sccc.batch.sccc1101;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.TxnTypeDef;
import net.engining.pcx.cc.infrastructure.shared.model.BtEodTxnImport;
import net.engining.pcx.cc.infrastructure.shared.model.QBtEodTxnImport;
import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;

@Service
@StepScope
public class Sccc1101R01 extends AbstractKeyBasedStreamReader<String, BtEodTxnImport>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	protected List<String> loadKeys() {
		QBtEodTxnImport qBtEodTxnImport = QBtEodTxnImport.btEodTxnImport;
		return new JPAQueryFactory(em).select(qBtEodTxnImport.id)
				.from(qBtEodTxnImport).where(qBtEodTxnImport.txnType.eq(TxnTypeDef.RECON),qBtEodTxnImport.bizDate.eq(batchDate)).fetch();
	}

	@Override
	protected BtEodTxnImport loadItemByKey(String key) {
		
		return em.find(BtEodTxnImport.class, key);
	}

}
