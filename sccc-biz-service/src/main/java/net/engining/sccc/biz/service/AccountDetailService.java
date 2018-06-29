package net.engining.sccc.biz.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.PostTxnTypeDef;
import net.engining.pcx.cc.infrastructure.shared.model.CactTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.CactTxnPost;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlHst;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccount;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccountAddi;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccountNo;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnPost;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.biz.bean.AccountDetailRes;
import net.engining.sccc.biz.bean.AccountRecord;
import net.engining.sccc.biz.bean.AccountingRecordReq;
import net.engining.sccc.biz.bean.HistoricalCondition;
import net.engining.sccc.biz.bean.HistoricalaccountingRecordReq;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.bean.batchBean.RequestData;
import net.engining.sccc.biz.service.params.ProductAccount;

@Service
public class AccountDetailService {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private ParameterFacility facility;
	
	private JPAQuery<Tuple> where;
	private final static String defaultAcctSeq="0";
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public FetchResponse<AccountDetailRes> accountDetailedInquiry(String custId, String iouNo, Range range,
			String postSeq, String txnSeq, PostTxnTypeDef type) {
		QCactAccountNo accountNo = QCactAccountNo.cactAccountNo;// 账号表
		QCactAccount account = QCactAccount.cactAccount;// 账户信息表
		QCactAccountAddi cactaccountaddi = QCactAccountAddi.cactAccountAddi;// 账户附加信息表
		QApGlTxn apgltxn = QApGlTxn.apGlTxn;// 当日总账交易流水
		QCactTxnPost qpost = QCactTxnPost.cactTxnPost;// 入账交易表
		BooleanExpression w1 = null;// 客户号
		BooleanExpression w2 = null;// 借据号
		BooleanExpression w3 = null;// 记账流水号
		BooleanExpression w4 = null;// 交易流水号
		BooleanExpression w5 = null;// 交易类型
		List<AccountDetailRes> details = new ArrayList<AccountDetailRes>();
		FetchResponse<Tuple> build;
		FetchResponse<AccountDetailRes> res = new FetchResponse<AccountDetailRes>();
		
		if (StringUtils.isNotBlank(custId)) {
			w1 = accountNo.custId.eq(custId);
		}
		if (StringUtils.isNotBlank(iouNo)) {
			w2 = cactaccountaddi.iouNo.eq(iouNo);
		}
		if (StringUtils.isNotBlank(postSeq)) {
			w3 = apgltxn.gltSeq.eq(postSeq);
		}
		if (StringUtils.isNotBlank(txnSeq)) {
			w4 = qpost.txnDetailSeq.eq(txnSeq);
		}
		w5 = qpost.postTxnType.eq(type);// 交易类型

		List<Tuple> fetch = new JPAQueryFactory(em).select(accountNo.custId, account.acctSeq)
				.from(accountNo, account).where(accountNo.acctNo.eq(account.acctNo), w1).fetch();
		List acctSeq1 = new ArrayList();
		for (Tuple tuple : fetch) {
			acctSeq1.add(tuple.get(account.acctSeq));
		}
		
		try {
			List<Tuple> qcact = new JPAQueryFactory(em).select(qpost.txnSeq,qpost.acctSeq,qpost.txnTime,qpost.requestData,qpost.txnDetailSeq).from(qpost)
					.where(w4,w5,qpost.acctSeq.in(acctSeq1)).fetch();
			List acctSeq2 = new ArrayList();
			for (Tuple tuple : qcact) {
				acctSeq2.add(tuple.get(qpost.acctSeq));
			}
			JPAQuery<Tuple> query = new JPAQueryFactory(em)
					.select(cactaccountaddi.iouNo, apgltxn.transDate, apgltxn.postDate,apgltxn.txnDetailSeq,
							apgltxn.gltSeq, apgltxn.postAmount, apgltxn.acctSeq)
					.from(apgltxn, cactaccountaddi)
					.where(cactaccountaddi.acctSeq.in(acctSeq2), apgltxn.acctSeq.eq(cactaccountaddi.acctSeq), w2, w3);
			build = new JPAFetchResponseBuilder<Tuple>().range(range).build(query);//创建分页
			for (Tuple row : build.getData()) {
				AccountDetailRes detailRes = new AccountDetailRes();
				detailRes.setTxnDate(row.get(apgltxn.transDate));
				detailRes.setPostDate(row.get(apgltxn.postDate));
				detailRes.setTxnType(type);
				detailRes.setPostSeq(row.get(apgltxn.gltSeq));
				detailRes.setPostAmount(row.get(apgltxn.postAmount));
				detailRes.setIouNo(row.get(cactaccountaddi.iouNo));
				for (Tuple tuple : qcact) {
					if(tuple.get(qpost.txnSeq).toString().equals(row.get(apgltxn.txnDetailSeq).toString())){
						EveryDayAccountingBean item = JSON.parseObject(tuple.get(qpost.requestData),EveryDayAccountingBean.class);
						detailRes.setProductCode(item.getRequestData().getProductId());
						detailRes.setCustNo(item.getRequestData().getCustId());
						detailRes.setTxnSeq(tuple.get(qpost.txnDetailSeq));
						detailRes.setTxnTime(tuple.get(qpost.txnTime).toString());
						break;
					}
				}
				details.add(detailRes);
			}
			res.setData(details);
			res.setRowCount(build.getRowCount());
			} catch (Exception e) {
				logger.info("出现异常",e);
				res.setData(details);
				res.setRowCount(0);
			}
		return res;
	}

