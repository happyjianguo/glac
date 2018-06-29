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

import com.querydsl.jpa.impl.JPADeleteClause;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;
import net.engining.pcx.cc.infrastructure.shared.model.QApSubjectSummary;
import net.engining.pcx.cc.infrastructure.shared.model.QCactTxnPost;

/**
 * 
 * 数据治理，当日入账交易表数据删除；<br>
 * AP_SUBJECT_SUMMARY -> AP_SUBJECT_SUMMARY_HST;<br> 
 *
 */
@Service
@StepScope
public class Sccc1000T20DeleteApSubjectSummary implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(Sccc1000T20DeleteApSubjectSummary.class);
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;
	
	@PersistenceContext
	private EntityManager em;

 	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		log.info("删除AP_SUBJECT_SUMMARY 当日入账交易表迁移中除未对账的数据，batchDate={}", batchDate);
		
		QApSubjectSummary qApSubjectSummary =QApSubjectSummary.apSubjectSummary;
		
		new JPADeleteClause(em, qApSubjectSummary).execute();
		
		
		
		return RepeatStatus.FINISHED;
	}
}