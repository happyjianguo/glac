package net.engining.sccc.batch.sccc5701;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckFlagDef;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.GlTransOprHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QGlTransOprHst;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pg.batch.sdk.KeyBasedStreamReader;

/**
 * 读取会计分录表数据
 * 
 * @author wanglidong
 */
@Service
@StepScope
public class Sccc5701R01ApGlVolDtl extends KeyBasedStreamReader<String, ApGlVolDtl> {

	@PersistenceContext
	private EntityManager em;
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	protected List<String> loadKeys() {
		QApGlVolDtl q = QApGlVolDtl.apGlVolDtl;
		// 查询会计分录表，状态不是挂账的数据
		List<Tuple> tuple = new JPAQueryFactory(em).select(q.glvSeq, q.txnDetailSeq).from(q)
				.where(q.bizDate.eq(batchDate), q.postGlInd.eq(PostGlInd.Normal)).fetch();
		List<String> txnDetailSeqList = new ArrayList<String>();
		List<String> glvSeqList = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		for (Tuple t : tuple) {
			String glvSeq = t.get(q.glvSeq);
			String txnDetailSeq = t.get(q.txnDetailSeq);
			txnDetailSeqList.add(txnDetailSeq);
			glvSeqList.add(glvSeq);
			map.put(txnDetailSeq, glvSeq);
		}

		QGlTransOprHst qGlTransOprHst = QGlTransOprHst.glTransOprHst;
		List<GlTransOprHst> glList = new JPAQueryFactory(em).select(qGlTransOprHst).from(qGlTransOprHst)
				.where(qGlTransOprHst.txnDetailSeq.in(txnDetailSeqList)).fetch();
		List<String> removeList = new ArrayList<String>();
		for(GlTransOprHst gl : glList){
			if(!gl.getCheckFlag().equals(CheckFlagDef.A)){
				removeList.add(map.get(gl.getTxnDetailSeq()));
			}
		}
		
		glvSeqList.removeAll(removeList);
		return glvSeqList;

	}

	@Override
	protected ApGlVolDtl loadItemByKey(String key) {
		return em.find(ApGlVolDtl.class, key);
	}

}
