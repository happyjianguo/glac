package net.engining.sccc.batch.sccc0902;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
import net.engining.sccc.biz.bean.batchBean.UnionData;
import net.engining.sccc.biz.enums.AccountTradingDef;
import net.engining.sccc.biz.enums.SubAcctTypeDef;
import net.engining.sccc.biz.enums.SysInternalAcctionCdDef;
import net.engining.sccc.biz.service.ComputingPostCodeService;
import net.engining.sccc.biz.service.ComputingTxnCdService;
import net.engining.sccc.biz.service.SplitVolService;
import net.engining.sccc.biz.service.params.IntTaxRate;
import net.engining.sccc.entity.model.BtEodTxnImport;

/**
 * 批量还款文件入账
 * 
 * @author xiachuanhu
 *
 */
@Service
@StepScope
public class Sccc0902P14BatchRepayPostLedger implements ItemProcessor<BtEodTxnImport, Object> {
	private static final Logger log = LoggerFactory.getLogger(Sccc0902P14BatchRepayPostLedger.class);

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
	private ComputingTxnCdService getTxnCdService;

	@Override
	public Object process(BtEodTxnImport item) throws Exception {
		// 取数据
		EveryDayAccountingBean headerData = JSON.parseObject(item.getJsonInputData(),
				new TypeReference<EveryDayAccountingBean>() {
				});
		RequestData data = headerData.getRequestData();
		Date clearDate = headerData.getClearDate();
		// 获取外部交易码
		SysInternalAcctionCdDef sysInternalAcctionCdDef = null;
//		SysTxnCd txnCd = getTxnCdService.getTxnCd(AccountTradingDef.H, data.getTotalPeriod(), sysInternalAcctionCdDef,
//				data.getAgeCd(), data.getIsUnion());

		// 获取入账交易码,并保存当日入账交易表，当日总账交易流水表
//		PostCodeSeq postCodeSeq = getPostCodeService.getPostCd(data, txnCd, headerData, clearDate,
//				PostTxnTypeDef.BATREPA, data.getTotalAmt());

		PostCodeSeq postCodeSeq = getPostCodeService.getPostCode(data, headerData, clearDate,
				PostTxnTypeDef.BATREPA, data.getTotalAmt(), AccountTradingDef.H, 
				sysInternalAcctionCdDef, data.getAgeCd(), data.getIsUnion());

		Map<SubjectAmtType, BigDecimal> map = new HashMap<SubjectAmtType, BigDecimal>();
		AgeGroupCd ageGroup = null;
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

		// 新加对于正常还款时
		BigDecimal beforeLoan = BigDecimal.ZERO;
		BigDecimal afterLoan = BigDecimal.ZERO;
		BigDecimal beforeULoan = BigDecimal.ZERO;
		BigDecimal afterULoan = BigDecimal.ZERO;

		// 销项税
		// BigDecimal otxaAmt = null;
		BigDecimal InteOtxaAmt = null;// 利息销项税
		BigDecimal PnitOtxaAmt = null;// 罚息销项税

		for (SubAcctData beforeData : data.getBeforeSubAcctData()) {

			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
				beforeFine = beforeData.getIntPenaltyAccrual().add(beforeFine);
				if (data.getIsUnion() == false) {
					beforeLbal = beforeData.getCurrBal().add(beforeLbal);
				}
				if (data.getIsUnion() == true) {
					for (UnionData unionData : data.getUnionData()) {
						if (SubAcctTypeDef.LBAL.equals(SubAcctTypeDef.valueOf(unionData.getSubAcctType()))) {
							beforeLbal = unionData.getOwnAmt().add(beforeLbal);
							beforeULbal = unionData.getOtherAmt().add(beforeULbal);
						}
					}

				}
			}

			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeTi = beforeData.getIntAccrual().add(beforeFine);
				if (data.getIsUnion() == false) {
					beforeLoan = beforeData.getCurrBal();
				}
				if (data.getIsUnion() == true) {
					for (UnionData unionData : data.getUnionData()) {
						if (SubAcctTypeDef.LOAN.equals(SubAcctTypeDef.valueOf(unionData.getSubAcctType()))) {
							beforeLoan = unionData.getOwnAmt().add(beforeLoan);
							beforeULoan = unionData.getOtherAmt().add(beforeULoan);
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

		for (SubAcctData afterData : data.getSubAcctData()) {
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				afterFine = afterData.getIntPenaltyAccrual().add(afterFine);
				if (data.getIsUnion() == false) {
					afterLbal = afterData.getCurrBal().add(afterLbal);
				}
				if (data.getIsUnion() == true) {
					for (UnionData unionData : data.getUnionData()) {
						if (SubAcctTypeDef.LBAL.equals(SubAcctTypeDef.valueOf(unionData.getSubAcctType()))) {
							afterLbal = BigDecimal.ZERO;
							afterULbal = BigDecimal.ZERO;
						}
					}

				}
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				if (data.getIsUnion() == false) {
					afterLoan = afterData.getCurrBal();
				}
				if (data.getIsUnion() == true) {
					for (UnionData unionData : data.getUnionData()) {
						if (SubAcctTypeDef.LOAN.equals(SubAcctTypeDef.valueOf(unionData.getSubAcctType()))) {
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
		// otxaAmt =
		// (beforeInte.subtract(afterInte).add(beforePnit.subtract(afterPnit))).multiply(intTaxRate.taxRt);
		InteOtxaAmt = (beforeInte.subtract(afterInte)).multiply(intTaxRate.taxRt);
		PnitOtxaAmt = (beforePnit.subtract(afterPnit)).multiply(intTaxRate.taxRt);
		// 贷款应还本金
		map.put(SubjectAmtType.LBAL, beforeLoan.subtract(afterLoan).add(beforeLbal.subtract(afterLbal)));
		map.put(SubjectAmtType.ULBAL, beforeULoan.subtract(afterULoan).add(beforeULbal.subtract(afterULbal)));
		//
		map.put(SubjectAmtType.RECEIVABLE_INTEREST, beforeInte.subtract(afterInte));
		map.put(SubjectAmtType.RECEIVABLE_PNIT, beforePnit.subtract(afterPnit));
		map.put(SubjectAmtType.ACCRUED_INTEREST, beforeTi.subtract(afterTi));
		map.put(SubjectAmtType.ACCRUED_PNIT, beforeFine.subtract(afterFine));
		// map.put(SubjectAmtType.OTAX, otxaAmt);
		map.put(SubjectAmtType.INTE_OTAX, InteOtxaAmt);
		map.put(SubjectAmtType.PNIT_OTAX, PnitOtxaAmt);
		// 调用分录拆分方法
		splitVolService.split(postCodeSeq, map, clearDate, bizDate, data, ageGroup);

		return null;

	}

}
