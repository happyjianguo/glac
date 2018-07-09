package net.engining.sccc.batch.sccc5600;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.ApGlBal;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlBal;
import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;

/**
 * 读取总账表数据
 * 
 * @author wanglidong
 */
@Service
@StepScope
public class Sccc5600R60ApGlBal extends AbstractKeyBasedStreamReader<Integer, ApGlBal> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	protected List<Integer> loadKeys() {
		QApGlBal q = QApGlBal.apGlBal;
		return new JPAQueryFactory(em).select(q.id).from(q).fetch();
	}

	@Override
	protected ApGlBal loadItemByKey(Integer key) {
		return em.find(ApGlBal.class, key);
	}
}
