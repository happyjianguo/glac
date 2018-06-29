package net.engining.sccc.accounting.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import net.engining.gm.facility.SystemStatusFacility;
import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.gm.param.model.SystemStatus;
import net.engining.pcx.cc.param.model.enums.SubjectAmtType;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.web.WebCommonResponse;
import net.engining.sccc.accounting.bean.ResponseData;
import net.engining.sccc.accounting.sao.AccountingSao;
import net.engining.sccc.biz.bean.PostCodeSeq;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.service.AmountCalculationService;
import net.engining.sccc.biz.service.CheckDataService;
import net.engining.sccc.biz.service.DataComplementationService;
import net.engining.sccc.biz.service.SplitVolService;

@RequestMapping("/accounting")
@RestController
public class AccountingController {

	@Autowired
	AccountingSao accountingSao;

	@Autowired
	private CheckDataService checkDataService;

	@Autowired
	private SplitVolService splitVolService;

	@Autowired
	ParameterFacility parameterFacility;

	@Autowired
	private AmountCalculationService amountCalculationService;

	@Autowired
	private Provider7x24 provider7x24;

	@Autowired
	private DataComplementationService dataComplementation;
	
	@Autowired
	SystemStatusFacility systemStatusFacility;
	
	@ApiOperation(value = "查询系统状态", notes = "")
	@RequestMapping(value = "/checkStatus", method = RequestMethod.POST)
	public @ResponseBody String checkStatus() {
		
		SystemStatus status = systemStatusFacility.getSystemStatus();
		String result = status.systemStatus.name();

		return result;
	}

	@ApiOperation(value = "额度数据校验", notes = "")
	@RequestMapping(value = "/accountLimit", method = RequestMethod.POST)
	public @ResponseBody String accountLimit(@RequestBody EveryDayAccountingBean accountLimitBean) {

		String result = checkDataService.checkData(accountLimitBean);

		return result;
	}

	@Transactional
	@ApiOperation(value = "额度数据记账", notes = "")
	@RequestMapping(value = "/accountLimitTxn", method = RequestMethod.POST)
	public @ResponseBody ResponseData accountLimitTxn(@RequestBody EveryDayAccountingBean onlineDataBean) {

		// 账户信息补全
		Integer acctSeq = dataComplementation.insertCactAccount(onlineDataBean, new Date());

		// 入当日记账交易表
		PostCodeSeq postCodeSeq = dataComplementation.insertCactTxnPost(onlineDataBean, new Date(), acctSeq);

		// 入总账表
		dataComplementation.insertApGlTxn(acctSeq, postCodeSeq, onlineDataBean);

		// 金额计算
		Map<SubjectAmtType, BigDecimal> map = amountCalculationService.uoDownLimit(onlineDataBean);

		// 数据入账
		AgeGroupCd ageGroup = null;
		splitVolService.split(postCodeSeq, map, onlineDataBean.getClearDate(), provider7x24.getCurrentDate(),
				onlineDataBean.getRequestData(), ageGroup);
		ResponseData responseData = new ResponseData();
		responseData.setReturnCode(WebCommonResponse.CODE_OK);
		responseData.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return responseData;

	}

	@ApiOperation(value = "费用收取数据校验", notes = "")
	@RequestMapping(value = "/costAccount", method = RequestMethod.POST)
	public @ResponseBody String costAccount(@RequestBody EveryDayAccountingBean costAccountBean) {

		String result = checkDataService.checkData(costAccountBean);

		return result;

	}

	@Transactional
	@ApiOperation(value = "费用收取数据记账", notes = "")
	@RequestMapping(value = "/costAccountTxn", method = RequestMethod.POST)
	public @ResponseBody ResponseData costAccountTxn(@RequestBody EveryDayAccountingBean item) {

		// 账户信息补全
		Integer acctSeq = dataComplementation.insertCactAccount(item, new Date());

		// 入当日记账交易表
		PostCodeSeq postCodeSeq = dataComplementation.insertCactTxnPost(item, new Date(), acctSeq);

		// 入总账表
		dataComplementation.insertApGlTxn(acctSeq, postCodeSeq, item);

		// 金额计算
		Map<SubjectAmtType, BigDecimal> map = amountCalculationService.costAccountTxn(item);

		AgeGroupCd ageGroup = null;

		// 套型入表
		splitVolService.split(postCodeSeq, map, item.getClearDate(), provider7x24.getCurrentDate(),
				item.getRequestData(), ageGroup);
		ResponseData responseData = new ResponseData();
		responseData.setReturnCode(WebCommonResponse.CODE_OK);
		responseData.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return responseData;

	}

	@ApiOperation(value = "贷款发放数据校验", notes = "")
	@RequestMapping(value = "/loanAccount", method = RequestMethod.POST)
	public @ResponseBody String loanAccount(@RequestBody EveryDayAccountingBean loanAccountBean) {

		String result = checkDataService.checkData(loanAccountBean);

		return result;

	}

