/**
 * 
 */
package net.engining.sccc.batch.infrastructure.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import net.engining.sccc.batch.infrastructure.metas.FileOperateActionBean;
import net.engining.sccc.batch.infrastructure.metas.FileOperationType;

/**
 * 文件操作工具
 * @author luxue
 *
 */
public class FileOperationUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileOperationUtils.class);

	public static void operate(List<FileOperateActionBean> fileOperateActions) throws IOException{

		checkNotNull(fileOperateActions, "fileOperateActions 文件操作定义必须指定");

		for (FileOperateActionBean fileOperateActionBean : fileOperateActions) {
			if (FileOperationType.DELFILE.equals(fileOperateActionBean.getFileOperationType())) {
				Resource srcResource = fileOperateActionBean.getSrcResource();
				checkNotNull(srcResource, "请指定源文件或目录");

				if (srcResource.exists()) {
					File file = srcResource.getFile().getAbsoluteFile();
					logger.info("准备删除文件{}", file.getAbsolutePath());
					if (FileUtils.deleteQuietly(file)) {
						logger.info("文件{}删除成功", file.getAbsolutePath());
					} else {
						logger.info("文件{}删除失败", file.getAbsolutePath());
					}
				}
				
			} else {
				Resource srcResource = fileOperateActionBean.getSrcResource();
				Resource destResource = fileOperateActionBean.getDestResource();
				checkNotNull(srcResource, "请指定源文件或目录");
				checkNotNull(destResource, "请指定目标文件或目录");

				File srcFile = srcResource.getFile().getAbsoluteFile();
				File destFile = destResource.getFile().getAbsoluteFile();

				switch (fileOperateActionBean.getFileOperationType()) {
				case COPYFILE:
					FileUtils.copyFile(srcFile, destFile);
					logger.info("文件{}拷贝成功", srcFile.getAbsolutePath());
					break;

				case MVFILE:
					FileUtils.moveFile(srcFile, destFile);
					logger.info("文件{}成功移动到{}", srcFile.getAbsolutePath(), destFile.getAbsoluteFile());
					break;

				case COPY2DIR:
					FileUtils.copyFileToDirectory(srcFile, destFile);
					logger.info("文件{}拷贝成功", srcFile.getAbsolutePath());
					break;

				case MV2DIR:
					FileUtils.moveFileToDirectory(srcFile, destFile, true);
					logger.info("文件{}成功移动到{}", srcFile.getAbsolutePath(), destFile.getAbsoluteFile());
					break;

				case ZIPFILE:
					zipFile(srcFile, destFile);
					break;

				default:
					break;
				}

				// 这里为了满足GTP用户的文件操作权限，设置文件的权限为777
				destFile.setReadable(true, false);
				destFile.setWritable(true, false);
				destFile.setExecutable(true, false);
			}

		}
		
	}
	
	private static void zipFile(File srcFile, File destFile) throws IOException {

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destFile));
		out.putNextEntry(new ZipEntry(srcFile.getName()));

		FileInputStream in = new FileInputStream(srcFile.getPath());

		int b;
		byte[] by = new byte[1024];
		while ((b = in.read(by)) != -1) {
			// 将需要压缩文件数据写到压缩文件中
			out.write(by, 0, b);
		}

		IOUtils.closeQuietly(in);
		IOUtils.closeQuietly(out);

		logger.info("文件{}压缩成{}任务完成", srcFile.getAbsolutePath(), destFile.getAbsolutePath());

	}
}
