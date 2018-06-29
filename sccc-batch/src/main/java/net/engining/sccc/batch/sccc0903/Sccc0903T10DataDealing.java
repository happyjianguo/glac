package net.engining.sccc.batch.sccc0903;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.facility.SystemStatusFacility;
import net.engining.gm.infrastructure.enums.BusinessType;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.CactAccount;
import net.engining.pcx.cc.infrastructure.shared.model.CactAccountAddi;
import net.engining.pcx.cc.infrastructure.shared.model.CactAccountNo;
import net.engining.pcx.cc.infrastructure.shared.model.CactTxnPost;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlTxn;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccount;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccountAddi;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccountNo;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnPost;
import net.engining.pcx.cc.param.model.enums.Deadline;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.bean.batchBean.RequestData;
import net.engining.sccc.biz.service.CheckAcctSeqService;
import net.engining.sccc.biz.service.params.ProductAccount;

/**
 * 数据处理
 *
 */
@Service
@StepScope
public class Sccc0903T10DataDealing implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(Sccc0903T10DataDealing.class);

	@Autowired
	SystemStatusFacility systemStatusFacility;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ParameterFacility parameterFacility;

	@Autowired
	private Provider4Organization provider4Organization;

	@Autowired
	private CheckAcctSeqService checkAcctSeqService;

	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		QCactTxnPost qCactTxnPost = QCactTxnPost.cactTxnPost;
		List<CactTxnPost> cactTxnPostList = (List<CactTxnPost>) new JPAQueryFactory(em).select(qCactTxnPost)
				.from(qCactTxnPost).where(qCactTxnPost.bizDate.eq(bizDate)).fetch();
		for (CactTxnPost cactTxnPost : cactTxnPostList) {

			if (cactTxnPost.getAcctSeq() != null) {
				continue;
			}
			// 取数据
			EveryDayAccountingBean headerdata = JSON.parseObject(cactTxnPost.getRequestData(),
					new TypeReference<EveryDayAccountingBean>() {
					});
			// 查询总账表相关信息，便于更新
			QApGlTxn qApGlTxn = QApGlTxn.apGlTxn;
			String txnSeq= cactTxnPost.getTxnSeq().toString();
			ApGlTxn apGlTxn = new JPAQueryFactory(em).select(qApGlTxn).from(qApGlTxn)
					.where(qApGlTxn.txnDetailSeq.eq(txnSeq)).fetchOne();

			Integer acctSeq = null;
			if (headerdata.getRequestData().getIouNo() != null && headerdata.getRequestData().getCustId() != null) {
				acctSeq = checkAcctSeqService.checkAcctSeq(headerdata.getRequestData().getCustId(),
						headerdata.getRequestData().getIouNo(), headerdata.getRequestData().getTotalPeriod(),
						headerdata.getRequestData().getProductId(), bizDate);
//				acctSeq = acct.getAcctSeq();
			} else {
				acctSeq = 0;
			}

			// 账户信息新增后，更新当日交易表与总账表信息
			cactTxnPost.setAcctSeq(acctSeq);
			apGlTxn.setAcctSeq(acctSeq);

		}

		return RepeatStatus.FINISHED;
	}
}
