package net.engining.sccc.batch.sccc5400;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummary;
import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummaryHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApSubjectSummary;
import net.engining.pcx.cc.infrastructure.shared.model.QApSubjectSummaryHst;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.sccc.batch.sccc5400.bean.SummaryBySubject;
import net.engining.sccc.batch.service.GlCalculatorService;

/**
 *    
 *
 */
@Service
@StepScope
public class Sccc5400P01SummaryLedgerBySubject implements ItemProcessor<SummaryBySubject, SummaryBySubject> {

	private static final Logger log = LoggerFactory.getLogger(Sccc5400P01SummaryLedgerBySubject.class);

	ConcurrentMap<String, ApSubjectSummary> map = Maps.newConcurrentMap();

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Provider4Organization provider4Organization;

	@Autowired
	private ParameterFacility parameterFacility;

	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Autowired
	private GlCalculatorService summaryService;

	public SummaryBySubject process(SummaryBySubject item) throws Exception {

		// 计算贷方红字金额是否有四位小数
		BigDecimal crRedAmtAfter = BigDecimal.ZERO;
		if (item.getCrRedAmt() != null) {
			BigDecimal crAmt = item.getCrRedAmt();
			crRedAmtAfter = crAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
			// 贷方发生额小数点有四位入新增分录表
			if (crAmt.subtract(crRedAmtAfter).compareTo(BigDecimal.ZERO) > 0) {

				summaryService.insertApGlVolDtl(item.getSubjectCd(), crAmt, TxnDirection.C, RedBlueInd.R, batchDate);
			}
		}

		// 计算贷方蓝字金额是否有四位小数
		BigDecimal crBlueAmtAfter = BigDecimal.ZERO;
		if (item.getCrBlueAmt() != null) {
			BigDecimal crAmt = item.getCrBlueAmt();
			crBlueAmtAfter = crAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
			// 贷方发生额小数点有四位入新增分录表
			if (crAmt.subtract(crBlueAmtAfter).compareTo(BigDecimal.ZERO) > 0) {

				summaryService.insertApGlVolDtl(item.getSubjectCd(), crAmt, TxnDirection.C, RedBlueInd.B, batchDate);
			}
		}

		// 计算贷方正常金额是否有四位小数
		BigDecimal crNorAmtAfter = BigDecimal.ZERO;
		if (item.getCrNorAmt() != null) {
			BigDecimal crAmt = item.getCrNorAmt();
			crNorAmtAfter = crAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
			// 贷方发生额小数点有四位入新增分录表
			if (crAmt.subtract(crNorAmtAfter).compareTo(BigDecimal.ZERO) > 0) {

				summaryService.insertApGlVolDtl(item.getSubjectCd(), crAmt, TxnDirection.C, RedBlueInd.N, batchDate);
			}
		}

		// 计算借方红字金额是否有四位小数
		BigDecimal dbRedAmtAfter = BigDecimal.ZERO;
		if (item.getDbRedAmt() != null) {
			if (item.getDbRedAmt().compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal dbAmt = item.getDbRedAmt();
				dbRedAmtAfter = dbAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
				// 借方发生额小数点有四位入新增分录表
				if (dbAmt.subtract(dbRedAmtAfter).compareTo(BigDecimal.ZERO) > 0) {
					summaryService.insertApGlVolDtl(item.getSubjectCd(), dbAmt, TxnDirection.D, RedBlueInd.R,
							batchDate);
				}
			}
		}

		// 计算借方正常金额是否有四位小数
		BigDecimal dbNorAmtAfter = BigDecimal.ZERO;
		if (item.getDbNorAmt() != null) {
			if (item.getDbNorAmt().compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal dbAmt = item.getDbNorAmt();
				dbNorAmtAfter = dbAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
				// 借方发生额小数点有四位入新增分录表
				if (dbAmt.subtract(dbNorAmtAfter).compareTo(BigDecimal.ZERO) > 0) {
					summaryService.insertApGlVolDtl(item.getSubjectCd(), dbAmt, TxnDirection.D, RedBlueInd.R,
							batchDate);
				}
			}
		}
		// 计算借方蓝字金额是否有四位小数
		BigDecimal dbBlueAmtAfter = BigDecimal.ZERO;
		if (item.getDbBlueAmt() != null) {
			if (item.getDbBlueAmt().compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal dbAmt = item.getDbBlueAmt();
				dbBlueAmtAfter = dbAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
				// 借方发生额小数点有四位入新增分录表
				if (dbAmt.subtract(dbBlueAmtAfter).compareTo(BigDecimal.ZERO) > 0) {
					summaryService.insertApGlVolDtl(item.getSubjectCd(), dbAmt, TxnDirection.D, RedBlueInd.R,
							batchDate);
				}
			}
		}

		if (item != null) {
			QApSubjectSummary qApSubjectSummary = QApSubjectSummary.apSubjectSummary;
			ApSubjectSummary subSummary = new JPAQueryFactory(em).select(qApSubjectSummary).from(qApSubjectSummary)
					.where(qApSubjectSummary.subjectCd.eq(item.getSubjectCd())).fetchOne();
			QApSubjectSummaryHst qApSubjectSummaryHst = QApSubjectSummaryHst.apSubjectSummaryHst;
			List<ApSubjectSummaryHst> hstList = new JPAQueryFactory(em).select(qApSubjectSummaryHst)
					.from(qApSubjectSummaryHst).where(qApSubjectSummaryHst.subjectCd.eq(item.getSubjectCd()))
					.orderBy(qApSubjectSummaryHst.bizDate.desc()).fetch();
			if (subSummary == null) {
				subSummary = new ApSubjectSummary();
				subSummary.setOrg(provider4Organization.getCurrentOrganizationId());
				subSummary.setBranchNo(provider4Organization.getCurrentOrganizationId());
				subSummary.setOwingBranch(provider4Organization.getCurrentOrganizationId());
				subSummary.setSubjectCd(item.getSubjectCd());
				subSummary.setDbAmt(BigDecimal.ZERO);
				subSummary.setDbCount(0);
				if (hstList.size() > 0) {
					subSummary.setDbBal(BigDecimal.ZERO.add(hstList.get(0).getDbBal()));
					subSummary.setCrBal(BigDecimal.ZERO.add(hstList.get(0).getCrBal()));
				} else {
					subSummary.setDbBal(BigDecimal.ZERO);
					subSummary.setCrBal(BigDecimal.ZERO);
				}
				subSummary.setCrAmt(BigDecimal.ZERO);
				subSummary.setCrCount(0);
				subSummary.setBizDate(batchDate);
				subSummary.fillDefaultValues();
				em.persist(subSummary);

			}
			// 将查询得到的分录表数据入账到summary表
			Subject subjectCd = parameterFacility.getParameter(Subject.class, item.getSubjectCd());

			if (item.getCrNorAmt() != null) {
				TxnDirection txnDirection = TxnDirection.C;
				BigDecimal postAmount = item.getCrNorAmt();
				int count = item.getCrNorCount();
				subSummary = summaryService.writeOn(subSummary, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
			}
			if (item.getDbNorAmt() != null) {
				TxnDirection txnDirection = TxnDirection.D;
				BigDecimal postAmount = item.getDbNorAmt();
				int count = item.getDbNorCount();
				subSummary = summaryService.writeOn(subSummary, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
			}
			if (item.getCrBlueAmt() != null) {
				TxnDirection txnDirection = TxnDirection.C;
				BigDecimal postAmount = item.getCrBlueAmt();
				int count = item.getCrBlueCount();
				subSummary = summaryService.writeOn(subSummary, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
			}
			if (item.getDbBlueAmt() != null) {
				TxnDirection txnDirection = TxnDirection.D;
				BigDecimal postAmount = item.getDbBlueAmt();
				int count = item.getDbBlueCount();
				subSummary = summaryService.writeOn(subSummary, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
			}
			if (item.getCrRedAmt() != null) {
				TxnDirection txnDirection = TxnDirection.C;
				BigDecimal postAmount = item.getCrRedAmt();
				int count = item.getCrRedCount();
				subSummary = summaryService.writeOff(subSummary, count, subjectCd.balDbCrFlag, txnDirection,
						postAmount);
			}
			if (item.getDbRedAmt() != null) {
				TxnDirection txnDirection = TxnDirection.D;
				BigDecimal postAmount = item.getDbRedAmt();
				int count = item.getDbRedCount();
				subSummary = summaryService.writeOff(subSummary, count, subjectCd.balDbCrFlag, txnDirection,
						postAmount);
			}
		}
		return null;
	}

}
