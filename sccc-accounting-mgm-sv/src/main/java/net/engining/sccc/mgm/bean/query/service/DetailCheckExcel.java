package net.engining.sccc.mgm.bean.query.service;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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

import net.engining.sccc.biz.bean.DetailCheck;

@Service
public class DetailCheckExcel {
public void excelFile(List<DetailCheck> list,HttpServletResponse response){
		
		String sheetName = "明细核对";
		
		String []title = new String[]{"交易日期","记账日期","交易流水号","记账流水号","交易类型","交易金额","记账金额","差错原因"};
		
		String fileName = "accountRecord.xls";
		
//		createExcel(sheetName,title,fileName,list);
		
        String[] [] values = new String[list.size()] [];
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i = 0;i<list.size();i++){
			values[i] = new String[title.length];
			DetailCheck map = list.get(i);
			values[i][0] = String.valueOf(map.getTxnDate()) == "null"?"":String.valueOf(map.getTxnDate());
			values[i][1] = String.valueOf(map.getPostDate()) == "null"?"":String.valueOf(map.getPostDate());
			values[i][2] = String.valueOf(map.getTxnSeq()) == "null"?"":String.valueOf(map.getTxnSeq());
			values[i][3] = String.valueOf(map.getPostSeq()) == "null"?"":String.valueOf(map.getPostSeq());
			values[i][4] = String.valueOf(map.getTxnType()) == "null"?"":String.valueOf(map.getTxnType());
			values[i][5] = String.valueOf(map.getTxnAmount()) == "null"?"":String.valueOf(map.getTxnAmount());
			values[i][6] = String.valueOf(map.getPostAmount()) == "null"?"":String.valueOf(map.getPostAmount());
			values[i][7] = String.valueOf(map.getErrorReason()) == "null"?"":String.valueOf(map.getErrorReason());
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet = wb.createSheet(sheetName);
//		wb.setSheetName(0, sheetName);
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
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
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
		/*sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(9);
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
