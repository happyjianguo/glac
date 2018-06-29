package net.engining.sccc.batch.sccc5800;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummary;
import net.engining.pcx.cc.infrastructure.shared.model.QApSubjectSummary;
import net.engining.pg.batch.sdk.KeyBasedStreamReader;
@Service
@StepScope
public class Sccc5800R02CRF extends KeyBasedStreamReader<Integer, ApSubjectSummary>{
	@PersistenceContext
	private EntityManager em;
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	protected List<Integer> loadKeys() {
		QApSubjectSummary q = QApSubjectSummary.apSubjectSummary;
		return new JPAQueryFactory(em).select(q.seq).from(q).where(q.bizDate.eq(batchDate)).fetch();
	}

	@Override
	protected ApSubjectSummary loadItemByKey(Integer key) {
		return em.find(ApSubjectSummary.class, key);
	}


}
