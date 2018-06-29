package net.engining.sccc.biz.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.pcx.cc.param.model.enums.SubjectAmtType;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.biz.bean.PostCodeSeq;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.bean.batchBean.SubAcctData;
import net.engining.sccc.biz.bean.batchBean.UnionData;
import net.engining.sccc.biz.enums.SubAcctTypeDef;
import net.engining.sccc.biz.enums.SysInternalAcctionCdDef;
import net.engining.sccc.biz.service.params.IntTaxRate;

@Service
@Validated
public class AmountCalculationService {

	@Autowired
	ParameterFacility parameterFacility;

	// 提降额金额数据计算
	public Map<SubjectAmtType, BigDecimal> uoDownLimit(EveryDayAccountingBean onlineDataBean) {
		// 金额计算
		Map<SubjectAmtType, BigDecimal> map = new HashMap<SubjectAmtType, BigDecimal>();
		if (SysInternalAcctionCdDef.S031.equals(onlineDataBean.getRequestData().getSysInternalAcctActionCd())
				|| SysInternalAcctionCdDef.S032.equals(onlineDataBean.getRequestData().getSysInternalAcctActionCd())) {
			map.put(SubjectAmtType.ADDLIMIT, onlineDataBean.getRequestData().getCreditLimitAmt());
		}
		if (SysInternalAcctionCdDef.S033.equals(onlineDataBean.getRequestData().getSysInternalAcctActionCd())) {
			map.put(SubjectAmtType.SUBLIMIT, onlineDataBean.getRequestData().getCreditLimitAmt());
		}
		return map;
	}

	// 费用收取数据金额计算
	public Map<SubjectAmtType, BigDecimal> costAccountTxn(EveryDayAccountingBean costAccountBean) {
		
		Map<SubjectAmtType, BigDecimal> map = new HashMap<SubjectAmtType, BigDecimal>();
		IntTaxRate intTaxRate = parameterFacility.loadParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
		BigDecimal dbAmt = costAccountBean.getRequestData().getSubAcctData().get(0).getCurrBal();
		map.put(SubjectAmtType.SFEE, dbAmt);
		BigDecimal otaxAmt = dbAmt.multiply(intTaxRate.taxRt);
		map.put(SubjectAmtType.OTAX, otaxAmt);
		return map;
	}
	
	// 贷款发放数据金额计算
	public Map<SubjectAmtType, BigDecimal> loanAccountTxn(EveryDayAccountingBean item) {
	
		Map<SubjectAmtType, BigDecimal> map = new HashMap<SubjectAmtType, BigDecimal>();

		if (item.getRequestData().getIsUnion() == true) {
			BigDecimal ownAmt = item.getRequestData().getUnionData().get(0).getOwnAmt();
			BigDecimal otherAmt = item.getRequestData().getUnionData().get(0).getOtherAmt();
			map.put(SubjectAmtType.LOAN, ownAmt);
			map.put(SubjectAmtType.ULOAN, otherAmt);
		}
		if (item.getRequestData().getIsUnion() == false) {
			BigDecimal ownAmt = item.getRequestData().getUnionData().get(0).getOwnAmt();
			map.put(SubjectAmtType.LOAN, ownAmt);
		}
		return map;
	}

	// 退款数据金额计算
	public Map<SubjectAmtType, BigDecimal> refundsAccountTxn(EveryDayAccountingBean item) {
		
		BigDecimal beforAmt = BigDecimal.ZERO;
		BigDecimal afterAmt = BigDecimal.ZERO;
		beforAmt = item.getRequestData().getBeforeSubAcctData().get(0).getCurrBal();
		afterAmt = item.getRequestData().getSubAcctData().get(0).getCurrBal();
		Map<SubjectAmtType, BigDecimal> map = new HashMap<SubjectAmtType, BigDecimal>();

		BigDecimal postAmt = beforAmt.subtract(afterAmt);
		map.put(SubjectAmtType.PAYM, postAmt);
		return map;
	}

