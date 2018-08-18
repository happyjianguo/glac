package net.engining.sccc.batch.sccc0902;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.pcx.cc.infrastructure.shared.enums.PostTxnTypeDef;
import net.engining.pcx.cc.param.model.enums.SubjectAmtType;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.biz.bean.PostCodeSeq;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.bean.batchBean.RequestData;
import net.engining.sccc.biz.bean.batchBean.SubAcctData;
import net.engining.sccc.biz.enums.AccountTradingDef;
import net.engining.sccc.biz.service.ComputingPostCodeService;
import net.engining.sccc.biz.service.ComputingTxnCdService;
import net.engining.sccc.biz.service.SplitVolService;
import net.engining.sccc.biz.service.params.IntTaxRate;
import net.engining.sccc.entity.model.BtEodTxnImport;

/**
 * 罚息计提文件入账
 * 
 * @author xiachuanhu
 *
 */
@Service
@StepScope
public class Sccc0902P12PintAccrualPostLedger implements ItemProcessor<BtEodTxnImport, Object> {
	private static final Logger log = LoggerFactory.getLogger(Sccc0902P12PintAccrualPostLedger.class);

	@PersistenceContext
	private EntityManager em;
	@Value("#{new org.joda.time.LocalDate(jobParameters['bizDate'].time)}")
	private LocalDate bizDate;
	@Autowired
	private ParameterFacility parameterFacility;
	@Autowired
	private ComputingPostCodeService getPostCodeService;
	@Autowired
	private SplitVolService splitVolService;
	
	@Autowired
	private ComputingTxnCdService batchGetPostCodeService;

	@Override
	public Object process(BtEodTxnImport item) throws Exception {
		// 取数据
		EveryDayAccountingBean headerData = JSON.parseObject(item.getJsonInputData(), new TypeReference<EveryDayAccountingBean>() {
		});
		RequestData data = headerData.getRequestData();
		// 取交易前List
		List<SubAcctData> listBefore = data.getBeforeSubAcctData();
		// 取交易后List
		List<SubAcctData> listAfter = data.getSubAcctData();
		Date clearDate = headerData.getClearDate();
//		Deadline deadLine = null;
//		if (data.getTotalPeriod() <= 12) {
//			deadLine = Deadline.S;// 短期
//		} else {
//			deadLine = Deadline.M;// 中长期
//		}
		// 获取外部交易码
//		SysTxnCd txnCd = batchGetPostCodeService.getTxnCd(AccountTradingDef.PINTACC, data.getTotalPeriod(), null,
//				data.getAgeCd(), null);
		
//		SysTxnCd txnCd = null;
//		if (deadLine.equals(Deadline.S) && data.getAgeCd() <= 3) {
//			txnCd = SysTxnCd.T013;
//		}
//		else if (deadLine.equals(Deadline.S) && data.getAgeCd() > 3) {
//			txnCd = SysTxnCd.T014;
//		}
//		else if (deadLine.equals(Deadline.M) && data.getAgeCd() <= 3) {
//			txnCd = SysTxnCd.T015;
//		}else {
//			txnCd = SysTxnCd.T016;
//		}
		// 获取入账交易码,并保存当日入账交易表，当日总账交易流水表
//		PostCodeSeq postCodeSeq = getPostCodeService.getPostCd(data, txnCd, headerData, clearDate , PostTxnTypeDef.PINTACC,data.getTotalAmt());
		
		PostCodeSeq postCodeSeq = getPostCodeService.getPostCode(data, headerData, clearDate,
				PostTxnTypeDef.PINTACC, data.getTotalAmt(), AccountTradingDef.PINTACC, 
				null, data.getAgeCd(), null);
		
		BigDecimal postAmt = BigDecimal.ZERO;// 记账金额
		BigDecimal postTax = BigDecimal.ZERO;// 销项税发生额
		for (int i = 0; i < listBefore.size(); i++) {
			BigDecimal accrual = listAfter.get(i).getIntPenaltyAccrual()
					.subtract(listBefore.get(i).getIntPenaltyAccrual());
			postAmt = postAmt.add(accrual);
		}
		
		// 取价税分离参数
		IntTaxRate intTaxRate = parameterFacility.getParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
		
		postTax = postAmt.multiply(intTaxRate.taxRt);
		// 记账金额成分入MAP
		Map<SubjectAmtType, BigDecimal> map = new HashMap<>();
		AgeGroupCd ageGroup = null;
		if (data.getAgeCd() <= 3) {
			map.put(SubjectAmtType.RECEIVABLE_PNIT, postAmt.subtract(postTax));
			map.put(SubjectAmtType.OTAX, postTax);
			ageGroup = AgeGroupCd.Under4M3;
		}
		if (data.getAgeCd() > 3) {
			map.put(SubjectAmtType.RECEIVABLE_PNIT, postAmt);
			ageGroup = AgeGroupCd.Above4M3;
		}
		//调用分录拆分方法
		splitVolService.split(postCodeSeq, map, clearDate, bizDate, data,ageGroup);
		return null;
	}

}
