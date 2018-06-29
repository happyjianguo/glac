package net.engining.sccc.mgm.bean.query.service;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import net.engining.sccc.biz.bean.HistoricalSubjectDetail;
@Service
public class HistoricalSubjectDetailExcelService {
	
	public void excelFile(List<HistoricalSubjectDetail> list,HttpServletResponse response){
		
		String sheetName = "当日科目明细表详情查询";
		
		String []title = new String[]{"记账日期","记账流水号","记账摘要","红蓝字","借贷方向","记账金额","辅助核算项内容","录入操作员","复核操作员"};
		
		String fileName = "loanDetail.xls";
		
//		createExcel(sheetName,title,fileName,list);
		
        String[] [] values = new String[list.size()] [];
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i = 0;i<list.size();i++){
			values[i] = new String[title.length];
			HistoricalSubjectDetail map = list.get(i);
			values[i][0] = String.valueOf(map.getPostDate()) == "null"?"":String.valueOf(map.getPostDate());
			values[i][1] = String.valueOf(map.getGltSeq()) == "null"?"":String.valueOf(map.getGltSeq());
			values[i][2] = String.valueOf(map.getPostDesc()) == "null"?"":String.valueOf(map.getPostDesc());
			values[i][3] = String.valueOf(map.getRedBlueInd()) == "null"?"":String.valueOf(map.getRedBlueInd());
			values[i][4] = String.valueOf(map.getTxnDirection()) == "null"?"":String.valueOf(map.getTxnDirection());
			values[i][5] = String.valueOf(map.getPostAmount()) == "null"?"":String.valueOf(map.getPostAmount());
			values[i][6] = String.valueOf(map.getAssistAccountData()) == "null"?"":String.valueOf(map.getAssistAccountData());
			values[i][7] = String.valueOf(map.getOpratorId()) == "null"?"":String.valueOf(map.getOpratorId());
			values[i][8] = String.valueOf(map.getCheckedId()) == "null"?"":String.valueOf(map.getCheckedId());
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
