package net.engining.sccc.mgm.service;

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
@Service
public class VodtlAssSumExcelService {
	
	public void excelFile(List<Map<String, Object>> list,HttpServletResponse response){
		
		String sheetName = "辅助核算余额";
		
		String []title = new String[]{"科目编号","辅助核算类型","辅助核算项数据","记账日期","借贷方向","上期借方余额","上期借方余额","本期借方发生额","本期贷方发生额",
				"累计借方发生额","累计贷方发生额","借方余额","贷方余额"};
		
		String fileName = System.currentTimeMillis() + "当日辅助余额.xls";
		
//		createExcel(sheetName,title,fileName,list);
		
        String[] [] values = new String[list.size()] [];
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i = 0; i<list.size();i++){
			values[i] = new String[title.length];
			Map<String, Object> map = list.get(i);
			values[i][0] = String.valueOf(map.get("subjectCd")) == "null"?"":String.valueOf(map.get("subjectCd"));
			values[i][1] = String.valueOf(map.get("assistType")) == "null"?"":String.valueOf(map.get("assistType"));
			values[i][2] = String.valueOf(map.get("assistAccountData")) == "null"?"":String.valueOf(map.get("assistAccountData"));
			values[i][3] = String.valueOf(map.get("volDt")) == "null"?"":String.valueOf(map.get("volDt"));
			values[i][4] = String.valueOf(map.get("lastDbBal")) == "null"?"":String.valueOf(map.get("lastDbBal"));
			values[i][5] = String.valueOf(map.get("lastCrBal")) == "null"?"":String.valueOf(map.get("lastCrBal"));
			values[i][6] = String.valueOf(map.get("mtdCrAmt")) == "null"?"":String.valueOf(map.get("mtdCrAmt"));
			values[i][7] = String.valueOf(map.get("mtdDbAmt")) == "null"?"":String.valueOf(map.get("mtdDbAmt"));
			values[i][8] = String.valueOf(map.get("dbBal")) == "null"?"":String.valueOf(map.get("dbBal"));
			values[i][9] = String.valueOf(map.get("crBal")) == "null"?"":String.valueOf(map.get("crBal"));
			values[i][10] = String.valueOf(map.get("dbAmt")) == "null"?"":String.valueOf(map.get("dbAmt"));
			values[i][11] = String.valueOf(map.get("crAmt")) == "null"?"":String.valueOf(map.get("crAmt"));
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
			for(int j = 0;i<values[i].length;j++){
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
		sheet.autoSizeColumn(9);
		sheet.autoSizeColumn(10);
		sheet.autoSizeColumn(11);
		
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
