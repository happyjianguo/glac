package net.engining.sccc.accounting.sharding.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxnHst;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlAss;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlAssHst;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAss;

@Service
public class BatchDataTransferService {

//	@Autowired
//	private SQLQueryFactory queryFactory;

	@PersistenceContext
	private EntityManager em;

	// 分录表当日数据迁移到历史表
	@Transactional
	public void insertApGlTxnHst(List<String> keys) {
		QApGlTxn qApGlTxn = QApGlTxn.apGlTxn;
		List<ApGlTxn> apGlTxnList = new JPAQueryFactory(em).select(qApGlTxn).from(qApGlTxn)
				.where(qApGlTxn.gltSeq.in(keys)).fetch();
		if (apGlTxnList.size() > 0) {
			for (ApGlTxn apGlTxn : apGlTxnList) {
				ApGlTxnHst apGlTxnHst = new ApGlTxnHst();
				apGlTxnHst.setGltSeq(apGlTxn.getGltSeq());
				apGlTxnHst.setOrg(apGlTxn.getOrg());
				apGlTxnHst.setBranchNo(apGlTxn.getBranchNo());
				apGlTxnHst.setAcctSeq(apGlTxn.getAcctSeq());
				apGlTxnHst.setCurrCd(apGlTxn.getCurrCd());
				apGlTxnHst.setPostCode(apGlTxn.getPostCode());
				apGlTxnHst.setPostDesc(apGlTxn.getPostDesc());
				apGlTxnHst.setTxnDirection(apGlTxn.getTxnDirection());
				apGlTxnHst.setPostDate(apGlTxn.getPostDate());
				apGlTxnHst.setPostAmount(apGlTxn.getPostAmount());
				apGlTxnHst.setPostGlInd(apGlTxn.getPostGlInd());
				apGlTxnHst.setOwingBranch(apGlTxn.getOwingBranch());
				apGlTxnHst.setAcqBranch(apGlTxn.getAcqBranch());
				apGlTxnHst.setAgeGroupCd(apGlTxn.getAgeGroupCd());
				apGlTxnHst.setTxnDetailSeq(apGlTxn.getTxnDetailSeq());
				apGlTxnHst.setTxnDetailType(apGlTxn.getTxnDetailType());
				apGlTxnHst.setInOutFlag(apGlTxn.getInOutFlag());
				apGlTxnHst.setPostType(apGlTxn.getPostType());
				apGlTxnHst.setAccountDesc(apGlTxn.getAccountDesc());
				apGlTxnHst.setClearDate(apGlTxn.getClearDate());
				apGlTxnHst.setTransDate(apGlTxn.getTransDate());
				apGlTxnHst.setSetupDate(apGlTxn.getSetupDate());
				apGlTxnHst.setLastUpdateDate(apGlTxn.getLastUpdateDate());
				apGlTxnHst.setBizDate(apGlTxn.getBizDate());
				apGlTxnHst.setJpaVersion(apGlTxn.getJpaVersion());
				em.persist(apGlTxnHst);
			}
		}
		// QsqlApGlTxnHst qsqlApGlTxnHst = QsqlApGlTxnHst.apGlTxnHst;
		// SQLInsertClause insert = queryFactory.insert(qsqlApGlTxnHst);
		// for (ApGlTxn apGlTxn : apGlTxnList) {
		//
		// insert
		// .set(qsqlApGlTxnHst.gltSeq,apGlTxn.getGltSeq())
		// .set(qsqlApGlTxnHst.org,apGlTxn.getOrg())
		// .set(qsqlApGlTxnHst.branchNo,apGlTxn.getBranchNo())
		// .set(qsqlApGlTxnHst.acctSeq,apGlTxn.getAcctSeq())
		// .set(qsqlApGlTxnHst.currCd,apGlTxn.getCurrCd())
		// .set(qsqlApGlTxnHst.postCode,apGlTxn.getPostCode())
		// .set(qsqlApGlTxnHst.postDesc,apGlTxn.getPostDesc())
		// .set(qsqlApGlTxnHst.txnDirection,apGlTxn.getTxnDirection().toString())
		// .set(qsqlApGlTxnHst.postDate,new
		// Date(apGlTxn.getPostDate().getTime()))
		// .set(qsqlApGlTxnHst.postAmount,apGlTxn.getPostAmount())
		// .set(qsqlApGlTxnHst.postGlInd,apGlTxn.getPostGlInd().toString())
		// .set(qsqlApGlTxnHst.owingBranch,apGlTxn.getOwingBranch())
		// .set(qsqlApGlTxnHst.acqBranch,apGlTxn.getAcqBranch())
		// .set(qsqlApGlTxnHst.ageGroupCd,apGlTxn.getAgeGroupCd().toString())
		// .set(qsqlApGlTxnHst.txnDetailSeq,apGlTxn.getTxnDetailSeq())
		// .set(qsqlApGlTxnHst.txnDetailType,apGlTxn.getTxnDetailType().toString())
		// .set(qsqlApGlTxnHst.inOutFlag,apGlTxn.getInOutFlag().toString())
		// .set(qsqlApGlTxnHst.postType,apGlTxn.getPostType().toString())
		// .set(qsqlApGlTxnHst.accountDesc,apGlTxn.getAccountDesc())
		// .set(qsqlApGlTxnHst.clearDate,new
		// Date(apGlTxn.getClearDate().getTime()))
		// .set(qsqlApGlTxnHst.transDate,new
		// Date(apGlTxn.getTransDate().getTime()))
		// .set(qsqlApGlTxnHst.setupDate,new
		// Timestamp(apGlTxn.getSetupDate().getTime()))
		// .set(qsqlApGlTxnHst.lastUpdateDate,new
		// Timestamp(apGlTxn.getLastUpdateDate().getTime()))
		// .set(qsqlApGlTxnHst.bizDate,new Date(apGlTxn.getBizDate().getTime()))
		// .set(qsqlApGlTxnHst.jpaVersion,apGlTxn.getJpaVersion())
		// .addBatch();
		//
		// }
		//
		// insert.execute();
	}

