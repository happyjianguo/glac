package net.engining.sccc.biz.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;
import net.engining.pcx.cc.param.model.enums.Deadline;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.biz.bean.batchBean.BizData;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.bean.batchBean.SubAcctData;
import net.engining.sccc.biz.bean.batchBean.UnionData;
import net.engining.sccc.biz.enums.AccountTradingDef;
import net.engining.sccc.biz.enums.CheckDataResDef;
import net.engining.sccc.biz.enums.SubAcctTypeDef;
import net.engining.sccc.biz.service.params.ProductAccount;

@Service
@Validated
public class CheckDataService {

	@Autowired
	private ParameterFacility parameterFacility;

	public String checkData(EveryDayAccountingBean item) {
		if (item.getRequestData().getAccountTrading() == null) {
			return "数据异常，没有对账交易码！";
		}

		if (item.getRequestData().getTotalAmt() == null
				|| item.getRequestData().getTotalAmt().equals(BigDecimal.ZERO)) {
			return "数据异常，交易金额不正确！";
		}
		Boolean typeDef = false;
		for (BizData bizData : item.getRequestData().getBizData()) {
			if (bizData.getIsAssisting()) {
				for (AssistAccountingType type : AssistAccountingType.values()) {
					System.out.println(type);
					if (bizData.getKeyId().equals(type.toString())) {
						typeDef = true;
					}
				}
			}
			if (typeDef != true) {
				return "AssistAccountingType数据异常，辅助核算项key不正确！";
			}
		}
		// 查询产品参数是否存在
		Deadline deadline = null;
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.S))
				|| item.getRequestData().getAccountTrading().equals((AccountTradingDef.F))
				|| item.getRequestData().getAccountTrading().equals((AccountTradingDef.T))) {
			deadline = Deadline.M;
		} else {
			if (item.getRequestData().getTotalPeriod() <= 12) {
				deadline = Deadline.S;
			} else {
				deadline = Deadline.M;
			}
		}
		ProductAccount product = parameterFacility.getParameter(ProductAccount.class,
				ProductAccount.key(item.getRequestData().getProductId(), deadline));
		if (product == null) {
			return "数据异常，产品唯一标识不正确，没有此产品！";
		}
		// 校验提降额相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.S))) {
			String reult = checkLimitAmt(item);
			return reult;
		}

		// 校验贷款发放相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.D))) {
			String reult = checkGrantAmt(item);
			return reult;
		}
		// 校验费用收取相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.F))) {
			String reult = checkCostAmt(item);
			return reult;
		}
		// 校验还款相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.H))) {
			String reult = checkRepayAmt(item);
			return reult;
		}
		// 校验退款相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.T))) {
			String reult = checkRefundsAmt(item);
			return reult;
		}
		//校验冲正还款相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.C_T))) {
			String reult = checkCorrRepayAmt(item);
			return reult;
		}
		//校验冲正利息计提相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.C_INTEACC))) {
			String reult = checkIntePintAmt(item);
			return reult;
		}
		//校验冲正罚息计提相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.C_PINTACC))) {
			String reult = checkIntePintAmt(item);
			return reult;
		}
		//校验冲正本金结转相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.C_LBAL))) {
			String reult = checkTransfoAmt(item);
			return reult;
		}
		//校验冲正利息结转相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.C_INTE))) {
			String reult = checkTransfoAmt(item);
			return reult;
		}
		//校验冲正罚息结转相关数据
		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.C_PINT))) {
			String reult = checkTransfoAmt(item);
			return reult;
		}
		return "没有此类交易";
	}

	// 校验递降额相关数据
	public String checkLimitAmt(EveryDayAccountingBean item) {

		if (item.getRequestData().getSysInternalAcctActionCd() == null) {
			return "数据异常，内部账交易代码！";
		}

		BigDecimal currBal = BigDecimal.ZERO;
		BigDecimal unionAmt = BigDecimal.ZERO;
		if (item.getRequestData().getIsUnion()) {

			currBal = item.getRequestData().getTotalAmt();
			for (UnionData unionData : item.getRequestData().getUnionData()) {
				unionAmt = unionData.getOtherAmt().add(unionData.getOwnAmt());
			}
			if (currBal.compareTo(unionAmt) != 0) {
				return "数据异常，联合贷金额与总金额不对应！";
			}
		}
		return CheckDataResDef.SUCCESS.toString();
	}

	// 校验贷款发放相关数据
	public String checkGrantAmt(EveryDayAccountingBean item) {
		if (item.getRequestData().getSubAcctData() == null) {
			return "数据异常，没有交易后余额！";
		}
		if (item.getRequestData().getIouNo() == null) {
			if (item.getRequestData().getAcctSeq() == null) {
				return "没有借据号的情况必填贷款账号";
			}
		}
		
		for (SubAcctData subAcctData : item.getRequestData().getSubAcctData()) {
			if (!subAcctData.getSubAcctType().equals(SubAcctTypeDef.SFEE) && !subAcctData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				return "贷款发放交易只包含LOAN和SFEE";
			}
			if (subAcctData.getCurrBal() == null) {
				return "数据异常，当前余额不能为空！";
			}
		}

		BigDecimal currBal = BigDecimal.ZERO;
		BigDecimal unionAmt = BigDecimal.ZERO;
		if (item.getRequestData().getIsUnion()) {
			currBal = item.getRequestData().getTotalAmt();
			for (UnionData unionData : item.getRequestData().getUnionData()) {
				if (!unionData.getSubAcctType().equals(SubAcctTypeDef.SFEE.name()) && !unionData.getSubAcctType().equals(SubAcctTypeDef.LOAN.name())) {
					return "贷款发放交易只包含LOAN和SFEE";
				}
				unionAmt = unionData.getOtherAmt().add(unionData.getOwnAmt());
			}
			if (currBal.compareTo(unionAmt) != 0) {
				return "数据异常，联合贷金额与交易总金额不对应！";
			}
		}
		return CheckDataResDef.SUCCESS.toString();
	}

	// 校验费用收取相关数据
	public String checkCostAmt(EveryDayAccountingBean item) {
		if (item.getRequestData().getSubAcctData() == null) {
			return "数据异常，没有交易后余额！";
		}

		for (SubAcctData subAcctData : item.getRequestData().getSubAcctData()) {
			if (!subAcctData.getSubAcctType().equals(SubAcctTypeDef.SFEE)) {
				return "费用收取交易只包含SFEE";
			}
			if (subAcctData.getCurrBal() == null) {
				return "数据异常，当前余额不能为空！";
			}
		}
		return CheckDataResDef.SUCCESS.toString();
	}

	// 校验还款相关数据
	public String checkRepayAmt(EveryDayAccountingBean item) {
		if (item.getRequestData().getSubAcctData() == null) {
			return "数据异常，没有交易后余额！";
		}
		if (item.getRequestData().getBeforeSubAcctData() == null) {
			return "数据异常，没有交易前余额！";
		}

		for (SubAcctData subAcctData : item.getRequestData().getSubAcctData()) {
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.LBAL)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.LOAN)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.SFEE)) {
				if (subAcctData.getCurrBal() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				if (subAcctData.getPenalizedAmt() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				if (subAcctData.getIntReceivable() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}

		}
		for (SubAcctData subAcctData : item.getRequestData().getBeforeSubAcctData()) {
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.LBAL)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.LOAN)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.SFEE)) {
				if (subAcctData.getCurrBal() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				if (subAcctData.getPenalizedAmt() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				if (subAcctData.getIntReceivable() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}

		}

		// 交易前应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal beforeLbal = BigDecimal.ZERO;

		// 交易后应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal afterLbal = BigDecimal.ZERO;

		// 交易前应还利息
		BigDecimal beforeInte = BigDecimal.ZERO;
		// 交易后应还利息
		 BigDecimal afterInte = BigDecimal.ZERO;

		// 交易前应还罚息
		BigDecimal beforePnit = BigDecimal.ZERO;
		// 交易后应还罚息
		BigDecimal afterPnit = BigDecimal.ZERO;

		BigDecimal beforePaym = BigDecimal.ZERO;
		BigDecimal afterPaym = BigDecimal.ZERO;

		// 本金产生计提利息(此处本金可为当期应还本金LBAL和剩余应还本金Loan)
		BigDecimal beforeTi = BigDecimal.ZERO;
		BigDecimal afterTi = BigDecimal.ZERO;

		// 本金产生计提罚息(此处本金为当期应还本金)
		BigDecimal beforeFine = BigDecimal.ZERO;
		BigDecimal afterFine = BigDecimal.ZERO;

		// 销项税
		// BigDecimal otxaAmt = null;

		// 新加对于正常还款时
		BigDecimal beforeLoan = BigDecimal.ZERO;
		BigDecimal afterLoan = BigDecimal.ZERO;

		for (SubAcctData beforeData : item.getRequestData().getBeforeSubAcctData()) {
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				beforeLbal = beforeData.getCurrBal().add(beforeLbal);
				beforeFine = beforeData.getIntPenaltyAccrual().add(beforeFine);
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
				beforeLoan = beforeData.getCurrBal();
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				beforeInte = beforeData.getCurrBal().add(beforeInte);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				beforePnit = beforeData.getCurrBal().add(beforePnit);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PAYM)) {
				beforePaym = beforeData.getCurrBal().add(beforePaym);
			}
		}
		// 交易前总金额为本金产生利息罚息加应还本金产生利息加应还利息罚息
		BigDecimal before = beforeLbal.add(beforeFine).add(beforeTi).add(beforeLoan).add(beforeInte).add(beforePnit)
				.add(beforePaym);

		for (SubAcctData afterData : item.getRequestData().getSubAcctData()) {
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				afterLbal = afterData.getCurrBal().add(afterLbal);
				afterFine = afterData.getIntPenaltyAccrual().add(afterFine);
				afterTi = afterData.getIntAccrual().add(afterTi);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				afterLoan = afterData.getCurrBal();
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				afterInte = afterData.getCurrBal().add(afterInte);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				afterPnit = afterData.getCurrBal().add(afterPnit);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PAYM)) {
				afterPaym = afterData.getCurrBal().add(afterPaym);
			}
		}
		// 交易后总金额为本金产生利息罚息加应还本金产生利息加应还利息罚息
		BigDecimal after = afterLbal.add(afterFine).add(afterTi).add(afterLoan).add(beforeInte).add(afterPnit)
				.add(afterPaym);
		if ((before.subtract(after)).compareTo(item.getRequestData().getTotalAmt()) != 0) {
			return "交易前后只差不等于交易总金额";
		}

		BigDecimal unLbal = BigDecimal.ZERO;
		BigDecimal unLoan = BigDecimal.ZERO;
		BigDecimal unInte = BigDecimal.ZERO;
		BigDecimal unPnit = BigDecimal.ZERO;
		BigDecimal unPaym = BigDecimal.ZERO;
		if(item.getRequestData().getIsUnion()){
			for (UnionData unionData : item.getRequestData().getUnionData()) {
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.LBAL.name())) {
					unLbal = unionData.getOwnAmt().add(unionData.getOtherAmt());
				}
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.LOAN.name())) {
					unLoan= unionData.getOwnAmt().add(unionData.getOtherAmt());
				}
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.INTE.name())) {
					unInte= unionData.getOwnAmt().add(unionData.getOtherAmt());
				}
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.PNIT.name())) {
					unPnit= unionData.getOwnAmt().add(unionData.getOtherAmt());
				}
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.PAYM.name())) {
					unPaym= unionData.getOwnAmt().add(unionData.getOtherAmt());
				}
			}
			if(beforeLbal.subtract(afterLbal).compareTo(unLbal)!=0){
				return "联合贷金额应还本金不对";
			}
			if(beforeLoan.subtract(afterLoan).compareTo(unLoan)!=0){
				return "联合贷金额本金不对";
			}
			if(beforeInte.subtract(afterInte).compareTo(unInte)!=0){
				return "联合贷金额利息不对";
			}
			
			if(beforePnit.subtract(afterPnit).compareTo(unPnit)!=0){
				return "联合贷金额罚息不对";
			}
			if(beforePaym.subtract(afterPaym).compareTo(unPaym)!=0){
				return "联合贷金额贷款未冲销还款不对";
			}
			
		}

		return CheckDataResDef.SUCCESS.toString();
	}

	// 校验退款相关数据
	public String checkRefundsAmt(EveryDayAccountingBean item) {
		if (item.getRequestData().getSubAcctData() == null) {
			return "数据异常，没有交易后余额！";
		}

		for (SubAcctData subAcctData : item.getRequestData().getSubAcctData()) {
			if (subAcctData.getCurrBal() == null) {
				return "数据异常，当前余额不能为空！";
			}
		}

		boolean beforePaym = false;
		boolean afterPaym = false;
		for (SubAcctData subAcctData : item.getRequestData().getSubAcctData()) {
			if (subAcctData.getCurrBal() == null) {
				return "数据异常，交易后当前余额不能为空！";
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.PAYM)) {
				afterPaym = true;
			}
		}

		if (afterPaym == false) {
			return "退款数据异常，交易后金额成分没有退款！";
		}

		for (SubAcctData subAcctData : item.getRequestData().getBeforeSubAcctData()) {
			if (subAcctData.getCurrBal() == null) {
				return "数据异常，交易前当前余额不能为空！";
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.PAYM)) {
				beforePaym = true;
			}
		}
		if (beforePaym == false) {
			return "退款数据异常，交易前金额成分没有退款！";
		}
		return CheckDataResDef.SUCCESS.toString();
	}

	// 校验冲正利、罚息计提相关数据
	public String checkIntePintAmt(EveryDayAccountingBean item) {
		if (item.getRequestData().getSubAcctData() == null) {
			return "数据异常，没有交易后余额！";
		}
		if (item.getRequestData().getBeforeSubAcctData() == null) {
			return "数据异常，没有交易前余额！";
		}

		for (SubAcctData subAcctData : item.getRequestData().getSubAcctData()) {
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.LBAL)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.LOAN)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.SFEE)) {
				if (subAcctData.getCurrBal() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				if (subAcctData.getPenalizedAmt() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				if (subAcctData.getIntReceivable() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}

		}
		for (SubAcctData subAcctData : item.getRequestData().getBeforeSubAcctData()) {
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.LBAL)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.LOAN)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.SFEE)) {
				if (subAcctData.getCurrBal() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				if (subAcctData.getPenalizedAmt() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				if (subAcctData.getIntReceivable() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}

		}

		// 交易前应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal beforeLbal = BigDecimal.ZERO;

		// 交易后应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal afterLbal = BigDecimal.ZERO;

		// 交易前应还利息
		BigDecimal beforeInte = BigDecimal.ZERO;
		// 交易后应还利息
		 BigDecimal afterInte = BigDecimal.ZERO;

		// 交易前应还罚息
		BigDecimal beforePnit = BigDecimal.ZERO;
		// 交易后应还罚息
		BigDecimal afterPnit = BigDecimal.ZERO;

		BigDecimal beforePaym = BigDecimal.ZERO;
		BigDecimal afterPaym = BigDecimal.ZERO;

		// 本金产生计提利息(此处本金可为当期应还本金LBAL和剩余应还本金Loan)
		BigDecimal beforeTi = BigDecimal.ZERO;
		BigDecimal afterTi = BigDecimal.ZERO;

		// 本金产生计提罚息(此处本金为当期应还本金)
		BigDecimal beforeFine = BigDecimal.ZERO;
		BigDecimal afterFine = BigDecimal.ZERO;

		// 销项税
		// BigDecimal otxaAmt = null;

		// 新加对于正常还款时
		BigDecimal beforeLoan = BigDecimal.ZERO;
		BigDecimal afterLoan = BigDecimal.ZERO;

		for (SubAcctData beforeData : item.getRequestData().getBeforeSubAcctData()) {
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				beforeLbal = beforeData.getCurrBal().add(beforeLbal);
				beforeFine = beforeData.getIntPenaltyAccrual().add(beforeFine);
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
				beforeLoan = beforeData.getCurrBal();
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				beforeInte = beforeData.getCurrBal().add(beforeInte);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				beforePnit = beforeData.getCurrBal().add(beforePnit);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PAYM)) {
				beforePaym = beforeData.getCurrBal().add(beforePaym);
			}
		}
		// 交易前总金额为本金产生利息罚息加应还本金产生利息加应还利息罚息
		BigDecimal before = beforeLbal.add(beforeFine).add(beforeTi).add(beforeLoan).add(beforeInte).add(beforePnit)
				.add(beforePaym);

		for (SubAcctData afterData : item.getRequestData().getSubAcctData()) {
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				afterLbal = afterData.getCurrBal().add(afterLbal);
				afterFine = afterData.getIntPenaltyAccrual().add(afterFine);
				afterTi = afterData.getIntAccrual().add(afterTi);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				afterLoan = afterData.getCurrBal();
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				afterInte = afterData.getCurrBal().add(afterInte);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				afterPnit = afterData.getCurrBal().add(afterPnit);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PAYM)) {
				afterPaym = afterData.getCurrBal().add(afterPaym);
			}
		}
		// 交易后总金额为本金产生利息罚息加应还本金产生利息加应还利息罚息
		BigDecimal after = afterLbal.add(afterFine).add(afterTi).add(afterLoan).add(beforeInte).add(afterPnit)
				.add(afterPaym);
		if ((before.subtract(after)).compareTo(item.getRequestData().getTotalAmt()) != 0) {
			return "交易前后只差不等于交易总金额";
		}		

		return CheckDataResDef.SUCCESS.toString();
	}
	
	// 校验冲正余额结转相关数据
	public String checkTransfoAmt(EveryDayAccountingBean item) {
		if (item.getRequestData().getSubAcctData() == null) {
			return "数据异常，没有交易后余额！";
		}
		if (item.getRequestData().getBeforeSubAcctData() == null) {
			return "数据异常，没有交易前余额！";
		}

		for (SubAcctData subAcctData : item.getRequestData().getSubAcctData()) {
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.LBAL)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.LOAN)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.SFEE)) {
				if (subAcctData.getCurrBal() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				if (subAcctData.getPenalizedAmt() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				if (subAcctData.getIntReceivable() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}

		}
		for (SubAcctData subAcctData : item.getRequestData().getBeforeSubAcctData()) {
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.LBAL)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.LOAN)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.SFEE)) {
				if (subAcctData.getCurrBal() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				if (subAcctData.getPenalizedAmt() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				if (subAcctData.getIntReceivable() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}

		}

		// 交易前应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal beforeLbal = BigDecimal.ZERO;

		// 交易后应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal afterLbal = BigDecimal.ZERO;

		// 交易前应还利息
		BigDecimal beforeInte = BigDecimal.ZERO;
		// 交易后应还利息
		 BigDecimal afterInte = BigDecimal.ZERO;

		// 交易前应还罚息
		BigDecimal beforePnit = BigDecimal.ZERO;
		// 交易后应还罚息
		BigDecimal afterPnit = BigDecimal.ZERO;

		BigDecimal beforePaym = BigDecimal.ZERO;
		BigDecimal afterPaym = BigDecimal.ZERO;

		// 本金产生计提利息(此处本金可为当期应还本金LBAL和剩余应还本金Loan)
		BigDecimal beforeTi = BigDecimal.ZERO;
		BigDecimal afterTi = BigDecimal.ZERO;

		// 本金产生计提罚息(此处本金为当期应还本金)
		BigDecimal beforeFine = BigDecimal.ZERO;
		BigDecimal afterFine = BigDecimal.ZERO;

		// 销项税
		// BigDecimal otxaAmt = null;

		// 新加对于正常还款时
		BigDecimal beforeLoan = BigDecimal.ZERO;
		BigDecimal afterLoan = BigDecimal.ZERO;

		for (SubAcctData beforeData : item.getRequestData().getBeforeSubAcctData()) {
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				beforeLbal = beforeData.getCurrBal().add(beforeLbal);
				beforeFine = beforeData.getIntPenaltyAccrual().add(beforeFine);
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
				beforeLoan = beforeData.getCurrBal();
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				beforeInte = beforeData.getCurrBal().add(beforeInte);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				beforePnit = beforeData.getCurrBal().add(beforePnit);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PAYM)) {
				beforePaym = beforeData.getCurrBal().add(beforePaym);
			}
		}
		// 交易前总金额为本金产生利息罚息加应还本金产生利息加应还利息罚息
		BigDecimal before = beforeLbal.add(beforeFine).add(beforeTi).add(beforeLoan).add(beforeInte).add(beforePnit)
				.add(beforePaym);

		for (SubAcctData afterData : item.getRequestData().getSubAcctData()) {
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				afterLbal = afterData.getCurrBal().add(afterLbal);
				afterFine = afterData.getIntPenaltyAccrual().add(afterFine);
				afterTi = afterData.getIntAccrual().add(afterTi);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				afterLoan = afterData.getCurrBal();
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				afterInte = afterData.getCurrBal().add(afterInte);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				afterPnit = afterData.getCurrBal().add(afterPnit);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PAYM)) {
				afterPaym = afterData.getCurrBal().add(afterPaym);
			}
		}
		// 交易后总金额为本金产生利息罚息加应还本金产生利息加应还利息罚息
		BigDecimal after = afterLbal.add(afterFine).add(afterTi).add(afterLoan).add(beforeInte).add(afterPnit)
				.add(afterPaym);
		if ((before.subtract(after)).compareTo(item.getRequestData().getTotalAmt()) != 0) {
			return "交易前后只差不等于交易总金额";
		}		

		return CheckDataResDef.SUCCESS.toString();
	}
	
	// 校验冲正还款相关数据
	public String checkCorrRepayAmt(EveryDayAccountingBean item) {
		if (item.getRequestData().getSubAcctData() == null) {
			return "数据异常，没有交易后余额！";
		}
		if (item.getRequestData().getBeforeSubAcctData() == null) {
			return "数据异常，没有交易前余额！";
		}

		for (SubAcctData subAcctData : item.getRequestData().getSubAcctData()) {
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.LBAL)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.LOAN)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.SFEE)) {
				if (subAcctData.getCurrBal() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				if (subAcctData.getPenalizedAmt() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				if (subAcctData.getIntReceivable() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}

		}
		for (SubAcctData subAcctData : item.getRequestData().getBeforeSubAcctData()) {
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.LBAL)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.LOAN)
					|| subAcctData.getSubAcctType().equals(SubAcctTypeDef.SFEE)) {
				if (subAcctData.getCurrBal() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				if (subAcctData.getPenalizedAmt() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}
			if (subAcctData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				if (subAcctData.getIntReceivable() == null) {
					return "数据异常，" + subAcctData.getSubAcctType().name() + "对应金额不能为空！";
				}
			}

		}

		// 交易前应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal beforeLbal = BigDecimal.ZERO;

		// 交易后应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal afterLbal = BigDecimal.ZERO;

		// 交易前应还利息
		BigDecimal beforeInte = BigDecimal.ZERO;
		// 交易后应还利息
		 BigDecimal afterInte = BigDecimal.ZERO;

		// 交易前应还罚息
		BigDecimal beforePnit = BigDecimal.ZERO;
		// 交易后应还罚息
		BigDecimal afterPnit = BigDecimal.ZERO;

		BigDecimal beforePaym = BigDecimal.ZERO;
		BigDecimal afterPaym = BigDecimal.ZERO;

		// 本金产生计提利息(此处本金可为当期应还本金LBAL和剩余应还本金Loan)
		BigDecimal beforeTi = BigDecimal.ZERO;
		BigDecimal afterTi = BigDecimal.ZERO;

		// 本金产生计提罚息(此处本金为当期应还本金)
		BigDecimal beforeFine = BigDecimal.ZERO;
		BigDecimal afterFine = BigDecimal.ZERO;

		// 销项税
		// BigDecimal otxaAmt = null;

		// 新加对于正常还款时
		BigDecimal beforeLoan = BigDecimal.ZERO;
		BigDecimal afterLoan = BigDecimal.ZERO;

		for (SubAcctData beforeData : item.getRequestData().getBeforeSubAcctData()) {
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				beforeLbal = beforeData.getCurrBal().add(beforeLbal);
				beforeFine = beforeData.getIntPenaltyAccrual().add(beforeFine);
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
				beforeLoan = beforeData.getCurrBal();
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				beforeInte = beforeData.getCurrBal().add(beforeInte);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				beforePnit = beforeData.getCurrBal().add(beforePnit);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PAYM)) {
				beforePaym = beforeData.getCurrBal().add(beforePaym);
			}
		}
		// 交易前总金额为本金产生利息罚息加应还本金产生利息加应还利息罚息
		BigDecimal before = beforeLbal.add(beforeFine).add(beforeTi).add(beforeLoan).add(beforeInte).add(beforePnit)
				.add(beforePaym);

		for (SubAcctData afterData : item.getRequestData().getSubAcctData()) {
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				afterLbal = afterData.getCurrBal().add(afterLbal);
				afterFine = afterData.getIntPenaltyAccrual().add(afterFine);
				afterTi = afterData.getIntAccrual().add(afterTi);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				afterLoan = afterData.getCurrBal();
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				afterInte = afterData.getCurrBal().add(afterInte);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				afterPnit = afterData.getCurrBal().add(afterPnit);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PAYM)) {
				afterPaym = afterData.getCurrBal().add(afterPaym);
			}
		}
		// 交易后总金额为本金产生利息罚息加应还本金产生利息加应还利息罚息
		BigDecimal after = afterLbal.add(afterFine).add(afterTi).add(afterLoan).add(beforeInte).add(afterPnit)
				.add(afterPaym);
		if ((before.subtract(after)).compareTo(item.getRequestData().getTotalAmt()) != 0) {
			return "交易前后只差不等于交易总金额";
		}

		BigDecimal unLbal = BigDecimal.ZERO;
		BigDecimal unLoan = BigDecimal.ZERO;
		BigDecimal unInte = BigDecimal.ZERO;
		BigDecimal unPnit = BigDecimal.ZERO;
		BigDecimal unPaym = BigDecimal.ZERO;
		if(item.getRequestData().getIsUnion()){
			for (UnionData unionData : item.getRequestData().getUnionData()) {
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.LBAL.name())) {
					unLbal = unionData.getOwnAmt().add(unionData.getOtherAmt());
				}
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.LOAN.name())) {
					unLoan= unionData.getOwnAmt().add(unionData.getOtherAmt());
				}
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.INTE.name())) {
					unInte= unionData.getOwnAmt().add(unionData.getOtherAmt());
				}
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.PNIT.name())) {
					unPnit= unionData.getOwnAmt().add(unionData.getOtherAmt());
				}
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.PAYM.name())) {
					unPaym= unionData.getOwnAmt().add(unionData.getOtherAmt());
				}
			}
			if(beforeLbal.subtract(afterLbal).compareTo(unLbal)!=0){
				return "联合贷金额应还本金不对";
			}
			if(beforeLoan.subtract(afterLoan).compareTo(unLoan)!=0){
				return "联合贷金额本金不对";
			}
			if(beforeInte.subtract(afterInte).compareTo(unInte)!=0){
				return "联合贷金额利息不对";
			}
			
			if(beforePnit.subtract(afterPnit).compareTo(unPnit)!=0){
				return "联合贷金额罚息不对";
			}
			if(beforePaym.subtract(afterPaym).compareTo(unPaym)!=0){
				return "联合贷金额贷款未冲销还款不对";
			}
			
		}
		return CheckDataResDef.SUCCESS.toString();
	}
}
