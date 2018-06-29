package net.engining.sccc.biz.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.engining.pg.parameter.entity.model.QParameterAudit;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.profile.entity.model.QProfileUser;

@Service
public class ParameterAuditService {

	@PersistenceContext
	private EntityManager em;
	
	private HierarchicalStreamDriver xstreamDriver;
	
	protected XStream xstream;

	public FetchResponse<Map<String, Object>> parameterAuditRecord(Date startDate, Date endDate, String mtnId,
			String mtnUser, Range range) {
		
		if (xstreamDriver == null)
			xstreamDriver = new DomDriver();
		xstream = new XStream(xstreamDriver);
		xstream.setMode(XStream.NO_REFERENCES);	//在生成的xml中不使用引用，以避免出现维护问题
		xstream.ignoreUnknownElements();

		QParameterAudit qParameterAudit = QParameterAudit.parameterAudit;
		QProfileUser qProfileUser = QProfileUser.profileUser;

		BooleanExpression w1 = null;
		BooleanExpression w2 = null;
		BooleanExpression w3 = null;
		BooleanExpression w4 = null;

		if (startDate != null) {
			w1 = qParameterAudit.mtnTimestamp.goe(startDate);
		}
		if (endDate != null) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(endDate);
			calendar.add(Calendar.DATE, 1);
			endDate = calendar.getTime();
			w2 = qParameterAudit.mtnTimestamp.loe(endDate);
		}
		if (mtnId != null) {
			w3 = qParameterAudit.mtnUser.eq(mtnId);
		}
		if (mtnUser != null) {
			// 通过条件操作员姓名 到用户信息表 模糊查询用户id
			List<String> userId = new JPAQueryFactory(em).select(qProfileUser.userId).from(qProfileUser)
					.where(qProfileUser.name.like("%"+mtnUser+"%")).fetch();
			w4 = qParameterAudit.mtnUser.in(userId);
		}

		JPAQuery<Tuple> query = new JPAQueryFactory(em)
				.select(qParameterAudit.paramAuditSeq, qParameterAudit.effectiveDate, qParameterAudit.paramKey,
						qParameterAudit.paramClass, qParameterAudit.newObject, qParameterAudit.oldObject,
						qParameterAudit.updateLog, qProfileUser.name, qParameterAudit.mtnTimestamp)
				.from(qParameterAudit, qProfileUser)
				.where(qParameterAudit.mtnUser.eq(qProfileUser.userId),w1,w2,w3,w4);

		FetchResponse<Map<String, Object>> fetch = new JPAFetchResponseBuilder<Map<String, Object>>().range(range).buildAsMap(query,
				qParameterAudit.paramAuditSeq, qParameterAudit.effectiveDate, qParameterAudit.paramKey,
				qParameterAudit.paramClass, qParameterAudit.newObject, qParameterAudit.oldObject,
				qParameterAudit.updateLog, qProfileUser.name, qParameterAudit.mtnTimestamp);
		
		List<Map<String, Object>> list = fetch.getData();
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> map : list){
			if(StringUtils.isNotBlank(map.get("newObject").toString())){
				Object newOject = xstream.fromXML(map.get("newObject").toString());
				map.put("newObject", newOject);
			}
			if(StringUtils.isNotBlank(map.get("oldObject").toString())){
				Object oldObject = xstream.fromXML(map.get("oldObject").toString());
				map.put("oldObject", oldObject);
			}
			listResult.add(map);
		}
		fetch.setData(listResult);
		
		return fetch;
	}
}
