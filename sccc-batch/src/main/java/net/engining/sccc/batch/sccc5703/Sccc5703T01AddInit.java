package net.engining.sccc.batch.sccc5703;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlAssSum;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAss;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAssSum;

@Service
@StepScope
public class Sccc5703T01AddInit implements Tasklet {
	@PersistenceContext
	private EntityManager em;
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		QApGlVolDtlAss q = QApGlVolDtlAss.apGlVolDtlAss;
		// 查询分录表，按科目编号，辅助核算项类型，辅助核算项数据
		List<Tuple> ass = new JPAQueryFactory(em)
				.select(q.subjectCd, q.assistType, q.assistAccountValue, q.org, q.branchNo, q.volDt).from(q)
				.groupBy(q.subjectCd, q.assistType, q.assistAccountValue).fetch();

		for (Tuple t : ass) {
			// 查询汇总表，条件科目编号，辅助核算项类型，辅助核算项数据
			QApGlVolDtlAssSum sum = QApGlVolDtlAssSum.apGlVolDtlAssSum;
			ApGlVolDtlAssSum gl = new JPAQueryFactory(em).select(sum).from(sum)
					.where(sum.subjectCd.eq(t.get(q.subjectCd)), sum.assistType.eq(t.get(q.assistType)),
							sum.assistAccountValue.eq(t.get(q.assistAccountValue)))
					.fetchOne();
			// 不存在，初始化一条数据
			if (gl == null) {
				ApGlVolDtlAssSum vol = new ApGlVolDtlAssSum();
				vol.setSubjectCd(t.get(q.subjectCd));
				vol.setAssistType(t.get(q.assistType));
				vol.setAssistAccountValue(t.get(q.assistAccountValue));
				vol.setOrg(t.get(q.org));
				vol.setBranchNo(t.get(q.branchNo));
				vol.setVolDt(t.get(q.volDt));
				vol.setBizDate(batchDate);
				
				vol.setDbAmt(BigDecimal.ZERO);
				vol.setDbBal(BigDecimal.ZERO);
				vol.setDbCount(0);
				vol.setLastDbBal(BigDecimal.ZERO);
				vol.setLastMthDbBal(BigDecimal.ZERO);
				vol.setLastQtrDbBal(BigDecimal.ZERO);
				vol.setLastYrDbBal(BigDecimal.ZERO);
				vol.setMtdDbAmt(BigDecimal.ZERO);
				vol.setQtdDbAmt(BigDecimal.ZERO);
				vol.setYtdDbAmt(BigDecimal.ZERO);
				
				vol.setCrAmt(BigDecimal.ZERO);
				vol.setCrBal(BigDecimal.ZERO);
				vol.setCrCount(0);
				vol.setLastCrBal(BigDecimal.ZERO);
				vol.setLastMthCrBal(BigDecimal.ZERO);
				vol.setLastQtrCrBal(BigDecimal.ZERO);
				vol.setLastYrCrBal(BigDecimal.ZERO);
				vol.setMtdCrAmt(BigDecimal.ZERO);
				vol.setQtdCrAmt(BigDecimal.ZERO);
				vol.setYtdCrAmt(BigDecimal.ZERO);
				
				vol.setProcDate(batchDate);
				vol.fillDefaultValues();

				em.persist(vol);
			}

		}

		return null;
	}

}
