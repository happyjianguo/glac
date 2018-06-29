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
import net.engining.sccc.biz.bean.batchBean.SubAcctData;
import net.engining.sccc.biz.bean.batchBean.UnionData;
import net.engining.sccc.biz.enums.AccountTradingDef;
import net.engining.sccc.biz.enums.SubAcctTypeDef;
import net.engining.sccc.biz.service.SplitVolService;
import net.engining.sccc.biz.service.params.IntTaxRate;
@Service
@StepScope
public class Sccc1101P07RepaymentHandle implements ItemProcessor<DataTransBean, DataTransBean> {

	@PersistenceContext
	private EntityManager em;

	@Value("#{new org.joda.time.LocalDate(jobParameters['bizDate'].time)}")
	private LocalDate bizDate;

	@Autowired
	private SplitVolService splitVolService;
	
	@Autowired
	private ParameterFacility parameterFacility;

	// 还款
	@Override
	public DataTransBean process(DataTransBean bean) throws Exception {
		
        EveryDayAccountingBean item = bean.getEveryDayAccountingBean();
        
        if(AccountTradingDef.H.equals(item.getRequestData().getAccountTrading()) && CheckAccountStatusDef.ADD.equals(bean.getCheckAccountStatusDef())){

		String postCode = bean.getPostCode();

		Map<SubjectAmtType, BigDecimal> map = new HashMap<SubjectAmtType, BigDecimal>();

		// 交易前应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal beforeLbal = BigDecimal.ZERO;
		// 交易前联合贷参与方金额
		BigDecimal beforeULbal = BigDecimal.ZERO;

		// 交易后应还本金/如果是联合贷，此处金额为我方金额
		BigDecimal afterLbal = BigDecimal.ZERO;
		// 交易后联合贷参与方金额
		BigDecimal afterULbal = BigDecimal.ZERO;

		// 交易前应还利息
		BigDecimal beforeInte = BigDecimal.ZERO;
		// 交易后应还利息
		BigDecimal afterInte = BigDecimal.ZERO;

		// 交易前应还罚息
		BigDecimal beforePnit = BigDecimal.ZERO;
		// 交易后应还罚息
		BigDecimal afterPnit = BigDecimal.ZERO;

		// 本金产生计提利息(此处本金可为当期应还本金LBAL和剩余应还本金Loan)
		BigDecimal beforeTi = BigDecimal.ZERO;
		BigDecimal afterTi = BigDecimal.ZERO;

		// 本金产生计提罚息(此处本金为当期应还本金)
		BigDecimal beforeFine = BigDecimal.ZERO;
		BigDecimal afterFine = BigDecimal.ZERO;
		
		//销项税
		BigDecimal otxaAmt = null;
		
		
		//新加对于正常还款时
		BigDecimal beforeLoan = BigDecimal.ZERO;
		BigDecimal afterLoan = BigDecimal.ZERO;
		BigDecimal beforeULoan = BigDecimal.ZERO;
		BigDecimal afterULoan = BigDecimal.ZERO;

		for (SubAcctData beforeData : item.getRequestData().getBeforeSubAcctData()) {

			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				beforeTi = beforeData.getIntAccrual().add(beforeTi);
				beforeFine = beforeData.getIntPenaltyAccrual().add(beforeFine);
				if (item.getRequestData().getIsUnion() == false) {
					beforeLbal = beforeData.getCurrBal().add(beforeLbal);
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData())
						if (SubAcctTypeDef.LBAL.equals(unionData.getSubAcctType())) {
							beforeLbal = unionData.getOwnAmt().add(beforeLbal);
							beforeULbal = unionData.getOtherAmt().add(beforeULbal);
						}
				}
			}
			
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeTi = beforeData.getIntAccrual().add(beforeFine);
				if (item.getRequestData().getIsUnion() == false) {
					beforeLoan = beforeData.getCurrBal();
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData()){
						if (SubAcctTypeDef.LOAN.toString().equals(unionData.getSubAcctType())) {
							beforeLoan = unionData.getOwnAmt().add(beforeLoan);
							beforeULoan = unionData.getOtherAmt().add(beforeULoan);
						}
					}
						
				}
			}
			
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				beforeInte = beforeData.getCurrBal().add(beforeInte);
			}
			if (beforeData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				beforePnit = beforeData.getCurrBal().add(beforePnit);
			}
		}

		for (SubAcctData afterData : item.getRequestData().getSubAcctData()) {
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				afterFine = afterData.getIntPenaltyAccrual().add(afterFine);
				if (item.getRequestData().getIsUnion() == false) {
					afterLbal = afterData.getCurrBal().add(afterLbal);
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData())
						if (SubAcctTypeDef.LBAL.equals(unionData.getSubAcctType())) {
							afterLbal = unionData.getOwnAmt().add(afterLbal);
							afterULbal = unionData.getOtherAmt().add(afterULbal);
						}
				}
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterTi = afterData.getIntAccrual().add(afterTi);
				if (item.getRequestData().getIsUnion() == false) {
					afterLoan = afterData.getCurrBal();
				}
				if (item.getRequestData().getIsUnion() == true) {
					for (UnionData unionData : item.getRequestData().getUnionData()){
						if (SubAcctTypeDef.LOAN.toString().equals(unionData.getSubAcctType())) {
							afterLoan = BigDecimal.ZERO;
							afterULoan = BigDecimal.ZERO;
						}
					}
						
				}
			}
			
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
				afterInte = afterData.getCurrBal().add(afterInte);
			}
			if (afterData.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
				afterPnit = afterData.getCurrBal().add(afterPnit);
			}
		}
		
		IntTaxRate intTaxRate = parameterFacility.loadParameter(IntTaxRate.class, ParameterFacility.UNIQUE_PARAM_KEY);
		otxaAmt = ((beforeInte.subtract(afterInte)).add(beforePnit.subtract(afterPnit))).multiply(intTaxRate.taxRt);
		//贷款应还本金
		map.put(SubjectAmtType.LBAL,beforeLoan.subtract(afterLoan).add(beforeLbal.subtract(afterLbal)));
		map.put(SubjectAmtType.ULBAL, beforeULoan.subtract(afterULoan).add(beforeULbal.subtract(afterULbal)));
	
//		map.put(SubjectAmtType.LBAL, beforeLbal.subtract(afterLbal));
//		map.put(SubjectAmtType.ULBAL, beforeULbal.subtract(afterULbal));

		map.put(SubjectAmtType.RECEIVABLE_INTEREST, beforeInte.subtract(afterInte));
		map.put(SubjectAmtType.RECEIVABLE_PNIT, beforePnit.subtract(afterPnit));
		map.put(SubjectAmtType.ACCRUED_INTEREST, beforeTi.subtract(afterTi));
		map.put(SubjectAmtType.ACCRUED_PNIT, beforeFine.subtract(afterFine));
		map.put(SubjectAmtType.OTAX, otxaAmt);
		
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
