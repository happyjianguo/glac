package net.engining.sccc.batch.sccc5902;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pg.batch.entity.model.PgFileItem;
import net.engining.pg.batch.entity.model.QPgFileItem;
import net.engining.pg.batch.sdk.KeyBasedStreamReader;
@Service
@StepScope
public class Sccc5902R02PgFileItem extends KeyBasedStreamReader<Long, PgFileItem>{
	@PersistenceContext
	private EntityManager em;
	@Value("#{jobParameters['batchSeq']}")
	private String batchSeq;

	@Override
	protected List<Long> loadKeys() {
		QPgFileItem q = QPgFileItem.pgFileItem;
		return new JPAQueryFactory(em).select(q.itemId).from(q).where(q.batchNumber.eq(batchSeq)).fetch();
	}

	@Override
	protected PgFileItem loadItemByKey(Long key) {
		return em.find(PgFileItem.class, key);
	}

}