	// 删除分录表当日数据
	@Transactional
	public void deleteApGlTxn(List<String> keys) {

		QApGlTxn qApGlTxn = QApGlTxn.apGlTxn;
		new JPADeleteClause(em, qApGlTxn).where(qApGlTxn.gltSeq.in(keys)).execute();

	}

	// 会计分录表当日数据迁移到历史表
	@Transactional
	public void insertApGlVolDtlHst(List<String> keys) {
		QApGlVolDtl qApGlVolDtl = QApGlVolDtl.apGlVolDtl;
		List<ApGlVolDtl> apGlVolDtlList = new JPAQueryFactory(em).select(qApGlVolDtl).from(qApGlVolDtl)
				.where(qApGlVolDtl.glvSeq.in(keys)).fetch();
		for(ApGlVolDtl apGlVolDtl: apGlVolDtlList){
			ApGlVolDtlHst apGlVolDtlHst =new ApGlVolDtlHst();
			apGlVolDtlHst.setGlvSeq(apGlVolDtl.getGlvSeq());
			apGlVolDtlHst.setOrg(apGlVolDtl.getOrg());
			apGlVolDtlHst.setBranchNo(apGlVolDtl.getBranchNo());
			apGlVolDtlHst.setVolDt(apGlVolDtl.getVolDt());
			apGlVolDtlHst.setBranch(apGlVolDtl.getBranch());
			apGlVolDtlHst.setTxnBrcd(apGlVolDtl.getTxnBrcd());
			apGlVolDtlHst.setPostGlInd(apGlVolDtl.getPostGlInd());
			apGlVolDtlHst.setInOutFlag(apGlVolDtl.getInOutFlag());
			apGlVolDtlHst.setCurrCd(apGlVolDtl.getCurrCd());
			apGlVolDtlHst.setDbsubjectCd(apGlVolDtl.getDbsubjectCd());
			apGlVolDtlHst.setCrsubjectCd(apGlVolDtl.getCrsubjectCd());
			apGlVolDtlHst.setSubjAmount(apGlVolDtl.getSubjAmount());
			apGlVolDtlHst.setVolSeq(apGlVolDtl.getVolSeq());
			apGlVolDtlHst.setRedBlueInd(apGlVolDtl.getRedBlueInd());
			apGlVolDtlHst.setVolDesc(apGlVolDtl.getVolDesc());
			apGlVolDtlHst.setRefNo(apGlVolDtl.getRefNo());
			apGlVolDtlHst.setTxnDetailSeq(apGlVolDtl.getTxnDetailSeq());
			apGlVolDtlHst.setTxnDetailType(apGlVolDtl.getTxnDetailType());
			apGlVolDtlHst.setTxnDirection(apGlVolDtl.getTxnDirection());
			apGlVolDtlHst.setTransDate(apGlVolDtl.getTransDate());
			apGlVolDtlHst.setAssistAccountData(apGlVolDtl.getAssistAccountData());
			apGlVolDtlHst.setSetupDate(apGlVolDtl.getSetupDate());
			apGlVolDtlHst.setLastUpdateDate(apGlVolDtl.getLastUpdateDate());
			apGlVolDtlHst.setBizDate(apGlVolDtl.getBizDate());
			apGlVolDtlHst.setJpaVersion(apGlVolDtl.getJpaVersion());
			em.persist(apGlVolDtlHst);
		}
//		QsqlApGlVolDtlHst qsqlApGlVolDtlHst = QsqlApGlVolDtlHst.apGlVolDtlHst;
//		SQLInsertClause insert = queryFactory.insert(qsqlApGlVolDtlHst);
//		if (apGlVolDtlList.size() > 0) {
//			for (ApGlVolDtl apGlVolDtl : apGlVolDtlList) {
//				insert.set(qsqlApGlVolDtlHst.glvSeq, apGlVolDtl.getGlvSeq())
//						.set(qsqlApGlVolDtlHst.org, apGlVolDtl.getOrg())
//						.set(qsqlApGlVolDtlHst.branchNo, apGlVolDtl.getBranchNo())
//						.set(qsqlApGlVolDtlHst.volDt, new Date(apGlVolDtl.getVolDt().getTime()))
//						.set(qsqlApGlVolDtlHst.branch, apGlVolDtl.getBranch())
//						.set(qsqlApGlVolDtlHst.txnBrcd, apGlVolDtl.getTxnBrcd())
//						.set(qsqlApGlVolDtlHst.postGlInd, apGlVolDtl.getPostGlInd().toString())
//						.set(qsqlApGlVolDtlHst.inOutFlag, apGlVolDtl.getInOutFlag().toString())
//						.set(qsqlApGlVolDtlHst.currCd, apGlVolDtl.getCurrCd())
//						.set(qsqlApGlVolDtlHst.dbsubjectCd, apGlVolDtl.getDbsubjectCd())
//						.set(qsqlApGlVolDtlHst.crsubjectCd, apGlVolDtl.getCrsubjectCd())
//						.set(qsqlApGlVolDtlHst.subjAmount, apGlVolDtl.getSubjAmount())
//						.set(qsqlApGlVolDtlHst.volSeq, apGlVolDtl.getVolSeq())
//						.set(qsqlApGlVolDtlHst.redBlueInd, apGlVolDtl.getRedBlueInd().toString())
//						.set(qsqlApGlVolDtlHst.volDesc, apGlVolDtl.getVolDesc())
//						.set(qsqlApGlVolDtlHst.refNo, apGlVolDtl.getRefNo())
//						.set(qsqlApGlVolDtlHst.txnDetailSeq, apGlVolDtl.getTxnDetailSeq())
//						.set(qsqlApGlVolDtlHst.txnDetailType, apGlVolDtl.getTxnDetailType().toString())
//						.set(qsqlApGlVolDtlHst.txnDirection, apGlVolDtl.getTxnDirection().toString())
//						.set(qsqlApGlVolDtlHst.transDate, new Date(apGlVolDtl.getTransDate().getTime()))
//						.set(qsqlApGlVolDtlHst.assistAccountData, apGlVolDtl.getAssistAccountData())
//						.set(qsqlApGlVolDtlHst.setupDate, new Timestamp(apGlVolDtl.getSetupDate().getTime()))
//						.set(qsqlApGlVolDtlHst.lastUpdateDate, new Timestamp(apGlVolDtl.getLastUpdateDate().getTime()))
//						.set(qsqlApGlVolDtlHst.bizDate, new Date(apGlVolDtl.getBizDate().getTime()))
//						.set(qsqlApGlVolDtlHst.jpaVersion, apGlVolDtl.getJpaVersion()).addBatch();
//			}
//		}
//		if (apGlVolDtlList.size() != 0) {
//			insert.execute();
//		}
	}

