package net.engining.sccc.batch.sccc1000;

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

/**
 * 
 * 数据治理，批量迁移数据到相应历史表；<br>
 * AP_SUBJECT_SUMMARY -> AP_SUBJECT_SUMMARY_HST;<br> 
 *
 */
@Service
@StepScope
public class Sccc1000T10ApSubjectSummary implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(Sccc1000T10ApSubjectSummary.class);
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		log.info("AP_SUBJECT_SUMMARY 当日数据迁移到 AP_SUBJECT_SUMMARY_HST，batchDate={}", batchDate);
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("INSERT INTO AP_SUBJECT_SUMMARY_HST (SEQ, ORG, BRANCH_NO, SUBJECT_CD, OWING_BRANCH, DB_AMT, CR_AMT, DB_BAL, CR_BAL, DB_COUNT, CR_COUNT, SETUP_DATE, LAST_UPDATE_DATE, BIZ_DATE, JPA_VERSION)");
		stringBuffer.append(" ");
		stringBuffer.append("SELECT SEQ, ORG, BRANCH_NO, SUBJECT_CD, OWING_BRANCH, DB_AMT, CR_AMT, DB_BAL, CR_BAL, DB_COUNT, CR_COUNT, SETUP_DATE, LAST_UPDATE_DATE, BIZ_DATE, JPA_VERSION FROM AP_SUBJECT_SUMMARY");
		
		em.createNativeQuery(stringBuffer.toString()).executeUpdate();
		
		return RepeatStatus.FINISHED;
	}
}
