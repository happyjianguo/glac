package net.engining.sccc.batch.sccc0901;

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

import net.engining.pcx.cc.infrastructure.shared.model.BtNqhImport;
import net.engining.pcx.cc.infrastructure.shared.model.QBtNqhImport;
import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;

/**
 * @author luxue
 *
 */
@Service
@StepScope
public class Sccc0901R02Nqh extends AbstractKeyBasedStreamReader<Integer, BtNqhImport> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;

	private static final Logger log = LoggerFactory.getLogger(Sccc0901R02Nqh.class);

	@Override
	protected List<Integer> loadKeys() {
		QBtNqhImport qBtNqhImport = QBtNqhImport.btNqhImport;
		return new JPAQueryFactory(em).select(qBtNqhImport.id).from(qBtNqhImport)
				.where(qBtNqhImport.bizDate.eq(bizDate)).fetch();

	}

	@Override
	protected BtNqhImport loadItemByKey(Integer key) {
		return em.find(BtNqhImport.class, key);
	}

}
