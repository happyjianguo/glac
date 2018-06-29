package net.engining.sccc.mgm.controller.reportforms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.mgm.service.reportforms.CommonReportService;

/**
 * 
 * @author liqingfeng
 *
 */
@Api(value="CommonReportController",description="报表部分公用Controller")
@RequestMapping("/CommonReport")
@RestController
public class CommonReportController {

	@Autowired
	private CommonReportService commonReportService;

	
	/**
	 * 获取科目下拉框
	 * 
	 */
	@RequestMapping(value = "/buildExcelDocument", method = RequestMethod.POST)
	@ApiOperation(value = "下载excel文件", notes = "")
	@PreAuthorize("hasAuthority('buildExcelDocument')")
	public @ResponseBody WebCommonResponse<Void> buildExcelDocument(HSSFWorkbook workbook,
			HttpServletResponse response) {
		try {
			List<String> dates = new ArrayList<>();
			for (int i = 0; i < 20; i++) {
				dates.add(new Date().toString());
			}
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String("报表Excel".getBytes("utf-8"), "ISO8859-1"));
			commonReportService.buildExcelDocument(dates, workbook, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
	
	
	
}