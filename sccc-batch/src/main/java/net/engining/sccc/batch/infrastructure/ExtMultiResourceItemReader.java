package net.engining.sccc.batch.infrastructure;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

import net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd;

/**
 * 相同FlatFileItemReader的多文件读取，可忽略异常数据并持久化
 * 
 * @param <T>
 */
public class ExtMultiResourceItemReader<T> extends MultiResourceItemReader<T> implements InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(ExtMultiResourceItemReader.class);
	
	/**
	 * 获取文件资源的路径pattern
	 */
	private String locationPattern;
	
	private InspectionCd inspectionCd;
	
	private FileHeaderLineCallbackHandler headerLinesCallback;
	
	private FlatFileItemReader<T> flatFileItemReader;
	
	@BeforeStep
	void beforeStep(StepExecution stepExecution) throws IOException{
		ExecutionContext executionContext = stepExecution.getExecutionContext();
		executionContext.put("inspectionCd", inspectionCd);
		logger.debug("为Step ExecutionContext 加入对应的检查项标识={}", inspectionCd);
		
		//设置文件Header处理必要属性字段
		headerLinesCallback.setInspectionCd(inspectionCd);
		headerLinesCallback.setBizDate(stepExecution.getJobParameters().getDate(BatchJobParameterKeys.BizDate));
		headerLinesCallback.setBatchSeq(stepExecution.getJobParameters().getString(BatchJobParameterKeys.BatchSeq));
		
		int mult = stepExecution.getJobExecution().getExecutionContext().getInt(inspectionCd.toString()+"-Numbers");
		logger.debug("从Job ExecutionContext 获取读取dat文件的数量={}", mult);
		headerLinesCallback.setMult(stepExecution.getJobExecution().getExecutionContext().getInt(inspectionCd.toString()+"-Numbers"));
		
		//为父类设置
		flatFileItemReader.setSkippedLinesCallback(headerLinesCallback);
		this.setDelegate(flatFileItemReader);
		
		//获取前序step操作的相应资源文件目录
		String dir = stepExecution.getJobExecution().getExecutionContext().getString(inspectionCd.toString()+"-Dir");
		if(this.locationPattern != null && dir !=null){
			logger.debug("从Job ExecutionContext 获取读取dat文件的目录={}", dir);
			StringBuffer urlPathStr = new StringBuffer();
			urlPathStr.append("file:");
			urlPathStr.append(dir + this.locationPattern);
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources(urlPathStr.toString());
			super.setResources(resources);
		}
		else {
			logger.warn("未配置获取文件资源的路径pattern，应该配置直接配置了resources");
		}
		
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
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(inspectionCd, "inspectionCd is required");
		Assert.notNull(headerLinesCallback, "headerLinesCallback is required");
		Assert.notNull(flatFileItemReader, "flatFileItemReader is required");
		
	}

	public String getLocationPattern() {
		return locationPattern;
	}

	public void setLocationPattern(String locationPattern) {
		this.locationPattern = locationPattern;
	}

	public FlatFileItemReader<T> getFlatFileItemReader() {
		return flatFileItemReader;
	}

	public void setFlatFileItemReader(FlatFileItemReader<T> flatFileItemReader) {
		this.flatFileItemReader = flatFileItemReader;
	}
	
}
