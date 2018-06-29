package net.engining.sccc.batch.infrastructure.metas;

import java.io.Serializable;

import org.springframework.core.io.Resource;

/**
 * @author luxue
 *
 */
public class FileOperateActionBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Resource srcResource;
	
	Resource destResource;
	
	FileOperationType fileOperationType;

	public Resource getSrcResource() {
		return srcResource;
	}

	public void setSrcResource(Resource srcResource) {
		this.srcResource = srcResource;
	}

	public Resource getDestResource() {
		return destResource;
	}

	public void setDestResource(Resource destResource) {
		this.destResource = destResource;
	}

	public FileOperationType getFileOperationType() {
		return fileOperationType;
	}

	public void setFileOperationType(FileOperationType fileOperationType) {
		this.fileOperationType = fileOperationType;
	}
	
}
