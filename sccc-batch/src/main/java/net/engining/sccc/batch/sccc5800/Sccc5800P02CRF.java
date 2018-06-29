package net.engining.sccc.batch.sccc5800;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummary;
import net.engining.pcx.cc.infrastructure.shared.model.CrfReport;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApSubjectSummaryHst;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.enums.BalDbCrFlag;
import net.engining.pg.parameter.ParameterFacility;

@Service
@StepScope
public class Sccc5800P02CRF implements ItemProcessor<ApSubjectSummary, Object> {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private ParameterFacility parameterFacility;
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	public Object process(ApSubjectSummary item) throws Exception {
		Subject subject = parameterFacility.loadParameter(Subject.class, item.getSubjectCd());
		// 查询科目分录汇总历史表，找到上期余额
		QApSubjectSummaryHst hst = QApSubjectSummaryHst.apSubjectSummaryHst;
		Tuple hstData = new JPAQueryFactory(em).select(hst.dbBal,hst.crBal).from(hst)
				.where(hst.subjectCd.eq(item.getSubjectCd())).orderBy(hst.bizDate.desc()).fetchFirst();
		// 查询当日总账交易流水表，找到清算日期，入账日期
		QApGlVolDtl dtl = QApGlVolDtl.apGlVolDtl;
		QApGlTxn txn = QApGlTxn.apGlTxn;
		Tuple txnData = new JPAQueryFactory(em).select(txn.clearDate,txn.postDate).from(dtl, txn)
				.where(txn.gltSeq.eq(dtl.txnDetailSeq),
						dtl.dbsubjectCd.eq(item.getSubjectCd()).or(dtl.crsubjectCd.eq(item.getSubjectCd())))
				.fetchFirst();

		CrfReport crf = new CrfReport();

		crf.setBranchNo(item.getBranchNo());
		crf.setOrg(item.getOrg());
		crf.setReportDate(new Date());
		crf.setClearDate(txnData.get(txn.clearDate));
		crf.setAccountingDate(txnData.get(txn.postDate));
		crf.setSubjectCd(item.getSubjectCd());
		crf.setSubjectName(subject.name);
		// 判断科目的借贷方向
		if (subject.balDbCrFlag.equals(BalDbCrFlag.D)) {
			crf.setPriorPeriod(hstData == null ? BigDecimal.ZERO : hstData.get(hst.dbBal));
			crf.setCurrentBalance(item.getDbBal());
			crf.setTxnDirection(TxnDirection.D);
		} else if (subject.balDbCrFlag.equals(BalDbCrFlag.C)) {
			crf.setPriorPeriod(hstData == null ? BigDecimal.ZERO : hstData.get(hst.crBal));
			crf.setCurrentBalance(item.getCrBal());
			crf.setTxnDirection(TxnDirection.C);
		} else {
			if (item.getDbBal().compareTo(item.getCrBal()) > 0) {
				crf.setPriorPeriod(hstData == null ? BigDecimal.ZERO : hstData.get(hst.dbBal));
				crf.setCurrentBalance(item.getDbBal());
				crf.setTxnDirection(TxnDirection.D);
			} else {
				crf.setPriorPeriod(hstData == null ? BigDecimal.ZERO : hstData.get(hst.crBal));
				crf.setCurrentBalance(item.getCrBal());
				crf.setTxnDirection(TxnDirection.C);
			}
		}

		crf.setAmountDebitSide(item.getDbAmt());
		crf.setAmountCreditSide(item.getCrAmt());
		crf.setBizDate(batchDate);
		crf.fillDefaultValues();
		em.persist(crf);

		return null;
	}

}
