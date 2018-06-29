package net.engining.sccc.batch.sccc5703;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlAssSum;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAssSum;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.batch.sccc5703.bean.SubjectAssist;
import net.engining.sccc.batch.service.GlCalculatorService;

@Service
@StepScope
public class Sccc5703P02UpdateApGlVolDtlAssSum implements ItemProcessor<SubjectAssist, ApGlVolDtlAssSum> {
	@PersistenceContext
	private EntityManager em;

	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;
	
	@Autowired
	private ParameterFacility parameterFacility;
	
	@Autowired
	private GlCalculatorService glCalculatorService;

	@Override
	public ApGlVolDtlAssSum process(SubjectAssist item) throws Exception {
		QApGlVolDtlAssSum qSum = QApGlVolDtlAssSum.apGlVolDtlAssSum;
		// 查询汇总表 条件科目，辅助核算项类型，辅助核算项值
		ApGlVolDtlAssSum sum = new JPAQueryFactory(em).select(qSum).from(qSum)
				.where(qSum.subjectCd.eq(item.getSubjectCd()),
						qSum.assistType.eq(AssistAccountingType.valueOf(item.getAssistType())),
						qSum.assistAccountValue.eq(item.getAssistAccountValue()), qSum.bizDate.eq(batchDate))
				.fetchOne();
		
		// 将查询得到的分录表数据入账到ApGlVolDtlAssSum表
		Subject subjectCd = parameterFacility.getParameter(Subject.class, item.getSubjectCd());

		if (item.getCrNorAmt() != null) {
			TxnDirection txnDirection = TxnDirection.C;
			BigDecimal postAmount = item.getCrNorAmt();
			int count = item.getCrNorCount();
			sum = glCalculatorService.writeOnAssist(sum, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
		}
		if (item.getDbNorAmt() != null) {
			TxnDirection txnDirection = TxnDirection.D;
			BigDecimal postAmount = item.getDbNorAmt();
			int count = item.getDbNorCount();
			sum = glCalculatorService.writeOnAssist(sum, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
		}
		if (item.getCrBlueAmt() != null) {
			TxnDirection txnDirection = TxnDirection.C;
			BigDecimal postAmount = item.getCrBlueAmt();
			int count = item.getCrBlueCount();
			sum = glCalculatorService.writeOnAssist(sum, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
		}
		if (item.getDbBlueAmt() != null) {
			TxnDirection txnDirection = TxnDirection.D;
			BigDecimal postAmount = item.getDbBlueAmt();
			int count = item.getDbRedCount();
			sum = glCalculatorService.writeOnAssist(sum, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
		}
		if (item.getCrRedAmt() != null) {
			TxnDirection txnDirection = TxnDirection.C;
			BigDecimal postAmount = item.getCrRedAmt();
			int count = item.getCrRedCount();
			sum = glCalculatorService.writeOffAssist(sum, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
		}
		if (item.getDbRedAmt() != null) {
			TxnDirection txnDirection = TxnDirection.D;
			BigDecimal postAmount = item.getDbRedAmt();
			int count = item.getDbRedCount();
			sum = glCalculatorService.writeOffAssist(sum, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
		}
		
		
		
		
		// 更新数据
//		if (BigDecimal.ZERO != item.getDbAmt() && null != item.getDbAmt()) {
//			sum.setDbAmt(item.getDbAmt().add(sum.getDbAmt()));
//			sum.setDbBal(item.getDbAmt().add(sum.getDbBal()));
//			sum.setMtdDbAmt(item.getDbAmt().add(sum.getMtdDbAmt()));
//			sum.setQtdDbAmt(item.getDbAmt().add(sum.getQtdDbAmt()));
//			sum.setYtdDbAmt(item.getDbAmt().add(sum.getYtdDbAmt()));
//		}
//		sum.setDbCount(sum.getDbCount() + item.getDbCount());
//
//		if (BigDecimal.ZERO != item.getCrAmt() && null != item.getCrAmt()) {
//			sum.setCrAmt(item.getCrAmt().add(sum.getCrAmt()));
//			sum.setCrBal(item.getCrAmt().add(sum.getCrBal()));
//			sum.setMtdCrAmt(item.getCrAmt().add(sum.getMtdCrAmt()));
//			sum.setQtdCrAmt(item.getCrAmt().add(sum.getQtdCrAmt()));
//			sum.setYtdCrAmt(item.getCrAmt().add(sum.getYtdCrAmt()));
//		}
//		sum.setCrCount(sum.getCrCount() + item.getCrCount());

		return sum;
	}

}
