package net.engining.sccc.biz.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.facility.SystemStatusFacility;
import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.gm.param.model.SystemStatus;
import net.engining.pcx.cc.infrastructure.shared.enums.PostTypeDef;
import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlBal;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlBalHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pcx.cc.param.model.enums.SubjectType;
import net.engining.pcx.cc.process.service.support.Provider7x24;
//import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.sccc.biz.bean.ProfitAndLossOne;
import net.engining.sccc.biz.bean.ProfitAndLossTwo;

@Service
public class FinancialService {

	@Autowired
	ParameterFacility parameterFacility;
	@Autowired
	private Provider4Organization provider4Organization;
	@Autowired
	private ApGlTxnService apGlTxnService;
	@Autowired
	private ApGlVolDtlService apGlVolDtlService;
	@Autowired
	private Provider7x24 provider7x24;
	@Autowired
	SystemStatusFacility systemFacility;
	@PersistenceContext
	private EntityManager em;

	public List<Map<String,Object>> profitAndLoss(Date endDate,String subjectNo) throws ParseException {
		QApGlBalHst qBalHst = QApGlBalHst.apGlBalHst;
		QApGlBal qBal=QApGlBal.apGlBal;
		List list = new ArrayList();
		List<ProfitAndLossOne> oneList = new ArrayList<ProfitAndLossOne>();
		List<ProfitAndLossTwo> twoList = new ArrayList<ProfitAndLossTwo>();
		String bizDate=new SimpleDateFormat("yyyy-MM").format(endDate);
		SystemStatus systemStatus = systemFacility.getSystemStatus();
		String businessDate=new SimpleDateFormat("yyyy-MM").format(systemStatus.businessDate);
		
		Map tableOne = new HashMap();
		List<Map<String, Object>> module = new ArrayList<Map<String, Object>>();
		Map<String,Subject> ss = parameterFacility.getParameterMap(Subject.class);
		for(Subject subject : ss.values()){
			if(SubjectType.C.equals(subject.type)){
				list.add(subject.subjectCd);
			}
		}
		List<Tuple> fetch = new ArrayList();
		if(bizDate.equals(businessDate)){
			fetch=new JPAQueryFactory(em).select(qBal.bizDate,qBal.subjectCd,qBal.subjectName
					,qBal.crBal,qBal.dbBal).from(qBal).where(qBal.bizDate.eq(endDate),qBal.subjectCd.in(list)).fetch();
			for (Tuple tuple : fetch) {
				ProfitAndLossOne one = new ProfitAndLossOne();
				ProfitAndLossTwo two = new ProfitAndLossTwo();
				one.setBizDate(tuple.get(qBal.bizDate));
				one.setSubjectNo(tuple.get(qBal.subjectCd));
				one.setSubjectName(tuple.get(qBal.subjectName));
				
				BigDecimal crBal = tuple.get(qBal.crBal);
				BigDecimal dbBal = tuple.get(qBal.dbBal);
				BigDecimal balance = crBal.subtract(dbBal).abs();
				one.setBalance(balance);
				if(crBal.subtract(dbBal).compareTo(BigDecimal.ZERO)>0){//余额方向在贷方
					one.setDirection(TxnDirection.C.toString());
					two.setDbSubjectNo(tuple.get(qBal.subjectCd));//结转的科目号就是在借方
					two.setCrSubjectNo(subjectNo);
					two.setPostAmount(balance);
				}else if(crBal.subtract(dbBal).compareTo(BigDecimal.ZERO)<0){
					one.setDirection(TxnDirection.D.toString());
					two.setDbSubjectNo(subjectNo);
					two.setCrSubjectNo(tuple.get(qBal.subjectCd));
					two.setPostAmount(balance);
				}else{
					one.setDirection(TxnDirection.O.toString());
					two.setDbSubjectNo(null);
					two.setCrSubjectNo(null);
					two.setPostAmount(null);
				}
				
				oneList.add(one);
				twoList.add(two);
			}
		}else{
			fetch=new JPAQueryFactory(em).select(qBalHst.bizDate,qBalHst.subjectCd,qBalHst.subjectName
					,qBalHst.crBal,qBalHst.dbBal).from(qBalHst).where(qBalHst.bizDate.eq(endDate),qBalHst.subjectCd.in(list)).fetch();
			
			for (Tuple tuple : fetch) {
				ProfitAndLossOne one = new ProfitAndLossOne();
				ProfitAndLossTwo two = new ProfitAndLossTwo();
				one.setBizDate(tuple.get(qBalHst.bizDate));
				one.setSubjectNo(tuple.get(qBalHst.subjectCd));
				one.setSubjectName(tuple.get(qBalHst.subjectName));
				
				BigDecimal crBal = tuple.get(qBalHst.crBal);
				BigDecimal dbBal = tuple.get(qBalHst.dbBal);
				BigDecimal balance = crBal.subtract(dbBal).abs();
				one.setBalance(balance);
				if(crBal.subtract(dbBal).compareTo(BigDecimal.ZERO)>0){//余额方向在贷方
					one.setDirection(TxnDirection.C.toString());
					two.setDbSubjectNo(tuple.get(qBalHst.subjectCd));//结转的科目号就是在借方
					two.setCrSubjectNo(subjectNo);
					two.setPostAmount(balance);
				}else if(crBal.subtract(dbBal).compareTo(BigDecimal.ZERO)<0){
					one.setDirection(TxnDirection.D.toString());
					two.setDbSubjectNo(subjectNo);
					two.setCrSubjectNo(tuple.get(qBalHst.subjectCd));
					two.setPostAmount(balance);
				}else{
					one.setDirection(TxnDirection.O.toString());
					two.setDbSubjectNo(null);
					two.setCrSubjectNo(null);
					two.setPostAmount(null);
				}
				oneList.add(one);
				twoList.add(two);
			}
		}
		
		tableOne.put("one", oneList);
		tableOne.put("two", twoList);
		module.add(tableOne);
		return module;
	}
	
