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

import net.engining.sccc.biz.bean.TotalProrateCheckRes;

@Service
public class TotalScoreCheckExcel {
public void excelFile(List<TotalProrateCheckRes> list,HttpServletResponse response){
		
		String sheetName = "总分核对";
		
		String []title = new String[]{"业务日期","科目编号","科目名称","总账期初金额","借贷方向","总账借方金额","总帐贷方金额","明细账借方金额","明细账贷方金额"
				,"总账期末余额","借贷方向","实际期末余额","差额"};
		
		String fileName = "accountRecord.xls";
		
//		createExcel(sheetName,title,fileName,list);
		
        String[] [] values = new String[list.size()] [];
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i = 0;i<list.size();i++){
			values[i] = new String[title.length];
			TotalProrateCheckRes map = list.get(i);
			values[i][0] = String.valueOf(map.getBizDate()) == "null"?"":String.valueOf(map.getBizDate());
			values[i][1] = String.valueOf(map.getSubjectNo()) == "null"?"":String.valueOf(map.getSubjectNo());
			values[i][2] = String.valueOf(map.getSubjectName()) == "null"?"":String.valueOf(map.getSubjectName());
			values[i][3] = String.valueOf(map.getBeginAmount()) == "null"?"":String.valueOf(map.getBeginAmount());
			values[i][4] = String.valueOf(map.getBeginDirection()) == "null"?"":String.valueOf(map.getBeginDirection());
			values[i][5] = String.valueOf(map.getDbAmt()) == "null"?"":String.valueOf(map.getDbAmt());
			values[i][6] = String.valueOf(map.getCrAmt()) == "null"?"":String.valueOf(map.getCrAmt());
			values[i][7] = String.valueOf(map.getDetailDbAmt()) == "null"?"":String.valueOf(map.getDetailDbAmt());
			values[i][8] = String.valueOf(map.getDetailcrAmt()) == "null"?"":String.valueOf(map.getDetailcrAmt());
			values[i][9] = String.valueOf(map.getEndAmount()) == "null"?"":String.valueOf(map.getEndAmount());
			values[i][10] = String.valueOf(map.getEndDirection()) == "null"?"":String.valueOf(map.getEndDirection());
			values[i][11] = String.valueOf(map.getFactEndAmount()) == "null"?"":String.valueOf(map.getFactEndAmount());
			values[i][12] = String.valueOf(map.getDifference()) == "null"?"":String.valueOf(map.getDifference());
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
		sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(9);
		sheet.autoSizeColumn(10);
		sheet.autoSizeColumn(11);
		sheet.autoSizeColumn(12);
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
