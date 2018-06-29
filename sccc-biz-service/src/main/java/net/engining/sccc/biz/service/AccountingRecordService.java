package net.engining.sccc.biz.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlHst;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccount;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccountAddi;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccountNo;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnPost;
import net.engining.pcx.cc.infrastructure.shared.model.QGlTransOprHst;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.biz.bean.AccountRecord;
import net.engining.sccc.biz.bean.AccountingRecordDetailsRes;
import net.engining.sccc.biz.bean.AcctIouNo;
import net.engining.sccc.biz.bean.HistoricalAccountRecord;
import net.engining.sccc.biz.enums.DateTypeDef;

@Service
public class AccountingRecordService {

	@PersistenceContext
	private EntityManager em;
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 当日会计分录查询
	 * @param custId
	 * @param iouNo
	 * @param range
	 * @return
	 */
	public FetchResponse<HistoricalAccountRecord> getAccountingRecord(String custId, String iouNo,Range range) {
		
		//当客户号和借据号同时为空时返回一个没有数据的对象
		if(StringUtils.isBlank(iouNo)&&StringUtils.isBlank(custId)){
			return new FetchResponse<HistoricalAccountRecord>();
		}
		
		QCactAccountNo accountNo=QCactAccountNo.cactAccountNo;//账号表
		QCactAccount account = QCactAccount.cactAccount;//账户信息表
		QCactAccountAddi cactaccountaddi = QCactAccountAddi.cactAccountAddi;//账户附加信息表
		QApGlTxn apgltxn = QApGlTxn.apGlTxn;//当日总账交易流水
		QCactTxnPost post = QCactTxnPost.cactTxnPost;//当日入账交易表
		
		BooleanExpression w1 = accountNo.acctNo.eq(account.acctNo);
		
		BooleanExpression w3 = null;//借据号
		BooleanExpression w4 = null;//客户号
		BooleanExpression w5 = null;//帐编
		List<Integer> acctSeq1=new ArrayList<Integer> ();
		FetchResponse<HistoricalAccountRecord> res;
		if(StringUtils.isNotBlank(iouNo)){
			w3=cactaccountaddi.iouNo.eq(iouNo);
		}
		if(StringUtils.isNotBlank(custId)){
			w4=accountNo.custId.eq(custId);
			 acctSeq1 = new JPAQueryFactory(em).select(account.acctSeq).from(accountNo,account).where(w4,w1).fetch();
		}
		if(!acctSeq1.isEmpty()){
			w5=cactaccountaddi.acctSeq.in(acctSeq1);
		}
		
		try {
			JPAQuery<Tuple> where = new JPAQueryFactory(em).select(cactaccountaddi.iouNo,apgltxn.transDate,apgltxn.postDate,apgltxn.gltSeq,apgltxn.postDesc,apgltxn.postAmount,apgltxn.acctSeq,apgltxn.txnDetailSeq)
					.from(apgltxn,cactaccountaddi).where(apgltxn.acctSeq.eq(cactaccountaddi.acctSeq),w5,w3);
			FetchResponse<Tuple> build = new JPAFetchResponseBuilder<Tuple>().range(range).build(where);
			
			
			List<Integer> txnList =new ArrayList<Integer>();
			for(Tuple txn : build.getData()){
				 if(StringUtils.isNotBlank(txn.get(apgltxn.txnDetailSeq))){
					 txnList.add(Integer.parseInt(txn.get(apgltxn.txnDetailSeq)));
				 } 
			}
			
			 List<Tuple> coreSeq = new JPAQueryFactory(em).select(post.txnSeq,post.txnDetailSeq).from(post).where(post.txnSeq.in(txnList)).fetch();
			 List<HistoricalAccountRecord> list = new ArrayList<HistoricalAccountRecord>();
			for (Tuple tuple : build.getData()) {
				HistoricalAccountRecord record = new HistoricalAccountRecord();
				record.setIouNo(tuple.get(cactaccountaddi.iouNo));
				record.setTxnDate(new SimpleDateFormat("yyyy-MM-dd").format(tuple.get(apgltxn.transDate)));
				record.setPostDate(new SimpleDateFormat("yyyy-MM-dd").format(tuple.get(apgltxn.postDate)));
				record.setGltSeq(tuple.get(apgltxn.gltSeq));
				record.setTxnDesc(tuple.get(apgltxn.postDesc));
				record.setPostDesc(tuple.get(apgltxn.postDesc));
				record.setTxnAmount(tuple.get(apgltxn.postAmount));
				record.setPostAmount(tuple.get(apgltxn.postAmount));
				record.setAcctSeq(tuple.get(apgltxn.acctSeq));
				for (Tuple seq : coreSeq) {
					if(null!=seq.get(post.txnSeq)&&seq.get(post.txnSeq).toString().equals(tuple.get(apgltxn.txnDetailSeq))){
						record.setTxnDetailSeq(seq.get(post.txnDetailSeq));
						break;
					}
				}
				list.add(record);
			}
			res = new FetchResponse<HistoricalAccountRecord>();
			res.setData(list);
			res.setRowCount(build.getRowCount());
		} catch (Exception e) {
			logger.info("出现异常",e);
			return null;
		}
		return res;
	}
/**
 * 当日会计分录查询详情
 * @param acctSeq
 * @param gltSeq
 * @return
 */
	
