package net.engining.sccc.biz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.InOutFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.SubjectLevelDef;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlBal;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlBalHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlHst;
import net.engining.pcx.cc.infrastructure.shared.model.QGlTransOprHst;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.biz.bean.HistoricalSubjectDetail;

@Service
public class SubjectDetailService {
/**
 * 2018年4月4日 17:11:34
 * wym
 */
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ParameterFacility parameterFacility;
	
	/**
	 * 当日科目明细表查询
	 * @param subjectLevel
	 * @param subjectNo
	 * @param inOutFlag
	 * @param range
	 * @return
	 */
	public FetchResponse<Map<String, Object>> getSubjectListOnTheDay(String subjectNo, InOutFlagDef inOutFlag,Range range) {
		//TODO
		QApGlBal apglBal=QApGlBal.apGlBal;
		BooleanExpression w1=null;
		if(null==inOutFlag){
			w1=apglBal.inOutFlag.eq(InOutFlagDef.A).or(apglBal.inOutFlag.eq(InOutFlagDef.B));
		}else{
			w1=apglBal.inOutFlag.eq(inOutFlag);
		}
		JPAQuery<Tuple> query = new JPAQueryFactory(em).select(apglBal.subjectCd,apglBal.subjectName,apglBal.bizDate,apglBal.lastDbBal,apglBal.lastCrBal,
				apglBal.dbAmt,apglBal.crAmt,apglBal.dbBal,apglBal.crBal).from(apglBal);
		if(StringUtils.isNotBlank(subjectNo)){
			query.where(apglBal.subjectCd.like(subjectNo+"%"),w1).orderBy(apglBal.bizDate.desc());
		}
		return new JPAFetchResponseBuilder<Map<String, Object>>().range(range).buildAsMap(query,apglBal.subjectCd,apglBal.subjectName,apglBal.bizDate,
				apglBal.lastDbBal,apglBal.lastCrBal,apglBal.dbAmt,apglBal.crAmt,apglBal.dbBal,apglBal.crBal);
	}
	/**
	 * 当日科目明细查询详情
	 * @param subjectNo
	 * @return
	 */
	public FetchResponse<Map<String, Object>> getSubjectListDetailOnTheDay(String subjectNo) {
		QApGlVolDtl apGlVolDtl =  QApGlVolDtl.apGlVolDtl;//会计分录流水
		QGlTransOprHst glTransOprHst = QGlTransOprHst.glTransOprHst;//总账交易操作历史表
		JPAQuery<Tuple> query = new JPAQueryFactory(em).select(apGlVolDtl.volDt,apGlVolDtl.txnDetailSeq,apGlVolDtl.volDesc,apGlVolDtl.redBlueInd,apGlVolDtl.txnDirection,
				apGlVolDtl.subjAmount,apGlVolDtl.assistAccountData,glTransOprHst.operaId,glTransOprHst.checkerId)
				.from(apGlVolDtl,glTransOprHst).where(apGlVolDtl.dbsubjectCd.eq(subjectNo).or(apGlVolDtl.crsubjectCd.eq(subjectNo)),
				apGlVolDtl.txnDetailSeq.eq(glTransOprHst.txnDetailSeq));
		return new JPAFetchResponseBuilder<Map<String, Object>>().buildAsMap(query,
				apGlVolDtl.volDt,apGlVolDtl.txnDetailSeq,
				apGlVolDtl.volDesc,apGlVolDtl.redBlueInd,apGlVolDtl.txnDirection,
				apGlVolDtl.subjAmount,apGlVolDtl.assistAccountData,glTransOprHst.operaId,glTransOprHst.checkerId);
	}
	/**
	 * 历史科目明细表查询
	 * @param beginDate
	 * @param endDate
	 * @param subjectLevel
	 * @param subjectNo
	 * @param inOutFlag
	 * @param range
	 * @return
	 */

	public FetchResponse<Map<String, Object>> getHistoricalSubjectList(Date beginDate, Date endDate,String subjectNo,
			InOutFlagDef inOutFlag, Range range) {
				
		QApGlBalHst apglBalHst=QApGlBalHst.apGlBalHst;
		BooleanExpression w1=null;
		BooleanExpression w2=apglBalHst.bizDate.between(beginDate, endDate);
//		BooleanExpression w3=apglBalHst.bizDate.lt(endDate);
//		BooleanExpression w4=apglBalHst.bizDate.eq(beginDate);
		JPAQuery<Tuple> query = new JPAQueryFactory(em).select(apglBalHst.subjectCd,apglBalHst.subjectName,apglBalHst.bizDate,apglBalHst.lastDbBal,apglBalHst.lastCrBal,
				apglBalHst.dbAmt,apglBalHst.crAmt,apglBalHst.dbBal,apglBalHst.crBal).from(apglBalHst);
		if(null==inOutFlag){
			w1=apglBalHst.inOutFlag.eq(InOutFlagDef.A).or(apglBalHst.inOutFlag.eq(InOutFlagDef.B));
		}else{
			w1=apglBalHst.inOutFlag.eq(inOutFlag);
		}
		/*if(null!=subjectLevel){
			if(StringUtils.isNotBlank(subjectNo)){
				throw new ErrorMessageException(ErrorCode.CheckError, MessageFormat.format("科目层级和会计科目不能同时选subjectNo:{0}", subjectNo));
			}
			query.where(apglBalHst.subjectLevel.eq(subjectLevel),w1,w2,w3).orderBy(apglBalHst.bizDate.desc());
		}*/
		if(StringUtils.isNotBlank(subjectNo)){
			query.where(apglBalHst.subjectCd.like(subjectNo+"%"),w1,w2).orderBy(apglBalHst.bizDate.desc());
		}
		
		
		return new JPAFetchResponseBuilder<Map<String, Object>>().range(range).buildAsMap(query,
				apglBalHst.subjectCd,apglBalHst.subjectName,apglBalHst.bizDate,apglBalHst.lastDbBal,
				apglBalHst.lastCrBal,apglBalHst.dbAmt,apglBalHst.crAmt,apglBalHst.dbBal,apglBalHst.crBal);
		
	}
	/**
	 * 历史科目明细查询详情
	 * @param subjectNo
	 * @return
	 */
	
