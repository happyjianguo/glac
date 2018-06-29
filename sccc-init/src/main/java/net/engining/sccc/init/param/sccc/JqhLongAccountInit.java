package net.engining.sccc.init.param.sccc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.gm.infrastructure.enums.AuthType;
import net.engining.gm.infrastructure.enums.BusinessType;
import net.engining.gm.infrastructure.enums.Interval;
import net.engining.pcx.cc.param.model.Account;
import net.engining.pcx.cc.param.model.BalTransferPostCode;
import net.engining.pcx.cc.param.model.BlockCodeControl;
import net.engining.pcx.cc.param.model.PostCode;
import net.engining.pcx.cc.param.model.SubAcct;
import net.engining.pcx.cc.param.model.SubAcctType;
import net.engining.pcx.cc.param.model.enums.BalanceDirection;
import net.engining.pcx.cc.param.model.enums.BalanceType;
import net.engining.pcx.cc.param.model.enums.CalcMethod;
import net.engining.pcx.cc.param.model.enums.CycleStartDay;
import net.engining.pcx.cc.param.model.enums.DelqDayInd;
import net.engining.pcx.cc.param.model.enums.DelqTolInd;
import net.engining.pcx.cc.param.model.enums.DownpmtTolInd;
import net.engining.pcx.cc.param.model.enums.GenAcctMethod;
import net.engining.pcx.cc.param.model.enums.IntAccumFrom;
import net.engining.pcx.cc.param.model.enums.PaymentMethod;
import net.engining.pcx.cc.param.model.enums.PnitType;
import net.engining.pcx.cc.param.model.enums.SubAcctCategory;
import net.engining.pcx.cc.param.model.enums.SysTxnCd;
import net.engining.pcx.cc.param.model.enums.TransMergeMethod;
import net.engining.pcx.cc.param.model.enums.TransformType;
import net.engining.pcx.cc.process.service.param.CcParamComparatorService;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.entity.model.ParameterSeqence;
import net.engining.pg.support.init.TableDataInitializer;

