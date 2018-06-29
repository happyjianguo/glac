package net.engining.sccc.batch.listener;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd;
import net.engining.pcx.cc.infrastructure.shared.model.BtImportException;
import net.engining.pg.support.core.exception.ErrorMessageException;

/**
 * 针对批量中读取Flat文件的Step监听；
 * 主要记录读取文件时出现的异常，需要进行记录；
 * @author luxue
 *
 */
@Component
public class BatchStepFlatFileErroredListener implements StepExecutionListener {

	private static final Logger log = LoggerFactory.getLogger(BatchStepFlatFileErroredListener.class);

	@PersistenceContext
	private EntityManager em;

	private Date bizDate;

	private String batchSeq;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		JobParameters jobParameters = stepExecution.getJobParameters();
		bizDate = jobParameters.getDate("bizDate");
		batchSeq = jobParameters.getString("batchSeq");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		ExecutionContext executionContext = stepExecution.getExecutionContext();
		
		if (stepExecution.getStatus() == BatchStatus.FAILED) {
			for (Throwable t : stepExecution.getFailureExceptions()) {
				if (t instanceof FlatFileParseException) {
					FlatFileParseException ex = (FlatFileParseException) t;
					// 记录异常数据
					BtImportException impEx = new BtImportException();
					impEx.setLineContent(ex.getInput());
					impEx.setExceptionMsg(ex.getMessage());
					impEx.setBatchSeq(batchSeq);
					impEx.setBizDate(bizDate);
					// 记录该文件对应的检查项代码
					if(executionContext.containsKey("inspectionCd")){
						impEx.setInspectionCd((InspectionCd) executionContext.get(("inspectionCd")));
					}
					else{
						log.warn("未找到对应的检查项代码，请设置！");
					}
					em.persist(impEx);
					log.warn("读取文件数据记录出现异常：{}", ex.getMessage());
				}
				else if (t instanceof ErrorMessageException){
					ErrorMessageException ex = (ErrorMessageException) t;
					// 记录异常数据
					BtImportException impEx = new BtImportException();
					impEx.setExceptionMsg(ex.getMessage());
					impEx.setBatchSeq(batchSeq);
					impEx.setBizDate(bizDate);
					// 记录该文件对应的检查项代码
					if(executionContext.containsKey("inspectionCd")){
						impEx.setInspectionCd((InspectionCd) executionContext.get(("inspectionCd")));
					}
					else{
						log.warn("未找到对应的检查项代码，请设置！");
					}
					em.persist(impEx);
					log.warn("处理文件数据记录出现异常：{}", ex.getMessage());
				}
			}
		}

		return stepExecution.getExitStatus();
	}
	
}
