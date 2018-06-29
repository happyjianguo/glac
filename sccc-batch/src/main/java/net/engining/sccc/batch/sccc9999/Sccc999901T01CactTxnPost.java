package net.engining.sccc.batch.sccc9999;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;

/**
 * 
 * 数据治理，当日入账交易表迁移到相应历史表；<br>
 * CACT_TXN_POST -> CACT_TXN_HST;<br> 
 *
 */
@Service
@StepScope
public class Sccc999901T01CactTxnPost implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(Sccc999901T01CactTxnPost.class);
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;
	
	@PersistenceContext
	private EntityManager em;

 	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		log.info("CACT_TXN_POST 当日入账交易表迁移到 CACT_TXN_HST，batchDate={}", batchDate);
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("INSERT INTO CACT_TXN_HST (TXN_SEQ, ORG, BRANCH_NO, ACCT_SEQ, SUB_ACCT_ID,SUBACCT_PARAM_ID, BUSINESS_TYPE,CARD_NO,CARD_GROUP_ID,TXN_DATE,TXN_TIME,POST_TXN_TYPE, POST_CODE,DB_CR_IND,TXN_AMT,POST_AMT,POST_DATE,AUTH_CODE,TXN_CURR_CD,POST_CURR_CD,REF_NBR,TXN_DESC,TXN_SHORT_DESC,POSTING_FLAG,PRE_POSTING_FLAG,ACQ_BRANCH_ID,ACQ_TERMINAL_ID,ACQ_ACCEPTOR_ID,MERCHANT_NAME_ADDR,MCC,STMT_DATE,AGE_CD_B4_POSTING,AGE_CD_AFTER_POSTING,ACCT_CURR_BAL,SUB_ACCT_TYPE,TERMS_TYPE,FEE_TYPE,IS_CUN_LOAN,TXN_DETAIL_SEQ,TXN_DETAIL_TYPE,TXN_TYPE,OPP_ACCT,REQUEST_DATA,SV_PR_ID,CHANNE_ID,ASYN_IND,INTO_GL_STATUS,CHECK_ACCOUNT_STATUS,CLEAR_DATE,SETUP_DATE,LAST_UPDATE_DATE,BIZ_DATE,JPA_VERSION)");
		stringBuffer.append(" ");                                                  
		stringBuffer.append("SELECT TXN_SEQ, ORG, BRANCH_NO, ACCT_SEQ, SUB_ACCT_ID,SUBACCT_PARAM_ID, BUSINESS_TYPE,CARD_NO,CARD_GROUP_ID,TXN_DATE,TXN_TIME,POST_TXN_TYPE, POST_CODE,DB_CR_IND,TXN_AMT,POST_AMT,POST_DATE,AUTH_CODE,TXN_CURR_CD,POST_CURR_CD,REF_NBR,TXN_DESC,TXN_SHORT_DESC,POSTING_FLAG,PRE_POSTING_FLAG,ACQ_BRANCH_ID,ACQ_TERMINAL_ID,ACQ_ACCEPTOR_ID,MERCHANT_NAME_ADDR,MCC,STMT_DATE,AGE_CD_B4_POSTING,AGE_CD_AFTER_POSTING,ACCT_CURR_BAL,SUB_ACCT_TYPE,TERMS_TYPE,FEE_TYPE,IS_CUN_LOAN,TXN_DETAIL_SEQ,TXN_DETAIL_TYPE,TXN_TYPE,OPP_ACCT,REQUEST_DATA,SV_PR_ID,CHANNE_ID,ASYN_IND,INTO_GL_STATUS,CHECK_ACCOUNT_STATUS,CLEAR_DATE,SETUP_DATE,LAST_UPDATE_DATE,BIZ_DATE,JPA_VERSION FROM CACT_TXN_POST WHERE CHECK_ACCOUNT_STATUS != '"+CheckAccountStatusDef.UNCHK+"'");
		
		em.createNativeQuery(stringBuffer.toString()).executeUpdate();
		
		return RepeatStatus.FINISHED;
	}
}