	public List<HistoricalSubjectDetail> getHistoricalSubjectListDetail(String subjectNo,Date beginDate,Date endDate) {
		QApGlVolDtlHst apglvoldtlhst = QApGlVolDtlHst.apGlVolDtlHst;
		BooleanExpression w1=apglvoldtlhst.volDt.between(beginDate, endDate);
		
		List<Tuple> fetch = new JPAQueryFactory(em).select(apglvoldtlhst.volDt,apglvoldtlhst.txnDetailSeq,apglvoldtlhst.volDesc,apglvoldtlhst.redBlueInd,apglvoldtlhst.txnDirection,
				apglvoldtlhst.subjAmount,apglvoldtlhst.assistAccountData)
				.from(apglvoldtlhst).where(w1,apglvoldtlhst.dbsubjectCd.eq(subjectNo)).fetch();
		
		List<Tuple> fetchOne=new JPAQueryFactory(em).select(apglvoldtlhst.volDt,apglvoldtlhst.txnDetailSeq,apglvoldtlhst.volDesc,apglvoldtlhst.redBlueInd,apglvoldtlhst.txnDirection,
				apglvoldtlhst.subjAmount,apglvoldtlhst.assistAccountData)
				.from(apglvoldtlhst).where(w1,apglvoldtlhst.crsubjectCd.eq(subjectNo)).fetch();
		List<Tuple> list = new ArrayList<Tuple>();
		HistoricalSubjectDetail subjectDetail = null;
		List<HistoricalSubjectDetail> array = new ArrayList<HistoricalSubjectDetail>();
		list.addAll(fetch);
		list.addAll(fetchOne);
			for(Tuple subject:list){
				subjectDetail=new HistoricalSubjectDetail();
				subjectDetail.setPostDate(subject.get(apglvoldtlhst.volDt));
				subjectDetail.setGltSeq(subject.get(apglvoldtlhst.txnDetailSeq));
				subjectDetail.setPostDesc(subject.get(apglvoldtlhst.volDesc));
				subjectDetail.setRedBlueInd(subject.get(apglvoldtlhst.redBlueInd));
				subjectDetail.setTxnDirection(subject.get(apglvoldtlhst.txnDirection));
				subjectDetail.setPostAmount(subject.get(apglvoldtlhst.subjAmount));
				subjectDetail.setAssistAccountData(subject.get(apglvoldtlhst.assistAccountData));
				array.add(subjectDetail);
			}
		
		return array;
		
	}
	public List<HistoricalSubjectDetail> getTxnSubjectQuery(List<HistoricalSubjectDetail> subjectList) {
		QGlTransOprHst glTransOprHst = QGlTransOprHst.glTransOprHst;//总账交易操作历史表
		List list = new ArrayList();
		for(int i=0;i<subjectList.size();i++){
			list.add(subjectList.get(i).getGltSeq());
		}
		List<Tuple> fetch = new JPAQueryFactory(em).select(glTransOprHst.operaId,glTransOprHst.checkerId,glTransOprHst.txnDetailSeq)
		.from(glTransOprHst).where(glTransOprHst.txnDetailSeq.in(list)).fetch();
		for(HistoricalSubjectDetail subjectDetail :subjectList){
			for(Tuple txnSeq:fetch){
				if(subjectDetail.getGltSeq().equals(txnSeq.get(glTransOprHst.txnDetailSeq))){
					subjectDetail.setOpratorId(txnSeq.get(glTransOprHst.operaId));
					subjectDetail.setOpratorId(txnSeq.get(glTransOprHst.checkerId));
					break;
				}
			}
		}
		return subjectList;
	}
	
	/**
	 * 根据科目层级查询科目号
	 * @param subjectLevelDef 
	 */
	public List<Map<String, Object>> querySubjectNo(SubjectLevelDef subjectLevelDef) {
		Map<String, Object> subMap = null;
		List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
		Map<String, Subject> subjects = parameterFacility.getParameterMap(Subject.class);
		
		String currSubLevel = null;
		if (subjectLevelDef.equals(SubjectLevelDef.S)) currSubLevel = "1";
		if (subjectLevelDef.equals(SubjectLevelDef.L)) currSubLevel = "3";
		if (subjectLevelDef.equals(SubjectLevelDef.O)) currSubLevel = "2";
		for (Subject subject : subjects.values()) {
			if (subject.subjectHierarchy.equals(currSubLevel)) {
				subMap = new HashMap<String, Object>();
				subMap.put("subjectCd", subject.subjectCd);
				subMap.put("name", subject.name);
				subList.add(subMap);
			}
		}
		return subList;
	}
	
	public FetchResponse<Map<String, Object>> queryHistoricalSubjectNo(SubjectLevelDef subjectLevel) {
		QApGlBalHst q = QApGlBalHst.apGlBalHst;
		JPAQuery<Tuple> query = new JPAQueryFactory(em).select(q.subjectCd,q.subjectName).from(q)
				.where(q.subjectLevel.eq(subjectLevel));
		return new JPAFetchResponseBuilder<Map<String, Object>>().buildAsMap(query,q.subjectCd,q.subjectName);
	}
}
