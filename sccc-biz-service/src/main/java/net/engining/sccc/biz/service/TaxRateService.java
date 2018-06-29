package net.engining.sccc.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.biz.bean.IntTaxRateUpdate;
import net.engining.sccc.biz.service.params.IntTaxRate;

@Service
public class TaxRateService {
	@Autowired
	private ParameterFacility facility;
	
	public IntTaxRate taxRateRecordInquiryDetails(){
		
		return facility.getParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
	}
	
	public void taxRateRecordUpdate(IntTaxRateUpdate itr){
		
//		facility.removeParameter(IntTaxRate.class,ParameterFacility.UNIQUE_PARAM_KEY);
		
		IntTaxRate tax = new IntTaxRate();
//		IntTaxRate tax = facility.getParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
		tax.taxCd = itr.getTaxCd();
		tax.taxableItem = itr.getTaxableItem();
		tax.withHoldingInt = itr.getWithHoldingInt();
		tax.isInvoice = itr.getIsInvoice();
		tax.involceType = itr.getInvolceType();
		tax.taxpayerAttribute = itr.getTaxpayerAttribute();
		tax.taxRt = itr.getTaxRt();
		tax.subjectCd = itr.getSubjectCd();
		
		facility.updateParameter(ParameterFacility.UNIQUE_PARAM_KEY,tax);
		
	}
	
}
