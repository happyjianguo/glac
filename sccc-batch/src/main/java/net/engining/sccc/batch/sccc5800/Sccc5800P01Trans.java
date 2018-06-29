package net.engining.sccc.batch.sccc5800;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnPost;
import net.engining.pcx.cc.infrastructure.shared.model.TransReport;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pg.parameter.ParameterFacility;

@Service
@StepScope
public class Sccc5800P01Trans implements ItemProcessor<ApGlVolDtl, Object> {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private ParameterFacility parameterFacility;
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	public Object process(ApGlVolDtl item) throws Exception {
		Subject subject = parameterFacility.loadParameter(Subject.class,
				item.getTxnDirection().equals(TxnDirection.D) ? item.getDbsubjectCd() : item.getCrsubjectCd());
		//查询当日总账流水表
		QApGlTxn q = QApGlTxn.apGlTxn;
		ApGlTxn apGlTxn = new JPAQueryFactory(em).select(q).from(q).where(q.gltSeq.eq(item.getTxnDetailSeq()))
				.fetchFirst();
		//查询当日入账交易表
		Tuple cactTxnPost = null;
		QCactTxnPost p = QCactTxnPost.cactTxnPost;
		if(null!=apGlTxn.getTxnDetailSeq()){
			String seq = apGlTxn.getTxnDetailSeq();
			cactTxnPost = new JPAQueryFactory(em).select(p.txnDetailSeq,p.requestData,p.postTxnType).from(p).where(p.txnSeq.eq(Integer.parseInt(seq)))
					.fetchFirst();
		}
		

		TransReport transReport = new TransReport();
		transReport.setOrg(item.getOrg());
		transReport.setBranchNo(item.getBranchNo());
		transReport.setInOutFlag(item.getInOutFlag());
		transReport.setTransactionSerialNumber(apGlTxn.getGltSeq());
		transReport.setAccountNumber(apGlTxn.getTxnDetailSeq());
		if(null!=cactTxnPost){
			transReport.setTradeType(cactTxnPost.get(p.postTxnType).toString());
			transReport.setExternalFlowNumber(cactTxnPost.get(p.txnDetailSeq));
			Map mapData = JSON.parseObject(cactTxnPost.get(p.requestData),Map.class);//找到借据号
			if(null != mapData.get("iouNo")){
				transReport.setIousNumber(mapData.get("iouNo").toString());
			}
		}
		transReport.setAccountingDate(item.getVolDt());
		transReport.setReportDate(new Date());
		transReport.setClearDate(apGlTxn.getClearDate());
		transReport.setTradeDate(apGlTxn.getTransDate());
		transReport.setAccountAbstract(apGlTxn.getPostDesc());
		transReport.setSubjectCd(subject.subjectCd);
		transReport.setSubjectName(subject.name);
		transReport.setAmountDebitSide(item.getTxnDirection().equals(TxnDirection.D) ? item.getSubjAmount() : BigDecimal.ZERO);
		transReport.setAmountCreditSide(item.getTxnDirection().equals(TxnDirection.C) ? item.getSubjAmount() : BigDecimal.ZERO);
		transReport.setAssistAccount(item.getAssistAccountData());
		transReport.setBizDate(batchDate);
		transReport.fillDefaultValues();
		em.persist(transReport);
		return null;
	}

}
