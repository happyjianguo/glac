package net.engining.sccc.batch.sccc0800;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.CactAccountTem;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccountTem;
import net.engining.pg.batch.sdk.KeyBasedStreamReader;

@Service
@StepScope
public class Sccc0820R02CactAccountTem extends KeyBasedStreamReader<Integer, CactAccountTem>{

	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	protected List<Integer> loadKeys() {
		QCactAccountTem q = QCactAccountTem.cactAccountTem;
		return new JPAQueryFactory(em).select(q.seq).from(q).where(q.bizDate.eq(bizDate)).fetch();
	}

	@Override
	protected CactAccountTem loadItemByKey(Integer key) {
		return em.find(CactAccountTem.class, key);
	}

}