	public FetchResponse<AccountDetailRes> getDetailByNo(AccountingRecordReq account) {
		BooleanExpression w3 = null;// 记账流水号
		BooleanExpression w4 = null;// 交易流水号
		BooleanExpression w5 = null;// 交易类型
		List<AccountDetailRes> details = new ArrayList<AccountDetailRes>();
		QApGlTxn apgltxn = QApGlTxn.apGlTxn;// 当日总账交易流水
		QCactTxnPost qpost = QCactTxnPost.cactTxnPost;// 入账交易表
		if (StringUtils.isNotBlank(account.getPostSeq())) {
			w3 = apgltxn.gltSeq.eq(account.getPostSeq());
		}
		if (StringUtils.isNotBlank(account.getTxnSeq())) {
			w4 = qpost.txnDetailSeq.eq(account.getTxnSeq());
		}
		w5 = qpost.postTxnType.eq(account.getType());// 交易类型
		JPAQuery<Tuple> postAccount = new JPAQueryFactory(em)
				.select(qpost.acctSeq, qpost.requestData, apgltxn.transDate, qpost.txnDetailSeq, apgltxn.postDate,
						apgltxn.gltSeq, apgltxn.postAmount)
				.from(apgltxn, qpost).where(w3, w4, w5, apgltxn.txnDetailSeq.eq(qpost.txnSeq.stringValue()));
		FetchResponse<Tuple> build = new JPAFetchResponseBuilder<Tuple>().range(account.getRange()).build(postAccount);//创建分页
		
		for (Tuple tuple : build.getData()) {
			AccountDetailRes detailRes = new AccountDetailRes();
			EveryDayAccountingBean item = JSON.parseObject(tuple.get(qpost.requestData),EveryDayAccountingBean.class);
			detailRes.setProductCode(item.getRequestData().getProductId());
			detailRes.setCustNo(item.getRequestData().getCustId());
			detailRes.setIouNo(item.getRequestData().getIouNo());
			detailRes.setTxnSeq(tuple.get(qpost.txnDetailSeq));
			detailRes.setTxnDate(tuple.get(apgltxn.transDate));
			detailRes.setPostDate(tuple.get(apgltxn.postDate));
			detailRes.setPostSeq(tuple.get(apgltxn.gltSeq));
			detailRes.setPostAmount(tuple.get(apgltxn.postAmount));
			detailRes.setTxnType(account.getType());
			details.add(detailRes);
		}
		FetchResponse<AccountDetailRes> res = new FetchResponse<AccountDetailRes>();
		res.setData(details);
		res.setRowCount(build.getRowCount());
		return res;
	}

	/**
	 * 当日记账明细详情查询
	 * 
	 * @param postSeq
	 * @return
	 */
	public List<AccountRecord> queryDetails(String postSeq) {
		QApGlVolDtl qDtl = QApGlVolDtl.apGlVolDtl;
		List<Tuple> query = new JPAQueryFactory(em)
				.select(qDtl.crsubjectCd, qDtl.dbsubjectCd, qDtl.subjAmount.sum(), qDtl.txnDirection, qDtl.assistAccountData)
				.from(qDtl).where(qDtl.txnDetailSeq.eq(postSeq)).groupBy(qDtl.crsubjectCd, qDtl.dbsubjectCd).fetch();
		 List<AccountRecord> alist = new ArrayList<AccountRecord>();
		for (Tuple tuple : query) {
			 AccountRecord accountRecord= new AccountRecord();
			 accountRecord.setCrsubjectCd(tuple.get(qDtl.crsubjectCd));
			 accountRecord.setDbsubjectCd(tuple.get(qDtl.dbsubjectCd));
			 accountRecord.setTxnDirection(tuple.get(qDtl.txnDirection));
			 accountRecord.setPostAmount(tuple.get(qDtl.subjAmount.sum()));
			 accountRecord.setAssistAccountData(tuple.get(qDtl.assistAccountData));
			 alist.add(accountRecord);
		}
		
		return alist;
	}

