package net.engining.sccc.biz.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.gm.infrastructure.enums.BusinessType;
import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;
import net.engining.pcx.cc.infrastructure.shared.enums.InOutFlagDef;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.param.model.TxnSubjectMapping;
import net.engining.pcx.cc.param.model.TxnSubjectParam;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;
import net.engining.pcx.cc.param.model.enums.SubjectAmtType;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.pcx.cc.process.service.support.refactor.DirectAccountSplitEvent;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.biz.bean.AssistAccountData;
import net.engining.sccc.biz.bean.PostCodeSeq;
import net.engining.sccc.biz.bean.batchBean.RequestData;
import net.engining.sccc.biz.service.params.IntTaxRate;

/**
 * 保存会计分录拆分交易流水表
 * 
 * @author xiachuanhu
 *
 */
@Service
public class SplitVolService {
	private static final Logger logger = LoggerFactory.getLogger(SplitVolService.class);
	@Autowired
	private ApplicationContext ctx;
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private ParameterFacility parameterFacility;

	private static String subject1 = "122102";
	private static String subject2 = "7006";
	
	@Autowired
	private Provider7x24 provider7x24;

	/**
	 * 分录拆分
	 * 
	 * @param postCode
	 * @param map
	 * @param clearDate
	 * @param bizDate
	 * @param assistData
	 */
	public void split(PostCodeSeq postCodeSeq, Map<SubjectAmtType, BigDecimal> map, Date clearDate, LocalDate bizDate,
			RequestData data, AgeGroupCd ageGroup) {
		// 辅助核算项处理
		String assistData = null;
		bizDate = provider7x24.getCurrentDate();
		if (data.getBizData().get(0).getIsAssisting()) {
			List<AssistAccountData> list = new ArrayList<AssistAccountData>();
			AssistAccountData assisType = new AssistAccountData();
			assisType.setKey(AssistAccountingType.valueOf(data.getBizData().get(0).getKeyId()));
			assisType.setAssistAccountingDesc(data.getBizData().get(0).getValue());
			list.add(assisType);
			assistData = JSON.toJSONString(list);
		}
		// 获取当日总账交易流水表的流水号
		QApGlTxn qapGltxn = QApGlTxn.apGlTxn;
		String gltSeq = new JPAQueryFactory(em).select(qapGltxn.gltSeq).from(qapGltxn)
				.where(qapGltxn.postCode.eq(postCodeSeq.getPostCode()),
						qapGltxn.txnDetailSeq.eq(postCodeSeq.getTxnSeq()))
				.fetchOne();
		// 账龄处理
		AgeGroupCd ageGroup2 = null;
		if (ageGroup == null) {
			Integer age = data.getAgeCd();
			if (age == null) {
				ageGroup = AgeGroupCd.Normality;
			} else {
				if (age == 0) {
					ageGroup = AgeGroupCd.Normality;
				} else if (age >= 1 && age <= 3) {
					ageGroup = AgeGroupCd.Attention;
				} else if (age == 4) {
					ageGroup = AgeGroupCd.Secondary;
				} else if (age >= 5 && age <= 6) {
					ageGroup = AgeGroupCd.Suspicious;
				} else {
					ageGroup = AgeGroupCd.Loss;
				}
				if (age <= 3) {
					ageGroup2 = AgeGroupCd.Under4M3;
				} else {
					ageGroup2 = AgeGroupCd.Above4M3;
				}
			}

		}
		// 获取会计分录套型
		TxnSubjectParam txnSubjectParam = parameterFacility.getParameter(TxnSubjectParam.class,
				TxnSubjectParam.key(postCodeSeq.getPostCode(), ageGroup, BusinessType.BL));
		if (txnSubjectParam == null) {
			txnSubjectParam = parameterFacility.getParameter(TxnSubjectParam.class, 
					TxnSubjectParam.key(postCodeSeq.getPostCode(), ageGroup2, BusinessType.BL));
		}
		if (txnSubjectParam == null) {
			// 没配参数则不处理
			throw new ErrorMessageException(ErrorCode.Null, "会计分录套型参数没有配置！");
		}
		String DbsubjectCd = null;// 借方科目
		String CrsubjectCd = null;// 贷方科目
		InOutFlagDef inoutFlag = null;// 表内表外标志
		TxnDirection txnDirection = null;// 借贷标志
		RedBlueInd redBlueInd = null;// 红蓝字标识
		int volSeq = 0;// 分录序号
		BigDecimal postAmt = BigDecimal.ZERO;
		for (TxnSubjectMapping txnSubjectMapping : txnSubjectParam.entryList) {
			volSeq++;
			postAmt = map.get(txnSubjectMapping.amtType).abs();
			if (txnSubjectMapping.ntDbRedFlag == RedBlueInd.R && txnSubjectMapping.ntCrRedFlag == RedBlueInd.R) {
				postAmt = postAmt.negate();
			}

			if (txnSubjectMapping.ntDbSubjectCd != null) {
				// 借方
				CrsubjectCd = null;
				inoutFlag = InOutFlagDef.A;
				txnDirection = TxnDirection.D;
				DbsubjectCd = txnSubjectMapping.ntDbSubjectCd;// 借方科目
				redBlueInd = txnSubjectMapping.ntDbRedFlag;
				// 触发会计分录拆分交易流水表event 直接记账
				splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postAmt, volSeq, txnDirection,
						assistData, PostGlInd.Normal, gltSeq, redBlueInd);

			}
			if (txnSubjectMapping.ntCrSubjectCd != null) {
				// 贷方
				DbsubjectCd = null;
				inoutFlag = InOutFlagDef.A;
				txnDirection = TxnDirection.C;
				redBlueInd = txnSubjectMapping.ntCrRedFlag;
				CrsubjectCd = txnSubjectMapping.ntCrSubjectCd;// 贷方科目
				// 触发会计分录拆分交易流水表event 直接记账
				splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postAmt, volSeq, txnDirection,
						assistData, PostGlInd.Normal, gltSeq, redBlueInd);

			}
			if (txnSubjectMapping.ntDbSubjectCdOs != null) {
				// 收
				CrsubjectCd = null;
				inoutFlag = InOutFlagDef.B;
				txnDirection = TxnDirection.D;
				redBlueInd = txnSubjectMapping.ntDbRedFlagOs;
				DbsubjectCd = txnSubjectMapping.ntDbSubjectCdOs;
				// 触发会计分录拆分交易流水表event 直接记账
				splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postAmt.abs(), volSeq, txnDirection,
						assistData, PostGlInd.Normal, gltSeq, redBlueInd);

			}
			if (txnSubjectMapping.ntCrSubjectCdOs != null) {
				// 付
				DbsubjectCd = null;
				inoutFlag = InOutFlagDef.B;
				txnDirection = TxnDirection.C;
				redBlueInd = txnSubjectMapping.ntCrRedFlagOs;
				CrsubjectCd = txnSubjectMapping.ntCrSubjectCdOs;

				// 触发会计分录拆分交易流水表event 直接记账
				splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postAmt.abs(), volSeq, txnDirection,
						assistData, PostGlInd.Normal, gltSeq, redBlueInd);

			}
		}
	}

	/**
	 * 针对中银，拿去花分录拆分
	 * 
	 * @param postCode
	 * @param postAmt
	 * @param clearDate
	 * @param bizDate
	 * @param assistData
	 */
	public void splitZyNqh(String postCode, BigDecimal postAmt, Date clearDate, LocalDate bizDate, String assistData) {

		TxnSubjectParam txnSubjectParam = parameterFacility.getParameter(TxnSubjectParam.class,
				TxnSubjectParam.key(postCode, AgeGroupCd.Normality, BusinessType.BL));
		if (txnSubjectParam == null) {
			// 没配参数则不处理
			throw new ErrorMessageException(ErrorCode.Null, "会计分录套型参数没有配置！");
		}
		// 取价税分离参数
		IntTaxRate intTaxRate = parameterFacility.getParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
		// 获取当日总账交易流水表的流水号
		QApGlTxn qapGltxn = QApGlTxn.apGlTxn;
		String gltSeq = new JPAQueryFactory(em).select(qapGltxn.gltSeq).from(qapGltxn)
				.where(qapGltxn.postCode.eq(postCode), qapGltxn.bizDate.eq(bizDate.toDate())).fetchOne();

		String DbsubjectCd = null;// 借方科目
		String CrsubjectCd = null;// 贷方科目
		InOutFlagDef inoutFlag = null;// 表内表外标志
		TxnDirection txnDirection = null;// 借贷标志
		RedBlueInd redBlueInd = null;// 红蓝字标识
		int volSeq = 0;// 分录序号
		BigDecimal postTax = BigDecimal.ZERO;
		for (TxnSubjectMapping txnSubjectMapping : txnSubjectParam.entryList) {
			if (txnSubjectMapping.amtType != null && txnSubjectMapping.amtType.equals(SubjectAmtType.OTAX)) {
				inoutFlag = InOutFlagDef.A;
				postTax = postAmt.multiply(intTaxRate.taxRt);
				if (txnSubjectMapping.ntDbRedFlag == RedBlueInd.R && txnSubjectMapping.ntCrRedFlag == RedBlueInd.R) {
					postTax = postTax.negate();
				}
				if (txnSubjectMapping.ntDbSubjectCd != null) {
					// 借方
					CrsubjectCd = null;
					redBlueInd = txnSubjectMapping.ntDbRedFlag;
					txnDirection = TxnDirection.D;
					DbsubjectCd = txnSubjectMapping.ntDbSubjectCd;// 借方科目
					redBlueInd = txnSubjectMapping.ntDbRedFlag;
					assistData = assistWay(CrsubjectCd, DbsubjectCd, assistData);
					// 触发会计分录拆分交易流水表event 直接记账
					splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postTax, volSeq, txnDirection,
							assistData, PostGlInd.Normal, gltSeq, redBlueInd);
				}
				if (txnSubjectMapping.ntCrSubjectCd != null) {
					// 贷方
					DbsubjectCd = null;
					txnDirection = TxnDirection.C;
					CrsubjectCd = txnSubjectMapping.ntCrSubjectCd;// 贷方科目
					redBlueInd = txnSubjectMapping.ntCrRedFlag;
					assistData = assistWay(CrsubjectCd, DbsubjectCd, assistData);
					// 触发会计分录拆分交易流水表event 直接记账
					splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postTax, volSeq, txnDirection,
							assistData, PostGlInd.Normal, gltSeq, redBlueInd);
				}
				if (txnSubjectMapping.ntDbSubjectCdOs != null) {
					// 收
					CrsubjectCd = null;
					inoutFlag = InOutFlagDef.B;
					txnDirection = TxnDirection.D;
					DbsubjectCd = txnSubjectMapping.ntDbSubjectCdOs;
					redBlueInd = txnSubjectMapping.ntDbRedFlagOs;
					assistData = assistWay(CrsubjectCd, DbsubjectCd, assistData);
					// 触发会计分录拆分交易流水表event 直接记账
					splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postTax.abs(), volSeq,
							txnDirection, assistData, PostGlInd.Normal, gltSeq, redBlueInd);

				}
				if (txnSubjectMapping.ntCrSubjectCdOs != null) {
					// 付
					DbsubjectCd = null;
					inoutFlag = InOutFlagDef.B;
					txnDirection = TxnDirection.C;
					redBlueInd = txnSubjectMapping.ntCrRedFlagOs;
					CrsubjectCd = txnSubjectMapping.ntCrSubjectCdOs;
					assistData = assistWay(CrsubjectCd, DbsubjectCd, assistData);
					// 触发会计分录拆分交易流水表event 直接记账
					splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postTax.abs(), volSeq,
							txnDirection, assistData, PostGlInd.Normal, gltSeq, redBlueInd);

				}

			}
		}
		for (TxnSubjectMapping txnSubjectMapping : txnSubjectParam.entryList) {
			postAmt = postAmt.subtract(postTax.abs());
			if (txnSubjectMapping.amtType != null && txnSubjectMapping.amtType.equals(SubjectAmtType.OTAX)) {
				continue;
			}
			if (txnSubjectMapping.ntDbRedFlag == RedBlueInd.R && txnSubjectMapping.ntCrRedFlag == RedBlueInd.R) {
				postAmt = postAmt.negate();
			}
			if (txnSubjectMapping.ntDbSubjectCd != null) {
				// 借方
				CrsubjectCd = null;
				inoutFlag = InOutFlagDef.A;
				txnDirection = TxnDirection.D;
				DbsubjectCd = txnSubjectMapping.ntDbSubjectCd;// 借方科目
				redBlueInd = txnSubjectMapping.ntDbRedFlag;
				assistData = assistWay(CrsubjectCd, DbsubjectCd, assistData);
				// 触发会计分录拆分交易流水表event 直接记账
				splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postAmt, volSeq, txnDirection,
						assistData, PostGlInd.Normal, gltSeq, redBlueInd);

			}
			if (txnSubjectMapping.ntCrSubjectCd != null) {
				// 贷方
				DbsubjectCd = null;
				inoutFlag = InOutFlagDef.A;
				txnDirection = TxnDirection.C;
				CrsubjectCd = txnSubjectMapping.ntCrSubjectCd;// 贷方科目
				redBlueInd = txnSubjectMapping.ntCrRedFlag;
				assistData = assistWay(CrsubjectCd, DbsubjectCd, assistData);
				// 触发会计分录拆分交易流水表event 直接记账
				splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postAmt, volSeq, txnDirection,
						assistData, PostGlInd.Normal, gltSeq, redBlueInd);

			}
			if (txnSubjectMapping.ntDbSubjectCdOs != null) {
				// 收
				CrsubjectCd = null;
				inoutFlag = InOutFlagDef.B;
				txnDirection = TxnDirection.D;
				DbsubjectCd = txnSubjectMapping.ntDbSubjectCdOs;
				redBlueInd = txnSubjectMapping.ntDbRedFlagOs;
				assistData = assistWay(CrsubjectCd, DbsubjectCd, assistData);
				// 触发会计分录拆分交易流水表event 直接记账
				splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postAmt.abs(), volSeq, txnDirection,
						assistData, PostGlInd.Normal, gltSeq, redBlueInd);

			}
			if (txnSubjectMapping.ntCrSubjectCdOs != null) {
				// 付
				DbsubjectCd = null;
				inoutFlag = InOutFlagDef.B;
				txnDirection = TxnDirection.C;
				CrsubjectCd = txnSubjectMapping.ntCrSubjectCdOs;
				redBlueInd = txnSubjectMapping.ntCrRedFlagOs;
				assistData = assistWay(CrsubjectCd, DbsubjectCd, assistData);
				// 触发会计分录拆分交易流水表event 直接记账
				splitEvent(clearDate, bizDate, DbsubjectCd, CrsubjectCd, inoutFlag, postAmt.abs(), volSeq, txnDirection,
						assistData, PostGlInd.Normal, gltSeq, redBlueInd);

			}

		}

	}

	/**
	 * 科目是否有辅助核算项处理
	 * 
	 * @param crsubjectCd
	 * @param dbsubjectCd
	 * @param assistData
	 * @return
	 */
	private String assistWay(String crsubjectCd, String dbsubjectCd, String assistData) {

		if (crsubjectCd == subject1 || crsubjectCd == subject2 || dbsubjectCd == subject1 || dbsubjectCd == subject2) {
			assistData = null;
		}
		return assistData;
	}

	/**
	 * 触发会计分录拆分交易流水表event
	 * 
	 * @param trdate
	 * @param postDate
	 * @param dbSubCd
	 * @param crSubCd
	 * @param inoutFlag
	 * @param postAmt
	 * @param volSeq
	 * @param txnDirection
	 * @param assistData
	 */
	private void splitEvent(Date clearDate, LocalDate bizDate, String dbSubCd, String crSubCd, InOutFlagDef inoutFlag,
			BigDecimal postAmt, int volSeq, TxnDirection txnDirection, String assistData, PostGlInd postGlInd,
			String gltSeq, RedBlueInd redBlueInd) {
		logger.info("触发会计分录拆分交易流水表event");
		DirectAccountSplitEvent eventSpilt = new DirectAccountSplitEvent(this);
		eventSpilt.setTrdate(clearDate);
		eventSpilt.setPostDate(bizDate);
		eventSpilt.setDbSubCd(dbSubCd);
		eventSpilt.setCrSubCd(crSubCd);
		eventSpilt.setInoutFlag(inoutFlag);
		eventSpilt.setAccountingAmt(postAmt.abs());
		eventSpilt.setVolSeq(volSeq);
		eventSpilt.setTxnDirection(txnDirection);
		eventSpilt.setAssistData(assistData);
		eventSpilt.setPostGlInd(postGlInd);
		eventSpilt.setGltSeq(gltSeq);
		eventSpilt.setRedBlueInd(redBlueInd);
		ctx.publishEvent(eventSpilt);
	}
}
