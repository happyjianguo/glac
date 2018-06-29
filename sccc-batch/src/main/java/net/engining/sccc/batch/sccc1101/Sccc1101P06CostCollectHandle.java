package net.engining.sccc.batch.sccc1101;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.pcx.cc.infrastructure.shared.enums.CheckAccountStatusDef;
import net.engining.pcx.cc.param.model.enums.SubjectAmtType;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.biz.bean.PostCodeSeq;
import net.engining.sccc.biz.bean.batchBean.DataTransBean;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.bean.batchBean.RequestData;
import net.engining.sccc.biz.enums.AccountTradingDef;
import net.engining.sccc.biz.service.SplitVolService;
import net.engining.sccc.biz.service.params.IntTaxRate;

/**
 * 
 *
 */
@Service
@StepScope
public class Sccc1101P06CostCollectHandle implements ItemProcessor<DataTransBean, DataTransBean>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Value("#{new org.joda.time.LocalDate(jobParameters['bizDate'].time)}")
	private LocalDate bizDate;
	
	@Autowired
	private ParameterFacility parameterFacility;
	
	@Autowired
	private SplitVolService splitVolService;

	//费用收取记账
	@Override
	public DataTransBean process(DataTransBean bean) throws Exception {
		// TODO Auto-generated method stub
		EveryDayAccountingBean item = bean.getEveryDayAccountingBean();
		if(AccountTradingDef.F.equals(item.getRequestData().getAccountTrading()) && CheckAccountStatusDef.ADD.equals(bean.getCheckAccountStatusDef())){
			
		     String postCode = bean.getPostCode();
		     
		     Map<SubjectAmtType, BigDecimal> map = new HashMap<SubjectAmtType, BigDecimal>();
		     IntTaxRate intTaxRate = parameterFacility.loadParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
		     BigDecimal dbAmt = item.getRequestData().getSubAcctData().get(0).getCurrBal();
		   
		     
		     BigDecimal otaxAmt = dbAmt.multiply(intTaxRate.taxRt);
		     map.put(SubjectAmtType.OTAX, otaxAmt);
		     map.put(SubjectAmtType.SFEE,dbAmt.subtract(otaxAmt));
		     RequestData data = new RequestData();
		     BeanUtils.copyProperties(item.getRequestData(), data);
		     AgeGroupCd ageGroup = null;
		     
		   //套型入表
		     PostCodeSeq postCodeSeq =new  PostCodeSeq();
		     postCodeSeq.setPostCode(postCode);
		     postCodeSeq.setTxnSeq(bean.getTxnId());
		     splitVolService.split(postCodeSeq, map, item.getClearDate(), bizDate, data,ageGroup);
		     
		}
		return bean;
	}

}