	public AccountingRecordDetailsRes getAccountingRecordDetails(int acctSeq, String gltSeq,
			TxnDetailType txnDetailType,String txnSeq) {
		QCactAccountNo accountNo=QCactAccountNo.cactAccountNo;//账号表
		QCactAccount account = QCactAccount.cactAccount;//账户信息表
		QApGlVolDtl apGlVolDtl =  QApGlVolDtl.apGlVolDtl;//会计分录流水
		QGlTransOprHst glTransOprHst = QGlTransOprHst.glTransOprHst;//总账交易操作历史表
		QCactTxnPost cacttxnpost = QCactTxnPost.cactTxnPost;//当日入账交易表
		/**
		 * 外部流水号应该查哪个字段
		 */
		BooleanExpression w1 = accountNo.acctNo.eq(account.acctNo);
		BooleanExpression w5 = glTransOprHst.txnDetailSeq.eq(gltSeq);
		BooleanExpression w6 = account.acctSeq.eq(acctSeq);
		try{
		
		String custId = new JPAQueryFactory(em).select(accountNo.custId).from(accountNo,account).where(w1,w6).fetchFirst();
		//查询录入操作员号，复核员号
		List<Tuple> fetchOne = new JPAQueryFactory(em).select(glTransOprHst.operaId,glTransOprHst.checkerId)
				.from(glTransOprHst).where(w5,glTransOprHst.txnDetailType.eq(txnDetailType)).fetch();
		
		
		AccountingRecordDetailsRes singleResult = new AccountingRecordDetailsRes();
		singleResult.setCustId(custId);
		for(Tuple t : fetchOne){
			singleResult.setCheckOperatorNo(t.get(glTransOprHst.checkerId));
			singleResult.setEntryOperatorNo(t.get(glTransOprHst.operaId));
		}
		//查询分录明细
		List<Tuple> fetchTwo = new JPAQueryFactory(em).select(apGlVolDtl.redBlueInd,apGlVolDtl.crsubjectCd,apGlVolDtl.dbsubjectCd,apGlVolDtl.txnDirection,
				apGlVolDtl.subjAmount.sum(),apGlVolDtl.assistAccountData)
		 		.from(apGlVolDtl)
		 		.where(apGlVolDtl.txnDetailSeq.eq(gltSeq),apGlVolDtl.txnDetailType.eq(txnDetailType))
		 		.groupBy(apGlVolDtl.crsubjectCd,apGlVolDtl.dbsubjectCd).fetch();
		 List<AccountRecord> alist = new ArrayList<AccountRecord>();
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
		}catch (Exception e) {
			logger.info("出现异常",e);
			throw new ErrorMessageException(ErrorCode.CheckError, "详情好像出了点问题");
		}
	}

	
	public List<AcctIouNo> getHisroticAccountingRecord(String userId,String iouId,DateTypeDef dateType,Date begin,Date end) {
		
		QCactAccount cactAccount = QCactAccount.cactAccount;
		QCactAccountAddi  cactAccountAddi = QCactAccountAddi.cactAccountAddi;
		QCactAccountNo cactAccountNo = QCactAccountNo.cactAccountNo;
		QCactTxnHst post = QCactTxnHst.cactTxnHst;
		AcctIouNo account = null;
		BooleanExpression w1 =null;
		BooleanExpression w2 =null;
		List<AcctIouNo> alist = new ArrayList<AcctIouNo>();
		if(StringUtils.isNotBlank(iouId)){
			w1 = cactAccountAddi.iouNo.eq(iouId);
		}
	    JPAQuery<Tuple> query = new JPAQueryFactory(em)
				.select(cactAccountAddi.acctSeq,cactAccountAddi.iouNo).from(cactAccountAddi)
				.where(w1);
	    
	    if(StringUtils.isNotBlank(userId)){
	    	List<Integer> acctSeq = new JPAQueryFactory(em)
					.select(cactAccount.acctSeq).from(cactAccount,cactAccountNo)
					.where(cactAccount.acctNo.eq(cactAccountNo.acctNo),cactAccountNo.custId.eq(userId)).fetch();
	    	query.where(cactAccountAddi.acctSeq.in(acctSeq));
		}
	   
	    try {
	    	 List<Tuple> fetches = query.fetch();
	    	 for(Tuple fetch:fetches){
	 	    	account=new AcctIouNo();
	 	    	account.setAcctSeq(fetch.get(cactAccountAddi.acctSeq));
	 	    	account.setIouNo(fetch.get(cactAccountAddi.iouNo));
	 	    	alist.add(account);
	 	    	}
		} catch (Exception e) {
			logger.info("出现异常",e);
			return null;
		}
	    	
	    return alist;
	}
	
