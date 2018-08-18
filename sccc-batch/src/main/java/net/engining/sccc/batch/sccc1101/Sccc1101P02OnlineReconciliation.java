package net.engining.sccc.batch.sccc1101;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;
import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.CactSeqError;
import net.engining.pcx.cc.infrastructure.shared.model.CactTxnPost;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnPost;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.sccc.biz.bean.PostCodeSeq;
import net.engining.sccc.biz.bean.batchBean.DataTransBean;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.service.DataComplementationService;
import net.engining.sccc.entity.model.BtEodTxnImport;

@Service
@StepScope
public class Sccc1101P02OnlineReconciliation implements ItemProcessor<BtEodTxnImport, DataTransBean> {

	@PersistenceContext
	private EntityManager em;

	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;

	@Autowired
	private Provider4Organization provider4Organization;

	@Autowired
	private DataComplementationService dataComplementation;

	@Override
	public DataTransBean process(BtEodTxnImport btEodTxnImport) throws Exception {

		EveryDayAccountingBean item = JSON.parseObject(btEodTxnImport.getJsonInputData(),
				new TypeReference<EveryDayAccountingBean>() {
				});
		QCactTxnPost qCactTxnPost = QCactTxnPost.cactTxnPost;
		CactTxnPost cactTxnPost = new JPAQueryFactory(em).select(qCactTxnPost).from(qCactTxnPost)
				.where(qCactTxnPost.txnDetailSeq.eq(item.getTxnSerialNo())).fetchOne();
		String postCode = null;
		String txnId = null;

		CheckAccountStatusDef checkAccountStatusDef = null;
		// 交易流水在本地当日入账表未找到，
		if (cactTxnPost == null) {

			// 入当日记账交易表
			PostCodeSeq postCodeSeq = dataComplementation.insertCactTxnPost(item, bizDate, null);
			// （1）首先入差错表
			CactSeqError cactSeqError = new CactSeqError();
			cactSeqError.setBranchNo(provider4Organization.getCurrentOrganizationId());
			cactSeqError.setTxnDetailSeq(postCodeSeq.getTxnSeq());
			cactSeqError.setTxnDetailType(TxnDetailType.O);
			cactSeqError.setBizDate(bizDate);
			cactSeqError.fillDefaultValues();
			cactSeqError.fillDefaultValues();
			em.persist(cactSeqError);
			// 入总账表
			dataComplementation.insertApGlTxn(null, postCodeSeq, item);

			checkAccountStatusDef = CheckAccountStatusDef.ADD;
			txnId = postCodeSeq.getTxnSeq();
			postCode = postCodeSeq.getPostCode();
		}

		// 交易流水在本地当日入账表找到，
		if (cactTxnPost != null) {
			String requestData = JSON.toJSONString(item);
			cactTxnPost.setRequestData(requestData);
			cactTxnPost.setCheckAccountStatus(CheckAccountStatusDef.RIGHT);
			checkAccountStatusDef = CheckAccountStatusDef.RIGHT;

			txnId = cactTxnPost.getTxnSeq().toString();
		}

		DataTransBean bean = new DataTransBean();
		bean.setEveryDayAccountingBean(item);
		bean.setCheckAccountStatusDef(checkAccountStatusDef);
		bean.setPostCode(postCode);
		bean.setTxnId(txnId);

		return bean;
	}

}
