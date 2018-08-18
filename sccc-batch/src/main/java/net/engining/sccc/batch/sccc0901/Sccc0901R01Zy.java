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

import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;
import net.engining.sccc.entity.model.BtZyImport;
import net.engining.sccc.entity.model.QBtZyImport;

/**
 * @author luxue
 *
 */
@Service
@StepScope
public class Sccc0901R01Zy extends AbstractKeyBasedStreamReader<Integer, BtZyImport>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;
	
	private static final Logger log = LoggerFactory.getLogger(Sccc0901R01Zy.class);

	@Override
	protected List<Integer> loadKeys() {
		QBtZyImport qBtZyImport = QBtZyImport.btZyImport;
		return new JPAQueryFactory(em).select(qBtZyImport.id).from(qBtZyImport).where(qBtZyImport.bizDate.eq(bizDate)).fetch();
	
	}

	@Override
	protected BtZyImport loadItemByKey(Integer key) {
		return em.find(BtZyImport.class, key);
	}

}
