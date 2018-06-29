package net.engining.sccc.batch.service;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.bean.batchBean.RequestData;
import net.engining.sccc.biz.bean.batchBean.SubAcctData;
import net.engining.sccc.biz.bean.batchBean.UnionData;
import net.engining.sccc.biz.enums.SubAcctTypeDef;

/**
 * 业务检查
 * 
 * @author xiachuanhu
 *
 */
@Service
@Validated
public class BizValidatorService {
	/**
	 * 利息记账业务检查
	 * 
	 * @param data
	 * @throws ValidationException
	 */
	public void validateInte(EveryDayAccountingBean item) throws ValidationException {

		RequestData data = item.getRequestData();
		// 取交易前List
		List<SubAcctData> listBefore = data.getBeforeSubAcctData();
		// 取交易后List
		List<SubAcctData> listAfter = data.getSubAcctData();
		BigDecimal postAmt = BigDecimal.ZERO;// 记账金额
		for (int i = 0; i < listBefore.size(); i++) {
			BigDecimal accrual = listAfter.get(i).getIntAccrual().subtract(listBefore.get(i).getIntAccrual());
			postAmt = postAmt.add(accrual);
		}
		if (!data.getTotalAmt().equals(postAmt)) {
			throw new ErrorMessageException(ErrorCode.CheckError,
					String.format("计算出的记账金额与输入的交易金额不相等！错误的流水号 %s", item.getTxnSerialNo()));
		}
	}

	/**
	 * 罚息记账业务检查
	 * 
	 * @param item
	 * @throws ValidationException
	 */
	public void validatePint(EveryDayAccountingBean item) throws ValidationException {
		RequestData data = item.getRequestData();
		// 取交易前List
		List<SubAcctData> listBefore = data.getBeforeSubAcctData();
		// 取交易后List
		List<SubAcctData> listAfter = data.getSubAcctData();
		BigDecimal postAmt = BigDecimal.ZERO;// 记账金额
		for (int i = 0; i < listBefore.size(); i++) {
			BigDecimal accrual = listAfter.get(i).getIntPenaltyAccrual()
					.subtract(listBefore.get(i).getIntPenaltyAccrual());
			postAmt = postAmt.add(accrual);
		}
		if (!data.getTotalAmt().equals(postAmt)) {
			throw new ErrorMessageException(ErrorCode.CheckError,
					String.format("计算出的记账金额与输入的交易金额不相等！错误的流水号 %s", item.getTxnSerialNo()));
		}
	}

	/**
	 * 余额结转记账业务检查
	 * 
	 * @param item
	 * @throws ValidationException
	 */
	public void validateTrans(EveryDayAccountingBean item) throws ValidationException {
		RequestData data = item.getRequestData();
		// 取交易前List
		List<SubAcctData> listBefore = data.getBeforeSubAcctData();
		// 取交易后List
		List<SubAcctData> listAfter = data.getSubAcctData();
		BigDecimal beforeLoanCurrBal = BigDecimal.ZERO;// 交易前LOAN当前余额
		BigDecimal afterLoanCurrBal = BigDecimal.ZERO;// 交易后LOAN当前余额
		BigDecimal inteAmt = BigDecimal.ZERO;// 利息
		BigDecimal pintAmt = BigDecimal.ZERO;// 罚息
		for (SubAcctData list1 : listBefore) {
			if (list1.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				beforeLoanCurrBal = list1.getCurrBal();
			}
			inteAmt = inteAmt.add(list1.getIntAccrual() == null ? BigDecimal.ZERO : list1.getIntAccrual());// 交易前利息发生额累计
			pintAmt = pintAmt.add(list1.getIntPenaltyAccrual() == null ? BigDecimal.ZERO : list1.getIntPenaltyAccrual());// 交易前罚息发生额累计
		}
		for (SubAcctData list2 : listAfter) {
			if (list2.getSubAcctType().equals(SubAcctTypeDef.LOAN)) {
				afterLoanCurrBal = list2.getCurrBal();
			}
		}
		BigDecimal lbalAmt = beforeLoanCurrBal.subtract(afterLoanCurrBal);// 结转出的应还本金
		BigDecimal postAmt = BigDecimal.ZERO;// 记账金额
		postAmt = lbalAmt.add(inteAmt).add(pintAmt);
		if(data.getAgeCd() == 4){
			for (SubAcctData list1 : listBefore) {
				if (list1.getSubAcctType().equals(SubAcctTypeDef.INTE)) {
					postAmt = postAmt.add(list1.getCurrBal() == null ? BigDecimal.ZERO : list1.getCurrBal());
				}
				if (list1.getSubAcctType().equals(SubAcctTypeDef.PNIT)) {
					postAmt = postAmt.add(list1.getCurrBal() == null ? BigDecimal.ZERO : list1.getCurrBal());
				}
			}
		}
		if (!data.getTotalAmt().equals(postAmt)) {
			throw new ErrorMessageException(ErrorCode.CheckError,
					String.format("计算出的记账金额与输入的交易金额不相等！错误的流水号 %s", item.getTxnSerialNo()));
		}
		
		
	}

	/**
	 * 还款记账业务检查
	 * 
	 * @param item
	 * @throws ValidationException
	 */
	public void validateBatchRepay(EveryDayAccountingBean item) throws ValidationException {
		RequestData data = item.getRequestData();
		BigDecimal currBal = BigDecimal.ZERO;
		BigDecimal unionAmt = BigDecimal.ZERO;
		if (data.getIsUnion() == true) {
			for (SubAcctData beforeSubAcctData : data.getBeforeSubAcctData()) {
				if (beforeSubAcctData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
					currBal = currBal.add(beforeSubAcctData.getCurrBal());
				}
			}
			for (UnionData unionData : data.getUnionData()) {
				if (unionData.getSubAcctType().equals(SubAcctTypeDef.LBAL)) {
					unionAmt = unionAmt.add(unionData.getOtherAmt().add(unionData.getOwnAmt()));
				}

			}
		}
		if (!currBal.equals(unionAmt)) {
			throw new ErrorMessageException(ErrorCode.CheckError,
					String.format("联合贷时我方与参与方金额总和与LBAL发生金额不相等！错误的流水号 %s", item.getTxnSerialNo()));

		}
	}

}