@Service
public class JqhLongAccountInit implements TableDataInitializer {
	@Autowired
	private ParameterFacility facility;
	@PersistenceContext
	private EntityManager em;
	Date effectiveDate = new Date();
//	@Autowired
//	private CcParamComparatorService comparatorService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.engining.pg.support.init.TableDataInitializer#init()
	 */
	@Override
	@Transactional
	public void init() throws Exception {
		
		if (facility.getParameterMap(Account.class).size() > 0){
			facility.removeParameter(Account.class, ParameterFacility.UNIQUE_PARAM_KEY);
		}
		if (facility.getParameterMap(SubAcct.class).size() > 0){
			facility.removeParameter(SubAcct.class, ParameterFacility.UNIQUE_PARAM_KEY);
		}

		for (Account pc : getAccountList()) {
			facility.addParameter(pc.paramId, pc);
		}
		
		for (SubAcct pc : setupSubAcct()) {
			facility.addParameter(pc.subAcctId, pc);
		}

	}
//	public SubAcct jdugeNewSubAcct(SubAcct newOne) {
//		Map<String, SubAcct> subAcctsMap = facility.getParameterMap(SubAcct.class);
//
//		for (String key : subAcctsMap.keySet()) {
//			SubAcct oldOne = subAcctsMap.get(key);
//			// 只要找到一个相同的参数就返回
//			if (oldOne.compareTo(newOne) == 0) {
//				return oldOne;
//			}
//		}
//		
//		// 没有找到的情况，需要增加参数到系统
//		ParameterSeqence ParameterSeqence = em.find(ParameterSeqence.class, SubAcct.class.getCanonicalName());
//		ParameterSeqence.setParamSeq(ParameterSeqence.getParamSeq() + 1);
//		String parameter ="SUB" + StringUtils.leftPad(ParameterSeqence.getParamSeq().toString(), 10, "0");
//		newOne.subAcctId=parameter ;
//		facility.addParameter(parameter, newOne);
//		
//		return newOne;
//	}
	private List<Account> getAccountList() throws ParseException {
		List<Account> list = new ArrayList<Account>();
		// 加载子账户参数
		String paymId = "";
		String inteId = "";
		String loanId = "";
		String lbalId = "";
		String sfeeId = "";
		String pnitId = "";
		String otaxId = "";
		List<SubAcct> subAccts = setupSubAcct();
		for (SubAcct subAcct : subAccts) {
//			SubAcct newSub = jdugeNewSubAcct(subAcct);
			switch (subAcct.subAcctType) {
			case "PAYM":
				paymId = subAcct.subAcctId;
				break;
			case "INTE":
				inteId = subAcct.subAcctId;
				break;
			case "LOAN":
				loanId = subAcct.subAcctId;
				break;
			case "LBAL":
				lbalId = subAcct.subAcctId;
				break;
			case "SFEE":
				sfeeId = subAcct.subAcctId;
				break;
			case "PNIT":
				pnitId = subAcct.subAcctId;
				break;
			case "OTAX":
				otaxId = subAcct.subAcctId;
				break;
			default:
				break;
			}

		}

		// 设置主账户参数
		Account acct = new Account();
//		int a = UUID.randomUUID().hashCode();
//		acct.paramId = String.valueOf(Math.abs(a));
		acct.paramId = "JQH02";
		acct.description = "借趣花中长期产品";
		acct.isLockPaymentDay = false;
		acct.businessType = BusinessType.BL;
		acct.currencyCode = "156";
		acct.genAcctMethod = GenAcctMethod.N;
		acct.intFirstPeriodAdj = true;
		acct.carryType = TransformType.D;
		acct.pnitType = PnitType.A;
		acct.advanceType = "M";
		acct.isPnit = true;
		acct.effectiveDate = effectiveDate;
		acct.withHoldingInt = true;//是否代扣利息税
		acct.TaxCode = "";//税率参数码

		acct.pmtDueDays = 0;
		acct.blockcode = new HashMap<String, BlockCodeControl>();
		acct.subAcctParam = new HashMap<String, String>();
		acct.subAcctParam.put("PAYM", paymId);
		acct.subAcctParam.put("INTE", inteId);// 默认
		acct.subAcctParam.put("LOAN", loanId);
		acct.subAcctParam.put("LBAL", lbalId);
		acct.subAcctParam.put("SFEE", sfeeId);// 默认
		acct.subAcctParam.put("PNIT", pnitId);// 默认
		acct.subAcctParam.put("OTAX", otaxId);
		acct.intSettleStartMethod = CycleStartDay.P;
		acct.intUnit = Interval.D;
		acct.dIntSettleDay = 99;
		acct.intSettleFrequency = 3;
		acct.defaultAuthType = AuthType.O;
		acct.paymentMethod = PaymentMethod.MRF;
		acct.pmtGracePrd = 3;
		acct.delqDayInd = DelqDayInd.C;
		acct.delqTolInd = DelqTolInd.A;

		acct.delqTol = BigDecimal.ZERO;
		acct.delqTolPerc = BigDecimal.ZERO;
		acct.loanFeeCalcMethod = CalcMethod.R;
		acct.feeRate = BigDecimal.valueOf(0);
		acct.paymentHier = new HashMap<String, List<SubAcctType>>();
		// 科目规则
		acct.sysTxnCdMapping = new HashMap<SysTxnCd, String>();
		acct.sysTxnCdMapping.put(SysTxnCd.T003, "SC0001");
		acct.sysTxnCdMapping.put(SysTxnCd.T004, "SC0002");
		acct.sysTxnCdMapping.put(SysTxnCd.T007, "SC0005");
		acct.sysTxnCdMapping.put(SysTxnCd.T008, "SC0006");
		acct.sysTxnCdMapping.put(SysTxnCd.T011, "SC0009");
		acct.sysTxnCdMapping.put(SysTxnCd.T012, "SC0010");
		acct.sysTxnCdMapping.put(SysTxnCd.T015, "SC0013");
		acct.sysTxnCdMapping.put(SysTxnCd.T016, "SC0014");
		acct.sysTxnCdMapping.put(SysTxnCd.T021, "SC0019");
		acct.sysTxnCdMapping.put(SysTxnCd.T022, "SC0020");
		acct.sysTxnCdMapping.put(SysTxnCd.T023, "SC0021");
		acct.sysTxnCdMapping.put(SysTxnCd.T024, "SC0022");
		acct.sysTxnCdMapping.put(SysTxnCd.T027, "SC0025");
		acct.sysTxnCdMapping.put(SysTxnCd.T028, "SC0026");
		acct.sysTxnCdMapping.put(SysTxnCd.T029, "SC0027");
		acct.sysTxnCdMapping.put(SysTxnCd.T030, "SC0028");
		acct.sysTxnCdMapping.put(SysTxnCd.T031, "SC0029");
		acct.sysTxnCdMapping.put(SysTxnCd.T042, "SC0040");
		acct.sysTxnCdMapping.put(SysTxnCd.T043, "SC0041");
		acct.sysTxnCdMapping.put(SysTxnCd.T044, "SC0042");
		acct.sysTxnCdMapping.put(SysTxnCd.T045, "SC0043");
		acct.sysTxnCdMapping.put(SysTxnCd.T046, "SC0044");
		acct.sysTxnCdMapping.put(SysTxnCd.T047, "SC0045");
		acct.sysTxnCdMapping.put(SysTxnCd.T048, "SC0046");
		acct.sysTxnCdMapping.put(SysTxnCd.T049, "SC0047");
		acct.sysTxnCdMapping.put(SysTxnCd.T050, "SC0048");
		acct.sysTxnCdMapping.put(SysTxnCd.T051, "SC0049");
		acct.sysTxnCdMapping.put(SysTxnCd.T053, "SC0051");
		acct.sysTxnCdMapping.put(SysTxnCd.T055, "SC0053");
		acct.sysTxnCdMapping.put(SysTxnCd.T401, "SC0401");
		acct.sysTxnCdMapping.put(SysTxnCd.T403, "SC0403");
		acct.sysTxnCdMapping.put(SysTxnCd.T405, "SC0405");
		acct.sysTxnCdMapping.put(SysTxnCd.T410, "SC0410");
		acct.sysTxnCdMapping.put(SysTxnCd.T411, "SC0411");
		acct.sysTxnCdMapping.put(SysTxnCd.T412, "SC0412");
		acct.sysTxnCdMapping.put(SysTxnCd.T413, "SC0413");
		acct.sysTxnCdMapping.put(SysTxnCd.T417, "SC0417");
		acct.sysTxnCdMapping.put(SysTxnCd.T418, "SC0418");
		acct.sysTxnCdMapping.put(SysTxnCd.T419, "SC0419");
		acct.sysTxnCdMapping.put(SysTxnCd.T422, "SC0422");
		acct.sysTxnCdMapping.put(SysTxnCd.T423, "SC0423");
		acct.sysTxnCdMapping.put(SysTxnCd.T428, "SC0428");
		acct.sysTxnCdMapping.put(SysTxnCd.T429, "SC0429");
		acct.sysTxnCdMapping.put(SysTxnCd.T430, "SC0430");
		acct.sysTxnCdMapping.put(SysTxnCd.T431, "SC0431");
		acct.sysTxnCdMapping.put(SysTxnCd.T434, "SC0434");
		acct.sysTxnCdMapping.put(SysTxnCd.T435, "SC0435");
		acct.sysTxnCdMapping.put(SysTxnCd.T437, "SC0437");

		acct.feeAmount = BigDecimal.ZERO;
		acct.crMaxbalNoStmt = BigDecimal.ZERO;
		acct.stmtMinBal = BigDecimal.ZERO;
		acct.downpmtTolInd = DownpmtTolInd.A;
		acct.downpmtTol = BigDecimal.ZERO;

		// 内部帐规则
		// TODO
		List<String> internalAcctPostCodes = new ArrayList<String>();

		list.add(acct);
		
		return list;

	}

