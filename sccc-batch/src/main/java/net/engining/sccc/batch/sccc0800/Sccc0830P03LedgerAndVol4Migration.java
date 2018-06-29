package net.engining.sccc.batch.sccc0800;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.PostTypeDef;
import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.BtDataMigrationTempdtl;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccount;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccountAddi;
import net.engining.pcx.cc.param.model.enums.PostGlInd;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.sccc.biz.service.CheckAcctSeqService;

/**
 * 数据迁移 分别落总账流水和分录流水
 * 
 * @author luxue
 *
 */
@Service
@StepScope
public class Sccc0830P03LedgerAndVol4Migration implements ItemProcessor<List<BtDataMigrationTempdtl>, Object> {

	private static final Logger log = LoggerFactory.getLogger(Sccc0830P03LedgerAndVol4Migration.class);

	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Provider4Organization provider4Organization;

	private ExecutionContext jobExecutionContext;

	private ExecutionContext stepExecutionContext;
	
	@Autowired
	private CheckAcctSeqService checkAcctSeqService;
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();

		stepExecutionContext = stepExecution.getExecutionContext();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public Object process(List<BtDataMigrationTempdtl> item) throws Exception {
		
		if(item.size() == 0) return null;

		// item是来源交易流水号相同的所有记账流水，轮询并分别入总账流水和分录流水

		// 数据检查重复
		QApGlTxn qApGlTxn = QApGlTxn.apGlTxn;
		ApGlTxn ap = new JPAQueryFactory(em).select(qApGlTxn).from(qApGlTxn).where(qApGlTxn.txnDetailSeq.eq(item.get(0).getTxnDetailSeq())).fetchOne();
		if (ap == null) {
			Integer acctSeq=null;
			QCactAccount qCactAccount = QCactAccount.cactAccount;
			QCactAccountAddi qCactAccountAddi = QCactAccountAddi.cactAccountAddi;
			acctSeq = new JPAQueryFactory(em).select(qCactAccount.acctSeq).from(qCactAccountAddi,qCactAccount)
					.where(qCactAccountAddi.iouNo.eq(item.get(0).getIouNo()),qCactAccount.acctSeq.eq(qCactAccountAddi.acctSeq)).fetchOne();
			
			// 先取第一条记录，入总账流水
			BtDataMigrationTempdtl btDataMigrationTempdtl0 = item.get(0);
			ApGlTxn apGlTxn = new ApGlTxn();
			apGlTxn.setOrg(provider4Organization.getCurrentOrganizationId());
			apGlTxn.setBranchNo(provider4Organization.getCurrentOrganizationId());
			apGlTxn.setCurrCd(btDataMigrationTempdtl0.getCurrCd());
			apGlTxn.setPostCode(btDataMigrationTempdtl0.getPostCode());
			apGlTxn.setPostDate(btDataMigrationTempdtl0.getVolDt());// 数据迁移时用旧数据的记账日期
			apGlTxn.setPostAmount(BigDecimal.ZERO);// 需累加
			apGlTxn.setAcctSeq(acctSeq);
			apGlTxn.setPostGlInd(PostGlInd.Normal);
			apGlTxn.setOwingBranch(provider4Organization.getCurrentOrganizationId());
			apGlTxn.setAcqBranch(btDataMigrationTempdtl0.getBranch());
			apGlTxn.setTxnDetailType(TxnDetailType.S);
			apGlTxn.setTxnDetailSeq(btDataMigrationTempdtl0.getTxnDetailSeq());
			apGlTxn.setPostType(PostTypeDef.SYSM);
			apGlTxn.setClearDate(btDataMigrationTempdtl0.getVolDt());// 数据迁移时用旧数据的记账日期
			apGlTxn.setTransDate(item.get(0).getTransDate());
			apGlTxn.setBizDate(bizDate);
			apGlTxn.fillDefaultValues();
			em.persist(apGlTxn);

			// 轮询入分录流水
			for (BtDataMigrationTempdtl btDataMigrationTempdtl : item) {
				
				// 累加
				apGlTxn.setPostAmount(apGlTxn.getPostAmount().add(btDataMigrationTempdtl.getPostAmount()));
				
				// 分录流水
				ApGlVolDtl apGlVolDtl = new ApGlVolDtl();
				apGlVolDtl.setOrg(provider4Organization.getCurrentOrganizationId());
				apGlVolDtl.setBranchNo(provider4Organization.getCurrentOrganizationId());
				apGlVolDtl.setVolDt(btDataMigrationTempdtl.getVolDt());
				apGlVolDtl.setBranch(btDataMigrationTempdtl.getBranch());
				apGlVolDtl.setTxnBrcd(btDataMigrationTempdtl.getBranch());
				apGlVolDtl.setPostGlInd(PostGlInd.Normal);
				apGlVolDtl.setInOutFlag(btDataMigrationTempdtl.getInOutFlag());
				apGlVolDtl.setCurrCd(btDataMigrationTempdtl.getCurrCd());
				apGlVolDtl.setDbsubjectCd(btDataMigrationTempdtl.getDbsubjectCd());
				apGlVolDtl.setCrsubjectCd(btDataMigrationTempdtl.getCrsubjectCd());
				apGlVolDtl.setSubjAmount(btDataMigrationTempdtl.getPostAmount());
				apGlVolDtl.setVolSeq(btDataMigrationTempdtl.getVolSeq());
				apGlVolDtl.setRedBlueInd(btDataMigrationTempdtl.getRedBlueInd());
				apGlVolDtl.setVolDesc(btDataMigrationTempdtl.getVolDesc());
				apGlVolDtl.setTxnDetailType(TxnDetailType.C);
				apGlVolDtl.setTxnDetailSeq(apGlTxn.getGltSeq());
				apGlVolDtl.setTxnDirection(btDataMigrationTempdtl.getTxnDirection());
				apGlVolDtl.setTransDate(btDataMigrationTempdtl.getTransDate());
				apGlVolDtl.setAssistAccountData(btDataMigrationTempdtl.getAssistAccountData());
				apGlVolDtl.setBizDate(bizDate);
				em.persist(apGlVolDtl);
			}
		}

		return null;
	}

}
