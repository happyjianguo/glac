package net.engining.sccc.biz.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.gm.infrastructure.enums.BusinessType;
import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;
import net.engining.pcx.cc.infrastructure.shared.enums.PostTxnTypeDef;
import net.engining.pcx.cc.infrastructure.shared.enums.PostingFlag;
import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.CactTxnPost;
import net.engining.pcx.cc.param.model.Account;
import net.engining.pcx.cc.param.model.PostCode;
import net.engining.pcx.cc.param.model.enums.Deadline;
import net.engining.pcx.cc.param.model.enums.SysTxnCd;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.pcx.cc.process.service.support.refactor.DirectAccountingEvent;
import net.engining.pg.parameter.OrganizationContextHolder;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.biz.bean.PostCodeSeq;
import net.engining.sccc.biz.bean.TxnCdPostCode;
import net.engining.sccc.biz.bean.TxnRule;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.bean.batchBean.RequestData;
import net.engining.sccc.biz.bean.batchBean.SubAcctData;
import net.engining.sccc.biz.enums.AccountTradingDef;
import net.engining.sccc.biz.enums.SubAcctTypeDef;
import net.engining.sccc.biz.enums.SysInternalAcctionCdDef;
import net.engining.sccc.biz.service.params.ProductAccount;

/**
 * 获取入账交易码，保存当日入账交易表 、当日总账交易流水表
 * 
 * @author xiachuanhu
 *
 */