	private List<SubAcct> setupSubAcct() throws ParseException {
		List<SubAcct> list = new ArrayList<SubAcct>();

		SubAcct sa = new SubAcct();
		int s1 = UUID.randomUUID().hashCode();
		sa.subAcctId = String.valueOf(Math.abs(s1));
		sa.description = "贷款还款-子账户";
		sa.subAcctType = "PAYM";
		sa.balanceDirection = BalanceDirection.C;
		sa.balanceType = BalanceType.PAYM;
		sa.graceQualify = true;
		sa.intAccumFrom = IntAccumFrom.P;
		sa.interestPostCode = "SC0009"; // 利息入账交易代码
		sa.intTables = new ArrayList<String>();
		sa.intTables.add("Z0001");
		sa.intWaive = false;
		sa.minPaymentRates = new HashMap<Integer, BigDecimal>();
		sa.minPaymentRates.put(0, BigDecimal.ONE);
		sa.minPaymentRates.put(1, BigDecimal.ONE);
		sa.minPaymentRates.put(2, BigDecimal.ONE);
		sa.minPaymentRates.put(3, BigDecimal.ONE);
		sa.minPaymentRates.put(4, BigDecimal.ONE);
		sa.minPaymentRates.put(5, BigDecimal.ONE);
		sa.minPaymentRates.put(6, BigDecimal.ONE);
		sa.minPaymentRates.put(7, BigDecimal.ONE);
		sa.minPaymentRates.put(8, BigDecimal.ONE);
		sa.minPaymentRates.put(9, BigDecimal.ONE);
		sa.overlimitQualify = false;
		sa.penalizedInterestPastDuePostCode = "SC0013";// 罚息入账交易代码
		sa.penalizedInterestTable = "Z0001";
		sa.planPurgeDays = 30;
		sa.postponeDays = 0;
		sa.supportStmtLoan = false;
		sa.transMergeMethod = TransMergeMethod.A;
		sa.writeOffInd = true;
		sa.writeOffPostCode = "";// 核销转出交易代码
		sa.interestAccruedMethod = Interval.D;// 溢缴款帐号不计提
		sa.depositPostCode = "";// 冲销交易代码
		sa.effectiveDate =  effectiveDate;
		list.add(sa);

		sa = new SubAcct();
		int s2 = UUID.randomUUID().hashCode();
		sa.subAcctId = String.valueOf(Math.abs(s2));
		sa.description = "贷款利息-子账户";
		sa.subAcctType = "INTE";
		sa.balanceDirection = BalanceDirection.D;
		sa.balanceType = BalanceType.INTE;
		sa.graceQualify = true;
		sa.intAccumFrom = IntAccumFrom.P;
		sa.interestPostCode = "SC0009";
		sa.intTables = new ArrayList<String>();
		sa.intTables.add("Z0001");
		sa.intWaive = false;
		sa.minPaymentRates = new HashMap<Integer, BigDecimal>();
		sa.minPaymentRates.put(0, BigDecimal.ONE);
		sa.minPaymentRates.put(1, BigDecimal.ONE);
		sa.minPaymentRates.put(2, BigDecimal.ONE);
		sa.minPaymentRates.put(3, BigDecimal.ONE);
		sa.minPaymentRates.put(4, BigDecimal.ONE);
		sa.minPaymentRates.put(5, BigDecimal.ONE);
		sa.minPaymentRates.put(6, BigDecimal.ONE);
		sa.minPaymentRates.put(7, BigDecimal.ONE);
		sa.minPaymentRates.put(8, BigDecimal.ONE);
		sa.minPaymentRates.put(9, BigDecimal.ONE);
		sa.overlimitQualify = true;
		sa.penalizedInterestPastDuePostCode = "SC0013";
		sa.penalizedInterestTable = "Z0001";
		sa.planPurgeDays = 30;
		sa.postponeDays = 0;
		sa.supportStmtLoan = false;
		sa.transMergeMethod = TransMergeMethod.A;
		sa.writeOffInd = true;
		sa.writeOffPostCode = "";
		sa.interestAccruedMethod = Interval.D;
		sa.effectiveDate = effectiveDate;
		// 内部帐规则
		// TODO
		sa.depositPostCode = "";
		List<String> internalAcctPostCodes = new ArrayList<String>();

		// 余额结转规则
		sa.balTransferMap = new HashMap<String, BalTransferPostCode>();
		// 子账户余额结转
		// M3（含）以下
		BalTransferPostCode pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Normality;
		pc.ageCdAfterChanging = AgeGroupCd.Under4M3;
		pc.postCode = "SC0025";
		pc.internalAcctPostCodes = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// M4（含）以上
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Secondary;
		pc.ageCdAfterChanging = AgeGroupCd.Above4M3;
		pc.postCode = "SC0026";
		pc.postCode4IntPenaltyAccrual = "";
		pc.internalAcctPostCodes = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// 子账户利息计提结转
		// M3（含）以下
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Normality;
		pc.ageCdAfterChanging = AgeGroupCd.Under4M3;
		pc.postCode4IntAccrual = "SC0009";
		pc.internalAcctPostCodes4IntAccrual = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// M4（含）以上
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Secondary;
		pc.ageCdAfterChanging = AgeGroupCd.Above4M3;
		pc.postCode4IntAccrual = "SC0010";
		pc.internalAcctPostCodes4IntAccrual = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// 子账户罚息计提结转
		// M3（含）以下
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Normality;
		pc.ageCdAfterChanging = AgeGroupCd.Under4M3;
		pc.postCode4IntPenaltyAccrual = "SC0013";
		pc.internalAcctPostCodes4IntPenaltyAccrual = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// M4（含）以上
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Secondary;
		pc.ageCdAfterChanging = AgeGroupCd.Above4M3;
		pc.postCode4IntPenaltyAccrual = "SC0014";
		pc.internalAcctPostCodes4IntPenaltyAccrual = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		sa.subAcctCategory = SubAcctCategory.S;
		list.add(sa);

		sa = new SubAcct();
		int s3 = UUID.randomUUID().hashCode();
		sa.subAcctId = String.valueOf(Math.abs(s3));
		sa.description = "贷款-罚息子账户";
		sa.subAcctType = "PNIT";
		sa.balanceDirection = BalanceDirection.D;
		sa.balanceType = BalanceType.INTE;
		sa.graceQualify = true;
		sa.intAccumFrom = IntAccumFrom.P;
		sa.interestPostCode = "SC0009";
		sa.intTables = new ArrayList<String>();
		sa.intTables.add("Z0001");
		sa.intWaive = false;
		sa.effectiveDate = effectiveDate;
		sa.minPaymentRates = new HashMap<Integer, BigDecimal>();
		sa.minPaymentRates.put(0, BigDecimal.ONE);
		sa.minPaymentRates.put(1, BigDecimal.ONE);
		sa.minPaymentRates.put(2, BigDecimal.ONE);
		sa.minPaymentRates.put(3, BigDecimal.ONE);
		sa.minPaymentRates.put(4, BigDecimal.ONE);
		sa.minPaymentRates.put(5, BigDecimal.ONE);
		sa.minPaymentRates.put(6, BigDecimal.ONE);
		sa.minPaymentRates.put(7, BigDecimal.ONE);
		sa.minPaymentRates.put(8, BigDecimal.ONE);
		sa.minPaymentRates.put(9, BigDecimal.ONE);
		sa.overlimitQualify = false;
		sa.penalizedInterestPastDuePostCode = "SC0013";
		sa.penalizedInterestTable = "Z0001";
		sa.planPurgeDays = 30;
		sa.postponeDays = 0;
		sa.supportStmtLoan = false;
		sa.transMergeMethod = TransMergeMethod.A;
		sa.writeOffInd = true;
		sa.writeOffPostCode = "";
		sa.interestAccruedMethod = Interval.D;
		// 内部帐规则
		// TODO
		sa.depositPostCode = "";
		internalAcctPostCodes = new ArrayList<String>();
		sa.depositInternalPostCode = new HashMap<String, List<String>>();
		// 余额结转规则
		sa.balTransferMap = new HashMap<String, BalTransferPostCode>();
		// 子账户余额结转
		// M3（含）以下
//		pc = new BalTransferPostCode();
//		pc.ageCdB4Changing = AgeGroupCd.Normality;
//		pc.ageCdAfterChanging = AgeGroupCd.Down;
//		pc.postCode = "SC0025";
//		pc.internalAcctPostCodes = new ArrayList<String>();
//		sa.balTransferMap.put(pc.getKey(), pc);
		// M4（含）以上
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Secondary;
		pc.ageCdAfterChanging = AgeGroupCd.Above4M3;
		pc.postCode = "SC0053";
		pc.postCode4IntPenaltyAccrual = "";
		pc.internalAcctPostCodes = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// 子账户利息计提结转
		// M3（含）以下
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Normality;
		pc.ageCdAfterChanging = AgeGroupCd.Under4M3;
		pc.postCode4IntAccrual = "SC0009";
		pc.internalAcctPostCodes4IntAccrual = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// M4（含）以上
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Secondary;
		pc.ageCdAfterChanging = AgeGroupCd.Above4M3;
		pc.postCode4IntAccrual = "SC0010";
		pc.internalAcctPostCodes4IntAccrual = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// 子账户罚息计提结转
		// M3（含）以下
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Normality;
		pc.ageCdAfterChanging = AgeGroupCd.Under4M3;
		pc.postCode4IntPenaltyAccrual = "SC0013";
		pc.internalAcctPostCodes4IntPenaltyAccrual = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// M4（含）以上
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Secondary;
		pc.ageCdAfterChanging = AgeGroupCd.Above4M3;
		pc.postCode4IntPenaltyAccrual = "SC0014";
		pc.internalAcctPostCodes4IntPenaltyAccrual = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		sa.subAcctCategory = SubAcctCategory.S;
		list.add(sa);

		sa = new SubAcct();
		int s4 = UUID.randomUUID().hashCode();
		sa.subAcctId = String.valueOf(Math.abs(s4));
		sa.description = "贷款剩余本金-子账户";
		sa.subAcctType = "LOAN";
		sa.balanceDirection = BalanceDirection.D;
		sa.balanceType = BalanceType.LOAN;
		sa.graceQualify = false;
		sa.intAccumFrom = IntAccumFrom.P;
		sa.interestPostCode = "SC0009";
		sa.intTables = new ArrayList<String>();
		sa.intTables.add("Z0001");
		sa.intWaive = false;
		sa.minPaymentRates = new HashMap<Integer, BigDecimal>();
		sa.minPaymentRates.put(0, BigDecimal.ONE);
		sa.minPaymentRates.put(1, BigDecimal.ONE);
		sa.minPaymentRates.put(2, BigDecimal.ONE);
		sa.minPaymentRates.put(3, BigDecimal.ONE);
		sa.minPaymentRates.put(4, BigDecimal.ONE);
		sa.minPaymentRates.put(5, BigDecimal.ONE);
		sa.minPaymentRates.put(6, BigDecimal.ONE);
		sa.minPaymentRates.put(7, BigDecimal.ONE);
		sa.minPaymentRates.put(8, BigDecimal.ONE);
		sa.minPaymentRates.put(9, BigDecimal.ONE);
		sa.overlimitQualify = true;
		sa.effectiveDate = effectiveDate;
		sa.penalizedInterestPastDuePostCode = "SC0013";
		sa.penalizedInterestTable = "Z0001";
		sa.planPurgeDays = 30;
		sa.postponeDays = 0;
		sa.supportStmtLoan = false;
		sa.transMergeMethod = TransMergeMethod.N;
		sa.writeOffInd = false;
		sa.writeOffPostCode = "";
		sa.interestAccruedMethod = Interval.D;
		// 内部帐规则
		// TODO
		sa.depositPostCode = "";
		sa.depositInternalPostCode = new HashMap<String, List<String>>();
		internalAcctPostCodes = new ArrayList<String>();
		// 不需要余额结转规则
		sa.balTransferMap = new HashMap<String, BalTransferPostCode>();
		sa.subAcctCategory = SubAcctCategory.S;
		list.add(sa);

		sa = new SubAcct();
		int s5 = UUID.randomUUID().hashCode();
		sa.subAcctId = String.valueOf(Math.abs(s5));
		sa.description = "贷款当期应还本金-子账户";
		sa.subAcctType = "LBAL";
		sa.balanceDirection = BalanceDirection.D;
		sa.balanceType = BalanceType.TOPY;
		sa.graceQualify = true;
		sa.intAccumFrom = IntAccumFrom.P;
		sa.interestPostCode = "SC0009";
		sa.intTables = new ArrayList<String>();
		sa.intTables.add("Z0001");
		sa.intWaive = false;
		sa.effectiveDate = effectiveDate;
		sa.minPaymentRates = new HashMap<Integer, BigDecimal>();
		sa.minPaymentRates.put(0, BigDecimal.ONE);
		sa.minPaymentRates.put(1, BigDecimal.ONE);
		sa.minPaymentRates.put(2, BigDecimal.ONE);
		sa.minPaymentRates.put(3, BigDecimal.ONE);
		sa.minPaymentRates.put(4, BigDecimal.ONE);
		sa.minPaymentRates.put(5, BigDecimal.ONE);
		sa.minPaymentRates.put(6, BigDecimal.ONE);
		sa.minPaymentRates.put(7, BigDecimal.ONE);
		sa.minPaymentRates.put(8, BigDecimal.ONE);
		sa.minPaymentRates.put(9, BigDecimal.ONE);
		sa.overlimitQualify = true;
		sa.penalizedInterestPastDuePostCode = "SC0013";
		sa.penalizedInterestTable = "Z0001";
		sa.planPurgeDays = 30;
		sa.postponeDays = 0;
		sa.supportStmtLoan = false;
		sa.transMergeMethod = TransMergeMethod.N;
		sa.writeOffInd = true;
		sa.writeOffPostCode = "";
		sa.interestAccruedMethod = Interval.D;
		// 内部帐规则
		// TODO
		sa.depositPostCode = "";
		sa.depositInternalPostCode = new HashMap<String, List<String>>();
		internalAcctPostCodes = new ArrayList<String>();

		// 余额结转规则
		sa.balTransferMap = new HashMap<String, BalTransferPostCode>();
		// 正常-》关注
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Normality;
		pc.ageCdAfterChanging = AgeGroupCd.Attention;
		pc.postCode = "SC0019";
		pc.internalAcctPostCodes = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// 关注-》次级
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Attention;
		pc.ageCdAfterChanging = AgeGroupCd.Secondary;
		pc.postCode = "SC0020";
		pc.internalAcctPostCodes = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// 次级-》可疑
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Secondary;
		pc.ageCdAfterChanging = AgeGroupCd.Suspicious;
		pc.postCode = "SC0021";
		pc.internalAcctPostCodes = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		// 可疑-》损失
		pc = new BalTransferPostCode();
		pc.ageCdB4Changing = AgeGroupCd.Suspicious;
		pc.ageCdAfterChanging = AgeGroupCd.Loss;
		pc.postCode = "SC0022";
		pc.internalAcctPostCodes = new ArrayList<String>();
		sa.balTransferMap.put(pc.getKey(), pc);
		sa.subAcctCategory = SubAcctCategory.S;
		list.add(sa);

		sa = new SubAcct();
		int s6 = UUID.randomUUID().hashCode();
		sa.subAcctId = String.valueOf(Math.abs(s6));
		sa.description = "贷款费用-子账户";
		sa.subAcctType = "SFEE";
		sa.balanceDirection = BalanceDirection.D;
		sa.balanceType = BalanceType.SFEE;
		sa.graceQualify = true;
		sa.intAccumFrom = IntAccumFrom.P;
		sa.interestPostCode = "SC0009";
		sa.intTables = new ArrayList<String>();
		sa.intTables.add("Z0001");
		sa.intWaive = false;
		sa.effectiveDate = effectiveDate;
		sa.minPaymentRates = new HashMap<Integer, BigDecimal>();
		sa.minPaymentRates.put(0, BigDecimal.ONE);
		sa.minPaymentRates.put(1, BigDecimal.ONE);
		sa.minPaymentRates.put(2, BigDecimal.ONE);
		sa.minPaymentRates.put(3, BigDecimal.ONE);
		sa.minPaymentRates.put(4, BigDecimal.ONE);
		sa.minPaymentRates.put(5, BigDecimal.ONE);
		sa.minPaymentRates.put(6, BigDecimal.ONE);
		sa.minPaymentRates.put(7, BigDecimal.ONE);
		sa.minPaymentRates.put(8, BigDecimal.ONE);
		sa.minPaymentRates.put(9, BigDecimal.ONE);
		sa.overlimitQualify = true;
		sa.penalizedInterestPastDuePostCode = "SC0013";
		sa.penalizedInterestTable = "Z0001";
		sa.planPurgeDays = 30;
		sa.postponeDays = 0;
		sa.supportStmtLoan = false;
		sa.transMergeMethod = TransMergeMethod.A;
		sa.writeOffInd = true;
		sa.writeOffPostCode = "";
		sa.interestAccruedMethod = Interval.D;
		sa.depositPostCode = "";
		sa.subAcctCategory = SubAcctCategory.S;
		list.add(sa);

		sa = new SubAcct();
		int s7 = UUID.randomUUID().hashCode();
		sa.subAcctId = String.valueOf(Math.abs(s7));
		sa.description = "贷款销项税-子账户";
		sa.subAcctType = "OTAX";
		sa.balanceDirection = BalanceDirection.D;
		sa.balanceType = BalanceType.SFEE;
		sa.graceQualify = true;
		sa.intAccumFrom = IntAccumFrom.P;
		sa.interestPostCode = "SC0007";
		sa.intTables = new ArrayList<String>();
		sa.intTables.add("Z0001");
		sa.intWaive = false;
		sa.effectiveDate = effectiveDate;
		sa.minPaymentRates = new HashMap<Integer, BigDecimal>();
		sa.minPaymentRates.put(0, BigDecimal.ONE);
		sa.minPaymentRates.put(1, BigDecimal.ONE);
		sa.minPaymentRates.put(2, BigDecimal.ONE);
		sa.minPaymentRates.put(3, BigDecimal.ONE);
		sa.minPaymentRates.put(4, BigDecimal.ONE);
		sa.minPaymentRates.put(5, BigDecimal.ONE);
		sa.minPaymentRates.put(6, BigDecimal.ONE);
		sa.minPaymentRates.put(7, BigDecimal.ONE);
		sa.minPaymentRates.put(8, BigDecimal.ONE);
		sa.minPaymentRates.put(9, BigDecimal.ONE);
		sa.overlimitQualify = true;
		sa.penalizedInterestPastDuePostCode = "SC0011";
		sa.penalizedInterestTable = "Z0001";
		sa.planPurgeDays = 30;
		sa.postponeDays = 0;
		sa.supportStmtLoan = false;
		sa.transMergeMethod = TransMergeMethod.A;
		sa.writeOffInd = true;
		sa.writeOffPostCode = "";
		sa.interestAccruedMethod = Interval.D;
		sa.depositPostCode = "";
		sa.subAcctCategory = SubAcctCategory.S;
		list.add(sa);
		
		return list;
	}

}
