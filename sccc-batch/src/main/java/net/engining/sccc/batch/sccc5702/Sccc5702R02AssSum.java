package net.engining.sccc.batch.sccc5702;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlAssSum;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAssSum;
import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;
/**
 * 读取当日辅助汇总表
 * @author wanglidong
 */
@Service
@StepScope
public class Sccc5702R02AssSum extends AbstractKeyBasedStreamReader<Integer, ApGlVolDtlAssSum>{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	protected List<Integer> loadKeys() {
		QApGlVolDtlAssSum q = QApGlVolDtlAssSum.apGlVolDtlAssSum;
		return new JPAQueryFactory(em).select(q.seq).from(q).fetch();
	}

	@Override
	protected ApGlVolDtlAssSum loadItemByKey(Integer key) {
		return  em.find(ApGlVolDtlAssSum.class, key);
	}
}
