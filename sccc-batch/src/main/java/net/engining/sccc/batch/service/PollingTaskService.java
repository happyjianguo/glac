package net.engining.sccc.batch.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.ProcessingStrutsDef;
import net.engining.pcx.cc.infrastructure.shared.enums.ReportTypeDef;
import net.engining.pcx.cc.infrastructure.shared.model.CrfReport;
import net.engining.pcx.cc.infrastructure.shared.model.PollingTask;
import net.engining.pcx.cc.infrastructure.shared.model.QCrfReport;
import net.engining.pcx.cc.infrastructure.shared.model.QPollingTask;
import net.engining.pcx.cc.infrastructure.shared.model.QTransReport;
import net.engining.pcx.cc.infrastructure.shared.model.TransReport;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.sccc.batch.schedule.ScheduledPollingTask;
import net.engining.sccc.biz.bean.QueryCondition;
import net.engining.sccc.biz.enums.DateTypeDef;
@Service
public class PollingTaskService {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledPollingTask.class);
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Provider7x24 provider7x24;
	
	@Autowired(required = false)
	FileOutputStream fos;
	
	@Autowired(required = false)
	HSSFWorkbook workbook;
	
	@Transactional
	public void createReport() throws IOException{
		QPollingTask q = QPollingTask.pollingTask;
		List<PollingTask> taskList = new JPAQueryFactory(em).select(q).from(q)
				.where(q.processingStruts.eq(ProcessingStrutsDef.P),
						q.bizDate.eq(provider7x24.getCurrentDate().toDate()))
				.fetch();
		
		for (PollingTask task : taskList) {
			// 更新POLLING_TASK表处理状态为处理中
			task.setProcessingStruts(ProcessingStrutsDef.B);

			// 判断报表类型 Trans or CRF
			if (task.getReportType().equals(ReportTypeDef.T)) {
				getTrans(task);
			} else {
				getCRF(task);
			}
		}
	}
	
	@Transactional
	private void getTrans(PollingTask task) throws IOException {
		//获取查询条件
		QueryCondition query = JSON.parseObject(task.getQueryCondition(), QueryCondition.class);
		QTransReport q = QTransReport.transReport;

		BooleanExpression w1 = null;
		if (query.getInOutFlag() != null) {
			w1 = q.inOutFlag.eq(query.getInOutFlag());
		} 
		BooleanExpression w2 = null;
		if (query.getPostTxnTypeDef() != null) {
			w2 = q.tradeType.eq(query.getPostTxnTypeDef().name());
		} 
		BooleanExpression w3 = null;
		BooleanExpression w4 = null;
		if (query.getDateType().equals(DateTypeDef.C)) {
			w3 = q.clearDate.after(query.getStartDate());
			w4 = q.clearDate.before(query.getEndDate());
		} else {
			w3 = q.tradeDate.after(query.getStartDate());
			w4 = q.tradeDate.before(query.getEndDate());
		}

		// 查询trans报表
		List<TransReport> transList = new JPAQueryFactory(em).select(q).from(q).where(w1, w2, w3, w4).fetch();

		// 文件路径
		String filePath = task.getRecordLocation() + task.getFileName();
		// 生成Trans报表
		createTrans(transList, filePath);
		// 更新POLLING_TASK表处理状态为完成
		task.setProcessingStruts(ProcessingStrutsDef.F);
	}

	@Transactional
	private void getCRF(PollingTask task) throws IOException {
		//获取查询条件
		QueryCondition query = JSON.parseObject(task.getQueryCondition(), QueryCondition.class);
		QCrfReport q = QCrfReport.crfReport;
		
		BooleanExpression w1 = null;
		if (query.getInOutFlag() != null) {
			w1 = q.inOutFlag.eq(query.getInOutFlag());
		} 
		BooleanExpression w2 = null;
		if (query.getSubjectNumber() != null) {
			w2 = q.subjectCd.eq(query.getSubjectNumber());
		} 
		BooleanExpression w3 = null;
		BooleanExpression w4 = null;
		if (query.getDateType().equals(DateTypeDef.C)) {
			w3 = q.clearDate.after(query.getStartDate());
			w4 = q.clearDate.before(query.getEndDate());
		} else {
			w3 = q.accountingDate.after(query.getStartDate());
			w4 = q.accountingDate.before(query.getEndDate());
		}

		// 查询CRF报表
		List<CrfReport> crfList = new JPAQueryFactory(em).select(q).from(q).where(w1, w2, w3, w4).fetch();

		// 文件路径
		String filePath = task.getRecordLocation() + task.getFileName();
		// 生成CRF报表
		createCRF(crfList, filePath);
		// 更新POLLING_TASK表处理状态为完成
		task.setProcessingStruts(ProcessingStrutsDef.F);
	}

	private void createTrans(List<TransReport> transList, String filePath) throws IOException {
		workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Trans报表");
		String[] title = { "序号", "机构号", "分行号", "表内外标志", "交易类型", "交易流水号", "记账流水号", "外部流水号", "借据号", "记账日期", "报表日期",
				"清算日期", "交易日期", "记账摘要", "科目号", "科目名称", "借方金额", "贷方金额", "辅助核算项", "创建日期", "最后更新日期", "系统业务日期" };
		HSSFRow row = sheet.createRow((short) 0);
		int i = 0;
		for (String s : title) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(s);
			i++;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		int j = 1;
		for (TransReport t : transList) {
			// 创建第j行
			HSSFRow rowData = sheet.createRow((short) j);
			// 第一列数据
			HSSFCell cell0 = rowData.createCell((short) 0);
			cell0.setCellValue(t.getTransId());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第二列数据
			HSSFCell cell1 = rowData.createCell((short) 1);
			cell1.setCellValue(t.getOrg());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第三列数据
			HSSFCell cell2 = rowData.createCell((short) 2);
			cell2.setCellValue(t.getBranchNo());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第四列数据
			HSSFCell cell3 = rowData.createCell((short) 3);
			cell3.setCellValue(t.getInOutFlag().toString());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第五列数据
			HSSFCell cell4 = rowData.createCell((short) 4);
			cell4.setCellValue(t.getTradeType());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第六列数据
			HSSFCell cell5 = rowData.createCell((short) 5);
			cell5.setCellValue(t.getTransactionSerialNumber());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第七列数据
			HSSFCell cell6 = rowData.createCell((short) 6);
			cell6.setCellValue(t.getAccountNumber());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第八列数据
			HSSFCell cell7 = rowData.createCell((short) 7);
			cell7.setCellValue(t.getExternalFlowNumber());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第九列数据
			HSSFCell cell8 = rowData.createCell((short) 8);
			cell8.setCellValue(t.getIousNumber());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第十列数据
			HSSFCell cell9 = rowData.createCell((short) 9);
			cell9.setCellValue(format.format(t.getAccountingDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第11列数据
			HSSFCell cell10 = rowData.createCell((short) 10);
			cell10.setCellValue(format.format(t.getReportDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第12列数据
			HSSFCell cell11 = rowData.createCell((short) 11);
			cell11.setCellValue(format.format(t.getClearDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第13列数据
			HSSFCell cell12 = rowData.createCell((short) 12);
			cell12.setCellValue(format.format(t.getTradeDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第14列数据
			HSSFCell cell13 = rowData.createCell((short) 13);
			cell13.setCellValue(t.getAccountAbstract());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第15列数据
			HSSFCell cell14 = rowData.createCell((short) 14);
			cell14.setCellValue(t.getSubjectCd());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第16列数据
			HSSFCell cell15 = rowData.createCell((short) 15);
			cell15.setCellValue(t.getSubjectName());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第17列数据
			HSSFCell cell16 = rowData.createCell((short) 16);
			cell16.setCellValue(t.getAmountDebitSide().toString());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第18列数据
			HSSFCell cell17 = rowData.createCell((short) 17);
			cell17.setCellValue(t.getAmountCreditSide().toString());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第19列数据
			HSSFCell cell18 = rowData.createCell((short) 18);
			cell18.setCellValue(t.getAssistAccount());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第20列数据
			HSSFCell cell19 = rowData.createCell((short) 19);
			cell19.setCellValue(format.format(t.getSetupDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第21列数据
			HSSFCell cell20 = rowData.createCell((short) 20);
			cell20.setCellValue(format.format(t.getLastUpdateDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第22列数据
			HSSFCell cell21 = rowData.createCell((short) 21);
			cell21.setCellValue(format.format(t.getBizDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);

			j++;
		}
			// 导出数据库文件保存路径
			fos = new FileOutputStream(filePath);
			/*
			 * if(fos.toString().endsWith("xlsx")){ workbook=new XSSFWorkbook();
			 * }else if(fos.toString().endsWith("xls")){ workbook=new
			 * HSSFWorkbook(); }
			 */
			// 将工作簿写入文件
			workbook.write(fos);
			log.info("导出文件成功:" + filePath);
	}

	private void createCRF(List<CrfReport> crfList, String filePath) throws IOException {
//		FileOutputStream fos = null;
		workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("CRF报表");
		String[] title = { "序号", "机构号", "分行号", "报表日期", "清算日期", "记账日期", "表内外标志", "科目号", "科目名称", "上期余额", "当前余额", "借贷标志",
				"借方金额", "贷方金额", "辅助核算项", "创建日期", "最后更新日期", "系统业务日期" };
		HSSFRow row = sheet.createRow((short) 0);
		int i = 0;
		for (String s : title) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(s);
			i++;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		int j = 1;
		for (CrfReport t : crfList) {
			// 创建第j行
			HSSFRow rowData = sheet.createRow((short) j);
			// 第一列数据
			HSSFCell cell0 = rowData.createCell((short) 0);
			cell0.setCellValue(t.getCrfId());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第二列数据
			HSSFCell cell1 = rowData.createCell((short) 1);
			cell1.setCellValue(t.getOrg());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第三列数据
			HSSFCell cell2 = rowData.createCell((short) 2);
			cell2.setCellValue(t.getBranchNo());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第四列数据
			HSSFCell cell3 = rowData.createCell((short) 3);
			cell3.setCellValue(format.format(t.getReportDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第五列数据
			HSSFCell cell4 = rowData.createCell((short) 4);
			cell4.setCellValue(format.format(t.getClearDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第六列数据
			HSSFCell cell5 = rowData.createCell((short) 5);
			cell5.setCellValue(format.format(t.getAccountingDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第七列数据
			HSSFCell cell6 = rowData.createCell((short) 6);
			cell6.setCellValue(t.getInOutFlag().toString());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第八列数据
			HSSFCell cell7 = rowData.createCell((short) 7);
			cell7.setCellValue(t.getSubjectCd());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第九列数据
			HSSFCell cell8 = rowData.createCell((short) 8);
			cell8.setCellValue(t.getSubjectName());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第十列数据
			HSSFCell cell9 = rowData.createCell((short) 9);
			cell9.setCellValue(t.getPriorPeriod().toString());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第11列数据
			HSSFCell cell10 = rowData.createCell((short) 10);
			cell10.setCellValue(t.getCurrentBalance().toString());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第12列数据
			HSSFCell cell11 = rowData.createCell((short) 11);
			cell11.setCellValue(t.getTxnDirection().toString());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第13列数据
			HSSFCell cell12 = rowData.createCell((short) 12);
			cell12.setCellValue(t.getAmountDebitSide().toString());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第14列数据
			HSSFCell cell13 = rowData.createCell((short) 13);
			cell13.setCellValue(t.getAmountCreditSide().toString());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第15列数据
			HSSFCell cell14 = rowData.createCell((short) 14);
			cell14.setCellValue(t.getAssistAccount());
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第16列数据
			HSSFCell cell15 = rowData.createCell((short) 15);
			cell15.setCellValue(format.format(t.getSetupDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第17列数据
			HSSFCell cell16 = rowData.createCell((short) 16);
			cell16.setCellValue(format.format(t.getLastUpdateDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);
			// 第18列数据
			HSSFCell cell17 = rowData.createCell((short) 17);
			cell17.setCellValue(format.format(t.getBizDate()));
			// 设置单元格的宽度
			sheet.setColumnWidth((short) 0, (short) 10000);

			j++;
		}
		
			// 导出数据库文件保存路径
			fos = new FileOutputStream(filePath);
			/*
			 * if(fos.toString().endsWith("xlsx")){ workbook=new XSSFWorkbook();
			 * }else if(fos.toString().endsWith("xls")){ workbook=new
			 * HSSFWorkbook(); }
			 */
			// 将工作簿写入文件
			workbook.write(fos);
			log.info("导出文件成功:" + filePath);
			
	}

	public FileOutputStream getFos() {
		return fos;
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}
	
	
}
