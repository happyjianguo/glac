package net.engining.sccc.mgm.controller.param;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.IntTaxRateUpdate;
import net.engining.sccc.biz.service.TaxRateService;
import net.engining.sccc.biz.service.params.IntTaxRate;

@Api(value="价税分离查询")
@RequestMapping("/taxrate")
@RestController
public class TaxRateController {
	@Autowired TaxRateService taxRateService;
	
	@PreAuthorize("hasAuthority('taxRateRecordInquiryDetails')")
	@ApiOperation(value="价税分离查询详情", notes="")
	@RequestMapping(value="/taxRateRecordInquiryDetails", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<IntTaxRate> taxRateRecordInquiryDetails() {
		
		IntTaxRate intTaxRate = taxRateService.taxRateRecordInquiryDetails();
		return new WebCommonResponseBuilder<IntTaxRate>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(intTaxRate);
	}
	
	@PreAuthorize("hasAuthority('taxRateRecordUpdate')")
	@ApiOperation(value="价税分离更新", notes="")
	@RequestMapping(value="/taxRateRecordUpdate", method=RequestMethod.POST)
	public @ResponseBody WebCommonResponse<Void> taxRateRecordUpdate(@RequestBody IntTaxRateUpdate intTaxRateUpdate) {
		
		taxRateService.taxRateRecordUpdate(intTaxRateUpdate);
		
		return new WebCommonResponseBuilder<Void>().build().setStatusCode(WebCommonResponse.CODE_OK);
	}
}
