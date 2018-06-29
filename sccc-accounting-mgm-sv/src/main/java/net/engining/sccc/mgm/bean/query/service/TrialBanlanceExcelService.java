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

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.param.model.enums.SubjectType;
import net.engining.sccc.biz.bean.TrialBalanceRes;

@Service
public class TrialBanlanceExcelService {
public void excelFile(List<Map<String, TrialBalanceRes>> list,HttpServletResponse response){
		
		String sheetName = "试算平衡";
		
		String []title = new String[]{"科目属性","借方金额","贷方金额","借贷方向","余额","科目属性","借方金额","贷方金额","借贷方向","余额"};
		
		String fileName = "accountRecord.xls";
		
//		createExcel(sheetName,title,fileName,list);
		
        String[] [] values = new String[5] [];
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i = 0;i<list.size();i++){
			values[i] = new String[title.length];
			values[i+1] = new String[title.length];
			values[i+2] = new String[title.length];
			values[i+3] = new String[title.length];
			values[i+4] = new String[title.length];
			Map<String, TrialBalanceRes> map = list.get(i);
			values[i][0] = String.valueOf(map.get(SubjectType.A.toString())) == "null"?String.valueOf("资产"):String.valueOf("资产");
			values[i][1] = String.valueOf(map.get(SubjectType.A.toString())) == "null"?"":String.valueOf(map.get(SubjectType.A.toString()).getDbAmtSum());
			values[i][2] = String.valueOf(map.get(SubjectType.A.toString())) == "null"?"":String.valueOf(map.get(SubjectType.A.toString()).getCrAmtSum());
			values[i][3] = String.valueOf(map.get(SubjectType.A.toString())) == "null"?"":map.get(SubjectType.A.toString()).getDirection()=="null"?"":map.get(SubjectType.A.toString()).getDirection()==TxnDirection.C.toString()?"贷":"借";
			values[i][4] = String.valueOf(map.get(SubjectType.A.toString())) == "null"?"":String.valueOf(map.get(SubjectType.A.toString()).getBalance());
			values[i][5] = String.valueOf(map.get(SubjectType.B.toString())) == "null"?String.valueOf("负债"):String.valueOf("负债");
			values[i][6] = String.valueOf(map.get(SubjectType.B.toString())) == "null"?"":String.valueOf(map.get(SubjectType.B.toString()).getDbAmtSum());
			values[i][7] = String.valueOf(map.get(SubjectType.B.toString())) == "null"?"":String.valueOf(map.get(SubjectType.B.toString()).getCrAmtSum());
			values[i][8] = String.valueOf(map.get(SubjectType.B.toString())) == "null"?"":map.get(SubjectType.B.toString()).getDirection()=="null"?"":map.get(SubjectType.B.toString()).getDirection()==TxnDirection.C.toString()?"贷":"借";
			values[i][9] = String.valueOf(map.get(SubjectType.B.toString())) == "null"?"":String.valueOf(map.get(SubjectType.B.toString()).getBalance());
			values[i+1][0] = String.valueOf(map.get(SubjectType.D.toString())) == "null"?String.valueOf("共同"):String.valueOf("共同");
			values[i+1][1] = String.valueOf(map.get(SubjectType.D.toString())) == "null"?"":String.valueOf(map.get(SubjectType.D.toString()).getDbAmtSum());
			values[i+1][2] = String.valueOf(map.get(SubjectType.D.toString())) == "null"?"":String.valueOf(map.get(SubjectType.D.toString()).getCrAmtSum());
			values[i+1][3] = String.valueOf(map.get(SubjectType.D.toString())) == "null"?"":map.get(SubjectType.D.toString()).getDirection()=="null"?"":map.get(SubjectType.D.toString()).getDirection()==TxnDirection.C.toString()?"贷":"借";
			values[i+1][4] = String.valueOf(map.get(SubjectType.D.toString())) == "null"?"":String.valueOf(map.get(SubjectType.D.toString()).getBalance());
			values[i+1][5] = String.valueOf(map.get(SubjectType.E.toString())) == "null"?String.valueOf("权益"):String.valueOf("权益");
			values[i+1][6] = String.valueOf(map.get(SubjectType.E.toString())) == "null"?"":String.valueOf(map.get(SubjectType.E.toString()).getDbAmtSum());
			values[i+1][7] = String.valueOf(map.get(SubjectType.E.toString())) == "null"?"":String.valueOf(map.get(SubjectType.E.toString()).getCrAmtSum());
			values[i+1][8] = String.valueOf(map.get(SubjectType.E.toString())) == "null"?"":map.get(SubjectType.E.toString()).getDirection()=="null"?"":map.get(SubjectType.E.toString()).getDirection()==TxnDirection.C.toString()?"贷":"借";
			values[i+1][9] = String.valueOf(map.get(SubjectType.E.toString())) == "null"?"":String.valueOf(map.get(SubjectType.E.toString()).getBalance());
			values[i+2][0] = String.valueOf(map.get(SubjectType.C.toString())) == "null"?String.valueOf("损益"):String.valueOf("损益");
			values[i+2][1] = String.valueOf(map.get(SubjectType.C.toString())) == "null"?"":String.valueOf(map.get(SubjectType.C.toString()).getDbAmtSum());
			values[i+2][2] = String.valueOf(map.get(SubjectType.C.toString())) == "null"?"":String.valueOf(map.get(SubjectType.C.toString()).getCrAmtSum());
			values[i+2][3] = String.valueOf(map.get(SubjectType.C.toString())) == "null"?"":map.get(SubjectType.C.toString()).getDirection()=="null"?"":map.get(SubjectType.C.toString()).getDirection()==TxnDirection.C.toString()?"贷":"借";
			values[i+2][4] = String.valueOf(map.get(SubjectType.C.toString())) == "null"?"":String.valueOf(map.get(SubjectType.C.toString()).getBalance());
			values[i+3][0] = String.valueOf(map.get("total1")) == "null"?String.valueOf("合计"):String.valueOf("合计");
			values[i+3][1] = String.valueOf(map.get("total1")) == "null"?"":String.valueOf(map.get("total1").getDbAmtSum());
			values[i+3][2] = String.valueOf(map.get("total1")) == "null"?"":String.valueOf(map.get("total1").getCrAmtSum());
			values[i+3][3] = String.valueOf(map.get("total1")) == "null"?"":map.get("total1").getDirection()=="null"?"":map.get("total1").getDirection()==TxnDirection.C.toString()?"贷":"借";
			values[i+3][4] = String.valueOf(map.get("total1")) == "null"?"":String.valueOf(map.get("total1").getBalance());
			values[i+3][5] = String.valueOf(map.get("total2")) == "null"?String.valueOf("合计"):String.valueOf("合计");
			values[i+3][6] = String.valueOf(map.get("total2")) == "null"?"":String.valueOf(map.get("total2").getDbAmtSum());
			values[i+3][7] = String.valueOf(map.get("total2")) == "null"?"":String.valueOf(map.get("total2").getCrAmtSum());
			values[i+3][8] = String.valueOf(map.get("total2")) == "null"?"":map.get("total2").getDirection()=="null"?"":map.get("total2").getDirection()==TxnDirection.C.toString()?"贷":"借";
			values[i+3][9] = String.valueOf(map.get("total2")) == "null"?"":String.valueOf(map.get("total2").getBalance());
			
			
			values[i+4][0] = String.valueOf(map.get(SubjectType.F.toString())) == "null"?String.valueOf("表外"):String.valueOf("表外");
			values[i+4][1] = String.valueOf(map.get(SubjectType.F.toString())) == "null"?"":String.valueOf(map.get(SubjectType.F.toString()).getDbAmtSum());
			values[i+4][2] = String.valueOf(map.get(SubjectType.F.toString())) == "null"?"":String.valueOf(map.get(SubjectType.F.toString()).getCrAmtSum());
			values[i+4][3] = String.valueOf(map.get(SubjectType.F.toString())) == "null"?"":map.get(SubjectType.F.toString()).getDirection()==null?"":map.get(SubjectType.F.toString()).getDirection()==TxnDirection.C.toString()?"贷":"借";
			values[i+4][4] = String.valueOf(map.get(SubjectType.F.toString())) == "null"?"":String.valueOf(map.get(SubjectType.F.toString()).getBalance());
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
		
		for(int i =0;i<5;i++){//控制行数
			row = sheet.createRow(i+1);
			for(int j = 0;j<values[i].length;j++){//控制行内个数
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
