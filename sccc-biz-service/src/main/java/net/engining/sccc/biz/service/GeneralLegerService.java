package net.engining.sccc.biz.service;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.InOutFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.SubjectLevelDef;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlBal;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlBalHst;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;

@Service
public class GeneralLegerService {

	@PersistenceContext
	private EntityManager em;
/**
 *当日总账查询 
 * @param subjectLevel
 * @param inOutFlag
 * @param range
 * @return
 */
	public FetchResponse<Map<String, Object>> generalLegerQueryOntheDay(SubjectLevelDef subjectLevel,
			InOutFlagDef inOutFlag, Range range) {
		QApGlBal apglBal = QApGlBal.apGlBal;//总账表
		BooleanExpression w1=null;
		if(null==inOutFlag){
			w1=apglBal.inOutFlag.eq(InOutFlagDef.A).or(apglBal.inOutFlag.eq(InOutFlagDef.B));
		}else{
			w1=apglBal.inOutFlag.eq(inOutFlag);
		}
		JPAQuery<Tuple> query = new JPAQueryFactory(em)
				.select(apglBal.subjectCd, apglBal.subjectName, apglBal.lastCrBal, apglBal.lastDbBal, apglBal.crAmt,
						apglBal.dbAmt,apglBal.dbCount,apglBal.crCount,apglBal.ytdCrAmt, apglBal.ytdDbAmt, apglBal.dbBal, apglBal.crBal)
				.from(apglBal).where(w1, apglBal.subjectLevel.eq(subjectLevel));

		return new JPAFetchResponseBuilder<Map<String, Object>>().range(range).buildAsMap(query, apglBal.subjectCd,
				apglBal.subjectName, apglBal.lastCrBal, apglBal.lastDbBal, apglBal.crAmt, apglBal.dbAmt,
				apglBal.dbCount,apglBal.crCount,apglBal.ytdCrAmt, apglBal.ytdDbAmt, apglBal.dbBal, apglBal.crBal);
	}
/**
 * 历史总账查询
 * @param subjectLevel
 * @param beginDate
 * @param endDate
 * @param inOutFlag
 * @param range
 * @return
 */
	public FetchResponse<Map<String, Object>> historicalGeneralLegerQuery(SubjectLevelDef subjectLevel, Date beginDate,
			Date endDate, InOutFlagDef inOutFlag, Range range) {
		QApGlBalHst apglBalHst = QApGlBalHst.apGlBalHst;// 总账历史表
		BooleanExpression w1=null;
		if(null==inOutFlag){
			w1=apglBalHst.inOutFlag.eq(InOutFlagDef.A).or(apglBalHst.inOutFlag.eq(InOutFlagDef.B));
		}else{
			w1=apglBalHst.inOutFlag.eq(inOutFlag);
		}
		JPAQuery<Tuple> query = new JPAQueryFactory(em).select(apglBalHst.subjectCd, apglBalHst.subjectName,
				apglBalHst.lastCrBal, apglBalHst.lastDbBal, apglBalHst.crAmt, apglBalHst.dbAmt, apglBalHst.dbCount,
				apglBalHst.crCount,apglBalHst.ytdCrAmt,apglBalHst.ytdDbAmt, apglBalHst.dbBal, apglBalHst.crBal)
				.from(apglBalHst)
				.where(w1, apglBalHst.subjectLevel.eq(subjectLevel),
						apglBalHst.bizDate.between(beginDate, endDate));

		return new JPAFetchResponseBuilder<Map<String, Object>>().range(range).buildAsMap(query, apglBalHst.subjectCd,
				apglBalHst.subjectName, apglBalHst.lastCrBal, apglBalHst.lastDbBal, apglBalHst.crAmt, apglBalHst.dbAmt,
				apglBalHst.dbCount,apglBalHst.crCount,apglBalHst.ytdCrAmt, apglBalHst.ytdDbAmt,
				apglBalHst.dbBal, apglBalHst.crBal);

	}

}
