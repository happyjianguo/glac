package net.engining.sccc.mgm.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.enums.CheckFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.InOutFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.PostTypeDef;
import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.GlTransOprHst;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.biz.bean.TxnOperate;
import net.engining.sccc.biz.bean.TxnOperateReq;

@Service
public class GlTxnService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Provider4Organization provider4Organization;

	@Autowired
	private Provider7x24 provider7x24;

	@Transactional
	public ApGlTxn glTxnAdd(TxnOperateReq file) {
		ApGlTxn txn = new ApGlTxn();
		// 对空值填入默认值
		txn.fillDefaultValues();
		BeanUtils.copyProperties(file, txn);
		
		// 处理借贷方金额相关逻辑关系，并为借方或贷方金额赋值
		processingAmount(file, txn);
		
		txn.setAcctSeq(0);
		txn.setOwingBranch(provider4Organization.getCurrentOrganizationId());
		txn.setPostGlInd(PostGlInd.Normal);
		txn.setCurrCd("156");
		txn.setOrg(provider4Organization.getCurrentOrganizationId());
		txn.setInOutFlag(file.getInOutFlag());
		txn.setPostType(PostTypeDef.MANU);
		txn.setLastUpdateDate(new Date());
		txn.setTransDate(provider7x24.getCurrentDate().toDate());
		txn.setPostDate(provider7x24.getCurrentDate().toDate());
		txn.setBizDate(provider7x24.getCurrentDate().toDate());
		em.persist(txn);
		return txn;
	}

	@Transactional
	public ApGlVolDtl volDtlAdd(TxnOperate file, String txnDetailSeq) {
		ApGlVolDtl volDtl = new ApGlVolDtl();
		BeanUtils.copyProperties(file, volDtl);
		if (TxnDirection.C.equals(file.getTxnDirection())) {
			volDtl.setCrsubjectCd(file.getSubjectCd());
		}
		if (TxnDirection.D.equals(file.getTxnDirection())) {
			volDtl.setDbsubjectCd(file.getSubjectCd());
		}
		volDtl.setTransDate(provider7x24.getCurrentDate().toDate());
		volDtl.setInOutFlag(file.getInOutFlag());
		volDtl.setSubjAmount(file.getPostAmount());
		volDtl.fillDefaultValues();
		volDtl.setOrg(provider4Organization.getCurrentOrganizationId());
		volDtl.setBranch("00001");
		volDtl.setVolDt(provider7x24.getCurrentDate().toDate());
		volDtl.setPostGlInd(PostGlInd.Normal);
		volDtl.setCurrCd("156");
		volDtl.setTxnDetailSeq(txnDetailSeq);
		String jsoStr = JSON.toJSONString(file.getAssistAccountData());
		volDtl.setAssistAccountData(jsoStr);
		volDtl.setInOutFlag(file.getInOutFlag());
		volDtl.setBizDate(provider7x24.getCurrentDate().toDate());
		volDtl.setTxnDetailType(TxnDetailType.C);
		em.persist(volDtl);
		return volDtl;
	}

	@Transactional
	public void glTransOprAdd(TxnOperateReq file, String txnDetailSeq) {
		GlTransOprHst glTransOprHst = new GlTransOprHst();
		glTransOprHst.fillDefaultValues();
		BeanUtils.copyProperties(file, glTransOprHst);
		BigDecimal postAmount = new BigDecimal(0);
		glTransOprHst.setTxnDetailSeq(txnDetailSeq);
		for (TxnOperate txnOperate : file.getList()) {
			if (TxnDirection.D.equals(txnOperate.getTxnDirection())) {
				postAmount = postAmount.add(txnOperate.getPostAmount());
			}
		}
		glTransOprHst.setOrg(provider4Organization.getCurrentOrganizationId());
		glTransOprHst.setOperDate(provider7x24.getCurrentDate().toDate());
		glTransOprHst.setOperaId(file.getOperaId());
		glTransOprHst.setTxnDetailType(TxnDetailType.C);
		glTransOprHst.setCheckFlag(CheckFlagDef.B);
		glTransOprHst.setTxnAmt(postAmount);
		glTransOprHst.setBizDate(provider7x24.getCurrentDate().toDate());
		glTransOprHst.setBranchNo(provider4Organization.getCurrentOrganizationId());
		glTransOprHst.setPrintVoucherCount(0);
		glTransOprHst.setCurrCd("156");
		em.persist(glTransOprHst);
	}
	
	
	
	private void processingAmount(TxnOperateReq file, ApGlTxn txn) {

		// 判断是否只有贷方、只有借方、借贷方都有,统计借贷方金额
		BigDecimal dbPostAmount = new BigDecimal(0);
		BigDecimal crPostAmount = new BigDecimal(0);
		TxnDirection dDirection = null;
		TxnDirection cDirection = null;
		for (TxnOperate txnOperate : file.getList()) {
			// if (dDirection != null && cDirection != null) {break;}
			if (txnOperate.getTxnDirection().equals(TxnDirection.D)) {
				dDirection = txnOperate.getTxnDirection();
				dbPostAmount = dbPostAmount.add(txnOperate.getPostAmount());
			} else {
				cDirection = txnOperate.getTxnDirection();
				crPostAmount = crPostAmount.add(txnOperate.getPostAmount());
			}
		}

		// 判断借贷方向并
		if (dDirection != null && cDirection == null) {
			txn.setPostAmount(dbPostAmount);
		} else if (dDirection == null && cDirection != null) {
			txn.setPostAmount(crPostAmount);
		} else if (dDirection != null && cDirection != null) {
			if (!dbPostAmount.equals(crPostAmount)) {
				throw new ErrorMessageException(ErrorCode.CheckError, "借方和贷方金额不相等");
			} else {
				txn.setPostAmount(crPostAmount);
			}
		}
	}
}