	@Transactional
	public void insertProfit(List<Map<String, Object>> profitAndLoss) {
		QApGlVolDtl qDtl = QApGlVolDtl.apGlVolDtl;
		for (Map<String, Object> map : profitAndLoss) {
			ApGlTxn txn = new ApGlTxn();
			// 对空值填入默认值
			txn.fillDefaultValues();
			txn.setPostDate(provider7x24.getCurrentDate().toDate());
			txn.setPostGlInd(PostGlInd.Normal);
			txn.setCurrCd("156");
			txn.setOrg(provider4Organization.getCurrentOrganizationId());
			txn.setPostType(PostTypeDef.MANU);
			txn.setTransDate(provider7x24.getCurrentDate().toDate());
			txn.setBizDate(provider7x24.getCurrentDate().toDate());
			txn.setPostAmount(new BigDecimal((Double)map.get("postAmount")));
			txn.setPostCode("jiezhuan");
			txn.setPostDesc("损益结转");
			txn.setAcctSeq(0);
			// 单机构默认赋值
			em.persist(txn);
			map.put("postSeq", txn.getGltSeq());
		}
		
		for (Map<String, Object> map : profitAndLoss) {
			for(String key:map.keySet()){
				if("dbSubjectNo".equals(key)){//这个要区分贷方借方，科目号，封装bean的时候，没有key
					ApGlVolDtl volDtl = new ApGlVolDtl();
					volDtl.setTransDate(provider7x24.getCurrentDate().toDate());
					volDtl.fillDefaultValues();
					volDtl.setOrg(provider4Organization.getCurrentOrganizationId());
					volDtl.setTxnDetailSeq(map.get("postSeq").toString());
					volDtl.setBizDate(provider7x24.getCurrentDate().toDate());
					volDtl.setTxnDetailType(TxnDetailType.C);
					volDtl.setSubjAmount(new BigDecimal((Double)map.get("postAmount")));
					volDtl.setCurrCd("156");
					volDtl.setTxnDirection(TxnDirection.D);
					volDtl.setDbsubjectCd(map.get("dbSubjectNo").toString());
					apGlVolDtlService.apGlVolDtlAdd(volDtl);
				}
				else if("crSubjectNo".equals(key)){
					ApGlVolDtl volDtl = new ApGlVolDtl();
					volDtl.setTransDate(provider7x24.getCurrentDate().toDate());
					volDtl.fillDefaultValues();
					volDtl.setOrg(provider4Organization.getCurrentOrganizationId());
					volDtl.setTxnDetailSeq(map.get("postSeq").toString());
					volDtl.setBizDate(provider7x24.getCurrentDate().toDate());
					volDtl.setTxnDetailType(TxnDetailType.C);
					volDtl.setSubjAmount(new BigDecimal((Double)map.get("postAmount")));
					volDtl.setCurrCd("156");
					volDtl.setTxnDirection(TxnDirection.C);
					volDtl.setCrsubjectCd(map.get("crSubjectNo").toString());
					apGlVolDtlService.apGlVolDtlAdd(volDtl);
				}
			}
		}
		
	}
}