@Service
public class ComputingPostCodeService {
	private static final Logger logger = LoggerFactory.getLogger(ComputingPostCodeService.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Provider7x24 provider7x24;

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private ParameterFacility parameterFacility;

//	@Transactional
//	public PostCodeSeq getPostCd(RequestData data, SysTxnCd txnCd, EveryDayAccountingBean headerData, Date clearDate,
//			PostTxnTypeDef def, BigDecimal amt) {
//		PostCodeSeq postCodeSeq = new PostCodeSeq();
//
//		Deadline line = null;
//		if (headerData.getRequestData().getTotalPeriod() <= 12) {
//			line = Deadline.S;
//		} else {
//			line = Deadline.M;
//		}
//		ProductAccount init = parameterFacility.getParameter(ProductAccount.class,
//				ProductAccount.key(headerData.getRequestData().getProductId(), line));
//		String acctParamId = init.getAccountId();
//		Account account = parameterFacility.getParameter(Account.class, acctParamId);
//		Map<SysTxnCd, String> txnCdMap = account.sysTxnCdMapping;
//		String postCode = txnCdMap.get(txnCd);
//		postCodeSeq.setPostCode(postCode);
//		PostCode pc = parameterFacility.getParameter(PostCode.class, postCode);
//		// 插入当日入账交易表 CactTxnPost
//		CactTxnPost cactTxnPost = new CactTxnPost();
//		cactTxnPost.setOrg(OrganizationContextHolder.getCurrentOrganizationId());
//		cactTxnPost.setAcctSeq(data.getAcctSeq());
//		cactTxnPost.setBusinessType(BusinessType.BL);
//		cactTxnPost.setTxnDate(headerData.getTimestamp());// 交易日期
//		cactTxnPost.setPostTxnType(def);
//		cactTxnPost.setPostCode(txnCd.toString());
//		cactTxnPost.setTxnAmt(amt);
//		cactTxnPost.setPostAmt(amt);
//		cactTxnPost.setPostDate(provider7x24.getCurrentDate().toDate());// 入账日期
//		cactTxnPost.setPostCurrCd(data.getCurrency());
//		cactTxnPost.setPostingFlag(PostingFlag.F00);
//		cactTxnPost.setAgeCdB4Posting(data.getAgeCdBefore().toString());
//		cactTxnPost.setRequestData(JSON.toJSONString(headerData));// 请求数据
//		cactTxnPost.setTxnDetailType(TxnDetailType.P);
//		cactTxnPost.setTxnDetailSeq(headerData.getTxnSerialNo());
//		cactTxnPost.setClearDate(clearDate);// 清算日期
//		cactTxnPost.setCheckAccountStatus(CheckAccountStatusDef.RIGHT);
//		cactTxnPost.setTxnDesc(pc.description);
//		cactTxnPost.setBizDate(provider7x24.getCurrentDate().toDate());// 业务日期
//		cactTxnPost.setLastUpdateDate(new Date());
//		cactTxnPost.fillDefaultValues();
//		em.persist(cactTxnPost);
//		// 触发当日总账交易流水表event
//		DirectAccountingEvent event = new DirectAccountingEvent(this);
//		event.setAcctSeq(data.getAcctSeq());
//		event.setTrdate(headerData.getTimestamp());
//		event.setPostCode(postCode);
//		event.setPostAmount(amt);
//		event.setPostDate(provider7x24.getCurrentDate().toDate());
//		event.setClearDate(clearDate);
//		event.setTxnDetailSeq(cactTxnPost.getTxnSeq().toString());
//		event.setTxnDetailType(TxnDetailType.P);
//		ctx.publishEvent(event);
//		logger.info("入当日总账交易流水表结束！");
//		postCodeSeq.setTxnSeq(cactTxnPost.getTxnSeq().toString());
//		return postCodeSeq;
//	}

	@Transactional
	public PostCodeSeq getPostCode(RequestData data, EveryDayAccountingBean headerData, Date clearDate,
			PostTxnTypeDef def, BigDecimal amt, AccountTradingDef accountTrading,
			SysInternalAcctionCdDef sysInternalAcctionCdDef, Integer ageCdBefore, Boolean isUnion) {

		PostCodeSeq postCodeSeq = new PostCodeSeq();

		TxnCdPostCode tp = getTxnCdPostCode(headerData, accountTrading, sysInternalAcctionCdDef, ageCdBefore, isUnion);

		// ProductAccount init =
		// parameterFacility.getParameter(ProductAccount.class,
		// ProductAccount.key(headerData.getRequestData().getProductId(),
		// line));
		// String acctParamId = init.getAccountId();
		// Account account = parameterFacility.getParameter(Account.class,
		// acctParamId);
		// Map<SysTxnCd, String> txnCdMap = account.sysTxnCdMapping;
		String postCode = tp.postCode;
		postCodeSeq.setPostCode(postCode);
		PostCode pc = parameterFacility.getParameter(PostCode.class, postCode);
		// 插入当日入账交易表 CactTxnPost
		CactTxnPost cactTxnPost = new CactTxnPost();
		cactTxnPost.setOrg(OrganizationContextHolder.getCurrentOrganizationId());
		cactTxnPost.setAcctSeq(data.getAcctSeq());
		cactTxnPost.setBusinessType(BusinessType.BL);
		cactTxnPost.setTxnDate(headerData.getTimestamp());// 交易日期
		cactTxnPost.setPostTxnType(def);
		cactTxnPost.setPostCode(tp.txnCd.name());
		cactTxnPost.setTxnAmt(amt);
		cactTxnPost.setPostAmt(amt);
		cactTxnPost.setPostDate(provider7x24.getCurrentDate().toDate());// 入账日期
		cactTxnPost.setPostCurrCd(data.getCurrency());
		cactTxnPost.setPostingFlag(PostingFlag.F00);
		cactTxnPost.setAgeCdB4Posting(data.getAgeCdBefore().toString());
		cactTxnPost.setRequestData(JSON.toJSONString(headerData));// 请求数据
		cactTxnPost.setTxnDetailType(TxnDetailType.P);
		cactTxnPost.setTxnDetailSeq(headerData.getTxnSerialNo());
		cactTxnPost.setClearDate(clearDate);// 清算日期
		cactTxnPost.setCheckAccountStatus(CheckAccountStatusDef.RIGHT);
		cactTxnPost.setTxnDesc(pc.description);
		cactTxnPost.setBizDate(provider7x24.getCurrentDate().toDate());// 业务日期
		cactTxnPost.setLastUpdateDate(new Date());
		cactTxnPost.fillDefaultValues();
		em.persist(cactTxnPost);
		// 触发当日总账交易流水表event
		DirectAccountingEvent event = new DirectAccountingEvent(this);
		event.setAcctSeq(data.getAcctSeq());
		event.setTrdate(headerData.getTimestamp());
		event.setPostCode(postCode);
		event.setPostAmount(amt);
		event.setPostDate(provider7x24.getCurrentDate().toDate());
		event.setClearDate(clearDate);
		event.setTxnDetailSeq(cactTxnPost.getTxnSeq().toString());
		event.setTxnDetailType(TxnDetailType.P);
		ctx.publishEvent(event);
		logger.info("入当日总账交易流水表结束！");
		postCodeSeq.setTxnSeq(cactTxnPost.getTxnSeq().toString());
		return postCodeSeq;
	}
	
	public TxnCdPostCode getTxnCdPostCode(EveryDayAccountingBean headerData,AccountTradingDef accountTrading,
			SysInternalAcctionCdDef sysInternalAcctionCdDef, Integer ageCdBefore, Boolean isUnion){
		Deadline line = null;
		if (headerData.getRequestData().getTotalPeriod() != null && headerData.getRequestData().getTotalPeriod() <= 12) {
			line = Deadline.S;
		} else if (headerData.getRequestData().getTotalPeriod() != null && headerData.getRequestData().getTotalPeriod() > 12) {
			line = Deadline.M;
		}

		if (AccountTradingDef.S.equals(accountTrading)) {
			if (SysInternalAcctionCdDef.S031.equals(sysInternalAcctionCdDef)
					|| SysInternalAcctionCdDef.S032.equals(sysInternalAcctionCdDef)) {
				line = Deadline.M;
				isUnion = null;
			}
			if (SysInternalAcctionCdDef.S033.equals(sysInternalAcctionCdDef)) {
				line = Deadline.S;
				isUnion = null;
			}
		}

		AgeGroupCd ageGroup1 = null;
		AgeGroupCd ageGroup2 = null;
		if (ageCdBefore == null) {
			ageGroup1 = null;
		} else if (ageCdBefore == 0) {
			ageGroup1 = AgeGroupCd.Normality;
		} else if (ageCdBefore >= 1 && ageCdBefore <= 3) {
			ageGroup1 = AgeGroupCd.Attention;
		} else if (ageCdBefore == 4) {
			ageGroup1 = AgeGroupCd.Secondary;
		} else if (ageCdBefore == 5 || ageCdBefore == 6) {
			ageGroup1 = AgeGroupCd.Suspicious;
		} else if (ageCdBefore >= 7) {
			ageGroup1 = AgeGroupCd.Loss;
		}
		if (ageCdBefore == null) {
			ageGroup1 = null;
		} else if (ageCdBefore <= 3) {
			ageGroup2 = AgeGroupCd.Under4M3;
		} else {
			ageGroup2 = AgeGroupCd.Above4M3;
		}

		Map<String,TxnCdPostCode> txnPostMap = parameterFacility.getParameterMap(TxnCdPostCode.class);
		TxnCdPostCode tp = txnPostMap.get(TxnCdPostCode.key(new TxnRule(accountTrading, line, ageGroup1, isUnion)));
		if(tp == null){
			tp = txnPostMap.get(TxnCdPostCode.key(new TxnRule(accountTrading, line, ageGroup2, isUnion)));
		}
		
		return tp;
	}

	public PostCodeSeq validateTrans(AccountTradingDef accountTrading, RequestData data,
			EveryDayAccountingBean headerData, Date clearDate, PostTxnTypeDef def, 
			SysInternalAcctionCdDef sysInternalAcctionCdDef, Integer ageCdBefore, Boolean isUnion)
			throws ValidationException {
		// 取交易前List
		List<SubAcctData> listBefore = data.getBeforeSubAcctData();
		// 取交易后List
		List<SubAcctData> listAfter = data.getSubAcctData();
		BigDecimal beforeLoanCurrBal = BigDecimal.ZERO;// 交易前LOAN当前余额
		BigDecimal afterLoanCurrBal = BigDecimal.ZERO;// 交易后LOAN当前余额
		BigDecimal inteAmt = BigDecimal.ZERO;// 利息
		BigDecimal pintAmt = BigDecimal.ZERO;// 罚息
		for (SubAcctData list1 : listBefore) {
			if (list1.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeLoanCurrBal = list1.getCurrBal();
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
		if (data.getAgeCd() == 4) {
			for (SubAcctData list1 : listBefore) {
				if (list1.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
					inteAmt = inteAmt.add(list1.getCurrBal() == null ? BigDecimal.ZERO : list1.getCurrBal());
				}
				if (list1.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
					pintAmt = pintAmt.add(list1.getCurrBal() == null ? BigDecimal.ZERO : list1.getCurrBal());
				}
			}
		}
		
		
		if (AccountTradingDef.LBAL.equals(accountTrading)) {
			return this.getPostCode(data, headerData, clearDate, def, lbalAmt,accountTrading,null,ageCdBefore,null);
		} else if (AccountTradingDef.INTE.equals(accountTrading)) {
			return this.getPostCode(data, headerData, clearDate, def, inteAmt,accountTrading,null,ageCdBefore,null);
		} else {
			return this.getPostCode(data, headerData, clearDate, def, pintAmt,accountTrading,null,ageCdBefore,null);
		}

	}
}