	public HistoricalCondition getFirstPart(String custId, String iouNo, Date endDate, String postSeq, String txnSeq,
			Range range, PostTxnTypeDef type) {
		QCactAccountNo accountNo = QCactAccountNo.cactAccountNo;// 账号表
		QCactAccount account = QCactAccount.cactAccount;// 账户信息表
		QCactAccountAddi cactaccountaddi = QCactAccountAddi.cactAccountAddi;// 账户附加信息表
		QCactTxnHst qpost = QCactTxnHst.cactTxnHst;
		BooleanExpression w1 = null;// 客户号
		BooleanExpression w2 = null;// 借据号
		BooleanExpression w3 = null;// 交易流水号
		BooleanExpression w4 = qpost.postTxnType.eq(type);// 交易类型
		BooleanExpression w5 = qpost.txnDate.eq(endDate);//交易日期
		BooleanExpression w6 = null;// 账户编号
		List<Map<String, Object>> first = Lists.newArrayList();

		HistoricalCondition condition = new HistoricalCondition();
		condition.setEndDate(endDate);//现在是把入账表里的入账日期传到交易流水表的postDate
		condition.setPostSeq(postSeq);
		condition.setRange(range);
		condition.setType(type);
		List<Integer> acctSeq1=new ArrayList<Integer>();
		if (StringUtils.isNotBlank(custId)) {
			w1 = accountNo.custId.eq(custId);
			acctSeq1 = new JPAQueryFactory(em).select(account.acctSeq).from(accountNo, account)
					.where(accountNo.acctNo.eq(account.acctNo), w1).fetch();
		}
		if (StringUtils.isNotBlank(iouNo)) {
			w2 = cactaccountaddi.iouNo.eq(iouNo);
		}
		if (StringUtils.isNotBlank(txnSeq)) {
			w3 = qpost.txnDetailSeq.eq(txnSeq);
		}
		if(!acctSeq1.isEmpty()){
			w6=cactaccountaddi.acctSeq.in(acctSeq1);
		}

			List<Tuple> fetch2 = new JPAQueryFactory(em).select(cactaccountaddi.iouNo, cactaccountaddi.acctSeq,
					qpost.postDate,qpost.txnTime,qpost.txnSeq,qpost.txnDetailSeq,qpost.requestData)
					.from(cactaccountaddi, qpost)
					.where(w6, qpost.acctSeq.eq(cactaccountaddi.acctSeq),w2,w3,w4,w5).fetch();
			if(fetch2.isEmpty()){
				throw new ErrorMessageException(ErrorCode.SystemError, "对不起，没有符合的数据");
			}
			
			for (Tuple qcactTxn : fetch2) {
						Map<String, Object> map = Maps.newHashMap();
						map.put("txnSeq", qcactTxn.get(qpost.txnSeq));
						map.put("txnDetailSeq", qcactTxn.get(qpost.txnDetailSeq));
						map.put("requestData", qcactTxn.get(qpost.requestData));
						map.put("acctSeq", qcactTxn.get(cactaccountaddi.acctSeq));
						map.put("postDate", qcactTxn.get(qpost.postDate));
						map.put("txnTime", qcactTxn.get(qpost.txnTime));
						first.add(map);
				}
		condition.setList(first);
		return condition;
	}

