package net.engining.sccc.batch.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;

import net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd;

/**
 * 单文件读取，可忽略异常数据并持久化
 * 
 * @param <T>
 */
public class ExtFlatFileItemReader<T> extends FlatFileItemReader<T>{
	
	private static final Logger logger = LoggerFactory.getLogger(ExtFlatFileItemReader.class);
	
	private InspectionCd inspectionCd;
	
	private FileHeaderLineCallbackHandler headerLinesCallback;
	
	@BeforeStep
	void beforeStep(StepExecution stepExecution){
		ExecutionContext executionContext = stepExecution.getExecutionContext();
		executionContext.put("inspectionCd", inspectionCd);
		logger.debug("为Step ExecutionContext 加入对应的检查项标识={}", inspectionCd);
		
		//设置文件Header处理必要属性字段
		headerLinesCallback.setInspectionCd(inspectionCd);
		headerLinesCallback.setBizDate(stepExecution.getJobParameters().getDate(BatchJobParameterKeys.BizDate));
		headerLinesCallback.setBatchSeq(stepExecution.getJobParameters().getString(BatchJobParameterKeys.BatchSeq));
		//为父类设置
		this.setSkippedLinesCallback(headerLinesCallback);
	}
	@AfterStep
	ExitStatus afterStep(StepExecution stepExecution){
		headerLinesCallback.setReadLines(0);
		return ExitStatus.COMPLETED;
	}
	public InspectionCd getInspectionCd() {
		return inspectionCd;
	}

	public void setInspectionCd(InspectionCd inspectionCd) {
		this.inspectionCd = inspectionCd;
	}

	public FileHeaderLineCallbackHandler getHeaderLinesCallback() {
		return headerLinesCallback;
	}

	public void setHeaderLinesCallback(FileHeaderLineCallbackHandler headerLinesCallback) {
		this.headerLinesCallback = headerLinesCallback;
	}

}
