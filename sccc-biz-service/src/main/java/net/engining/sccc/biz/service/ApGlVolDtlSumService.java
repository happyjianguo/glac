package net.engining.sccc.biz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAss;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAssHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAssSum;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAssSumHst;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.sccc.biz.bean.ApGlVoldtlSumReq;
import net.engining.sccc.biz.bean.FetchDataProcess;
import net.engining.sccc.biz.bean.SortDef;
import net.engining.sccc.biz.bean.VodtlAssSumHstDetail;

@Service
public class ApGlVolDtlSumService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ParameterFacility parameterFacility;

	@Autowired
	private Provider7x24 provider7x24;
	
	/**
	 * 当日辅助核算余额查询
	 * 
	 * @param operDate
	 * @param checkerId
	 * 
	 * @return
	 */
	public FetchResponse<Map<String, Object>> getVolDtlSumRes(ApGlVoldtlSumReq req) {

		QApGlVolDtlAssSum qApGlVolDtlAssSum = QApGlVolDtlAssSum.apGlVolDtlAssSum;

		JPAQuery<Tuple> query = new JPAQueryFactory(em)
				.select(qApGlVolDtlAssSum.subjectCd, qApGlVolDtlAssSum.assistType, qApGlVolDtlAssSum.assistAccountValue,
						qApGlVolDtlAssSum.volDt, qApGlVolDtlAssSum.lastDbBal, qApGlVolDtlAssSum.lastCrBal,
						qApGlVolDtlAssSum.dbBal,qApGlVolDtlAssSum.crBal,qApGlVolDtlAssSum.dbCount,qApGlVolDtlAssSum.crCount,qApGlVolDtlAssSum.dbAmt, qApGlVolDtlAssSum.crAmt)
				.from(qApGlVolDtlAssSum);
		if (SortDef.A.equals(req.getSortDef())) { 
			query.orderBy(qApGlVolDtlAssSum.setupDate.desc(), qApGlVolDtlAssSum.assistType.desc());
		}

		if (SortDef.B.equals(req.getSortDef())) {
			query.orderBy(qApGlVolDtlAssSum.assistType.desc(), qApGlVolDtlAssSum.subjectCd.desc());
		}
		if (SortDef.C.equals(req.getSortDef())) {
			query.orderBy(qApGlVolDtlAssSum.setupDate.desc(), qApGlVolDtlAssSum.assistType.desc(),
					qApGlVolDtlAssSum.subjectCd.desc());
		}

		if (StringUtils.isNoneBlank(req.getSubjectCd()))
			query.where(qApGlVolDtlAssSum.subjectCd.eq(req.getSubjectCd()));

		if (null != req.getAssistType())
			query.where(qApGlVolDtlAssSum.assistType.eq(req.getAssistType()));

		return new JPAFetchResponseBuilder<Map<String, Object>>().range(req.getRange()).buildAsMap(query,
				qApGlVolDtlAssSum.subjectCd, qApGlVolDtlAssSum.assistType, qApGlVolDtlAssSum.assistAccountValue,
				qApGlVolDtlAssSum.volDt, qApGlVolDtlAssSum.lastDbBal, qApGlVolDtlAssSum.lastCrBal,
				qApGlVolDtlAssSum.dbBal,qApGlVolDtlAssSum.crBal,qApGlVolDtlAssSum.dbCount,qApGlVolDtlAssSum.crCount,
				qApGlVolDtlAssSum.dbAmt, qApGlVolDtlAssSum.crAmt);
	}

	/**
	 * 在返回数据中添加科目名称
	 * 
	 * @param result
	 * @return
	 */
	public FetchResponse<Map<String, Object>> insertSubjectIntoResult(FetchResponse<Map<String, Object>> result) {
		Map<String, Subject> subjects = parameterFacility.getParameterMap(Subject.class);
		List<Map<String, Object>> resultList = result.getData();
		List<Map<String, Object>> fetchList = new ArrayList<>();
		for (Map<String, Object> resultMap : resultList) {
			String subjectCd = (String) resultMap.get("subjectCd");
			String subjectName = subjects.get(subjectCd).name;
			resultMap.put("subjectName", subjectName);
			fetchList.add(resultMap);
		}
		result.setData(fetchList);
		return result;
	}

	/**
	 * 当日辅助核算余额明细查询
	 * 
	 * @param operDate
	 * @param checkerId
	 * 
	 * @returnreq.getSubjectCd(),req.getAssistType(),req.getAssistAccountData()
	 */
	public List<Tuple> vodtlAssSumDetailQuery(String subjectCd, AssistAccountingType assistType, String assistAccountData) {
		BooleanExpression w1 = null;
		BooleanExpression w2 = null;
		BooleanExpression w3 = null;
		QApGlVolDtlAss qApGlVolDtlAss = QApGlVolDtlAss.apGlVolDtlAss;
		if (StringUtils.isNotBlank(subjectCd)) {
			w1 = qApGlVolDtlAss.subjectCd.eq(subjectCd);
		}
		if (assistType != null) {
			w2 = qApGlVolDtlAss.assistType.eq(assistType);
		}
		if (assistAccountData != null) {
			w2 = qApGlVolDtlAss.assistAccountValue.eq(assistAccountData);
		}
		List<Tuple> fetch = new JPAQueryFactory(em)
				.select(qApGlVolDtlAss.volDt, qApGlVolDtlAss.txnDetailSeq, qApGlVolDtlAss.transDate,
						qApGlVolDtlAss.subjAmount, qApGlVolDtlAss.txnDirection, qApGlVolDtlAss.volSeq,
						qApGlVolDtlAss.volDesc, qApGlVolDtlAss.redBlueInd, qApGlVolDtlAss.refNo)
				.from(qApGlVolDtlAss).where(w1, w2,w3).fetch();
		return fetch;
	}

	/**
	 * 历史辅助核算余额查询
	 * 
	 * @param operDate
	 * @param checkerId
	 * 
	 * @return
	 */
	public FetchResponse<Map<String, Object>> getVolDtlSumHstRes(ApGlVoldtlSumReq req) {

		QApGlVolDtlAssSumHst qApGlVolDtlAssSumHst = QApGlVolDtlAssSumHst.apGlVolDtlAssSumHst;

		JPAQuery<Tuple> query = new JPAQueryFactory(em).select(qApGlVolDtlAssSumHst.subjectCd,
				qApGlVolDtlAssSumHst.assistType, qApGlVolDtlAssSumHst.assistAccountValue, qApGlVolDtlAssSumHst.volDt,
				qApGlVolDtlAssSumHst.lastDbBal, qApGlVolDtlAssSumHst.lastCrBal, qApGlVolDtlAssSumHst.dbCount,
				qApGlVolDtlAssSumHst.crCount, qApGlVolDtlAssSumHst.dbBal, qApGlVolDtlAssSumHst.crBal,
				qApGlVolDtlAssSumHst.dbAmt, qApGlVolDtlAssSumHst.crAmt).from(qApGlVolDtlAssSumHst);// .orderBy(w5,// w6)
																									
		query.where(qApGlVolDtlAssSumHst.volDt.between(req.getVolStartDt(), req.getVolEndDt()));

		if (StringUtils.isNotBlank(req.getSubjectCd()))
			query.where(qApGlVolDtlAssSumHst.subjectCd.eq(req.getSubjectCd()));

		if (req.getAssistType()!= null)
			query.where(qApGlVolDtlAssSumHst.assistType.eq(req.getAssistType()));

		if (SortDef.A.equals(req.getSortDef())) {
			query.orderBy(qApGlVolDtlAssSumHst.setupDate.desc(), qApGlVolDtlAssSumHst.assistType.desc());
		}
		if (SortDef.B.equals(req.getSortDef())) {
			query.orderBy(qApGlVolDtlAssSumHst.assistType.desc(), qApGlVolDtlAssSumHst.subjectCd.desc());
		}
		if (SortDef.C.equals(req.getSortDef())) {
			query.orderBy(qApGlVolDtlAssSumHst.setupDate.desc(), qApGlVolDtlAssSumHst.assistType.desc(),
					qApGlVolDtlAssSumHst.subjectCd.desc());
		}
		return new JPAFetchResponseBuilder<Map<String, Object>>().range(req.getRange()).buildAsMap(query,
				qApGlVolDtlAssSumHst.subjectCd, qApGlVolDtlAssSumHst.assistType,
				qApGlVolDtlAssSumHst.assistAccountValue, qApGlVolDtlAssSumHst.volDt, qApGlVolDtlAssSumHst.lastDbBal,
				qApGlVolDtlAssSumHst.lastCrBal, qApGlVolDtlAssSumHst.dbCount, qApGlVolDtlAssSumHst.crCount,
				qApGlVolDtlAssSumHst.dbBal, qApGlVolDtlAssSumHst.crBal, qApGlVolDtlAssSumHst.dbAmt,
				qApGlVolDtlAssSumHst.crAmt);
	}

	/**
	 * 历史辅助核算余额明细查询
	 * 
	 * @param operDate
	 * @param checkerId
	 * 
	 * @return
	 */
	public FetchDataProcess<VodtlAssSumHstDetail> vodtlAssSumHstDetailQuery(String subjectCd, AssistAccountingType assistType, String assistAccountData) {

		QApGlVolDtlAssHst qApGlVolDtlAssHst = QApGlVolDtlAssHst.apGlVolDtlAssHst;
		VodtlAssSumHstDetail vodtl = null;
		List<VodtlAssSumHstDetail> alist = new ArrayList<VodtlAssSumHstDetail>();
		BooleanExpression w1=null;
		BooleanExpression w2 = null;
		BooleanExpression w3 = null;
		if(null!=assistType){
			 w1 = qApGlVolDtlAssHst.assistType.eq(assistType);
		}
		if(StringUtils.isNotBlank(subjectCd)){
			w2 = qApGlVolDtlAssHst.subjectCd.eq(subjectCd);
		}
		if(StringUtils.isNotBlank(subjectCd)){
			w3 = qApGlVolDtlAssHst.assistAccountValue.eq(assistAccountData);
		}
		
		List<Tuple> fetch = new JPAQueryFactory(em)
				.select(qApGlVolDtlAssHst.volDt, qApGlVolDtlAssHst.txnDetailSeq, qApGlVolDtlAssHst.transDate,
						qApGlVolDtlAssHst.subjAmount, qApGlVolDtlAssHst.txnDirection, qApGlVolDtlAssHst.volSeq,
						qApGlVolDtlAssHst.volDesc, qApGlVolDtlAssHst.redBlueInd, qApGlVolDtlAssHst.refNo)
				.from(qApGlVolDtlAssHst).where(w1, w2, w3).fetch();
		for (Tuple f : fetch) {
			vodtl = new VodtlAssSumHstDetail();
			vodtl.setPostDate(f.get(qApGlVolDtlAssHst.volDt));
			vodtl.setTxnDetailSeq(f.get(qApGlVolDtlAssHst.txnDetailSeq));
			vodtl.setTransDate(f.get(qApGlVolDtlAssHst.transDate));
			vodtl.setSubjAmount(f.get(qApGlVolDtlAssHst.subjAmount));
			vodtl.setTxnDirection(f.get(qApGlVolDtlAssHst.txnDirection));
			vodtl.setVolSeq(f.get(qApGlVolDtlAssHst.volSeq));
			vodtl.setVolDesc(f.get(qApGlVolDtlAssHst.volDesc));
			vodtl.setRedBlueInd(f.get(qApGlVolDtlAssHst.redBlueInd));
			vodtl.setRefNo(f.get(qApGlVolDtlAssHst.refNo));
			alist.add(vodtl);
		}
		FetchDataProcess dataProcess = new FetchDataProcess();
		dataProcess.setData(alist);
		dataProcess.setRowCount(alist.size());
		return dataProcess;
	}

	//返回当前时间的yyyy-MM-dd格式
	private Date dateConversion(){
		Date date = new Date();
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormate.format(date);
		try {
			date = dateFormate.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
}
