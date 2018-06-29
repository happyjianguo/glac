package net.engining.sccc.batch.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlAssSum;
import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummary;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.param.model.enums.BalDbCrFlag;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;
import net.engining.pg.parameter.Provider4Organization;

@Service
public class GlCalculatorService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Provider4Organization provider4Organization;

	public ApSubjectSummary writeOn(ApSubjectSummary apSubjectSummary, int count, BalDbCrFlag balDbCrFlag,
			TxnDirection txnDirection, BigDecimal postAmount) {
		ApSubjectSummary subSummary = apSubjectSummary;
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

	public ApSubjectSummary writeOff(ApSubjectSummary apSubjectSummary, int count, BalDbCrFlag balDbCrFlag,
			TxnDirection txnDirection, BigDecimal postAmount) {
		ApSubjectSummary subSummary = apSubjectSummary;
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
	
	
	public ApGlVolDtlAssSum writeOnAssist(ApGlVolDtlAssSum apGlVolDtlAssSum, int count, BalDbCrFlag balDbCrFlag,
			TxnDirection txnDirection, BigDecimal postAmount) {
		ApGlVolDtlAssSum assSum = apGlVolDtlAssSum;
		// 统计借贷记笔数
		switch (txnDirection) {
		case D:
			assSum.setDbCount(assSum.getDbCount() + count);
			break;
		case C:
			assSum.setCrCount(assSum.getCrCount() + count);
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
				assSum.setDbAmt(assSum.getDbAmt().add(postAmount));
				// 借方余额=原借方余额+借方金额
				assSum.setDbBal(assSum.getDbBal().add(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额+贷方金额
				assSum.setCrAmt(assSum.getCrAmt().add(postAmount));
				// 借方余额=原借方余额-贷方金额
				assSum.setDbBal(assSum.getDbBal().subtract(postAmount));
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}

			break;
		case C: // 只允许贷方余额
			switch (txnDirection) {
			case D: // 借
				// 借方发生额 =原借记发生额+借方金额
				assSum.setDbAmt(assSum.getDbAmt().add(postAmount));
				// 贷方余额=原贷方余额-借方金额
				assSum.setCrBal(assSum.getCrBal().subtract(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额+贷方金额
				assSum.setCrAmt(assSum.getCrAmt().add(postAmount));
				// 贷方余额=原贷方余额+贷方金额
				assSum.setCrBal(assSum.getCrBal().add(postAmount));
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
				assSum.setDbAmt(assSum.getDbAmt().add(postAmount));
				//
				BigDecimal tmpCrBal = assSum.getCrBal().subtract(postAmount);
				if (tmpCrBal.compareTo(BigDecimal.ZERO) > 0) {
					assSum.setCrBal(tmpCrBal);
				} else {
					assSum.setCrBal(BigDecimal.ZERO);
					assSum.setDbBal(assSum.getDbBal().add(tmpCrBal.negate()));
				}
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额+贷方金额
				assSum.setCrAmt(assSum.getCrAmt().add(postAmount));
				//
				BigDecimal tmpDbBal = assSum.getDbBal().subtract(postAmount);
				if (tmpDbBal.compareTo(BigDecimal.ZERO) > 0) {
					assSum.setDbBal(tmpDbBal);
				} else {
					assSum.setDbBal(BigDecimal.ZERO);
					assSum.setCrBal(assSum.getCrBal().add(tmpDbBal.negate()));
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
				assSum.setDbAmt(assSum.getDbAmt().add(postAmount));
				// 借方余额=原借方余额+借方金额
				assSum.setDbBal(assSum.getDbBal().add(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额+贷方金额
				assSum.setCrAmt(assSum.getCrAmt().add(postAmount));
				// 贷方余额=原贷方余额+贷方金额
				assSum.setCrBal(assSum.getCrBal().add(postAmount));
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}

			break;
		default:
			break;
		}
		
		assSum.setDbBal(assSum.getDbBal().abs());
		assSum.setCrBal(assSum.getCrBal().abs());
		assSum.setDbAmt(assSum.getDbAmt().abs());
		assSum.setCrAmt(assSum.getCrAmt().abs());
		return assSum;
	}

	public ApGlVolDtlAssSum writeOffAssist(ApGlVolDtlAssSum apGlVolDtlAssSum, int count, BalDbCrFlag balDbCrFlag,
			TxnDirection txnDirection, BigDecimal postAmount) {
		ApGlVolDtlAssSum assSum = apGlVolDtlAssSum;
		// 统计借贷记笔数
		switch (txnDirection) {
		case D:
			assSum.setDbCount(assSum.getDbCount() - count);
			break;
		case C:
			assSum.setCrCount(assSum.getCrCount() - count);
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
				assSum.setDbAmt(assSum.getDbAmt().subtract(postAmount));
				// 借方余额=原借方余额-借方金额
				assSum.setDbBal(assSum.getDbBal().subtract(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额-贷方金额
				assSum.setCrAmt(assSum.getCrAmt().subtract(postAmount));
				// 借方余额=原借方余额+贷方金额
				assSum.setDbBal(assSum.getDbBal().add(postAmount));
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}

			break;
		case C: // 只允许贷方余额
			switch (txnDirection) {
			case D: // 借
				// 借方发生额 =原借记发生额-借方金额
				assSum.setDbAmt(assSum.getDbAmt().subtract(postAmount));
				// 贷方余额=原贷方余额+借方金额
				assSum.setCrBal(assSum.getCrBal().add(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额-贷方金额
				assSum.setCrAmt(assSum.getCrAmt().subtract(postAmount));
				// 贷方余额=原贷方余额-贷方金额
				assSum.setCrBal(assSum.getCrBal().subtract(postAmount));
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
				assSum.setDbAmt(assSum.getDbAmt().subtract(postAmount));
				//
				BigDecimal tmpDbBal = assSum.getDbBal().subtract(postAmount);
				if (tmpDbBal.compareTo(BigDecimal.ZERO) > 0) {
					assSum.setDbBal(tmpDbBal);
				} else {
					assSum.setDbBal(BigDecimal.ZERO);
					assSum.setCrBal(assSum.getCrBal().add(tmpDbBal.negate()));
				}
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额-贷方金额
				assSum.setCrAmt(assSum.getCrAmt().subtract(postAmount));
				//
				BigDecimal tmpCrBal = assSum.getCrBal().subtract(postAmount);
				if (tmpCrBal.compareTo(BigDecimal.ZERO) > 0) {
					assSum.setCrBal(tmpCrBal);
				} else {
					assSum.setCrBal(BigDecimal.ZERO);
					assSum.setDbBal(assSum.getDbBal().add(tmpCrBal.negate()));
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
				assSum.setDbAmt(assSum.getDbAmt().subtract(postAmount));
				// 借方余额=原借方余额-借方金额
				assSum.setDbBal(assSum.getDbBal().subtract(postAmount));
				break;
			case C: // 贷
				// 贷方发生额=原贷记发生额-贷方金额
				assSum.setCrAmt(assSum.getCrAmt().subtract(postAmount));
				// 贷方余额=原贷方余额-贷方金额
				assSum.setCrBal(assSum.getCrBal().subtract(postAmount));
				break;
			default:
				throw new RuntimeException("枚举未定义");
			}

			break;
		default:
			break;
		}
		
		assSum.setDbCount(Math.abs(assSum.getDbCount()));
		assSum.setCrCount(Math.abs(assSum.getCrCount()));
		assSum.setDbBal(assSum.getDbBal().abs());
		assSum.setCrBal(assSum.getCrBal().abs());
		assSum.setDbAmt(assSum.getDbAmt().abs());
		assSum.setCrAmt(assSum.getCrAmt().abs());
		return assSum;
	}

	public void insertApGlVolDtl(String subjectCd,BigDecimal amount,TxnDirection txnDirection,RedBlueInd redBlueInd,Date batchDate){
		QApGlVolDtl qApGlVolDtl = QApGlVolDtl.apGlVolDtl;
		ApGlVolDtl apGlVolDtl = new JPAQueryFactory(em).select(qApGlVolDtl).from(qApGlVolDtl)
				.where(qApGlVolDtl.crsubjectCd.eq(subjectCd), qApGlVolDtl.postGlInd.eq(PostGlInd.OddSuspend))
				.fetchOne();
		if (apGlVolDtl == null) {
			ApGlVolDtl apGlVolDtlFinal = new ApGlVolDtl();
			apGlVolDtlFinal.setOrg(provider4Organization.getCurrentOrganizationId());
			apGlVolDtlFinal.setBranch(provider4Organization.getCurrentOrganizationId());
			apGlVolDtlFinal.setBranchNo(provider4Organization.getCurrentOrganizationId());
			apGlVolDtlFinal.setCurrCd("156");
			apGlVolDtlFinal.setVolDt(new Date());
			apGlVolDtlFinal.setCrsubjectCd(subjectCd);
			apGlVolDtlFinal.setSubjAmount(amount);
			apGlVolDtlFinal.setVolSeq(1);
			apGlVolDtlFinal.setRedBlueInd(redBlueInd);
			apGlVolDtlFinal.setPostGlInd(PostGlInd.OddSuspend);
			apGlVolDtlFinal.setTxnDirection(txnDirection);
			apGlVolDtlFinal.setTxnDetailType(TxnDetailType.R);
			apGlVolDtlFinal.setBizDate(batchDate);
			apGlVolDtlFinal.fillDefaultValues();
			em.persist(apGlVolDtlFinal);
		} else {
			apGlVolDtl.setSubjAmount(amount);
			apGlVolDtl.setBizDate(batchDate);
		}
	}


}