	public FetchResponse<AccountDetailRes> getSecond(HistoricalCondition firstPart) {
		QApGlTxnHst qhst = QApGlTxnHst.apGlTxnHst;// 当日总账交易流水
		List acctList = new ArrayList();
		List date = new ArrayList();
		for (Map<String, Object> map : firstPart.getList()) {
			acctList.add(map.get("acctSeq"));
			ZoneId zon = ZoneId.systemDefault();
			date.add(Date.from(LocalDate.parse(map.get("postDate").toString()).atStartOfDay(zon).toInstant()));
		}

		String postSeq = firstPart.getPostSeq();
		PostTxnTypeDef type =firstPart.getType();
		Range range = firstPart.getRange();
		
		BooleanExpression w1 = null;// 记账流水号
		BooleanExpression w3 = qhst.postDate.in(date);// 记账日期
		List<AccountDetailRes> details = new ArrayList<AccountDetailRes>();

		if (StringUtils.isNotBlank(postSeq)) {
			w1 = qhst.gltSeq.eq(postSeq);
		}

		JPAQuery<Tuple> postAccount = new JPAQueryFactory(em)
				.select(qhst.transDate, qhst.txnDetailSeq, qhst.postDate, qhst.gltSeq, qhst.postAmount, qhst.acctSeq)
				.from(qhst).where(qhst.acctSeq.in(acctList),w3,w1);
		FetchResponse<Tuple> build = new
				 JPAFetchResponseBuilder<Tuple>().range(range).build(postAccount);
		
			for (Tuple row : build.getData()) {
				AccountDetailRes detailRes = new AccountDetailRes();
				detailRes.setTxnDate(row.get(qhst.transDate));
				detailRes.setPostDate(row.get(qhst.postDate));
				detailRes.setPostSeq(row.get(qhst.gltSeq));
				detailRes.setPostAmount(row.get(qhst.postAmount));
				detailRes.setTxnType(type);
				for (Map<String, Object> requestData : firstPart.getList()) {
					if(row.get(qhst.txnDetailSeq).toString().equals(requestData.get("txnSeq").toString())){
						detailRes.setTxnSeq(requestData.get("txnDetailSeq").toString());
						detailRes.setTxnTime(requestData.get("txnTime").toString());
						EveryDayAccountingBean item = JSON.parseObject(requestData.get("requestData").toString(),EveryDayAccountingBean.class);
						detailRes.setProductCode(item.getRequestData().getProductId());
						detailRes.setCustNo(item.getRequestData().getCustId());
						detailRes.setIouNo(item.getRequestData().getIouNo());
					}
				}
				details.add(detailRes);
			}
		FetchResponse<AccountDetailRes> res = new FetchResponse<AccountDetailRes>();
		res.setData(details);
		res.setRowCount(build.getRowCount());
		return res;
	}
	
	public HistoricalCondition getThirdPart(HistoricalaccountingRecordReq account){
		QCactTxnHst qpost = QCactTxnHst.cactTxnHst;
		BooleanExpression w1 = null;
		if(null!=w1){
			w1=qpost.txnDetailSeq.eq(account.getTxnSeq());
		}
		BooleanExpression w2 = qpost.txnDate.eq(account.getEndDate());// 交易日期
		BooleanExpression w3 = qpost.postTxnType.eq(account.getType());// 交易类型
		List<Tuple> one = new JPAQueryFactory(em).select(qpost.acctSeq,qpost.postDate,qpost.txnSeq,qpost.txnDetailSeq,qpost.requestData,qpost.txnTime)
		.from(qpost).where(w1,w2,w3).fetch();
		List<Map<String, Object>> third = Lists.newArrayList();

		HistoricalCondition condition = new HistoricalCondition();
		condition.setEndDate(account.getEndDate());
		condition.setPostSeq(account.getPostSeq());
		condition.setType(account.getType());
		condition.setRange(account.getRange());
		for (Tuple qcactTxn : one) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("txnSeq", qcactTxn.get(qpost.txnSeq));
			map.put("txnDetailSeq", qcactTxn.get(qpost.txnDetailSeq));
			map.put("requestData", qcactTxn.get(qpost.requestData));
			map.put("acctSeq", qcactTxn.get(qpost.acctSeq));
			map.put("postDate", qcactTxn.get(qpost.postDate));
			map.put("txnTime", qcactTxn.get(qpost.txnTime));
			third.add(map);
		}
		condition.setList(third);
		return condition;
	}
	
	public FetchResponse<Map<String, Object>> getAccountDetails(Date endDate, String postSeq) {
		QApGlVolDtlHst qDtl = QApGlVolDtlHst.apGlVolDtlHst;
		JPAQuery<Tuple> query = new JPAQueryFactory(em)
				.select(qDtl.crsubjectCd, qDtl.dbsubjectCd, qDtl.subjAmount, qDtl.txnDirection,qDtl.assistAccountData).from(qDtl)
				.where(qDtl.volDt.eq(endDate), qDtl.txnDetailSeq.eq(postSeq));
		return new JPAFetchResponseBuilder<Map<String, Object>>().buildAsMap(query, qDtl.crsubjectCd, qDtl.dbsubjectCd,
				qDtl.subjAmount, qDtl.txnDirection,qDtl.assistAccountData);
	}

}