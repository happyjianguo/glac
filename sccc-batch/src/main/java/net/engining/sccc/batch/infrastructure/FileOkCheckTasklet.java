package net.engining.sccc.batch.infrastructure;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;

/**
 * 检查OK文件是否到达; 
 * 还要检查对应文件名的data文件;
 * Ok标识文件与data文件文件名相同，扩展名不同;
 * @author luxue
 *
 */
public class FileOkCheckTasklet implements Tasklet {
	
	private static final Logger logger = LoggerFactory.getLogger(FileOkCheckTasklet.class);

	private Resource okResource;
	
	private Resource dataResource;
	
	private boolean strict = true;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Assert.notNull(okResource, "Input resource must be set");
		Assert.notNull(dataResource, "Input resource must be set");
		
		String okFilenameWithoutExtension = StringUtils.substringBeforeLast(okResource.getFilename(), ".");
		String dataFilenameWithoutExtension = StringUtils.substringBeforeLast(dataResource.getFilename(), ".");
		
		if(!okFilenameWithoutExtension.equals(dataFilenameWithoutExtension)){
			logger.warn("待检查ok标识文件{}，data文件{} 不匹配！", okResource.getFilename(), dataResource.getFilename());
			throw new ErrorMessageException(ErrorCode.CheckError, String.format("待检查ok标识文件%s，data文件%s 不匹配！", okResource.getFilename(), dataResource.getFilename()));
		}
		
		if (!okResource.exists()) {
			logger.warn("待检查的文件不存在！" + dataResource.getDescription());
			if(strict){
				throw new ErrorMessageException(ErrorCode.CheckError, String.format("待检查的文件不存在！%s", okResource.getDescription()));
			}
		}
		
		if (!okResource.isReadable()) {
			logger.warn("待检查的文件不可读！" + dataResource.getDescription());
			if(strict){
				throw new ErrorMessageException(ErrorCode.CheckError, String.format("待检查的文件不可读！%s", okResource.getDescription()));
			}
		}
		
		if (!dataResource.exists()) {
			logger.warn("待检查的文件不存在！" + dataResource.getDescription());
			if(strict){
				throw new ErrorMessageException(ErrorCode.CheckError, String.format("待检查的文件不存在！%s", dataResource.getDescription()));
			}
		}
		
		if (!dataResource.isReadable()) {
			logger.warn("待检查的文件不可读！" + dataResource.getDescription());
			if(strict){
				throw new ErrorMessageException(ErrorCode.CheckError, String.format("待检查的文件不可读！%s", dataResource.getDescription()));
			}
		}
		
		return RepeatStatus.FINISHED;
	}

	public Resource getOkResource() {
		return okResource;
	}

	public void setOkResource(Resource okResource) {
		this.okResource = okResource;
	}

	public Resource getDataResource() {
		return dataResource;
	}

	public void setDataResource(Resource dataResource) {
		this.dataResource = dataResource;
	}

	public boolean isStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

}
