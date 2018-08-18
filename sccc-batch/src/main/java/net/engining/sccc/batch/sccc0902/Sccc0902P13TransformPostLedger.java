package net.engining.sccc.batch.sccc0902;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.pcx.cc.infrastructure.shared.enums.PostTxnTypeDef;
import net.engining.pcx.cc.param.model.enums.SubjectAmtType;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.biz.bean.PostCodeSeq;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.bean.batchBean.RequestData;
import net.engining.sccc.biz.bean.batchBean.SubAcctData;
import net.engining.sccc.biz.enums.AccountTradingDef;
import net.engining.sccc.biz.enums.SubAcctTypeDef;
import net.engining.sccc.biz.service.ComputingPostCodeService;
import net.engining.sccc.biz.service.ComputingTxnCdService;
import net.engining.sccc.biz.service.SplitVolService;
import net.engining.sccc.biz.service.params.IntTaxRate;
import net.engining.sccc.entity.model.BtEodTxnImport;

/**
 * 余额结转文件入账
 * 
 * @author xiachuanhu
 *
 */
@Service
@StepScope
public class Sccc0902P13TransformPostLedger implements ItemProcessor<BtEodTxnImport, Object> {
	/*
	 * 结转类型枚举
	 */
	private enum FlagType {
		LBAL, PINT, INTE
	}

	private static final Logger log = LoggerFactory.getLogger(Sccc0902P13TransformPostLedger.class);

	@PersistenceContext
	private EntityManager em;
	@Value("#{new org.joda.time.LocalDate(jobParameters['bizDate'].time)}")
	private LocalDate bizDate;
	@Autowired
	private ParameterFacility parameterFacility;
	@Autowired
	private ComputingPostCodeService getPostCodeService;
	@Autowired
	private SplitVolService splitVolService;

	@Autowired
	private ComputingTxnCdService batchGetPostCodeService;

	@Override
	public Object process(BtEodTxnImport item) throws Exception {
		// 取数据
		EveryDayAccountingBean headerData = JSON.parseObject(item.getJsonInputData(),
				new TypeReference<EveryDayAccountingBean>() {
				});
		RequestData data = headerData.getRequestData();
		// 取交易前List
		List<SubAcctData> listBefore = data.getBeforeSubAcctData();
		// 取交易后List
		List<SubAcctData> listAfter = data.getSubAcctData();
		Date clearDate = headerData.getClearDate();
		BigDecimal beforeLoanCurrBal = BigDecimal.ZERO;// 交易前LOAN当前余额
		BigDecimal afterLoanCurrBal = BigDecimal.ZERO;// 交易后LOAN当前余额
		BigDecimal inteAmt = BigDecimal.ZERO;// 利息
		BigDecimal pintAmt = BigDecimal.ZERO;// 罚息
		BigDecimal beforeInte = BigDecimal.ZERO;// 账期为1，利息的期初余额
		for (SubAcctData list1 : listBefore) {
			if (list1.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeLoanCurrBal = list1.getCurrBal();
			}
			if (list1.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				if (list1.getStmtHist() == 1) {
					beforeInte = list1.getBeginBal();
				}
			}
			inteAmt = inteAmt.add(list1.getIntAccrual() == null ? BigDecimal.ZERO : list1.getIntAccrual());// 交易前利息发生额累计
			pintAmt = pintAmt
					.add(list1.getIntPenaltyAccrual() == null ? BigDecimal.ZERO : list1.getIntPenaltyAccrual());// 交易前罚息发生额累计
		}
		for (SubAcctData list2 : listAfter) {
			if (list2.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterLoanCurrBal = list2.getCurrBal();
			}
		}

		BigDecimal lbalAmt = beforeLoanCurrBal.subtract(afterLoanCurrBal);// 结转出的应还本金
		FlagType flag = null;// 定义取外部交易码标志
		// 取价税分离参数
		IntTaxRate intTaxRate = parameterFacility.getParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);

