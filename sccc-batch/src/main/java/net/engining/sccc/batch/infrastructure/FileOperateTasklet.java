package net.engining.sccc.batch.infrastructure;

import java.util.List;

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

import com.google.common.collect.Lists;

import net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd;
import net.engining.sccc.batch.infrastructure.metas.FileOperateActionBean;
import net.engining.sccc.batch.infrastructure.metas.FileOperationType;
import net.engining.sccc.batch.infrastructure.utils.FileOperationUtils;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * 负责文件的拷贝、移动、删除和压缩任务 支持多文件操作
 *
 */
public class FileOperateTasklet implements Tasklet {

	private static final Logger logger = LoggerFactory.getLogger(FileOperateTasklet.class);

	/**
	 * 获取文件资源的路径pattern
	 */
	private String locationPattern;
	
	private InspectionCd inspectionCd;
	
	List<FileOperateActionBean> fileOperateActions;
	
	@Autowired
	BatchTaskProperties batchTaskProperties;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		//根据业务文件类型从Context获取前序Step处理的文件所在路径
		String dir = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().getString(inspectionCd.toString()+"-Dir");
		//根据batchSeq确定处理目录
		String batchSeq = (String) chunkContext.getStepContext().getJobParameters().get(BatchJobParameterKeys.BatchSeq);
		String destDir = batchTaskProperties.getDefaultBackupInputDir()+batchSeq+"/";
		if(this.locationPattern != null && dir !=null){
			logger.debug("从Job ExecutionContext 获取读取dat文件的目录={}", dir);
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			StringBuffer urlPathStr = new StringBuffer();
			urlPathStr.append("file:");
			urlPathStr.append(dir + this.locationPattern);
			Resource[] resources = resolver.getResources(urlPathStr.toString());
			
			List<FileOperateActionBean> actions = Lists.newArrayList();

			FileOperateActionBean accountCheckingOkFileOperateActionBean = null;
			for (Resource resource : resources) {
				accountCheckingOkFileOperateActionBean = new FileOperateActionBean();
				accountCheckingOkFileOperateActionBean.setSrcResource(resource);
				urlPathStr = new StringBuffer();
				urlPathStr.append("file:");
				urlPathStr.append(destDir);
				accountCheckingOkFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
				accountCheckingOkFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
				actions.add(accountCheckingOkFileOperateActionBean);
			}
			
			fileOperateActions = actions;

		}
		else {
			logger.warn("未配置获取文件资源的路径pattern，应该配置直接配置了fileOperateActions");
		}
		
		Assert.notNull(fileOperateActions, "fileOperateActions must be set");
		FileOperationUtils.operate(fileOperateActions);
		
		return RepeatStatus.FINISHED;
	}

	public InspectionCd getInspectionCd() {
		return inspectionCd;
	}

	public void setInspectionCd(InspectionCd inspectionCd) {
		this.inspectionCd = inspectionCd;
	}

	public String getLocationPattern() {
		return locationPattern;
	}

	public void setLocationPattern(String locationPattern) {
		this.locationPattern = locationPattern;
	}

	public List<FileOperateActionBean> getFileOperateActions() {
		return fileOperateActions;
	}

	public void setFileOperateActions(List<FileOperateActionBean> fileOperateActions) {
		this.fileOperateActions = fileOperateActions;
	}
}
