package net.engining.sccc.init.param.sccc;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;

import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.init.ParameterInitializer;
import net.engining.sccc.biz.service.enums.InvolceType;
import net.engining.sccc.biz.service.enums.TaxpayerAttribute;
import net.engining.sccc.biz.service.params.IntTaxRate;

@Service
public class IntTaxTateInit implements ParameterInitializer{

	@Autowired
	private ParameterFacility facility;
	
	@Value("classpath:/data/taxtate.csv")
	private Resource data;

	@Override
	public void init() throws Exception {
		if(facility.getParameterMap(IntTaxRate.class).size()>0){
			facility.removeParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
		}
		
		List<String> lines = IOUtils.readLines(data.getInputStream(),Charsets.UTF_8);
		
		for(String line : lines){
			IntTaxRate itr = new IntTaxRate();
			
			String fields[] = StringUtils.splitPreserveAllTokens(line,",");
			int i = 0;
			itr.taxCd = fields[i++];
			itr.taxableItem = fields[i++];
			itr.withHoldingInt = Boolean.valueOf(fields[i++]);
			itr.isInvoice = Boolean.valueOf(fields[i++]);
			itr.involceType = InvolceType.valueOf(fields[i++]);
			itr.taxpayerAttribute = TaxpayerAttribute.valueOf(fields[i++]);
			itr.taxRt = new BigDecimal(fields[i++]);
			itr.subjectCd = fields[i++];
			
			facility.addParameter(ParameterFacility.UNIQUE_PARAM_KEY,itr);
			
		}
		
	}
}