	@Transactional
	@ApiOperation(value = "贷款发放数据入账", notes = "")
	@RequestMapping(value = "/loanAccountTxn", method = RequestMethod.POST)
	public @ResponseBody ResponseData loanAccountTxn(@RequestBody EveryDayAccountingBean item) {

		// 账户信息补全
		Integer acctSeq = dataComplementation.insertCactAccount(item, new Date());

		// 入当日记账交易表
		PostCodeSeq postCodeSeq = dataComplementation.insertCactTxnPost(item, new Date(), acctSeq);

		// 入总账表
		dataComplementation.insertApGlTxn(acctSeq, postCodeSeq, item);

		Map<SubjectAmtType, BigDecimal> map = amountCalculationService.loanAccountTxn(item);

		// 套型入表
		AgeGroupCd ageGroup = null;
		splitVolService.split(postCodeSeq, map, item.getClearDate(), provider7x24.getCurrentDate(),
				item.getRequestData(), ageGroup);
		ResponseData responseData = new ResponseData();
		responseData.setReturnCode(WebCommonResponse.CODE_OK);
		responseData.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return responseData;

	}

	@ApiOperation(value = "退款数据校验", notes = "")
	@RequestMapping(value = "/refundsAccount", method = RequestMethod.POST)
	public @ResponseBody String refundsAccount(@RequestBody EveryDayAccountingBean refundsBean) {

		String result = checkDataService.checkData(refundsBean);

		return result;

	}

	@Transactional
	@ApiOperation(value = "退款数据入账", notes = "")
	@RequestMapping(value = "/refundsAccountTxn", method = RequestMethod.POST)
	public @ResponseBody ResponseData refundsAccountTxn(@RequestBody EveryDayAccountingBean item) {

		// 账户信息补全
		Integer acctSeq = dataComplementation.insertCactAccount(item, new Date());

		// 入当日记账交易表
		PostCodeSeq postCodeSeq = dataComplementation.insertCactTxnPost(item, new Date(), acctSeq);

		// 入总账表
		dataComplementation.insertApGlTxn(acctSeq, postCodeSeq, item);

		// 金额计算
		Map<SubjectAmtType, BigDecimal> map = amountCalculationService.refundsAccountTxn(item);

		// 套型入表
		AgeGroupCd ageGroup = null;
		splitVolService.split(postCodeSeq, map, item.getClearDate(), provider7x24.getCurrentDate(),
				item.getRequestData(), ageGroup);
		ResponseData responseData = new ResponseData();
		responseData.setReturnCode(WebCommonResponse.CODE_OK);
		responseData.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return responseData;

	}

	@ApiOperation(value = "还款数据校验", notes = "")
	@RequestMapping(value = "/repaymentAccount", method = RequestMethod.POST)
	public @ResponseBody String repaymentAccount(@RequestBody EveryDayAccountingBean repaymentBean) {

		String result = checkDataService.checkData(repaymentBean);

		return result;

	}

	@Transactional
	@ApiOperation(value = "还款数据入账", notes = "")
	@RequestMapping(value = "/repaymentAccountTxn", method = RequestMethod.POST)
	public @ResponseBody ResponseData repaymentAccountTxn(@RequestBody EveryDayAccountingBean item) {

		// 账户信息补全
		Integer acctSeq = dataComplementation.insertCactAccount(item, new Date());

		// 入当日记账交易表
		PostCodeSeq postCodeSeq = dataComplementation.insertCactTxnPost(item, new Date(), acctSeq);

		// 入总账表
		dataComplementation.insertApGlTxn(acctSeq, postCodeSeq, item);

		// 金额计算
		Map<SubjectAmtType, BigDecimal> map = amountCalculationService.repaymentAccountTxn(item);

		// 套型入表
		AgeGroupCd ageGroup = null;
		splitVolService.split(postCodeSeq, map, item.getClearDate(), provider7x24.getCurrentDate(),
				item.getRequestData(), ageGroup);
		ResponseData responseData = new ResponseData();
		responseData.setReturnCode(WebCommonResponse.CODE_OK);
		responseData.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return responseData;

	}

	@ApiOperation(value = "冲正还款数据校验", notes = "")
	@RequestMapping(value = "/correctionRepayment", method = RequestMethod.POST)
	public @ResponseBody String correctionRepayment(@RequestBody EveryDayAccountingBean correctionBean) {

		String result = checkDataService.checkData(correctionBean);

		return result;

	}

	@Transactional
	@ApiOperation(value = "冲正还款数据入账", notes = "")
	@RequestMapping(value = "/correctionRepaymentTxn", method = RequestMethod.POST)
	public @ResponseBody ResponseData correctionRepaymentTxn(@RequestBody EveryDayAccountingBean item) {

		// 账户信息补全
		Integer acctSeq = dataComplementation.insertCactAccount(item, new Date());

		// 入当日记账交易表
		PostCodeSeq postCodeSeq = dataComplementation.insertCactTxnPost(item, new Date(), acctSeq);

		// 入总账表
		dataComplementation.insertApGlTxn(acctSeq, postCodeSeq, item);

		// 金额计算
		Map<SubjectAmtType, BigDecimal> map = amountCalculationService.correctionRepaymentTxn(item);

		// 套型入表
		AgeGroupCd ageGroup = null;
		splitVolService.split(postCodeSeq, map, item.getClearDate(), provider7x24.getCurrentDate(),
				item.getRequestData(), ageGroup);
		ResponseData responseData = new ResponseData();
		responseData.setReturnCode(WebCommonResponse.CODE_OK);
		responseData.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return responseData;

	}

