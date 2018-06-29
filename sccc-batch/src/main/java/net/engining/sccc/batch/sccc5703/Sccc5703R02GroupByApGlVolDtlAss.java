package net.engining.sccc.batch.sccc5703;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAss;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;
import net.engining.pg.batch.sdk.KeyBasedStreamReader;
import net.engining.sccc.batch.sccc5703.bean.SubjectAssist;
@Service
@StepScope
public class Sccc5703R02GroupByApGlVolDtlAss extends KeyBasedStreamReader<String, SubjectAssist> {

	@PersistenceContext
	private EntityManager em;

	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	protected List<String> loadKeys() {

		QApGlVolDtlAss q = QApGlVolDtlAss.apGlVolDtlAss;
		// 查询分录表 按科目，辅助核算项类型，辅助核算项值
		List<Tuple> list = new JPAQueryFactory(em)
				.select(q.subjectCd, q.assistType, q.assistAccountValue).from(q)
				.groupBy(q.subjectCd, q.assistType, q.assistAccountValue).fetch();
		
		//拼装key
		List<String> listStr = new ArrayList<String>();
		for (Tuple t : list) {
			String str = t.get(q.subjectCd) + "," + t.get(q.assistType) + "," + t.get(q.assistAccountValue);
			listStr.add(str);
		}

		return listStr;
	}

