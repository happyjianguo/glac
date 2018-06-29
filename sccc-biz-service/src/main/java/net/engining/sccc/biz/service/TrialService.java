package net.engining.sccc.biz.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.facility.SystemStatusFacility;
import net.engining.gm.infrastructure.enums.Direction;
import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.gm.param.model.SystemStatus;
import net.engining.pcx.cc.infrastructure.shared.enums.SubjectLevelDef;
import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummary;
import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummaryHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlBalHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApSubjectSummaryHst;
import net.engining.pcx.cc.infrastructure.shared.model.QCactSeqError;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnHst;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.enums.SubjectType;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.biz.bean.DetailCheck;
import net.engining.sccc.biz.bean.SummaryBySubject;
import net.engining.sccc.biz.bean.TotalProrateCheckRes;
import net.engining.sccc.biz.bean.TrialBalanceRes;

@Service
public class TrialService {
	
	@Autowired
	ParameterFacility parameterFacility;
	@Autowired LedgerService ledgerService;
	
	@PersistenceContext
	private EntityManager em;
	private static final NumberPath<BigDecimal> YTDCRAMT_SUM=Expressions.numberPath(BigDecimal.class, "ytdcrAmtSum");
	private static final NumberPath<BigDecimal> YTDDBAMT_SUM=Expressions.numberPath(BigDecimal.class, "ytdDbAmtSum");
	private static final NumberPath<BigDecimal> FACT_AMOUNT=Expressions.numberPath(BigDecimal.class, "factAmount");
	
	public List<Map<String,TrialBalanceRes>> trialBanlance(Date date) {
		
		List<String> subjectNoA = new ArrayList<String>();
		List<String> subjectNoB = new ArrayList<String>();
		List<String> subjectNoC = new ArrayList<String>();
		List<String> subjectNoD = new ArrayList<String>();
		List<String> subjectNoE = new ArrayList<String>();
		List<String> subjectNoF = new ArrayList<String>();
		
		Map<String,Subject> ss = parameterFacility.getParameterMap(Subject.class);
		
		for(Subject subject : ss.values()){
			if(SubjectType.A.equals(subject.type)&&subject.isLast){
				subjectNoA.add(subject.subjectCd);
			}
			else if(SubjectType.B.equals(subject.type)&&subject.isLast){
				subjectNoB.add(subject.subjectCd);
			}
			else if(SubjectType.C.equals(subject.type)&&subject.isLast){
				subjectNoC.add(subject.subjectCd);
			}
			else if(SubjectType.D.equals(subject.type)&&subject.isLast){
				subjectNoD.add(subject.subjectCd);
			}
			else if(SubjectType.E.equals(subject.type)&&subject.isLast){
				subjectNoE.add(subject.subjectCd);
			}
			else if(SubjectType.F.equals(subject.type)&&subject.isLast){
				subjectNoF.add(subject.subjectCd);
			}
		}
		
		TrialBalanceRes resA=getAmount(subjectNoA,date);
//		resA.setType(SubjectType.A);
		
		TrialBalanceRes resB=getAmount(subjectNoB,date);
		
		TrialBalanceRes resC=getAmount(subjectNoC,date);
		
		TrialBalanceRes resD=getAmount(subjectNoD,date);
		
		TrialBalanceRes resE=getAmount(subjectNoE,date);
		
		TrialBalanceRes resF=getAmount(subjectNoF,date);
		Map<String,TrialBalanceRes> map = new HashMap<String,TrialBalanceRes>();
		map.put(SubjectType.A.toString(), resA);
		map.put(SubjectType.B.toString(), resB);
		map.put(SubjectType.C.toString(), resC);
		map.put(SubjectType.D.toString(), resD);
		map.put(SubjectType.E.toString(), resE);
		map.put(SubjectType.F.toString(), resF);
		List<Map<String,TrialBalanceRes>> fetch = new ArrayList<Map<String,TrialBalanceRes>>();
		fetch.add(map);
		return fetch;
	}