	// 删除分录表当日数据
	@Transactional
	public void deleteApGlVolDtl(List<String> glvSeq) {

		QApGlVolDtl qApGlVolDtl = QApGlVolDtl.apGlVolDtl;

		new JPADeleteClause(em, qApGlVolDtl).where(qApGlVolDtl.glvSeq.in(glvSeq)).execute();

	}

	// 当日辅助核算拆分表数据迁移到历史表
	@Transactional
	public void insertApGlVolDtlAssHst(List<String> keys) {

		QApGlVolDtlAss qApGlVolDtlAss = QApGlVolDtlAss.apGlVolDtlAss;
		List<ApGlVolDtlAss> apGlVolDtlAssList = new JPAQueryFactory(em).select(qApGlVolDtlAss).from(qApGlVolDtlAss)
				.where(qApGlVolDtlAss.assSeq.in(keys)).fetch();
		if (apGlVolDtlAssList.size() > 0) {
			for (ApGlVolDtlAss apGlVolDtlAss : apGlVolDtlAssList) {
				ApGlVolDtlAssHst apGlVolDtlAssHst = new ApGlVolDtlAssHst();
				apGlVolDtlAssHst.setAssSeq(apGlVolDtlAss.getAssSeq());
				apGlVolDtlAssHst.setAssistType(apGlVolDtlAss.getAssistType());
				apGlVolDtlAssHst.setSubjectCd(apGlVolDtlAss.getSubjectCd());
				apGlVolDtlAssHst.setAssistAccountValue(apGlVolDtlAss.getAssistAccountValue());
				apGlVolDtlAssHst.setOrg(apGlVolDtlAss.getOrg());
				apGlVolDtlAssHst.setBranchNo(apGlVolDtlAss.getBranchNo());
				apGlVolDtlAssHst.setVolDt(apGlVolDtlAss.getVolDt());
				apGlVolDtlAssHst.setBranch(apGlVolDtlAss.getBranch());
				apGlVolDtlAssHst.setTxnBrcd(apGlVolDtlAss.getTxnBrcd());
				apGlVolDtlAssHst.setCurrCd(apGlVolDtlAss.getCurrCd());
				apGlVolDtlAssHst.setSubjAmount(apGlVolDtlAss.getSubjAmount());
				apGlVolDtlAssHst.setVolSeq(apGlVolDtlAss.getVolSeq());
				apGlVolDtlAssHst.setRedBlueInd(apGlVolDtlAss.getRedBlueInd());
				apGlVolDtlAssHst.setVolDesc(apGlVolDtlAss.getVolDesc());
				apGlVolDtlAssHst.setRefNo(apGlVolDtlAss.getRefNo());
				apGlVolDtlAssHst.setTxnDetailSeq(apGlVolDtlAss.getTxnDetailSeq());
				apGlVolDtlAssHst.setTxnDetailType(apGlVolDtlAss.getTxnDetailType());
				apGlVolDtlAssHst.setTxnDirection(apGlVolDtlAss.getTxnDirection());
				apGlVolDtlAssHst.setTransDate(apGlVolDtlAss.getTransDate());
				apGlVolDtlAssHst.setJpaVersion(apGlVolDtlAss.getJpaVersion());
				apGlVolDtlAssHst.setSetupDate(apGlVolDtlAss.getSetupDate());
				apGlVolDtlAssHst.setBizDate(apGlVolDtlAss.getBizDate());
				apGlVolDtlAssHst.setLastUpdateDate(apGlVolDtlAss.getLastUpdateDate());
				em.persist(apGlVolDtlAssHst);
			}
		}

		// QsqlApGlVolDtlAssHst qsqlApGlVolDtlAssHst =
		// QsqlApGlVolDtlAssHst.apGlVolDtlAssHst;
		// SQLInsertClause insert = queryFactory.insert(qsqlApGlVolDtlAssHst);
		// for(ApGlVolDtlAss apGlVolDtlAss : apGlVolDtlAssList){
		// String assistType =null;
		// if(apGlVolDtlAss.getAssistType() !=null){
		// assistType= apGlVolDtlAss.getAssistType().toString();
		// }
		// if(apGlVolDtlAss.getVolDt()!=null){}
		//
		// insert
		// .set(qsqlApGlVolDtlAssHst.assSeq,apGlVolDtlAss.getAssSeq())
		// .set(qsqlApGlVolDtlAssHst.subjectCd,apGlVolDtlAss.getSubjectCd())
		// .set(qsqlApGlVolDtlAssHst.assistType,assistType)
		// .set(qsqlApGlVolDtlAssHst.assistAccountValue,apGlVolDtlAss.getAssistAccountValue())
		// .set(qsqlApGlVolDtlAssHst.org,apGlVolDtlAss.getOrg())
		// .set(qsqlApGlVolDtlAssHst.branchNo,apGlVolDtlAss.getBranchNo())
		// .set(qsqlApGlVolDtlAssHst.volDt,new
		// Date(apGlVolDtlAss.getVolDt().getTime()))
		// .set(qsqlApGlVolDtlAssHst.branch,apGlVolDtlAss.getBranch())
		// .set(qsqlApGlVolDtlAssHst.txnBrcd,apGlVolDtlAss.getTxnBrcd())
		// .set(qsqlApGlVolDtlAssHst.currCd,apGlVolDtlAss.getCurrCd())
		// .set(qsqlApGlVolDtlAssHst.subjAmount,apGlVolDtlAss.getSubjAmount())
		// .set(qsqlApGlVolDtlAssHst.volSeq,apGlVolDtlAss.getVolSeq())
		// .set(qsqlApGlVolDtlAssHst.redBlueInd,apGlVolDtlAss.getRedBlueInd().toString())
		// .set(qsqlApGlVolDtlAssHst.volDesc,apGlVolDtlAss.getVolDesc())
		// .set(qsqlApGlVolDtlAssHst.refNo,apGlVolDtlAss.getRefNo())
		// .set(qsqlApGlVolDtlAssHst.txnDetailSeq,apGlVolDtlAss.getTxnDetailSeq())
		// .set(qsqlApGlVolDtlAssHst.txnDetailType,apGlVolDtlAss.getTxnDetailType().toString())
		// .set(qsqlApGlVolDtlAssHst.txnDirection,apGlVolDtlAss.getTxnDirection().toString())
		// .set(qsqlApGlVolDtlAssHst.transDate,new
		// Date(apGlVolDtlAss.getTransDate().getTime()))
		// .set(qsqlApGlVolDtlAssHst.jpaVersion,apGlVolDtlAss.getJpaVersion())
		// .set(qsqlApGlVolDtlAssHst.setupDate,new
		// Timestamp(apGlVolDtlAss.getSetupDate().getTime()))
		// .set(qsqlApGlVolDtlAssHst.bizDate,new
		// Date(apGlVolDtlAss.getBizDate().getTime()))
		// .set(qsqlApGlVolDtlAssHst.lastUpdateDate,new
		// Timestamp(apGlVolDtlAss.getLastUpdateDate().getTime()))
		// .addBatch();
		// }
		// insert.execute();
	}

	// 删除分录表当日数据
	@Transactional
	public void deleteApGlVolDtlAss(List<String> assSeq) {

		QApGlVolDtlAss qApGlVolDtlAss = QApGlVolDtlAss.apGlVolDtlAss;

		new JPADeleteClause(em, qApGlVolDtlAss).where(qApGlVolDtlAss.assSeq.in(assSeq)).execute();

	}
}
