package net.engining.sccc.biz.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummary;
import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummaryHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApSubjectSummary;
import net.engining.pcx.cc.infrastructure.shared.model.QApSubjectSummaryHst;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.enums.BalDbCrFlag;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.sccc.biz.bean.SummaryBySubject;

@Service
public class LedgerService {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private ParameterFacility parameterFacility;
	@Autowired
	private Provider4Organization provider4Organization;
	
	
	public ApSubjectSummaryHst process(SummaryBySubject item,Date batchDate){
		if (item != null) {
			QApSubjectSummaryHst hst = QApSubjectSummaryHst.apSubjectSummaryHst;
			Date qDate=new Date(batchDate.getTime()-1000*60*60*24);
			ApSubjectSummaryHst subSummaryHst = new JPAQueryFactory(em).select(hst).from(hst)
			.where(hst.subjectCd.eq(item.getSubjectCd()),hst.bizDate.eq(qDate)).fetchOne();
			
			if (subSummaryHst == null) {
				subSummaryHst = new ApSubjectSummaryHst();
				subSummaryHst.setOrg(provider4Organization.getCurrentOrganizationId());
				subSummaryHst.setBranchNo(provider4Organization.getCurrentOrganizationId());
				subSummaryHst.setOwingBranch(provider4Organization.getCurrentOrganizationId());
				subSummaryHst.setSubjectCd(item.getSubjectCd());
				subSummaryHst.setDbAmt(BigDecimal.ZERO);
				subSummaryHst.setDbCount(0);
				subSummaryHst.setDbBal(BigDecimal.ZERO);
				subSummaryHst.setCrBal(BigDecimal.ZERO);
				subSummaryHst.setCrAmt(BigDecimal.ZERO);
				subSummaryHst.setCrCount(0);
				subSummaryHst.setBizDate(qDate);
				subSummaryHst.fillDefaultValues();
			}
		// 将查询得到的分录表数据入账到summary表
					Subject subjectCd = parameterFacility.getParameter(Subject.class, item.getSubjectCd());

					if (item.getCrNorAmt() != null) {
						TxnDirection txnDirection = TxnDirection.C;
						BigDecimal postAmount = item.getCrNorAmt();
						int count = item.getCrNorCount();
						subSummaryHst = writeOn(subSummaryHst, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
					}
					if (item.getDbNorAmt() != null) {
						TxnDirection txnDirection = TxnDirection.D;
						BigDecimal postAmount = item.getDbNorAmt();
						int count = item.getDbNorCount();
						subSummaryHst = writeOn(subSummaryHst, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
					}
					if (item.getCrBlueAmt() != null) {
						TxnDirection txnDirection = TxnDirection.C;
						BigDecimal postAmount = item.getCrBlueAmt();
						int count = item.getCrBlueCount();
						subSummaryHst = writeOn(subSummaryHst, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
					}
					if (item.getDbBlueAmt() != null) {
						TxnDirection txnDirection = TxnDirection.D;
						BigDecimal postAmount = item.getDbBlueAmt();
						int count = item.getDbBlueCount();
						subSummaryHst = writeOn(subSummaryHst, count, subjectCd.balDbCrFlag, txnDirection, postAmount);
					}
					if (item.getCrRedAmt() != null) {
						TxnDirection txnDirection = TxnDirection.C;
						BigDecimal postAmount = item.getCrRedAmt();
						int count = item.getCrRedCount();
						subSummaryHst = writeOff(subSummaryHst, count, subjectCd.balDbCrFlag, txnDirection,
								postAmount);
					}
					if (item.getDbRedAmt() != null) {
						TxnDirection txnDirection = TxnDirection.D;
						BigDecimal postAmount = item.getDbRedAmt();
						int count = item.getDbRedCount();
						subSummaryHst = writeOff(subSummaryHst, count, subjectCd.balDbCrFlag, txnDirection,
								postAmount);
					}
					return subSummaryHst;
		}
		return null;
	}
				

	public ApSubjectSummaryHst writeOn(ApSubjectSummaryHst apSubjectSummary, int count, BalDbCrFlag balDbCrFlag,
			TxnDirection txnDirection, BigDecimal postAmount) {
		ApSubjectSummaryHst subSummary = apSubjectSummary;
		// 统计借贷记笔数
		switch (txnDirection) {
		case D:
			subSummary.setDbCount(subSummary.getDbCount() + count);
			break;
		case C:
			subSummary.setCrCount(subSummary.getCrCount() + count);
			break;
		default:
			break;
		}
	
		// 计算余额,发生额
		switch (balDbCrFlag) {
		case D: // 只允许借方余额
			switch (txnDirection) {
			case D: // 借
				// 借方发生额 =原借记发生额+借方金额
				subSummary.setDbAmt(subSummary.getDbAmt().add(postAmount));
				// 借方余额=原借方余额+借方金额
				subSummary.setDbBal(subSummary.getDbBal().add(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额+贷方金额
				subSummary.setCrAmt(subSummary.getCrAmt().add(postAmount));
				// 借方余额=原借方余额-贷方金额
				subSummary.setDbBal(subSummary.getDbBal().subtract(postAmount));
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}
	
			break;
		case C: // 只允许贷方余额
			switch (txnDirection) {
			case D: // 借
				// 借方发生额 =原借记发生额+借方金额
				subSummary.setDbAmt(subSummary.getDbAmt().add(postAmount));
				// 贷方余额=原贷方余额-借方金额
				subSummary.setCrBal(subSummary.getCrBal().subtract(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额+贷方金额
				subSummary.setCrAmt(subSummary.getCrAmt().add(postAmount));
				// 贷方余额=原贷方余额+贷方金额
				subSummary.setCrBal(subSummary.getCrBal().add(postAmount));
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}
	
			break;
		case B: // 按轧差金额
			// TODO 按轧差金额计算需要把一方置0
	
			switch (txnDirection) {
			case D: // 借
				// 借方发生额 =原借记发生额+借方金额
				subSummary.setDbAmt(subSummary.getDbAmt().add(postAmount));
				//
				BigDecimal tmpCrBal = subSummary.getCrBal().subtract(postAmount);
				if (tmpCrBal.compareTo(BigDecimal.ZERO) > 0) {
					subSummary.setCrBal(tmpCrBal);
				} else {
					subSummary.setCrBal(BigDecimal.ZERO);
					subSummary.setDbBal(subSummary.getDbBal().add(tmpCrBal.negate()));
				}
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额+贷方金额
				subSummary.setCrAmt(subSummary.getCrAmt().add(postAmount));
				//
				BigDecimal tmpDbBal = subSummary.getDbBal().subtract(postAmount);
				if (tmpDbBal.compareTo(BigDecimal.ZERO) > 0) {
					subSummary.setDbBal(tmpDbBal);
				} else {
					subSummary.setDbBal(BigDecimal.ZERO);
					subSummary.setCrBal(subSummary.getCrBal().add(tmpDbBal.negate()));
				}
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}
	
			break;
		case T: // 双向余额
			switch (txnDirection) {
			case D: // 借
				// 借方发生额 =原借记发生额+借方金额
				subSummary.setDbAmt(subSummary.getDbAmt().add(postAmount));
				// 借方余额=原借方余额+借方金额
				subSummary.setDbBal(subSummary.getDbBal().add(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额+贷方金额
				subSummary.setCrAmt(subSummary.getCrAmt().add(postAmount));
				// 贷方余额=原贷方余额+贷方金额
				subSummary.setCrBal(subSummary.getCrBal().add(postAmount));
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}
	
			break;
		default:
			break;
		}
		subSummary.setDbBal(subSummary.getDbBal().abs());
		subSummary.setCrBal(subSummary.getCrBal().abs());
		subSummary.setDbAmt(subSummary.getDbAmt().abs());
		subSummary.setCrAmt(subSummary.getCrAmt().abs());
		return subSummary;
	}
	
	public ApSubjectSummaryHst writeOff(ApSubjectSummaryHst apSubjectSummary, int count, BalDbCrFlag balDbCrFlag,
			TxnDirection txnDirection, BigDecimal postAmount) {
		ApSubjectSummaryHst subSummary = apSubjectSummary;
		// 统计借贷记笔数
		switch (txnDirection) {
		case D:
			subSummary.setDbCount(subSummary.getDbCount() - count);
			break;
		case C:
			subSummary.setCrCount(subSummary.getCrCount() - count);
			break;
		default:
			break;
		}
	
		// 统计余额,发生额
		switch (balDbCrFlag) {
		case D: // 只允许借方余额
			switch (txnDirection) {
			case D: // 借
				// 借方发生额 =原借记发生额-借方金额
				subSummary.setDbAmt(subSummary.getDbAmt().subtract(postAmount));
				// 借方余额=原借方余额-借方金额
				subSummary.setDbBal(subSummary.getDbBal().subtract(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额-贷方金额
				subSummary.setCrAmt(subSummary.getCrAmt().subtract(postAmount));
				// 借方余额=原借方余额+贷方金额
				subSummary.setDbBal(subSummary.getDbBal().add(postAmount));
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}
	
			break;
		case C: // 只允许贷方余额
			switch (txnDirection) {
			case D: // 借
				// 借方发生额 =原借记发生额-借方金额
				subSummary.setDbAmt(subSummary.getDbAmt().subtract(postAmount));
				// 贷方余额=原贷方余额+借方金额
				subSummary.setCrBal(subSummary.getCrBal().add(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额-贷方金额
				subSummary.setCrAmt(subSummary.getCrAmt().subtract(postAmount));
				// 贷方余额=原贷方余额-贷方金额
				subSummary.setCrBal(subSummary.getCrBal().subtract(postAmount));
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}
	
			break;
		case B: // 按轧差金额
			// TODO 按轧差金额计算需要把一方置0
	
			switch (txnDirection) {
			case D: // 借
				// 借方发生额 =原借记发生额-借方金额
				subSummary.setDbAmt(subSummary.getDbAmt().subtract(postAmount));
				//
				BigDecimal tmpDbBal = subSummary.getDbBal().subtract(postAmount);
				if (tmpDbBal.compareTo(BigDecimal.ZERO) > 0) {
					subSummary.setDbBal(tmpDbBal);
				} else {
					subSummary.setDbBal(BigDecimal.ZERO);
					subSummary.setCrBal(subSummary.getCrBal().add(tmpDbBal.negate()));
				}
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额-贷方金额
				subSummary.setCrAmt(subSummary.getCrAmt().subtract(postAmount));
				//
				BigDecimal tmpCrBal = subSummary.getCrBal().subtract(postAmount);
				if (tmpCrBal.compareTo(BigDecimal.ZERO) > 0) {
					subSummary.setCrBal(tmpCrBal);
				} else {
					subSummary.setCrBal(BigDecimal.ZERO);
					subSummary.setDbBal(subSummary.getDbBal().add(tmpCrBal.negate()));
				}
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}
	
			break;
		case T: // 双向余额
			switch (txnDirection) {
			case D: // 借
				// 借方发生额 =原借记发生额-借方金额
				subSummary.setDbAmt(subSummary.getDbAmt().subtract(postAmount));
				// 借方余额=原借方余额-借方金额
				subSummary.setDbBal(subSummary.getDbBal().subtract(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额-贷方金额
				subSummary.setCrAmt(subSummary.getCrAmt().subtract(postAmount));
				// 贷方余额=原贷方余额-贷方金额
				subSummary.setCrBal(subSummary.getCrBal().subtract(postAmount));
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}
	
			break;
		default:
			break;
		}
		
		subSummary.setDbCount(Math.abs(subSummary.getDbCount()));
		subSummary.setCrCount(Math.abs(subSummary.getCrCount()));
		subSummary.setDbBal(subSummary.getDbBal().abs());
		subSummary.setCrBal(subSummary.getCrBal().abs());
		subSummary.setDbAmt(subSummary.getDbAmt().abs());
		subSummary.setCrAmt(subSummary.getCrAmt().abs());
		return subSummary;
	}
}