	public TrialBalanceRes getAmount(List list,Date date){
		BigDecimal balance=new BigDecimal(0);
		QApGlBalHst apglBalHst = QApGlBalHst.apGlBalHst;// 总账历史表
		QApSubjectSummaryHst subjectHst = QApSubjectSummaryHst.apSubjectSummaryHst;
		Tuple one = new JPAQueryFactory(em).select(subjectHst.crAmt.sum().as(YTDCRAMT_SUM), subjectHst.dbAmt.sum().as(YTDDBAMT_SUM))
				.from(subjectHst)
				.where(subjectHst.subjectCd.in(list))
				.groupBy(subjectHst.bizDate).having(subjectHst.bizDate.eq(date)).fetchOne();
		/*for (Tuple tuple : fetch) {
			BigDecimal ytdCrAmt = tuple.get(apglBalHst.ytdCrAmt);
			crAmtSum = ytdCrAmt.add(crAmtSum);//需要累加，是计算所有同一属性科目的贷方金额，发生额
			BigDecimal ytdDbAmt = tuple.get(apglBalHst.ytdDbAmt);
			dbAmtSum = ytdDbAmt.add(dbAmtSum);
		}*/
		TrialBalanceRes trial = new TrialBalanceRes();
		if(null!=one){
			trial.setCrAmtSum(one.get(YTDCRAMT_SUM));
			trial.setDbAmtSum(one.get(YTDDBAMT_SUM));
			int compareTo = one.get(YTDCRAMT_SUM).subtract(one.get(YTDDBAMT_SUM)).compareTo(BigDecimal.ZERO);
			if(compareTo>0){//crAmtSum.compareTo(dbAmtSum)>0
				trial.setDirection(TxnDirection.C.toString());
			}
			else if(compareTo<0){
				trial.setDirection(TxnDirection.D.toString());
			}
			balance=(one.get(YTDCRAMT_SUM).subtract(one.get(YTDDBAMT_SUM))).abs();
			trial.setBalance(balance);
			return trial;
		}else{
			trial.setCrAmtSum(new BigDecimal(0));
			trial.setDbAmtSum(new BigDecimal(0));
			trial.setDirection(null);
			trial.setBalance(new BigDecimal(0));
			return trial;
		}
		
	}

