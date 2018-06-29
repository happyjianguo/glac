package net.engining.sccc.mgm.service.reportforms;

import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class CommonReportService {

	public void buildExcelDocument(List<String> dates, HSSFWorkbook workbook,OutputStream out){
		
		System.out.println("generateExecl start...");
		
		HSSFSheet sheet = workbook.createSheet("ExcelView");
		HSSFRow row;
		sheet.setDefaultColumnWidth(20);
		for(int i = 0; i < 10; i++){
			row = sheet.createRow(i);
			for (int j = 0; j < dates.size(); j++) {
				
				row.createCell(j).setCellValue(dates.get(j));
			}
		}
		 try{
             workbook.write(out);
             out.close();
         }catch(Exception e){
             e.printStackTrace();
         }
		System.out.println("generateExecl end...");
	}

}
