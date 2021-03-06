package net.engining.sccc.batch.sccc0902;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;
import net.engining.sccc.entity.enums.TxnTypeDef;
import net.engining.sccc.entity.model.BtEodTxnImport;
import net.engining.sccc.entity.model.QBtEodTxnImport;

/**
 * 罚息计提入账
 * 
 * @author xiachuanhu
 *
 */
@Service
@StepScope
public class Sccc0902R03 extends AbstractKeyBasedStreamReader<String, BtEodTxnImport> {

	@PersistenceContext
	private EntityManager em;
	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;

	private static final Logger log = LoggerFactory.getLogger(Sccc0902R03.class);

	@Override
	protected List<String> loadKeys() {
		QBtEodTxnImport qBtEodTxnImport = QBtEodTxnImport.btEodTxnImport;
		return new JPAQueryFactory(em).select(qBtEodTxnImport.id).from(qBtEodTxnImport)
				.where(qBtEodTxnImport.bizDate.eq(bizDate),qBtEodTxnImport.txnType.eq(TxnTypeDef.TRANSFO)).fetch();

	}

	@Override
	protected BtEodTxnImport loadItemByKey(String key) {
		return em.find(BtEodTxnImport.class, key);
	}

}
