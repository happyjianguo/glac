package net.engining.sccc.accounting.sharding.service;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlHst;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;

/**
 * @author luxue
 *
 */
@Service
public class OuterLedgerTxnHstService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void backup2Hst(ApGlTxn item){
		
		ApGlTxnHst apGltxnHis = new ApGlTxnHst();
		apGltxnHis.setGltSeq(item.getGltSeq());
		apGltxnHis.setAcctSeq(item.getAcctSeq());
		apGltxnHis.setAcqBranch(item.getAcqBranch());
		apGltxnHis.setAgeGroupCd(item.getAgeGroupCd());
		apGltxnHis.setCurrCd(item.getCurrCd());
		apGltxnHis.setOwingBranch(item.getOwingBranch());
		apGltxnHis.setPostAmount(item.getPostAmount());
		apGltxnHis.setPostCode(item.getPostCode());
		apGltxnHis.setPostDate(item.getPostDate());
		apGltxnHis.setPostDesc(item.getPostDesc());
		apGltxnHis.setPostGlInd(item.getPostGlInd());
		apGltxnHis.setTxnDetailSeq(item.getTxnDetailSeq());
		apGltxnHis.setTxnDetailType(item.getTxnDetailType());
		apGltxnHis.setTxnDirection(item.getTxnDirection());
		apGltxnHis.setBizDate(new Date());
		
		em.persist(apGltxnHis);
	}
	
	@Transactional
	public void backup2ApGlVolDtlHst(ApGlVolDtlHst item){
		
		ApGlVolDtlHst apGlVolDtlHst = new ApGlVolDtlHst();
		BeanUtils.copyProperties(item, apGlVolDtlHst);
		apGlVolDtlHst.setBizDate(new Date());
		apGlVolDtlHst.fillDefaultValues();
		
		em.persist(apGlVolDtlHst);
	}
	
	public FetchResponse<Map<String, Object>> queryApGlVolDtlHst(String dbSubjectCd, String crSubjectCd, Date volDate, Range range){
		QApGlVolDtlHst qApGlVolDtlHst = QApGlVolDtlHst.apGlVolDtlHst;
		JPAQuery<Tuple> query = new JPAQueryFactory(em)
				.select(
						qApGlVolDtlHst.glvSeq,
						qApGlVolDtlHst.branchNo,
						qApGlVolDtlHst.crsubjectCd,
						qApGlVolDtlHst.dbsubjectCd,
						qApGlVolDtlHst.org
						)
				.from(qApGlVolDtlHst);
		
		BooleanExpression p1 = null;
		BooleanExpression p2 = null;
		if(StringUtils.isNoneBlank(dbSubjectCd))
			p1 = qApGlVolDtlHst.dbsubjectCd.eq(dbSubjectCd);
		
		if(StringUtils.isNoneBlank(crSubjectCd))
			p2 = qApGlVolDtlHst.crsubjectCd.eq(crSubjectCd);
		
		if(volDate != null)
			query.where(qApGlVolDtlHst.volDt.eq(volDate));
		
//		query = query.where(p1.or(p2));
		query = query.where(p1,p2);
		
		return new JPAFetchResponseBuilder<Map<String, Object>>()
				.range(range)
				.buildAsMap(query, 
						qApGlVolDtlHst.glvSeq,
						qApGlVolDtlHst.branchNo,
						qApGlVolDtlHst.crsubjectCd,
						qApGlVolDtlHst.dbsubjectCd,
						qApGlVolDtlHst.org);
	}
	
	public FetchResponse<Map<String, Object>> queryHst(Integer acctSeq, Date postDate, Range range){
		QApGlTxnHst qApGlTxnHst = QApGlTxnHst.apGlTxnHst;
		JPAQuery<Tuple> query = new JPAQueryFactory(em)
				.select(
						qApGlTxnHst.gltSeq,
						qApGlTxnHst.acctSeq,
						qApGlTxnHst.postAmount,
						qApGlTxnHst.postCode,
						qApGlTxnHst.postDate)
				.from(qApGlTxnHst);
		
		if(acctSeq != null)
			query.where(qApGlTxnHst.acctSeq.eq(acctSeq));
		
		if(postDate != null)
			query.where(qApGlTxnHst.postDate.eq(postDate));
		
		return new JPAFetchResponseBuilder<Map<String, Object>>()
				.range(range)
				.buildAsMap(query, 
				qApGlTxnHst.gltSeq,
				qApGlTxnHst.acctSeq,
				qApGlTxnHst.postAmount,
				qApGlTxnHst.postCode,
				qApGlTxnHst.postDate);
	}

	public static void main(String[] args){
//		Date dt = new Date();
////		System.out.println(dt.hashCode());
//		
//		System.out.println(TxnDetailType.A.hashCode());
//		System.out.println(TxnDetailType.C.hashCode());
//		System.out.println(TxnDetailType.G.hashCode());
//		
//		System.out.println("12584695236".hashCode());
//		System.out.println("32569874651285".hashCode());
	}
}
