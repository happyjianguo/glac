package net.engining.sccc.batch.sccc0902;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.alibaba.fastjson.JSONObject;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pg.batch.sdk.file.FlatFileHeader;
import net.engining.sccc.batch.service.CheckListSysService;
import net.engining.sccc.entity.enums.TxnTypeDef;
import net.engining.sccc.entity.model.BtEodTxnImport;
import net.engining.sccc.entity.model.CactSysChecklist;
import net.engining.sccc.entity.model.QBtEodTxnImport;
import net.engining.sccc.entity.model.QCactSysChecklist;
import net.engining.sccc.enums.InspectionCd;

/**
 * 批量还款记账CheckList
 * @author luxue
 *
 */
@Service
@StepScope
public class Sccc0902T93BatchRepayUpdateCheckList implements Tasklet {
	
	private static final Logger logger = LoggerFactory.getLogger(Sccc0902T93BatchRepayUpdateCheckList.class);

	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;
	
	@Value("#{jobParameters['batchSeq']}")
	private String batchSeq;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CheckListSysService checkListSysService;
	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 *  更新对账文件到达CheckList项
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		QCactSysChecklist qCactSysChecklist = QCactSysChecklist.cactSysChecklist;
		CactSysChecklist cactSysChecklist = new JPAQueryFactory(em).select(qCactSysChecklist)
				.from(qCactSysChecklist).where(qCactSysChecklist.bizDate.eq(bizDate),
						qCactSysChecklist.inspectionCd.eq(InspectionCd.RepaymentAcct), qCactSysChecklist.batchSeq.eq(batchSeq))
				.fetchOne();

		QBtEodTxnImport qBtEodTxnImport = QBtEodTxnImport.btEodTxnImport;
		List<BtEodTxnImport> btEodTxnImportList = new JPAQueryFactory(em).select(qBtEodTxnImport).from(qBtEodTxnImport)
				.where(qBtEodTxnImport.bizDate.eq(bizDate), qBtEodTxnImport.batchSeq.eq(batchSeq),qBtEodTxnImport.txnType.eq(TxnTypeDef.BATREPA)).fetch();

		if (btEodTxnImportList.size() > 0 && cactSysChecklist != null) {
			List<FlatFileHeader> flatFileHeaderList = JSONObject.parseArray(cactSysChecklist.getCheckBizData(),
					FlatFileHeader.class);
			Integer totalLines = 0;
			for (FlatFileHeader FlatFileHeader : flatFileHeaderList) {
				totalLines =FlatFileHeader.getTotalLines() + totalLines;
			}
			if (btEodTxnImportList.size() != totalLines) {
				logger.warn("处理文件的总行数与文件中的总行数不一致，请检查！");
				return RepeatStatus.FINISHED;
			}
			checkListSysService.checkSys(cactSysChecklist);
		}
		return RepeatStatus.FINISHED;
	}


}