	@ApiOperation(value = "冲正利息计提数据校验", notes = "")
	@RequestMapping(value = "/correctionInteAcc", method = RequestMethod.POST)
	public @ResponseBody String correctionIntePintAcc(@RequestBody EveryDayAccountingBean intePintBean) {

		String result = checkDataService.checkData(intePintBean);

		return result;

	}

	@Transactional
	@ApiOperation(value = "冲正利息计提数据入账", notes = "")
	@RequestMapping(value = "/correctionInteAccTxn", method = RequestMethod.POST)
	public @ResponseBody ResponseData correctionIntePintAccTxn(@RequestBody EveryDayAccountingBean item) {

		// 账户信息补全
		Integer acctSeq = dataComplementation.insertCactAccount(item, new Date());

		// 入当日记账交易表
		PostCodeSeq postCodeSeq = dataComplementation.insertCactTxnPost(item, new Date(), acctSeq);

		// 入总账表
		dataComplementation.insertApGlTxn(acctSeq, postCodeSeq, item);

		// 金额计算
		Map<SubjectAmtType, BigDecimal> map = amountCalculationService.correctionInteAccTxn(item);

		// 套型入表
		AgeGroupCd ageGroup = null;
		splitVolService.split(postCodeSeq, map, item.getClearDate(), provider7x24.getCurrentDate(),
				item.getRequestData(), ageGroup);
		ResponseData responseData = new ResponseData();
		responseData.setReturnCode(WebCommonResponse.CODE_OK);
		responseData.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return responseData;

	}

	@ApiOperation(value = "冲正罚息计提数据校验", notes = "")
	@RequestMapping(value = "/correctionPnitAcc", method = RequestMethod.POST)
	public @ResponseBody String correctionPnitAcc(@RequestBody EveryDayAccountingBean intePintBean) {

		String result = checkDataService.checkData(intePintBean);

		return result;

	}

	@Transactional
	@ApiOperation(value = "冲正罚息计提数据入账", notes = "")
	@RequestMapping(value = "/correctionPnitAccTxn", method = RequestMethod.POST)
	public @ResponseBody ResponseData correctionPnitAccTxn(@RequestBody EveryDayAccountingBean item) {

		// 账户信息补全
		Integer acctSeq = dataComplementation.insertCactAccount(item, new Date());

		// 入当日记账交易表
		PostCodeSeq postCodeSeq = dataComplementation.insertCactTxnPost(item, new Date(), acctSeq);

		// 入总账表
		dataComplementation.insertApGlTxn(acctSeq, postCodeSeq, item);

		// 金额计算
		Map<SubjectAmtType, BigDecimal> map = amountCalculationService.correctionPnitAccTxn(item);

		// 套型入表
		AgeGroupCd ageGroup = null;
		splitVolService.split(postCodeSeq, map, item.getClearDate(), provider7x24.getCurrentDate(),
				item.getRequestData(), ageGroup);
		ResponseData responseData = new ResponseData();
		responseData.setReturnCode(WebCommonResponse.CODE_OK);
		responseData.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return responseData;

	}

	@ApiOperation(value = "冲正余额结转数据校验", notes = "")
	@RequestMapping(value = "/correctionTransfo", method = RequestMethod.POST)
	public @ResponseBody String correctionTransfo(@RequestBody EveryDayAccountingBean transfoBean) {

		String result = checkDataService.checkData(transfoBean);

		return result;

	}

	@Transactional
	@ApiOperation(value = "冲正余额结转数据入账", notes = "")
	@RequestMapping(value = "/correctionTransfoTxn", method = RequestMethod.POST)
	public @ResponseBody ResponseData correctionTransfoTxn(@RequestBody EveryDayAccountingBean item) {

		// 账户信息补全
		Integer acctSeq = dataComplementation.insertCactAccount(item, new Date());

		// 入当日记账交易表
		PostCodeSeq postCodeSeq = dataComplementation.insertCactTxnPost(item, new Date(), acctSeq);

		// 入总账表
		dataComplementation.insertApGlTxn(acctSeq, postCodeSeq, item);

		// 金额计算
		Map<SubjectAmtType, BigDecimal> map = amountCalculationService.correctionTransfoTxn(item);

		// 套型入表
		AgeGroupCd ageGroup = null;
		splitVolService.split(postCodeSeq, map, item.getClearDate(), provider7x24.getCurrentDate(),
				item.getRequestData(), ageGroup);
		ResponseData responseData = new ResponseData();
		responseData.setReturnCode(WebCommonResponse.CODE_OK);
		responseData.setReturnDesc(WebCommonResponse.DESC_SUCCESS);
		return responseData;

	}

}