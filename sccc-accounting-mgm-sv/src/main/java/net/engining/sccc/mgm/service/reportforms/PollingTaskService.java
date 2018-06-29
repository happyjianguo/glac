package net.engining.sccc.mgm.service.reportforms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.ProcessingStrutsDef;
import net.engining.pcx.cc.infrastructure.shared.enums.ReportTypeDef;
import net.engining.pcx.cc.infrastructure.shared.model.PollingTask;
import net.engining.pcx.cc.infrastructure.shared.model.QPollingTask;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.JPAFetchResponseBuilder;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.biz.bean.QueryCondition;
import net.engining.sccc.config.props.CommonProperties;

/**
 * 
 * @author liqingfeng
 *
 */
@Service
public class PollingTaskService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Provider4Organization provider4Organization;

	@Autowired
	private Provider7x24 provider7x24;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	CommonProperties commonProperties;
	/**
	 * 将查询报表的信息插入 POLLING_TASK 表
	 * 
	 * @param queryCondition
	 * @param reportTypeDef
	 */
	@Transactional
	public void insertQueryIntoPollingTast(QueryCondition queryCondition, ReportTypeDef reportTypeDef) {

		PollingTask pollingTask = new PollingTask();
		pollingTask.fillDefaultValues();
		pollingTask.setOrg(provider4Organization.getCurrentOrganizationId());
		pollingTask.setBranchNo("00001");
		pollingTask.setQueryCondition(JSON.toJSONString(queryCondition));
		pollingTask.setProcessingStruts(ProcessingStrutsDef.P);
		pollingTask.setReportType(reportTypeDef);
		pollingTask.setRecordLocation(commonProperties.getDefaultOutputDir() + "report/");
		if (reportTypeDef.equals(ReportTypeDef.C)) {
			pollingTask.setFileName(System.currentTimeMillis() + "CRF.xls");
		}
		if (reportTypeDef.equals(ReportTypeDef.T)) {
			pollingTask.setFileName(System.currentTimeMillis() + "Trans.xls");
		}
		pollingTask.setBizDate(provider7x24.getCurrentDate().toDate());
		em.persist(pollingTask);
	}

	/**
	 * 从轮循表查询轮循任务
	 * 
	 * @param reportType
	 * @param range
	 * @return
	 */
	public FetchResponse<Map<String, Object>> fetchPollingTast(ReportTypeDef reportType, Range range) {
		QPollingTask qPollingTask = QPollingTask.pollingTask;
		JPAQuery<Tuple> query = new JPAQueryFactory(em)
				.select(qPollingTask.id, qPollingTask.startTime, qPollingTask.queryCondition,
						qPollingTask.processingStruts)
				.from(qPollingTask).where(qPollingTask.reportType.eq(reportType))
				.orderBy(qPollingTask.startTime.desc());
		return new JPAFetchResponseBuilder<Map<String, Object>>().range(range).buildAsMap(query, qPollingTask.id,
				qPollingTask.startTime, qPollingTask.queryCondition, qPollingTask.processingStruts);
	}

	/**
	 * 获取id对应的实体
	 * 
	 * @param id
	 * @return
	 */
	public PollingTask getFileInfo(Integer id) {
		return em.find(PollingTask.class, id);
	}

	/**
	 * 传入文件名及文件路径执行文件下载
	 * @param filePath
	 * @param fileName
	 * @param os
	 */
	public void downloadFile(String filePath, String fileName, OutputStream os) {
		// 如果文件名与文件路径不为空，则执行下载

		InputStream is = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		if (StringUtils.isNotBlank(filePath) && StringUtils.isNotBlank(fileName)) {
			String path = constructPath(filePath, fileName);
			try {
				is = new FileInputStream(path);
				// 使用缓冲流
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(os);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = bis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new ErrorMessageException(ErrorCode.SystemError, "文件不存在");
			} finally {
				this.release(bos);
				this.release(os);
				this.release(bis);
				this.release(is);
			}
		}
	}
	
	private String constructPath(String filePath, String fileName) {
		String path = null;
		int index = filePath.lastIndexOf("\\");
		filePath = filePath.substring(0, index) + "\\sccc-batch-master-sv\\outputfiles\\report\\";
		path = filePath + fileName;
		return path;
	}

	private void release(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	private void release(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	
}