	public TotalProrateCheckRes totalScoreCheck(Date endDate,String subjectNo) {
			QApGlBalHst qApglBalHst = QApGlBalHst.apGlBalHst;// 总账历史表
			QApSubjectSummaryHst q=QApSubjectSummaryHst.apSubjectSummaryHst;//入账汇总历史表，按科目进行汇总，发生额就是同一个科目累加的金额
			
			Tuple tuple = new JPAQueryFactory(em).select(qApglBalHst.bizDate, qApglBalHst.subjectCd,qApglBalHst.subjectName,
					qApglBalHst.lastCrBal,qApglBalHst.lastDbBal,qApglBalHst.crAmt,qApglBalHst.dbAmt)
			.from(qApglBalHst).where(qApglBalHst.bizDate.eq(endDate),//因为要set期初余额方向，不能用sum
					qApglBalHst.subjectCd.eq(subjectNo)).fetchOne();
			
			Tuple fetch = new JPAQueryFactory(em).select(q.crAmt,q.dbAmt,q.crAmt.subtract(q.dbAmt).abs().as(FACT_AMOUNT))
					.from(q).where(q.subjectCd.eq(subjectNo),q.bizDate.eq(endDate)).fetchOne();
			 
			TotalProrateCheckRes check = new TotalProrateCheckRes();
			 if(null!=tuple && fetch!=null ){
				 check.setBizDate(tuple.get(qApglBalHst.bizDate));
					check.setSubjectNo(tuple.get(qApglBalHst.subjectCd));
					check.setSubjectName(tuple.get(qApglBalHst.subjectName));
					BigDecimal lastDbBal = tuple.get(qApglBalHst.lastDbBal);
					BigDecimal lastCrBal = tuple.get(qApglBalHst.lastCrBal);
					if(lastDbBal.subtract(lastCrBal).intValue()>0){
						check.setBeginDirection(TxnDirection.D.toString());
					}else{
						check.setBeginDirection(TxnDirection.C.toString());
					}
					check.setBeginAmount(lastDbBal.subtract(lastCrBal).abs());
					check.setCrAmt(tuple.get(qApglBalHst.crAmt));
					check.setDbAmt(tuple.get(qApglBalHst.dbAmt));
					check.setEndAmount(check.getCrAmt().subtract(check.getDbAmt()).abs());
					if(check.getCrAmt().subtract(check.getDbAmt()).intValue()>0){
						check.setEndDirection(TxnDirection.C.toString());
					}else{
						check.setEndDirection(TxnDirection.D.toString());
					}
//				 check.setDetailDbAmt(process.getDbAmt());
//				 check.setDetailcrAmt(process.getCrAmt());
//				 check.setFactEndAmount(process.getDbAmt().subtract(process.getCrAmt()).abs());
				 check.setDetailDbAmt(fetch.get(q.crAmt));
				 check.setDetailcrAmt(fetch.get(q.crAmt));
				 check.setFactEndAmount(fetch.get(FACT_AMOUNT));
				 check.setDifference(check.getEndAmount().subtract(check.getFactEndAmount()).abs());
			 }else{
				 return null;
			 }
			 
		//根据日期不同会查出来多条，
			return check; 
			
	}