	// 还款数据金额计算
	public Map<SubjectAmtType, BigDecimal> repaymentAccountTxn(EveryDayAccountingBean item) {

		Map<SubjectAmtType, BigDecimal> map = new HashMap<SubjectAmtType, BigDecimal>();

		// 交易前应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal beforeLbal = BigDecimal.ZERO;
		// 交易前联合贷参与方金额
		BigDecimal beforeULbal = BigDecimal.ZERO;

		// 交易后应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal afterLbal = BigDecimal.ZERO;
		// 交易后联合贷参与方金额
		BigDecimal afterULbal = BigDecimal.ZERO;

		// 交易前应还利息
		BigDecimal beforeInte = BigDecimal.ZERO;
		// 交易后应还利息
		BigDecimal afterInte = BigDecimal.ZERO;

		// 交易前应还罚息
		BigDecimal beforePnit = BigDecimal.ZERO;
		// 交易后应还罚息
		BigDecimal afterPnit = BigDecimal.ZERO;

		// 本金产生计提利息(此处本金可为当期应还本金LBAL和剩余应还本金Loan)
		BigDecimal beforeTi = BigDecimal.ZERO;
		BigDecimal afterTi = BigDecimal.ZERO;

		// 本金产生计提罚息(此处本金为当期应还本金)
		BigDecimal beforeFine = BigDecimal.ZERO;
		BigDecimal afterFine = BigDecimal.ZERO;

		// 销项税
		BigDecimal otxaAmt = null;

		// 新加对于正常还款时
		BigDecimal beforeLoan = BigDecimal.ZERO;
		BigDecimal afterLoan = BigDecimal.ZERO;
		BigDecimal beforeULoan = BigDecimal.ZERO;
		BigDecimal afterULoan = BigDecimal.ZERO;

		for (SubAcctData beforeData : item.getRequestData().getBeforeSubAcctData()) {

			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
				beforeFine = beforeData.getIntPenaltyAccrual().add(beforeFine);
				if (item.getRequestData().getIsUnion() == false) {
					beforeLbal = beforeData.getCurrBal().add(beforeLbal);
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData())
						if (SubAcctTypeDef.LBAL.name().equals(unionData.getSubAcctType())) {
							beforeLbal = unionData.getOwnAmt().add(beforeLbal);
							beforeULbal = unionData.getOtherAmt().add(beforeULbal);
						}
				}
			}

			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeTi = beforeData.getIntAccrual().add(beforeFine);
				if (item.getRequestData().getIsUnion() == false) {
					beforeLoan = beforeData.getCurrBal();
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData()) {
						if (SubAcctTypeDef.LOAN.name().equals(unionData.getSubAcctType())) {
							beforeLoan = BigDecimal.ZERO;
							beforeULoan = BigDecimal.ZERO;
						}
					}

				}
			}

			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				beforeInte = beforeData.getCurrBal().add(beforeInte);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				beforePnit = beforeData.getCurrBal().add(beforePnit);
			}
		}

		for (SubAcctData afterData : item.getRequestData().getSubAcctData()) {
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				afterFine = afterData.getIntPenaltyAccrual().add(afterFine);
				if (item.getRequestData().getIsUnion() == false) {
					afterLbal = afterData.getCurrBal().add(afterLbal);
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData())
						if (SubAcctTypeDef.LBAL.name().equals(unionData.getSubAcctType())) {
							afterLbal = unionData.getOwnAmt().add(afterLbal);
							afterULbal = unionData.getOtherAmt().add(afterULbal);
						}
				}
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				if (item.getRequestData().getIsUnion() == false) {
					afterLoan = afterData.getCurrBal();
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData()) {
						if (SubAcctTypeDef.LOAN.toString().equals(unionData.getSubAcctType())) {
							afterLoan = BigDecimal.ZERO;
							afterULoan = BigDecimal.ZERO;
						}
					}

				}
			}

			if (afterData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				afterInte = afterData.getCurrBal().add(afterInte);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				afterPnit = afterData.getCurrBal().add(afterPnit);
			}
		}

		IntTaxRate intTaxRate = parameterFacility.loadParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
		otxaAmt = ((beforeInte.subtract(afterInte)).add(beforePnit.subtract(afterPnit))).multiply(intTaxRate.taxRt);
		// 贷款应还本金
		map.put(SubjectAmtType.LBAL, beforeLoan.subtract(afterLoan).add(beforeLbal.subtract(afterLbal)));
		map.put(SubjectAmtType.ULBAL, beforeULoan.subtract(afterULoan).add(beforeULbal.subtract(afterULbal)));

		// map.put(SubjectAmtType.LBAL, beforeLbal.subtract(afterLbal));
		// map.put(SubjectAmtType.ULBAL, beforeULbal.subtract(afterULbal));

		map.put(SubjectAmtType.RECEIVABLE_INTEREST, beforeInte.subtract(afterInte));
		map.put(SubjectAmtType.RECEIVABLE_PNIT, beforePnit.subtract(afterPnit));
		map.put(SubjectAmtType.ACCRUED_INTEREST, beforeTi.subtract(afterTi));
		map.put(SubjectAmtType.ACCRUED_PNIT, beforeFine.subtract(afterFine));
		map.put(SubjectAmtType.OTAX, otxaAmt);
		return map;
	}

	// 冲正还款数据金额计算
	public Map<SubjectAmtType, BigDecimal> correctionRepaymentTxn(EveryDayAccountingBean item) {
		

		Map<SubjectAmtType, BigDecimal> map = new HashMap<SubjectAmtType, BigDecimal>();

		// 交易前应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal beforeLbal = BigDecimal.ZERO;
		// 交易前联合贷参与方金额
		BigDecimal beforeULbal = BigDecimal.ZERO;

		// 交易后应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal afterLbal = BigDecimal.ZERO;
		// 交易后联合贷参与方金额
		BigDecimal afterULbal = BigDecimal.ZERO;

		// 交易前应还利息
		BigDecimal beforeInte = BigDecimal.ZERO;
		// 交易后应还利息
		BigDecimal afterInte = BigDecimal.ZERO;

		// 交易前应还罚息
		BigDecimal beforePnit = BigDecimal.ZERO;
		// 交易后应还罚息
		BigDecimal afterPnit = BigDecimal.ZERO;

		// 本金产生计提利息(此处本金可为当期应还本金LBAL和剩余应还本金Loan)
		BigDecimal beforeTi = BigDecimal.ZERO;
		BigDecimal afterTi = BigDecimal.ZERO;

		// 本金产生计提罚息(此处本金为当期应还本金)
		BigDecimal beforeFine = BigDecimal.ZERO;
		BigDecimal afterFine = BigDecimal.ZERO;

		// 销项税
		BigDecimal otxaAmt = null;

		// 新加对于正常还款时
		BigDecimal beforeLoan = BigDecimal.ZERO;
		BigDecimal afterLoan = BigDecimal.ZERO;
		BigDecimal beforeULoan = BigDecimal.ZERO;
		BigDecimal afterULoan = BigDecimal.ZERO;

		for (SubAcctData beforeData : item.getRequestData().getBeforeSubAcctData()) {

			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
				beforeFine = beforeData.getIntPenaltyAccrual().add(beforeFine);
				if (item.getRequestData().getIsUnion() == false) {
					beforeLbal = beforeData.getCurrBal().add(beforeLbal);
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData())
						if (SubAcctTypeDef.LBAL.name().equals(unionData.getSubAcctType())) {
							beforeLbal = unionData.getOwnAmt().add(beforeLbal);
							beforeULbal = unionData.getOtherAmt().add(beforeULbal);
						}
				}
			}

			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeTi = beforeData.getIntAccrual().add(beforeFine);
				if (item.getRequestData().getIsUnion() == false) {
					beforeLoan = beforeData.getCurrBal();
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData()) {
						if (SubAcctTypeDef.LOAN.toString().equals(unionData.getSubAcctType())) {
							beforeLoan = BigDecimal.ZERO;
							beforeULoan = BigDecimal.ZERO;
						}
					}

				}
			}

			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				beforeInte = beforeData.getCurrBal().add(beforeInte);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				beforePnit = beforeData.getCurrBal().add(beforePnit);
			}
		}

		for (SubAcctData afterData : item.getRequestData().getSubAcctData()) {
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				afterFine = afterData.getIntPenaltyAccrual().add(afterFine);
				if (item.getRequestData().getIsUnion() == false) {
					afterLbal = afterData.getCurrBal().add(afterLbal);
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData())
						if (SubAcctTypeDef.LBAL.name().equals(unionData.getSubAcctType())) {
							afterLbal = unionData.getOwnAmt().add(afterLbal);
							afterULbal = unionData.getOtherAmt().add(afterULbal);
						}
				}
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				if (item.getRequestData().getIsUnion() == false) {
					afterLoan = afterData.getCurrBal();
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData()) {
						if (SubAcctTypeDef.LOAN.toString().equals(unionData.getSubAcctType())) {
							afterLoan = BigDecimal.ZERO;
							afterULoan = BigDecimal.ZERO;
						}
					}

				}
			}

			if (afterData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				afterInte = afterData.getCurrBal().add(afterInte);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				afterPnit = afterData.getCurrBal().add(afterPnit);
			}
		}

		IntTaxRate intTaxRate = parameterFacility.loadParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
		otxaAmt = ((beforeInte.subtract(afterInte)).add(beforePnit.subtract(afterPnit))).multiply(intTaxRate.taxRt);
		// 贷款应还本金
		map.put(SubjectAmtType.LBAL, beforeLoan.subtract(afterLoan).add(beforeLbal.subtract(afterLbal)));
		map.put(SubjectAmtType.ULBAL, beforeULoan.subtract(afterULoan).add(beforeULbal.subtract(afterULbal)));

