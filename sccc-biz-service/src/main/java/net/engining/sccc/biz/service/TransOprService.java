package net.engining.sccc.biz.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.GlTransOprHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QGlTransOprHst;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;

@Service
public class TransOprService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Provider7x24 provider7x24;

	/**
	 * 记账复核查询
	 * 
	 * @param operDate
	 * @param checkerId
	 * 
	 * @return
	 */
	@Transactional
	public FetchResponse<Map<String, Object>> transOprQuery(String operateId, Date transDate,String currOperateId, Range range) {

		QApGlTxn qApGlTxn = QApGlTxn.apGlTxn;
		QGlTransOprHst qGlTransOprHst = QGlTransOprHst.glTransOprHst;
		BooleanExpression w = qApGlTxn.gltSeq.eq(qGlTransOprHst.txnDetailSeq);
		
		JPAQuery<Tuple> query = new JPAQueryFactory(em)
				.select(qApGlTxn.transDate, qApGlTxn.gltSeq, qGlTransOprHst.operaId, qApGlTxn.postDesc,
						qApGlTxn.postAmount, qGlTransOprHst.txnDetailType, qGlTransOprHst.checkFlag, qGlTransOprHst.seq)
				.from(qApGlTxn, qGlTransOprHst).where(w).orderBy(qApGlTxn.transDate.desc());

		if (transDate != null)
			query.where(qApGlTxn.transDate.eq(transDate));
		
		if (StringUtils.isNotBlank(currOperateId)) 
			query.where((qGlTransOprHst.checkFlag.eq(CheckFlagDef.B).and(qGlTransOprHst.operaId.notEqualsIgnoreCase(currOperateId))).or(qGlTransOprHst.checkFlag.eq(CheckFlagDef.C).and(qGlTransOprHst.operaId.eq(currOperateId))));
		if (StringUtils.isNotBlank(operateId)) {
			query.where(qGlTransOprHst.operaId.eq(operateId));
		}
		
		return new JPAFetchResponseBuilder<Map<String, Object>>().range(range).buildAsMap(query, qApGlTxn.transDate,
				qApGlTxn.gltSeq, qGlTransOprHst.operaId, qApGlTxn.postDesc, qApGlTxn.postAmount,
				qGlTransOprHst.txnDetailType, qGlTransOprHst.checkFlag, qGlTransOprHst.seq);
	}

	/**
	 * 更新入账日期，复核标志（记账复核提交，拒绝）
	 * 
	 * @param profileRoleForm
	 * @param bindingResult
	 * @return
	 */
	@Transactional
	public void transOprUpdate(CheckFlagDef checkFlagDef, String gltSeq, Integer seq, String refuseReason,String checkerId) {

		if (CheckFlagDef.C.equals(checkFlagDef) && refuseReason.isEmpty()) {
			throw new ErrorMessageException(ErrorCode.SystemError, "拒绝原因不能为空");
		}
		//更新当日总账表的BizDate
 		ApGlTxn apGlTxn = em.find(ApGlTxn.class, gltSeq);
		apGlTxn.setLastUpdateDate(new Date());
		apGlTxn.setBizDate(provider7x24.getCurrentDate().toDate());

		//更新分录表的BizDate
		QApGlVolDtl qApGlVolDtl = QApGlVolDtl.apGlVolDtl;
		new JPAQueryFactory(em).update(qApGlVolDtl)
							   .set(qApGlVolDtl.bizDate, provider7x24.getCurrentDate().toDate())
							   .set(qApGlVolDtl.lastUpdateDate, new Date())
							   .where(qApGlVolDtl.txnDetailSeq.eq(gltSeq))
							   .execute();
		
		GlTransOprHst glTransOprHst = em.find(GlTransOprHst.class, seq);
		glTransOprHst.setCheckFlag(checkFlagDef);
		glTransOprHst.setLastUpdateDate(new Date());
		glTransOprHst.setOperDate(new Date());
		glTransOprHst.setCheckDate(new Date());
		glTransOprHst.setCheckerId(checkerId);
		glTransOprHst.setBizDate(provider7x24.getCurrentDate().toDate());
		if (CheckFlagDef.C.equals(checkFlagDef)) {
			glTransOprHst.setRefuseReason(refuseReason);
		}
	}

	/**
	 * 记账复核明细查询
	 * 
	 * @param operDate
	 * @param checkerId
	 * 
	 * @return
	 */
	public List<Tuple> transOprDetailQuery(String gltSeq, TxnDetailType txnDetailType) {

		QApGlVolDtl qApGlVolDtl = QApGlVolDtl.apGlVolDtl;

		List<Tuple> fetch = new JPAQueryFactory(em)
				.select(qApGlVolDtl.redBlueInd, qApGlVolDtl.txnDirection, qApGlVolDtl.dbsubjectCd,
						qApGlVolDtl.crsubjectCd, qApGlVolDtl.subjAmount, qApGlVolDtl.assistAccountData)
				.from(qApGlVolDtl)
				.where(qApGlVolDtl.txnDetailSeq.eq(gltSeq)).fetch();
		return fetch;
	}
}
