package net.engining.sccc.mgm.bean.query.service;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.param.model.Subject;
import net.engining.sccc.biz.bean.HistoricalAccountRecord;
@Service
public class HistoricalAccountExcelService {
	
public void excelFile(List<HistoricalAccountRecord> list,HttpServletResponse response){
		
		String sheetName = "会计分录查询";
		
		String []title = new String[]{"交易日期","记账日期","记账流水号","交易摘要","记账摘要","交易金额","记账金额","借据号"};
		
		String fileName = "loanDetail.xls";
		
//		createExcel(sheetName,title,fileName,list);
		
        String[] [] values = new String[list.size()] [];
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i = 0;i<list.size();i++){
			values[i] = new String[title.length];
			HistoricalAccountRecord map = list.get(i);
			values[i][0] = String.valueOf(map.getTxnDate()) == "null"?"":String.valueOf(map.getTxnDate());
			values[i][1] = String.valueOf(map.getPostDate()) == "null"?"":String.valueOf(map.getPostDate());
			values[i][2] = String.valueOf(map.getGltSeq()) == "null"?"":String.valueOf(map.getGltSeq());
			values[i][3] = String.valueOf(map.getTxnDesc()) == "null"?"":String.valueOf(map.getTxnDesc());
			values[i][4] = String.valueOf(map.getPostDesc()) == "null"?"":String.valueOf(map.getPostDesc());
			values[i][5] = String.valueOf(map.getTxnAmount()) == "null"?"":String.valueOf(map.getTxnAmount());
			values[i][6] = String.valueOf(map.getPostAmount()) == "null"?"":String.valueOf(map.getPostAmount());
			values[i][7] = String.valueOf(map.getIouNo()) == "null"?"":String.valueOf(map.getIouNo());
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeightInPoints(20);
		
		HSSFRow row = sheet.createRow(0);
		
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		
		HSSFCell cell = null;
		for(int i =0 ; i < title.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		
		for(int i =0;i<values.length;i++){
			row = sheet.createRow(i+1);
			for(int j = 0;j<values[i].length;j++){
				row.createCell(j).setCellValue(values[i][j]);
			}
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		/*sheet.autoSizeColumn(9);
		sheet.autoSizeColumn(10);
		sheet.autoSizeColumn(11);*/
		
		try {
			this.excelExport(response,fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void subjectExportExcel(List<Subject> subList,HttpServletResponse response){
		
		String sheetName = "会计科目配置";
		String []title = new String[]{"科目号","科目名称","余额方向","科目类型","父类科目号","科目是否启用","是否允许透支","科目层级","是否末级"};
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdf.format(new Date());
		String fileName = dateStr +"subject.xls";
		
        String[] [] values = new String[subList.size()] [];
		
		for(int i = 0;i<subList.size();i++){
			values[i] = new String[title.length];
			Subject subject = subList.get(i);
			values[i][0] = String.valueOf(subject.subjectCd);
			values[i][1] = String.valueOf(subject.name);
			values[i][2] = String.valueOf(subject.balDbCrFlag);
			values[i][3] = String.valueOf(subject.type) == "null"?"":String.valueOf(subject.type);
			values[i][4] = String.valueOf(subject.parentSubjectCd) == "null"?"":String.valueOf(subject.parentSubjectCd);
			values[i][5] = subject.enable == true?"是":"否";
			values[i][6] = subject.isOverdraft== true?"是":"否";
			values[i][7] = String.valueOf(subject.subjectHierarchy) == "null"?"":String.valueOf(subject.subjectHierarchy);
			values[i][8] = subject.isLast== true?"是":"否";
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeightInPoints(20);
		
		HSSFRow row = sheet.createRow(0);
		
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		
		HSSFCell cell = null;
		for(int i =0 ; i < title.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		
		for(int i =0;i<values.length;i++){
			row = sheet.createRow(i+1);
			for(int j = 0;j<values[i].length;j++){
				row.createCell(j).setCellValue(values[i][j]);
			}
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		
		try {
			this.excelExport(response,fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
public void parameterExportExcel(List<Map<String, Object>> subList,HttpServletResponse response){

		
		String sheetName = "参数历史维护";
		
		String []title = new String[]{"序列号","生效日期","参数主键","参数类别","新参数值","原参数值","修改日志","修改操作员","修改时间"};
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdf.format(new Date());
		String fileName = dateStr +"parameter.xls";
		
        String[] [] values = new String[subList.size()] [];
		
		for(int i = 0;i<subList.size();i++){
			values[i] = new String[title.length];
			Map<String, Object> map = subList.get(i);
			values[i][0] = String.valueOf(map.get("paramAuditSeq")) == "null"?"":String.valueOf(map.get("paramAuditSeq"));
			values[i][1] = String.valueOf(map.get("effectiveDate")) == "null"?"":String.valueOf(map.get("effectiveDate"));
			values[i][2] = String.valueOf(map.get("paramKey")) == "null"?"":String.valueOf(map.get("paramKey"));
			values[i][3] = String.valueOf(map.get("paramClass")) == "null"?"":String.valueOf(map.get("paramClass"));
			values[i][4] = String.valueOf(map.get("newObject")) == "null"?"":String.valueOf(map.get("newObject"));
			values[i][5] = String.valueOf(map.get("oldObject")) == "null"?"":String.valueOf(map.get("oldObject"));
			values[i][6] = String.valueOf(map.get("updateLog")) == "null"?"":String.valueOf(map.get("updateLog"));
			values[i][7] = String.valueOf(map.get("name")) == "null"?"":String.valueOf(map.get("name"));
			values[i][8] = String.valueOf(map.get("mtnTimestamp")) == "null"?"":String.valueOf(map.get("mtnTimestamp"));
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeightInPoints(20);
		
		HSSFRow row = sheet.createRow(0);
		
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		
		HSSFCell cell = null;
		for(int i =0 ; i < title.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		
		for(int i =0;i<values.length;i++){
			row = sheet.createRow(i+1);
			for(int j = 0;j<values[i].length;j++){
				row.createCell(j).setCellValue(values[i][j]);
			}
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		
		try {
			this.excelExport(response,fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	public void excelExport(HttpServletResponse response,String fileName){
		try {
			String encoding = System.getProperty("file.encoding");
			response.setContentType("application/vnd.ms-excel;charset="+encoding);
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName,encoding));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
