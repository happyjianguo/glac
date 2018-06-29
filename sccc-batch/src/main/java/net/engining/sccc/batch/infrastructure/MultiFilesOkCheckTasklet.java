package net.engining.sccc.batch.infrastructure;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.batch.infrastructure.metas.FileOperateActionBean;
import net.engining.sccc.batch.infrastructure.metas.FileOperationType;
import net.engining.sccc.batch.infrastructure.utils.FileOperationUtils;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 多文件 ok标识文件以及其对应的data文件; 检查是否到达;
 * Ok标识文件与data文件文件名相同，扩展名不同;
 * @author luxue
 *
 */
public class MultiFilesOkCheckTasklet implements Tasklet {
	
	private static final Logger logger = LoggerFactory.getLogger(MultiFilesOkCheckTasklet.class);
	
	/**
	 * 获取ok标识文件Resource的路径Pattern
	 */
	private String okResourcePattern;
	
	/**
	 * 获取dat标识文件Resource的路径Pattern
	 */
	private String dataResourcePattern;

	/**
	 * ok标识文件
	 */
	private Resource[] okResources;
	
	/**
	 * ok标识文件对应的数据文件，通常定义为文件名相同，扩展名不同
	 */
	private Resource[] dataResources;
	
	/**
	 * 检查项类型
	 */
	private InspectionCd inspectionCd;
	
	private boolean strict = true;
	
	@Autowired
	BatchTaskProperties batchTaskProperties;
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		if(StringUtils.isNotBlank(okResourcePattern) && StringUtils.isNotBlank(dataResourcePattern)){
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			okResources = resolver.getResources(okResourcePattern);
			dataResources = resolver.getResources(dataResourcePattern);
		}
		
		Assert.notNull(okResources, "Input ok-flag resources must be set");
		Assert.notNull(dataResources, "Input data resources must be set");
		
		List<Resource> okResourcesLs = Arrays.asList(okResources);
		List<Resource> dataResourcesLs = Arrays.asList(dataResources);
		
		//ok标识文件名list，不含扩展名
		List<String> okResourceFileNames = Lists.transform(okResourcesLs, new Function<Resource, String>() {

			@Override
			public String apply(Resource input) {
				String filenameWithoutExtension = StringUtils.substringBeforeLast(input.getFilename(), ".");
				return filenameWithoutExtension;
			}
			
		});
		
		//data文件名list，不含扩展名
		List<String> dataResourceFileNames = Lists.transform(dataResourcesLs, new Function<Resource, String>() {

			@Override
			public String apply(Resource input) {
				String filenameWithoutExtension = StringUtils.substringBeforeLast(input.getFilename(), ".");
				return filenameWithoutExtension;
			}
			
		});
		
		//检查ok标识文件与其对应的data文件都已到达
		for(String filenameWithoutExtension : okResourceFileNames){
			if(dataResourceFileNames.contains(filenameWithoutExtension)){
				break;
			}
			else {
				logger.warn("待检查文件{}，ok标识文件与data文件没有全部到达！", dataResourceFileNames);
				if(strict){
					throw new ErrorMessageException(ErrorCode.CheckError, String.format("待检查文件%s，ok标识文件与data文件没有全部到达！", dataResourceFileNames));
				}
			}
			
		}
		
		//检查文件是否可用
		for(Resource resource : okResourcesLs){
			checkResource(resource);
		}
		for(Resource resource : dataResourcesLs){
			checkResource(resource);
		}
		
		if(StringUtils.isNotBlank(okResourcePattern) && StringUtils.isNotBlank(dataResourcePattern)){
			//move到相应的处理目录, 确保后续step获取相同的资源
			//设置文件操作定义Bean
			List<FileOperateActionBean> actions = Lists.newArrayList();
			StringBuffer urlPathStr;
			//根据batchSeq确定处理目录
			String batchSeq = (String) chunkContext.getStepContext().getJobParameters().get(BatchJobParameterKeys.BatchSeq);
			String destDir = batchTaskProperties.getDefaultBatchInputDir()+batchSeq+"/";
			chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().putString(inspectionCd.toString()+"-Dir", destDir);
			logger.debug("为Job ExecutionContext 加入对应的resourceDirKey={}", chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().getString(inspectionCd.toString()+"-Dir"));
			
			FileOperateActionBean okFileOperateActionBean = null;
			for (Resource resource : okResourcesLs) {
				okFileOperateActionBean = new FileOperateActionBean();
				okFileOperateActionBean.setSrcResource(resource);
				urlPathStr = new StringBuffer();
				urlPathStr.append("file:");
				urlPathStr.append(destDir);
				okFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
				okFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
				actions.add(okFileOperateActionBean);
			}
			FileOperateActionBean datFileOperateActionBean = null;
			for (Resource resource : dataResourcesLs) {
				datFileOperateActionBean = new FileOperateActionBean();
				datFileOperateActionBean.setSrcResource(resource);
				urlPathStr = new StringBuffer();
				urlPathStr.append("file:");
				urlPathStr.append(destDir);
				datFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
				datFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
				actions.add(datFileOperateActionBean);
			}
			FileOperationUtils.operate(actions);
		}
		
		//设置检查类型对应的文件资源数，存入JobExecutionContext，供之后的Step使用
		chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().putInt(inspectionCd.toString()+"-Numbers", dataResources.length);
		logger.debug("为Job ExecutionContext 加入对应的resourceNumbersKey={}", chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().getInt(inspectionCd.toString()+"-Numbers"));
		return RepeatStatus.FINISHED;
	}
	
	private void checkResource(Resource resource) {
		if (!resource.exists()) {
			logger.warn("待检查的文件{}，不存在！" + resource.getDescription());
			if(strict){
				throw new ErrorMessageException(ErrorCode.CheckError, String.format("待检查的文件不存在！%s", resource.getDescription()));
			}
		}
		
		if (!resource.isReadable()) {
			logger.warn("待检查的文件不可读！" + resource.getDescription());
			if(strict){
				throw new ErrorMessageException(ErrorCode.CheckError, String.format("待检查的文件不可读！%s", resource.getDescription()));
			}
		}
	}

	public String getOkResourcePattern() {
		return okResourcePattern;
	}

	public void setOkResourcePattern(String okResourcePattern) {
		this.okResourcePattern = okResourcePattern;
	}

	public String getDataResourcePattern() {
		return dataResourcePattern;
	}

	public void setDataResourcePattern(String dataResourcePattern) {
		this.dataResourcePattern = dataResourcePattern;
	}

	public Resource[] getOkResources() {
		return okResources;
	}

	public void setOkResources(Resource[] okResources) {
		this.okResources = okResources;
	}

	public Resource[] getDataResources() {
		return dataResources;
	}

	public void setDataResources(Resource[] dataResources) {
		this.dataResources = dataResources;
	}

	public boolean isStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	public InspectionCd getInspectionCd() {
		return inspectionCd;
	}

	public void setInspectionCd(InspectionCd inspectionCd) {
		this.inspectionCd = inspectionCd;
	}

}