//		 map.put(SubjectAmtType.LBAL, beforeLbal.subtract(afterLbal));
//		 map.put(SubjectAmtType.ULBAL, beforeULbal.subtract(afterULbal));

		map.put(SubjectAmtType.RECEIVABLE_INTEREST, beforeInte.subtract(afterInte));
		map.put(SubjectAmtType.RECEIVABLE_PNIT, beforePnit.subtract(afterPnit));
		map.put(SubjectAmtType.ACCRUED_INTEREST, beforeTi.subtract(afterTi));
		map.put(SubjectAmtType.ACCRUED_PNIT, beforeFine.subtract(afterFine));
		map.put(SubjectAmtType.OTAX, otxaAmt);
		return map;
	}

	// 冲正利息计提数据金额计算
	public Map<SubjectAmtType, BigDecimal> correctionInteAccTxn(EveryDayAccountingBean item) {
		
		BigDecimal postAmt = BigDecimal.ZERO;// 记账金额
		BigDecimal postTax = BigDecimal.ZERO;// 销项税发生额
		for (int i = 0; i < item.getRequestData().getBeforeSubAcctData().size(); i++) {
			BigDecimal accrual = item.getRequestData().getSubAcctData().get(i).getIntAccrual().subtract(item.getRequestData().getBeforeSubAcctData().get(i).getIntAccrual());
			postAmt = postAmt.add(accrual);
		}

		// 取价税分离参数
		IntTaxRate intTaxRate = parameterFacility.getParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);

		postTax = postAmt.multiply(intTaxRate.taxRt);
		// 记账金额成分入MAP
		Map<SubjectAmtType, BigDecimal> map = new HashMap<>();
		if (item.getRequestData().getAgeCd() == 0) {
			map.put(SubjectAmtType.ACCRUED_INTEREST, postAmt.subtract(postTax));
			map.put(SubjectAmtType.OTAX, postTax);
		} else if (item.getRequestData().getAgeCd() <=1 && item.getRequestData().getAgeCd() <= 3) {
			map.put(SubjectAmtType.ACCRUED_INTEREST, postAmt.subtract(postTax));
			map.put(SubjectAmtType.OTAX, postTax);
		} else {
			map.put(SubjectAmtType.RECEIVABLE_INTEREST, postAmt);
		}
		return map;
	}
	
	// 冲正罚息计提数据金额计算
	public Map<SubjectAmtType, BigDecimal> correctionPnitAccTxn(EveryDayAccountingBean item) {

		BigDecimal postAmt = BigDecimal.ZERO;// 记账金额
		BigDecimal postTax = BigDecimal.ZERO;// 销项税发生额
		for (int i = 0; i < item.getRequestData().getBeforeSubAcctData().size(); i++) {
			BigDecimal accrual = item.getRequestData().getSubAcctData().get(i).getIntPenaltyAccrual()
					.subtract(item.getRequestData().getBeforeSubAcctData().get(i).getIntPenaltyAccrual());
			postAmt = postAmt.add(accrual);
		}
		
		// 取价税分离参数
		IntTaxRate intTaxRate = parameterFacility.getParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
		
		postTax = postAmt.multiply(intTaxRate.taxRt);
		// 记账金额成分入MAP
		Map<SubjectAmtType, BigDecimal> map = new HashMap<>();
		if (item.getRequestData().getAgeCd() <= 3) {
			map.put(SubjectAmtType.RECEIVABLE_PNIT, postAmt.subtract(postTax));
			map.put(SubjectAmtType.OTAX, postTax);
		}
		if (item.getRequestData().getAgeCd() > 3) {
			map.put(SubjectAmtType.RECEIVABLE_PNIT, postAmt);
		}
		return map;
	}

	// 冲正余额结转数据金额计算
	public Map<SubjectAmtType, BigDecimal> correctionTransfoTxn(EveryDayAccountingBean item) {

		BigDecimal beforeLoanCurrBal = BigDecimal.ZERO;// 交易前LOAN当前余额
		BigDecimal afterLoanCurrBal = BigDecimal.ZERO;// 交易后LOAN当前余额
		BigDecimal inteAmt = BigDecimal.ZERO;// 利息
		BigDecimal pintAmt = BigDecimal.ZERO;// 罚息
		BigDecimal beforeInte = BigDecimal.ZERO;// 账期为1，利息的期初余额
		for (SubAcctData beforeData : item.getRequestData().getBeforeSubAcctData()) {
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeLoanCurrBal = beforeData.getCurrBal();
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				if(beforeData.getStmtHist() == 1){
					beforeInte = beforeData.getBeginBal();
				}
			}
			inteAmt = inteAmt.add(beforeData.getIntAccrual() == null ? BigDecimal.ZERO : beforeData.getIntAccrual());// 交易前利息发生额累计
			pintAmt = pintAmt.add(beforeData.getIntPenaltyAccrual() == null ? BigDecimal.ZERO : beforeData.getIntPenaltyAccrual());// 交易前罚息发生额累计
		}
		
		for (SubAcctData afterData : item.getRequestData().getSubAcctData()) {
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterLoanCurrBal = afterData.getCurrBal();
			}
		}
		
		BigDecimal lbalAmt = beforeLoanCurrBal.subtract(afterLoanCurrBal);// 结转出的应还本金
		// 取价税分离参数
		IntTaxRate intTaxRate = parameterFacility.getParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);

		// 记账金额成分入MAP
		Map<SubjectAmtType, BigDecimal> map = new HashMap<>();
		if (!lbalAmt.equals(BigDecimal.ZERO)) {
			map.put(SubjectAmtType.LBAL, lbalAmt);
		}
		if (!inteAmt.equals(BigDecimal.ZERO)) {
			if (item.getRequestData().getAgeCd() == 4) {
				for (SubAcctData beforeData : item.getRequestData().getBeforeSubAcctData()) {
					if (beforeData.getSubAcctType().equals(SubAcctTypeDef.INTE) && beforeData.getStmtHist() != 1) {
						inteAmt = inteAmt.add(beforeData.getCurrBal() == null ? BigDecimal.ZERO : beforeData.getCurrBal());
					}
				}
				BigDecimal otax = beforeInte.multiply(intTaxRate.taxRt);//收入销项税
				BigDecimal inteAmtGz = inteAmt.subtract(beforeInte);//关注类收入
				BigDecimal otaxGz = inteAmt.multiply(intTaxRate.taxRt);//关注类收入销项税
				map.put(SubjectAmtType.RECEIVABLE_INTEREST, beforeInte.subtract(otax));
				map.put(SubjectAmtType.Attention_RECEIVABLE_INTEREST, inteAmtGz.subtract(otaxGz));
				map.put(SubjectAmtType.OTAX, otax.add(otaxGz));
			} else {
				map.put(SubjectAmtType.RECEIVABLE_INTEREST, inteAmt);
			}
		}
		if (!pintAmt.equals(BigDecimal.ZERO)) {
			if(item.getRequestData().getAgeCd() == 4){
				for (SubAcctData beforeData : item.getRequestData().getBeforeSubAcctData()) {
					if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
						pintAmt = pintAmt.add(beforeData.getCurrBal() == null ? BigDecimal.ZERO : beforeData.getCurrBal());
					}
				}
			}
			BigDecimal pintOtax = pintAmt.multiply(intTaxRate.taxRt);
			map.put(SubjectAmtType.RECEIVABLE_PNIT, pintAmt.subtract(pintOtax));
			map.put(SubjectAmtType.OTAX, pintOtax);
		}
		return map;
	}
}