	@Override
	protected SubjectAssist loadItemByKey(String key) {
		String[] saa = key.split(",");
		
		QApGlVolDtlAss q = QApGlVolDtlAss.apGlVolDtlAss;
		// 查询分录表 按科目，辅助核算项类型，辅助核算项值，借贷方向 分组
		//借方正常
		Tuple dbNor = new JPAQueryFactory(em)
				.select(q.subjectCd, q.assistType, q.assistAccountValue, q.txnDirection,
						q.subjectCd.count().as("count"), q.subjAmount.sum().as("sum"))
				.from(q)
				.where(q.txnDirection.eq(TxnDirection.D),q.redBlueInd.eq(RedBlueInd.N))
				.groupBy(q.subjectCd, q.assistType, q.assistAccountValue)
				.having(q.subjectCd.eq(saa[0]), q.assistType.eq(AssistAccountingType.valueOf(saa[1])), q.assistAccountValue.eq(saa[2])).fetchOne();
		
		//借方蓝字
		Tuple dbBlue = new JPAQueryFactory(em)
				.select(q.subjectCd, q.assistType, q.assistAccountValue, q.txnDirection,
						q.subjectCd.count().as("count"), q.subjAmount.sum().as("sum"))
				.from(q)
				.where(q.txnDirection.eq(TxnDirection.D),q.redBlueInd.eq(RedBlueInd.B))
				.groupBy(q.subjectCd, q.assistType, q.assistAccountValue)
				.having(q.subjectCd.eq(saa[0]), q.assistType.eq(AssistAccountingType.valueOf(saa[1])), q.assistAccountValue.eq(saa[2])).fetchOne();

		//借方红字
		Tuple dbRed = new JPAQueryFactory(em)
				.select(q.subjectCd, q.assistType, q.assistAccountValue, q.txnDirection,
						q.subjectCd.count().as("count"), q.subjAmount.sum().as("sum"))
				.from(q)
				.where(q.txnDirection.eq(TxnDirection.D),q.redBlueInd.eq(RedBlueInd.R))
				.groupBy(q.subjectCd, q.assistType, q.assistAccountValue)
				.having(q.subjectCd.eq(saa[0]), q.assistType.eq(AssistAccountingType.valueOf(saa[1])), q.assistAccountValue.eq(saa[2])).fetchOne();
		
		//贷方正常
		Tuple crNor = new JPAQueryFactory(em)
				.select(q.subjectCd, q.assistType, q.assistAccountValue, q.txnDirection,
						q.subjectCd.count().as("count"), q.subjAmount.sum().as("sum"))
				.from(q)
				.where(q.txnDirection.eq(TxnDirection.C),q.redBlueInd.eq(RedBlueInd.N))
				.groupBy(q.subjectCd, q.assistType, q.assistAccountValue)
				.having(q.subjectCd.eq(saa[0]), q.assistType.eq(AssistAccountingType.valueOf(saa[1])), q.assistAccountValue.eq(saa[2])).fetchOne();
		
		//贷方蓝字
		Tuple crBlue = new JPAQueryFactory(em)
				.select(q.subjectCd, q.assistType, q.assistAccountValue, q.txnDirection,
						q.subjectCd.count().as("count"), q.subjAmount.sum().as("sum"))
				.from(q)
				.where(q.txnDirection.eq(TxnDirection.C),q.redBlueInd.eq(RedBlueInd.B))
				.groupBy(q.subjectCd, q.assistType, q.assistAccountValue)
				.having(q.subjectCd.eq(saa[0]), q.assistType.eq(AssistAccountingType.valueOf(saa[1])), q.assistAccountValue.eq(saa[2])).fetchOne();

		//贷方红字
		Tuple crRed = new JPAQueryFactory(em)
				.select(q.subjectCd, q.assistType, q.assistAccountValue, q.txnDirection,
						q.subjectCd.count().as("count"), q.subjAmount.sum().as("sum"))
				.from(q)
				.where(q.txnDirection.eq(TxnDirection.C),q.redBlueInd.eq(RedBlueInd.R))
				.groupBy(q.subjectCd, q.assistType, q.assistAccountValue)
				.having(q.subjectCd.eq(saa[0]), q.assistType.eq(AssistAccountingType.valueOf(saa[1])), q.assistAccountValue.eq(saa[2])).fetchOne();
		
		
		SubjectAssist sa = new SubjectAssist();
		sa.setSubjectCd(saa[0]);
		sa.setAssistType(saa[1]);
		sa.setAssistAccountValue(saa[2]);
		if(dbNor != null){
			sa.setDbNorAmt(dbNor.get(q.subjAmount.sum().as("sum")));
			sa.setDbNorCount(dbNor.get(q.subjectCd.count().as("count")).intValue());
		}
		if(dbBlue != null){
			sa.setDbNorAmt(dbBlue.get(q.subjAmount.sum().as("sum")));
			sa.setDbNorCount(dbBlue.get(q.subjectCd.count().as("count")).intValue());
		}
		if(dbRed != null){
			sa.setDbNorAmt(dbRed.get(q.subjAmount.sum().as("sum")));
			sa.setDbNorCount(dbRed.get(q.subjectCd.count().as("count")).intValue());
		}
		if(crNor != null){
			sa.setCrNorAmt(crNor.get(q.subjAmount.sum().as("sum")));
			sa.setCrNorCount(crNor.get(q.subjectCd.count().as("count")).intValue());
		}
		if(crBlue != null){
			sa.setCrNorAmt(crBlue.get(q.subjAmount.sum().as("sum")));
			sa.setCrNorCount(crBlue.get(q.subjectCd.count().as("count")).intValue());
		}
		if(crRed != null){
			sa.setCrNorAmt(crRed.get(q.subjAmount.sum().as("sum")));
			sa.setCrNorCount(crRed.get(q.subjectCd.count().as("count")).intValue());
		}
		
		
//		sa.setSubjectCd(list.get(0).get(q.subjectCd));
//		sa.setAssistType(list.get(0).get(q.assistType).toString());
//		sa.setAssistAccountValue(list.get(0).get(q.assistAccountValue));
//		//sa.setTxnDirection(list.get(0).get(q.txnDirection));
//		// 将查询出的数据 封装到SubjectAssist
//		for (Tuple t : list) {
//			if (t.get(q.txnDirection).equals(TxnDirection.D)) {
//				sa.setDbAmt(t.get(q.subjAmount.sum().as("sum")));
//				sa.setDbCount(t.get(q.subjectCd.count().as("count")).intValue());
//			} else {
//				sa.setCrAmt(t.get(q.subjAmount.sum().as("sum")));
//				sa.setCrCount(t.get(q.subjectCd.count().as("count")).intValue());
//			}
//		}

		return sa;
	}

}