		// 记账金额成分入MAP
		Map<SubjectAmtType, BigDecimal> map = new HashMap<>();
		AgeGroupCd ageGroup = null;
		if (!lbalAmt.equals(BigDecimal.ZERO)) {
			map.put(SubjectAmtType.LBAL, lbalAmt);
			flag = FlagType.LBAL;
			transformCommon(item, headerData, map, clearDate, flag, ageGroup);
		}
		if (!inteAmt.equals(BigDecimal.ZERO)) {
			if (data.getAgeCd() == 4) {
				for (SubAcctData list1 : listBefore) {
					if (list1.getSubAcctType().equals(SubAcctTypeDef.INTE) && list1.getStmtHist() != 1) {
						inteAmt = inteAmt.add(list1.getCurrBal() == null ? BigDecimal.ZERO : list1.getCurrBal());
					}
				}

				BigDecimal otax = beforeInte.multiply(intTaxRate.taxRt);// 收入销项税
				BigDecimal inteAmtGz = inteAmt.subtract(beforeInte);// 关注类收入
				BigDecimal otaxGz = inteAmt.multiply(intTaxRate.taxRt);// 关注类收入销项税
				map.put(SubjectAmtType.RECEIVABLE_INTEREST, beforeInte.subtract(otax));
				map.put(SubjectAmtType.Attention_RECEIVABLE_INTEREST, inteAmtGz.subtract(otaxGz));
				map.put(SubjectAmtType.OTAX, otax.add(otaxGz));
				ageGroup = AgeGroupCd.Secondary;
			} else {
				map.put(SubjectAmtType.RECEIVABLE_INTEREST, inteAmt);
				ageGroup = AgeGroupCd.Under4M3;
			}
			flag = FlagType.INTE;
			transformCommon(item, headerData, map, clearDate, flag, ageGroup);
		}
		if (!pintAmt.equals(BigDecimal.ZERO)) {
			if (data.getAgeCd() == 4) {
				for (SubAcctData list1 : listBefore) {
					if (list1.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
						pintAmt = pintAmt.add(list1.getCurrBal() == null ? BigDecimal.ZERO : list1.getCurrBal());
					}
				}
			}

			BigDecimal pintOtax = pintAmt.multiply(intTaxRate.taxRt);
			map.put(SubjectAmtType.RECEIVABLE_PNIT, pintAmt.subtract(pintOtax));
			map.put(SubjectAmtType.OTAX, pintOtax);
			flag = FlagType.PINT;
			ageGroup = AgeGroupCd.Above4M3;
			transformCommon(item, headerData, map, clearDate, flag, ageGroup);
		}
		return null;
	}

	/**
	 * 
	 * @param item
	 * @param data
	 * @param map
	 * @param clearDate
	 * @param flag
	 */
	private void transformCommon(BtEodTxnImport item, EveryDayAccountingBean headerData, Map<SubjectAmtType, BigDecimal> map,
			Date clearDate, FlagType flag,AgeGroupCd ageGroup) {
		RequestData data = headerData.getRequestData();
//		Deadline deadLine = null;
//		if (data.getTotalPeriod() <= 12) {
//			deadLine = Deadline.S;// 短期
//		} else {
//			deadLine = Deadline.M;// 中长期
//		}
//		// 获取外部交易码
//		SysTxnCd txnCd = batchGetPostCodeService.getTxnCd(AccountTradingDef.valueOf(flag.toString()), 
//				data.getTotalPeriod(), null,data.getAgeCd(), null);
		
//		SysTxnCd txnCd = null;
//		if (flag == FlagType.LBAL) {
//			if (deadLine.equals(Deadline.S) && data.getAgeCd() <= 3) {
//				txnCd = SysTxnCd.T017;
//			} else if (deadLine.equals(Deadline.S) && data.getAgeCd() == 4) {
//				txnCd = SysTxnCd.T018;
//			} else if (deadLine.equals(Deadline.S) && data.getAgeCd() <= 6) {
//				txnCd = SysTxnCd.T019;
//			} else if (deadLine.equals(Deadline.S) && data.getAgeCd() >= 7) {
//				txnCd = SysTxnCd.T020;
//			} else if (deadLine.equals(Deadline.M) && data.getAgeCd() <= 3) {
//				txnCd = SysTxnCd.T021;
//			} else if (deadLine.equals(Deadline.M) && data.getAgeCd() == 4) {
//				txnCd = SysTxnCd.T022;
//			} else if (deadLine.equals(Deadline.M) && data.getAgeCd() <= 6) {
//				txnCd = SysTxnCd.T023;
//			} else {
//				txnCd = SysTxnCd.T024;
//			}
//		} else if (flag == FlagType.INTE) {
//			if (deadLine.equals(Deadline.S) && data.getAgeCd() <= 3) {
//				txnCd = SysTxnCd.T025;
//			} else if (deadLine.equals(Deadline.S) && data.getAgeCd() == 4) {
//				txnCd = SysTxnCd.T026;
//			} else if (deadLine.equals(Deadline.M) && data.getAgeCd() <= 3) {
//				txnCd = SysTxnCd.T027;
//			} else {
//				txnCd = SysTxnCd.T028;
//			}
//		} else {
//			if(data.getAgeCd() >= 4){
//				if (deadLine.equals(Deadline.S)) {
//					txnCd = SysTxnCd.T054;
//				} else {
//					txnCd = SysTxnCd.T055;
//				}
//			}
//		}
//		if(txnCd != null){
//			// 获取入账交易码,并保存当日入账交易表，当日总账交易流水表
//			PostCodeSeq postCodeSeq = getPostCodeService.validateTrans(AccountTradingDef.valueOf(flag.toString()),data, txnCd, headerData, clearDate , PostTxnTypeDef.TRANSFO);
//			// 调用分录拆分方法
//		}
		
		PostCodeSeq postCodeSeq = getPostCodeService.validateTrans(
				AccountTradingDef.valueOf(flag.toString()),
				data, headerData, clearDate , PostTxnTypeDef.TRANSFO,null,data.getAgeCd(),null);

		splitVolService.split(postCodeSeq, map, clearDate, bizDate, data,ageGroup);
	}

}
