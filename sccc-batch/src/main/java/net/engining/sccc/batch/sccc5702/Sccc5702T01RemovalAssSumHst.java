package net.engining.sccc.batch.sccc5702;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import net.engining.sccc.batch.sccc1000.Sccc1000T12ApGlVolDtlAssSum;

/**
 * 
 * 数据治理，批量迁移数据到相应历史表；<br>
 * AP_GL_VOL_DTL_ASS_SUM -> AP_GL_VOL_DTL_ASS_SUM_HST;<br>
 *
 */
@Service
@StepScope
public class Sccc5702T01RemovalAssSumHst implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(Sccc1000T12ApGlVolDtlAssSum.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		log.info("AP_GL_VOL_DTL_ASS_SUM 当日数据迁移到 AP_GL_VOL_DTL_ASS_SUM_HST");

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(
				"INSERT INTO AP_GL_VOL_DTL_ASS_SUM_HST (ASS_SUM_SEQ, SUBJECT_CD, ASSIST_TYPE, ASSIST_ACCOUNT_VALUE, ORG, BRANCH_NO, VOL_DT, DB_BAL, CR_BAL, DB_AMT, CR_AMT, DB_COUNT, CR_COUNT, LAST_DB_BAL, LAST_CR_BAL, MTD_DB_AMT, MTD_CR_AMT, LAST_MTH_DB_BAL, LAST_MTH_CR_BAL, QTD_DB_AMT, QTD_CR_AMT, LAST_QTR_DB_BAL, LAST_QTR_CR_BAL, YTD_DB_AMT, YTD_CR_AMT, LAST_YR_DB_BAL, LAST_YR_CR_BAL, PROC_DATE, JPA_VERSION, SETUP_DATE, LAST_UPDATE_DATE, BIZ_DATE)");
		stringBuffer.append(" ");
		stringBuffer.append(
				"SELECT ASS_SUM_SEQ, SUBJECT_CD, ASSIST_TYPE, ASSIST_ACCOUNT_VALUE, ORG, BRANCH_NO, VOL_DT, DB_BAL, CR_BAL, DB_AMT, CR_AMT, DB_COUNT, CR_COUNT, LAST_DB_BAL, LAST_CR_BAL, MTD_DB_AMT, MTD_CR_AMT, LAST_MTH_DB_BAL, LAST_MTH_CR_BAL, QTD_DB_AMT, QTD_CR_AMT, LAST_QTR_DB_BAL, LAST_QTR_CR_BAL, YTD_DB_AMT, YTD_CR_AMT, LAST_YR_DB_BAL, LAST_YR_CR_BAL, PROC_DATE, JPA_VERSION, SETUP_DATE, LAST_UPDATE_DATE, BIZ_DATE FROM AP_GL_VOL_DTL_ASS_SUM");

		em.createNativeQuery(stringBuffer.toString()).executeUpdate();

		return RepeatStatus.FINISHED;
	}
}
