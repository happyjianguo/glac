package net.engining.sccc.biz.service;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import net.engining.gm.infrastructure.enums.BusinessType;
import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;
import net.engining.pcx.cc.infrastructure.shared.enums.IntoGlStatusDef;
import net.engining.pcx.cc.infrastructure.shared.enums.PostTxnTypeDef;
import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.CactAccount;
import net.engining.pcx.cc.infrastructure.shared.model.CactTxnPost;
import net.engining.pcx.cc.param.model.Account;
import net.engining.pcx.cc.param.model.PostCode;
import net.engining.pcx.cc.param.model.enums.Deadline;
import net.engining.pcx.cc.param.model.enums.SysTxnCd;
import net.engining.pcx.cc.process.service.support.refactor.FlowIntoApGlTxnEvent;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.sccc.biz.bean.PostCodeSeq;
import net.engining.sccc.biz.bean.TxnCdPostCode;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.enums.AccountTradingDef;
import net.engining.sccc.biz.enums.SysInternalAcctionCdDef;
import net.engining.sccc.biz.service.params.ProductAccount;

@Service
public class DataComplementationService {
	@Autowired
	private Provider4Organization provider4Organization;

	@Autowired
	private ComputingTxnCdService batchGetPostCodeService;
	
	@Autowired
	private ComputingPostCodeService getPostCodeService;

	@Autowired
	ParameterFacility parameterFacility;

	@Autowired
	private CheckAcctSeqService checkAcctSeqService;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ApplicationContext ctx;

	@Transactional
	public Integer insertCactAccount(EveryDayAccountingBean item, Date date) {
		Integer acctSeq = null;
		if (item.getRequestData().getIouNo() != null && item.getRequestData().getCustId() != null) {
			acctSeq = checkAcctSeqService.checkAcctSeq(item.getRequestData().getCustId(),
					item.getRequestData().getIouNo(), item.getRequestData().getTotalPeriod(),
					item.getRequestData().getProductId(), date);
//			acctSeq = acct.getAcctSeq();
		} else {
			acctSeq = 0;
		}
		return acctSeq;
	}

	@Transactional
	public PostCodeSeq insertCactTxnPost(EveryDayAccountingBean item, Date date, Integer acctSeq) {
		// 获取txnCd
		AccountTradingDef accountTrading = item.getRequestData().getAccountTrading();
		Integer totalPeriod = item.getRequestData().getTotalPeriod();
		SysInternalAcctionCdDef sysInternalAcctionCdDef = item.getRequestData().getSysInternalAcctActionCd();
		Integer ageCdBefore = item.getRequestData().getAgeCdBefore();
		Boolean isUnion = item.getRequestData().getIsUnion();
//		SysTxnCd txnCd = batchGetPostCodeService.getTxnCd(accountTrading, totalPeriod, sysInternalAcctionCdDef,
//				ageCdBefore, isUnion);

		// 查询产品参数是否存在
//		Deadline deadline = null;
//		if (item.getRequestData().getAccountTrading().equals((AccountTradingDef.S))
//				|| item.getRequestData().getAccountTrading().equals((AccountTradingDef.F))
//				|| item.getRequestData().getAccountTrading().equals((AccountTradingDef.T))) {
//			deadline = Deadline.M;
//		} else {
//			if (item.getRequestData().getTotalPeriod() <= 12) {
//				deadline = Deadline.S;
//			} else {
//				deadline = Deadline.M;
//			}
//		}
//		ProductAccount init = parameterFacility.getParameter(ProductAccount.class,
//				ProductAccount.key(item.getRequestData().getProductId(), deadline));
//		String acctParamId = init.getAccountId();
//		Account account = parameterFacility.getParameter(Account.class, acctParamId);
//		Map<SysTxnCd, String> txnCdMap = account.sysTxnCdMapping;
//		String postCode = txnCdMap.get(txnCd);
		TxnCdPostCode tp = getPostCodeService.getTxnCdPostCode(item, accountTrading, sysInternalAcctionCdDef, ageCdBefore, isUnion);
		
		

		CactTxnPost cact = new CactTxnPost();
		cact.setOrg(provider4Organization.getCurrentOrganizationId());
		cact.setBranchNo(provider4Organization.getCurrentOrganizationId());
		cact.setAcctSeq(acctSeq);
		cact.setBusinessType(BusinessType.BL);
		cact.setTxnTime(item.getTimestamp());
		if(AccountTradingDef.S.equals(item.getRequestData().getAccountTrading())){
			cact.setPostTxnType(PostTxnTypeDef.GrantTally);
		}else if(AccountTradingDef.D.equals(item.getRequestData().getAccountTrading())){
			cact.setPostTxnType(PostTxnTypeDef.LoanTally);
		}else if(AccountTradingDef.T.equals(item.getRequestData().getAccountTrading())){
			cact.setPostTxnType(PostTxnTypeDef.RefTally);
		}else if(AccountTradingDef.F.equals(item.getRequestData().getAccountTrading())){
			cact.setPostTxnType(PostTxnTypeDef.FeeTally);
		}else if(AccountTradingDef.H.equals(item.getRequestData().getAccountTrading())){
			cact.setPostTxnType(PostTxnTypeDef.RepTally);
		}
		
		cact.setPostCode(tp.txnCd.name());
		PostCode pc = parameterFacility.getParameter(PostCode.class,tp.postCode);
		cact.setTxnDesc(pc.description);
		cact.setTxnAmt(item.getRequestData().getTotalAmt());
		cact.setPostAmt(item.getRequestData().getTotalAmt());
		cact.setPostDate(date);
		if (item.getRequestData().getAgeCdBefore() == null) {
			cact.setAgeCdB4Posting(null);
		} else {
			cact.setAgeCdB4Posting(String.valueOf(item.getRequestData().getAgeCdBefore()));
		}
		if (item.getRequestData().getAgeCd() == null) {
			cact.setAgeCdAfterPosting(null);
		} else {
			cact.setAgeCdAfterPosting(String.valueOf(item.getRequestData().getAgeCd()));
		}
		cact.setTxnDetailSeq(item.getTxnSerialNo());
		cact.setTxnDetailType(TxnDetailType.O);
		String requestData = JSON.toJSONString(item);
		cact.setRequestData(requestData);
		cact.setChanneId(item.getChannelId());
		cact.setAsynInd(item.getAsynInd());
		cact.setIntoGlStatus(IntoGlStatusDef.F);
		cact.setClearDate(item.getClearDate());
		cact.setTxnDate(item.getTimestamp());
		cact.setCheckAccountStatus(CheckAccountStatusDef.ADD);
		cact.setBizDate(date);
		cact.fillDefaultValues();
		em.persist(cact);

		PostCodeSeq postCodeSeq = new PostCodeSeq();
		postCodeSeq.setPostCode(tp.postCode);
		postCodeSeq.setTxnSeq(cact.getTxnSeq().toString());
		return postCodeSeq;
	}

	@Transactional
	public void insertApGlTxn(Integer acctSeq, PostCodeSeq postCodeSeq, EveryDayAccountingBean item) {
		FlowIntoApGlTxnEvent event = new FlowIntoApGlTxnEvent(this);
		event.setAcctSeq(acctSeq);
		event.setPostCode(postCodeSeq.getPostCode());
		event.setPostAmount(item.getRequestData().getTotalAmt());
		event.setTxnDetailSeq(postCodeSeq.getTxnSeq());
		event.setTransDate(item.getTimestamp());
		event.setClearDate(item.getClearDate());
		ctx.publishEvent(event);
	}

}
