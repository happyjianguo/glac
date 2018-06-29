package net.engining.sccc.biz.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlHst;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnPost;
import net.engining.pcx.cc.infrastructure.shared.model.QGlTransOprHst;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.biz.bean.AccountRecord;
import net.engining.sccc.biz.bean.AccountingRecordDetailsRes;
import net.engining.sccc.biz.bean.TotalBookkingRes;
import net.engining.sccc.biz.bean.TransOprHstDetailReq;

@Service
public class TransOprHstService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ParameterFacility parameterFacility;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 综合记账明细查询
	 * 
	 * @param operDate
	 * @param checkerId
	 * 
	 * @return
	 */
	public boolean formatDate(Date date1,Date date2){
			boolean flag;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			flag=sdf.format(date1).equals(sdf.format(date2));
			return flag;
		}

	public AccountingRecordDetailsRes todayDetail(String gltSeq) {
		QApGlVolDtl apGlVolDtl =  QApGlVolDtl.apGlVolDtl;//会计分录流水
		List<Tuple> fetchTwo = new JPAQueryFactory(em).select(apGlVolDtl.redBlueInd,apGlVolDtl.crsubjectCd,apGlVolDtl.dbsubjectCd,apGlVolDtl.txnDirection,
				apGlVolDtl.subjAmount.sum(),apGlVolDtl.assistAccountData)
		 		.from(apGlVolDtl).where(apGlVolDtl.txnDetailSeq.eq(gltSeq),apGlVolDtl.txnDetailType.eq(TxnDetailType.C))
		 		.groupBy(apGlVolDtl.crsubjectCd,apGlVolDtl.dbsubjectCd).fetch();
		 List<AccountRecord> alist = new ArrayList();
		 AccountingRecordDetailsRes singleResult = new AccountingRecordDetailsRes();
		 for (Tuple tuple : fetchTwo) {
			 AccountRecord accountRecord= new AccountRecord();
			 accountRecord.setRedBlueInd(tuple.get(apGlVolDtl.redBlueInd)); 
			 accountRecord.setCrsubjectCd(tuple.get(apGlVolDtl.crsubjectCd));
			 accountRecord.setDbsubjectCd(tuple.get(apGlVolDtl.dbsubjectCd));
			 accountRecord.setTxnDirection(tuple.get(apGlVolDtl.txnDirection));
			 accountRecord.setPostAmount(tuple.get(apGlVolDtl.subjAmount.sum()));
			 accountRecord.setAssistAccountData(tuple.get(apGlVolDtl.assistAccountData));
			 alist.add(accountRecord);
		}
		 singleResult.setAccount(alist);
		return singleResult;
	}
	
	public List<AccountRecord> insertSubjectIntoResult(List<AccountRecord> subjectNo) {
		Map<String, Subject> subjects = parameterFacility.getParameterMap(Subject.class);
		for(AccountRecord postAcct:subjectNo){
			String subjectCd=postAcct.getCrsubjectCd();
			if(StringUtils.isBlank(subjectCd)){
				subjectCd=postAcct.getDbsubjectCd();
			}
			String subjectName = subjects.get(subjectCd).name;
			postAcct.setSubjectName(subjectName);
		}
		return subjectNo;
	}

	public FetchResponse<TotalBookkingRes> bookkeeping(String accountSeq, Range range,CheckFlagDef checkFlag) {
		QApGlTxn qTxn = QApGlTxn.apGlTxn;
		QGlTransOprHst qOprHst = QGlTransOprHst.glTransOprHst;
		QCactTxnPost qPost = QCactTxnPost.cactTxnPost;
		BooleanExpression w1 = null;//记账流水号
		BooleanExpression w2 = null;//借据号
		FetchResponse<TotalBookkingRes> res = new FetchResponse<TotalBookkingRes>();
		List<TotalBookkingRes> total= new ArrayList<TotalBookkingRes>();
		if(StringUtils.isNotBlank(accountSeq)){
			w1=qTxn.gltSeq.eq(accountSeq);
		}
		if(checkFlag != null){
			w2=qOprHst.checkFlag.eq(checkFlag);	
		}
		//left join怎么用
		JPAQuery<Tuple> query = new JPAQueryFactory(em).select(qTxn.txnDetailSeq,qTxn.postType,qTxn.transDate,qTxn.postDate,qTxn.gltSeq,qTxn.postDesc,qTxn.postAmount)
		.from(qTxn).where(w1);
		try {
			FetchResponse<Tuple> build = new JPAFetchResponseBuilder<Tuple>().range(range).build(query);
			List postSeq =new ArrayList();
			List txnDetailSeq =new ArrayList();
			for (Tuple gltSeq : build.getData()) {
				postSeq.add(gltSeq.get(qTxn.gltSeq));//为查询操作表作为条件
				if(null!=gltSeq.get(qTxn.txnDetailSeq)){
					txnDetailSeq.add(Integer.parseInt(gltSeq.get(qTxn.txnDetailSeq)));//为查询cact_txn_post作为条件
				}
			}
			List<Tuple> txn = new JPAQueryFactory(em).select(qPost.txnDetailSeq,qPost.txnSeq,qPost.postTxnType).from(qPost).where(qPost.txnSeq.in(txnDetailSeq)).fetch();
			List<Tuple> oper = new JPAQueryFactory(em).select(qOprHst.txnDetailSeq,qOprHst.checkFlag,qOprHst.checkerId,qOprHst.operaId,qOprHst.refuseReason,qOprHst.printVoucherCount)
				.from(qOprHst).where(qOprHst.txnDetailSeq.in(postSeq),w2).fetch();
			
			for (Tuple first : build.getData()) {
				TotalBookkingRes bookking = new TotalBookkingRes();
				for(Tuple second : oper){
					if(first.get(qTxn.gltSeq).equals(second.get(qOprHst.txnDetailSeq))){
						bookking.setStatus(second.get(qOprHst.checkFlag));
						bookking.setRefuseReason(second.get(qOprHst.refuseReason));
						bookking.setOperaId(second.get(qOprHst.operaId));
						bookking.setCheckerId(second.get(qOprHst.checkerId));
						bookking.setPrintVoucherCount(second.get(qOprHst.printVoucherCount).toString());
						break;
					}
				}
				for (Tuple tuple : txn) {
					if(null!=first.get(qTxn.txnDetailSeq)&&first.get(qTxn.txnDetailSeq).equals(tuple.get(qPost.txnSeq).toString())){
						bookking.setTxnSeq(tuple.get(qPost.txnDetailSeq));
						bookking.setTxnType(tuple.get(qPost.postTxnType).toString());
						break;
					}
				}
				bookking.setTxnDate(first.get(qTxn.transDate).toString());
				bookking.setPostDate(first.get(qTxn.postDate).toString());
				bookking.setPostSeq(first.get(qTxn.gltSeq));
				bookking.setPostDesc(first.get(qTxn.postDesc));
				bookking.setPostAmount(first.get(qTxn.postAmount));
				total.add(bookking);
			}
			res.setData(total);
			res.setRowCount(build.getRowCount());
		} catch (Exception e) {
			logger.info("出现异常"+e);
			res.setData(new ArrayList());
			res.setRowCount(0);
		}
		return res;
	}

	public FetchResponse<TotalBookkingRes> historicalOne(Date txnDate,String accountSeq, Range range){
		QApGlTxnHst q = QApGlTxnHst.apGlTxnHst;
		BooleanExpression w1 = null;//记账流水号
		FetchResponse<TotalBookkingRes> res = new FetchResponse<TotalBookkingRes>();
		List<TotalBookkingRes> total= new ArrayList<TotalBookkingRes>();
		if(StringUtils.isNotBlank(accountSeq)){
			w1=q.gltSeq.eq(accountSeq);
		}
		JPAQuery<Tuple> query = new JPAQueryFactory(em)
		.select(q.txnDetailSeq,q.transDate,q.postDate,q.gltSeq,q.postDesc,q.postAmount).from(q).where(w1,q.transDate.eq(txnDate));
		FetchResponse<Tuple> build = new JPAFetchResponseBuilder<Tuple>().range(range).build(query);
		for (Tuple part : build.getData()) {
			TotalBookkingRes bookking = new TotalBookkingRes();
			bookking.setTxnDate(part.get(q.transDate).toString());
			bookking.setPostDate(part.get(q.postDate).toString());
			bookking.setTxnSeq(part.get(q.txnDetailSeq));
			bookking.setPostSeq(part.get(q.gltSeq));
			bookking.setPostDesc(part.get(q.postDesc));
			bookking.setPostAmount(part.get(q.postAmount));
			total.add(bookking);
		}
		res.setData(total);
		res.setRowCount(build.getRowCount());
		return res;
	}
	
	public FetchResponse<TotalBookkingRes> historicalTwo(FetchResponse<TotalBookkingRes> res,CheckFlagDef checkFlag){
		QCactTxnHst qHst = QCactTxnHst.cactTxnHst;
		QGlTransOprHst qOprHst = QGlTransOprHst.glTransOprHst;
		List<TotalBookkingRes> total= res.getData();
		BooleanExpression w1 = null;//复核状态
		if(checkFlag != null){
			w1=qOprHst.checkFlag.eq(checkFlag);	
		}
		List tSeq =new ArrayList();
		List pSeq =new ArrayList();
		for (TotalBookkingRes bookking : total) {
			if(null!=bookking.getTxnSeq()){
				tSeq.add(Integer.parseInt(bookking.getTxnSeq()));
			}
			pSeq.add(bookking.getPostSeq());
		}
		List<Tuple> txnSeq = new JPAQueryFactory(em).select(qHst.txnSeq,qHst.txnDetailSeq,qHst.postTxnType).from(qHst).where(qHst.txnSeq.in(tSeq)).fetch();
		List<Tuple> oper = new JPAQueryFactory(em).select(qOprHst.txnDetailSeq,qOprHst.checkFlag,qOprHst.checkerId,qOprHst.operaId,qOprHst.refuseReason,qOprHst.printVoucherCount)
		.from(qOprHst).where(qOprHst.txnDetailSeq.in(pSeq),w1).fetch();
		for (TotalBookkingRes bookking : total) {
			for(Tuple txn:txnSeq){
				if(null!=bookking.getTxnSeq()&&bookking.getTxnSeq().equals(txn.get(qHst.txnSeq).toString())){
					bookking.setTxnSeq(txn.get(qHst.txnDetailSeq));
					bookking.setTxnType(txn.get(qHst.postTxnType).toString());
				}
			}
		}
		for (TotalBookkingRes bookking : total) {
			for(Tuple operate:oper){
				if(bookking.getPostSeq().equals(operate.get(qOprHst.txnDetailSeq))){
					bookking.setStatus(operate.get(qOprHst.checkFlag));
					bookking.setRefuseReason(operate.get(qOprHst.refuseReason));
					bookking.setOperaId(operate.get(qOprHst.operaId));
					bookking.setCheckerId(operate.get(qOprHst.checkerId));
					bookking.setPrintVoucherCount(operate.get(qOprHst.printVoucherCount).toString());
				}
			}
		}
		res.setData(total);
		return res;
	}
}
