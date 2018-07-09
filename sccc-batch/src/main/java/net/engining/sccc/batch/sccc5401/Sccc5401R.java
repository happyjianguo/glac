package net.engining.sccc.batch.sccc5401;

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
import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;

@Service
@StepScope
public class Sccc5401R extends AbstractKeyBasedStreamReader<Integer, ApSubjectSummary>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	protected List<Integer> loadKeys() {
		QApSubjectSummary qApSubjectSummary = QApSubjectSummary.apSubjectSummary;
		return new JPAQueryFactory(em).select(qApSubjectSummary.seq).from(qApSubjectSummary).where(qApSubjectSummary.bizDate.loe(batchDate)).fetch();
	}

	@Override
	protected ApSubjectSummary loadItemByKey(Integer key) {
		return em.find(ApSubjectSummary.class, key);
	}

}