	public FetchResponse<DetailCheck> detailCheck(Date endDate,Range range) {
		QCactSeqError qError = QCactSeqError.cactSeqError;//差错表
		QCactTxnHst qTxnHst = QCactTxnHst.cactTxnHst;//入账交易流水表
		 JPAQuery<Tuple> where = new JPAQueryFactory(em).select(qTxnHst.txnDate,qTxnHst.postDate,qTxnHst.txnDetailSeq,qTxnHst.txnSeq,qTxnHst.postTxnType,qTxnHst.txnAmt,
				 qTxnHst.postAmt,qTxnHst.checkAccountStatus,qTxnHst.acctSeq)
		 			.from(qTxnHst,qError)
		 			.where(qError.txnDetailSeq.eq(qTxnHst.txnSeq.stringValue()),qError.bizDate.eq(endDate));//如果想用fetchResponse分页，只能是tuple对象或表对象
		 FetchResponse<Tuple> build = new JPAFetchResponseBuilder<Tuple>().range(range).build(where);
		 
		 List<DetailCheck> check = new ArrayList<DetailCheck>();
		 for (Tuple tuple : build.getData()) {
			 DetailCheck detail = new DetailCheck();
			detail.setTxnDate(new SimpleDateFormat("yyyy-MM-dd").format(tuple.get(qTxnHst.txnDate)));
			detail.setPostDateInfo(tuple.get(qTxnHst.postDate));
			detail.setPostDate(new SimpleDateFormat("yyyy-MM-dd").format(detail.getPostDateInfo()));
			detail.setTxnSeq(tuple.get(qTxnHst.txnDetailSeq).toString());
			detail.setCactPostSeq(tuple.get(qTxnHst.txnSeq).toString());
			detail.setTxnType(tuple.get(qTxnHst.postTxnType).toString());
			detail.setTxnAmount(tuple.get(qTxnHst.txnAmt));
			detail.setPostAmount(tuple.get( qTxnHst.postAmt));
			detail.setErrorReason(tuple.get(qTxnHst.checkAccountStatus));
			detail.setAcctSeq(tuple.get(qTxnHst.acctSeq));
			check.add(detail);
		}
		 FetchResponse<DetailCheck>  fetches = new FetchResponse<DetailCheck>();
		 fetches.setData(check);
		 fetches.setRowCount(build.getRowCount());
		 return fetches;
	}
//明细核对，获取记账流水号
	public List<DetailCheck> queryPostSeq(List<DetailCheck> accounting) {
		List acctSeq = new ArrayList();
		List postDate = new ArrayList();
		QApGlTxnHst q =	QApGlTxnHst.apGlTxnHst;
		for (DetailCheck detailCheck : accounting) {
			
			acctSeq.add(detailCheck.getAcctSeq());
			postDate.add(detailCheck.getPostDateInfo());
			
		}
		 List<Tuple> fetch = new JPAQueryFactory(em).select(q.gltSeq,q.txnDetailSeq).from(q).where(q.acctSeq.in(acctSeq),q.postDate.in(postDate)).fetch();
		for(Tuple seq : fetch){
			for(DetailCheck detailCheck : accounting){
				if(seq.get(q.txnDetailSeq).equals(detailCheck.getCactPostSeq())){
					detailCheck.setPostSeq(seq.get(q.gltSeq));
				}
				break;
			}
		}
		 return accounting;
	}
/**
 * 计算合计金额
 * @param trialBanlance
 * @return
 */
	public List<Map<String, TrialBalanceRes>> countTotal(List<Map<String, TrialBalanceRes>> trialBanlance) {
		TrialBalanceRes total1 = new TrialBalanceRes();
		TrialBalanceRes total2 = new TrialBalanceRes();
		for (Map<String, TrialBalanceRes> map : trialBanlance) {
			TrialBalanceRes trialA=map.get(SubjectType.A.toString());
			TrialBalanceRes trialB=map.get(SubjectType.B.toString());
			TrialBalanceRes trialC=map.get(SubjectType.C.toString());
			TrialBalanceRes trialD=map.get(SubjectType.D.toString());
			TrialBalanceRes trialE=map.get(SubjectType.E.toString());
			BigDecimal totalDbAmt=trialA.getDbAmtSum().add(trialB.getDbAmtSum()).add(trialD.getDbAmtSum());
			total1.setDbAmtSum(totalDbAmt);
			BigDecimal totalCrAmt=trialA.getCrAmtSum().add(trialB.getCrAmtSum()).add(trialD.getCrAmtSum());
			total1.setCrAmtSum(totalCrAmt);
			if(totalDbAmt.subtract(totalCrAmt).compareTo(BigDecimal.ZERO)>0){
				total1.setDirection(TxnDirection.D.toString());
			}else if(totalDbAmt.subtract(totalCrAmt).compareTo(BigDecimal.ZERO)<0){
				total1.setDirection(TxnDirection.C.toString());
			}else{
				total1.setDirection(TxnDirection.O.toString());
			}
			total1.setBalance(totalDbAmt.subtract(totalCrAmt).abs());
			
			BigDecimal totalDbAmt2=trialC.getDbAmtSum().add(trialE.getDbAmtSum());
			total2.setDbAmtSum(totalDbAmt2);
			BigDecimal totalCrAmt2=trialC.getCrAmtSum().add(trialE.getCrAmtSum());
			total2.setCrAmtSum(totalCrAmt2);
			if(totalDbAmt2.subtract(totalCrAmt2).compareTo(BigDecimal.ZERO)>0){
				total2.setDirection(TxnDirection.D.toString());
			}else if(totalDbAmt2.subtract(totalCrAmt2).compareTo(BigDecimal.ZERO)<0){
				total2.setDirection(TxnDirection.C.toString());
			}else{
				total2.setDirection(TxnDirection.O.toString());
			}
			total2.setBalance(totalDbAmt2.subtract(totalCrAmt2).abs());
			map.put("total1", total1);
			map.put("total2", total2);
//			trialBanlance.add(map);
		}
		return trialBanlance;
	}
}