   public FetchResponse<HistoricalAccountRecord> txnHstQuery(List<AcctIouNo> list, Date beginDate, Date endDate,DateTypeDef dateType,Range range){
		QApGlTxnHst apgltxnhst = QApGlTxnHst.apGlTxnHst;
		QCactTxnHst post=QCactTxnHst.cactTxnHst;
		HistoricalAccountRecord accounting=null;
		BooleanExpression w1=apgltxnhst.postDate.between(beginDate, endDate);
		List acct = new ArrayList();
		List<Date> date = new ArrayList<Date>();
		List<HistoricalAccountRecord> arraylist = new ArrayList<HistoricalAccountRecord>();
		//因为分库分表的规则，必须要以acctSeq以查询条件
		for(AcctIouNo acctSeq:list){
			acct.add(acctSeq.getAcctSeq());
			/*if(null!=acctSeq.getPostDate()){
				ZoneId zon = ZoneId.systemDefault();
				date.add(Date.from(LocalDate.parse(acctSeq.getPostDate().toString()).atStartOfDay(zon).toInstant()));
			}*/
		}
		if(DateTypeDef.T.equals(dateType)){
			w1=apgltxnhst.transDate.between(beginDate, endDate);
		}
		JPAQuery<Tuple> query = new JPAQueryFactory(em).select(apgltxnhst.transDate,apgltxnhst.postDate,apgltxnhst.gltSeq,apgltxnhst.postDesc,apgltxnhst.postType,
				apgltxnhst.postAmount,apgltxnhst.acctSeq,apgltxnhst.txnDetailSeq).from(apgltxnhst)
				.where(apgltxnhst.acctSeq.in(acct),w1);
		 FetchResponse<Tuple> build = new JPAFetchResponseBuilder<Tuple>().range(range).build(query);
		 List<Integer> txnList =new ArrayList<Integer>();
		for(Tuple txn : build.getData()){
			 if(StringUtils.isNotBlank(txn.get(apgltxnhst.txnDetailSeq))){
				 txnList.add(Integer.parseInt(txn.get(apgltxnhst.txnDetailSeq)));
			 } 
		}
		 List<Tuple> coreSeq = new JPAQueryFactory(em).select(post.txnSeq,post.txnDetailSeq).from(post).where(post.txnSeq.in(txnList)).fetch();
    
		 for(Tuple fetch : build.getData()){
	    	accounting=new HistoricalAccountRecord();
			accounting.setTxnDate(fetch.get(apgltxnhst.transDate).toString());
			accounting.setPostDate(new SimpleDateFormat("yyyy-MM-dd").format(fetch.get(apgltxnhst.postDate)));
			accounting.setGltSeq(fetch.get(apgltxnhst.gltSeq));
			accounting.setTxnDesc(fetch.get(apgltxnhst.postDesc));
			accounting.setPostDesc(fetch.get(apgltxnhst.postDesc));
			accounting.setPostType(fetch.get(apgltxnhst.postType));
			accounting.setTxnAmount(fetch.get(apgltxnhst.postAmount));
			accounting.setPostAmount(fetch.get(apgltxnhst.postAmount));
			accounting.setAcctSeq(fetch.get(apgltxnhst.acctSeq));
			for(AcctIouNo tuple: list){
				if(fetch.get(apgltxnhst.acctSeq).equals(tuple.getAcctSeq())){
					accounting.setIouNo(tuple.getIouNo());
					arraylist.add(accounting);
					break;
				}
			}
			for (Tuple seq : coreSeq) {
				if(null!=seq.get(post.txnSeq)&&seq.get(post.txnSeq).toString().equals(fetch.get(apgltxnhst.txnDetailSeq))){
					accounting.setTxnDetailSeq(seq.get(post.txnDetailSeq));
					break;
				}
			}
		 }
	    FetchResponse<HistoricalAccountRecord> res = new FetchResponse<HistoricalAccountRecord>();
	    res.setData(arraylist);
	    res.setRowCount(build.getRowCount());
	    return res;
	}

/**
 * 历史会计分录详情查询
 * @param acctSeq
 * @param gltSeq不能用group by为什么
 * @return
 */
	public AccountingRecordDetailsRes getHistoricalAcountingRecordDetails(AccountingRecordDetailsRes singleResult) {
		QApGlVolDtlHst apGlVolDtlHst =  QApGlVolDtlHst.apGlVolDtlHst;//会计分录流水历史QApGlTxnHst
		BooleanExpression w4 = apGlVolDtlHst.txnDetailSeq.eq(singleResult.getGltSeq());
		
		List<Tuple> fetchTwo = new JPAQueryFactory(em).select(apGlVolDtlHst.redBlueInd,apGlVolDtlHst.crsubjectCd,apGlVolDtlHst.dbsubjectCd,apGlVolDtlHst.txnDirection,
				apGlVolDtlHst.subjAmount/*.sum()*/,apGlVolDtlHst.assistAccountData)
		 		.from(apGlVolDtlHst).where(apGlVolDtlHst.volDt.eq(singleResult.getPostDate()),w4,apGlVolDtlHst.txnDetailType.eq(TxnDetailType.C))
		 		/*.groupBy(apGlVolDtlHst.crsubjectCd,apGlVolDtlHst.dbsubjectCd)*/.fetch();
		 List<AccountRecord> alist = new ArrayList();
		 BigDecimal amount = new BigDecimal(0);
		 
		 for (Tuple tuple : fetchTwo) {
			 AccountRecord accountRecord= new AccountRecord();
			 accountRecord.setRedBlueInd(tuple.get(apGlVolDtlHst.redBlueInd));
			 accountRecord.setCrsubjectCd(tuple.get(apGlVolDtlHst.crsubjectCd));
			 accountRecord.setDbsubjectCd(tuple.get(apGlVolDtlHst.dbsubjectCd));
			 accountRecord.setTxnDirection(tuple.get(apGlVolDtlHst.txnDirection));
			 accountRecord.setPostAmount(tuple.get(apGlVolDtlHst.subjAmount));
			 accountRecord.setAssistAccountData(tuple.get(apGlVolDtlHst.assistAccountData));
			 alist.add(accountRecord);
		}
		 singleResult.setAccount(alist);
		return singleResult;
	}
public AccountingRecordDetailsRes getFirstPart(int acctSeq, String gltSeq, TxnDetailType txnDetailType,Date postDate){
	QCactAccountNo accountNo=QCactAccountNo.cactAccountNo;//账号表
	QCactAccount account = QCactAccount.cactAccount;//账户信息表
	QGlTransOprHst glTransOprHst = QGlTransOprHst.glTransOprHst;//总账交易操作历史表
	
	BooleanExpression w1 = accountNo.acctNo.eq(account.acctNo);
	BooleanExpression w2 = glTransOprHst.txnDetailSeq.eq(gltSeq);
	BooleanExpression w3 = account.acctSeq.eq(acctSeq);
	
	String custId = new JPAQueryFactory(em).select(accountNo.custId).from(accountNo,account).where(w1,w3).fetchOne();
	
	List<Tuple> fetchOne = new JPAQueryFactory(em).select(glTransOprHst.operaId,glTransOprHst.checkerId)
			.from(glTransOprHst).where(w2,glTransOprHst.txnDetailType.eq(txnDetailType)).fetch();
	
	AccountingRecordDetailsRes singleResult = new AccountingRecordDetailsRes();
	singleResult.setCustId(custId);
	singleResult.setGltSeq(gltSeq);
	singleResult.setPostDate(postDate);
	for(Tuple t : fetchOne){
		singleResult.setCheckOperatorNo(t.get(glTransOprHst.checkerId));
		singleResult.setEntryOperatorNo(t.get(glTransOprHst.operaId));
	}
	return singleResult;
}
}
