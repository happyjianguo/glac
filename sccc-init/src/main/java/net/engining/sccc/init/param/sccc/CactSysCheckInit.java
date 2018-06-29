package net.engining.sccc.init.param.sccc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckStatusDef;
import net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd;
import net.engining.pcx.cc.infrastructure.shared.enums.SkipConditionTypeDef;
import net.engining.pcx.cc.param.model.enums.CheckListType;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.init.ParameterInitializer;
import net.engining.sccc.biz.service.params.CactSysCheck;

@Service
public class CactSysCheckInit implements ParameterInitializer {
	@Autowired
	private ParameterFacility facility;

	@Override
	public void init() throws Exception {
		for (String s : facility.getParameterMap(CactSysCheck.class).keySet()) {
			facility.removeParameter(CactSysCheck.class, s);
		}

		for (CactSysCheck cscl : getCactSysCheckList()) {
			facility.addParameter(cscl.getKey(), cscl);
		}

	}

	private List<CactSysCheck> getCactSysCheckList() throws ParseException {
		List<CactSysCheck> list = new ArrayList<CactSysCheck>();
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		CactSysCheck cscl = new CactSysCheck();
		cscl.inspectionCd = InspectionCd.CheckTrade;
		cscl.checkListDesc = "对账交易检查";
		cscl.checkListType = CheckListType.END_DAY_JOB;
		cscl.checkTimes = 10;
		cscl.checkStatus = CheckStatusDef.WAIT;
		cscl.skipable = Boolean.FALSE;
		cscl.skipConditionType = SkipConditionTypeDef.TIME;
		cscl.skipConMaxCount = 20;
		cscl.skipConDeadline = format.parse("22:00:00");
		list.add(cscl);

		cscl = new CactSysCheck();
		cscl.inspectionCd = InspectionCd.NQHAcct;
		cscl.checkListDesc = "拿去花记账交易检查";
		cscl.checkListType = CheckListType.END_DAY_JOB;
		cscl.checkTimes = 10;
		cscl.checkStatus = CheckStatusDef.WAIT;
		cscl.skipable = Boolean.TRUE;
		cscl.skipConditionType = SkipConditionTypeDef.TIME;
		cscl.skipConMaxCount = 20;
		cscl.skipConDeadline = format.parse("22:00:00");
		list.add(cscl);

		cscl = new CactSysCheck();
		cscl.inspectionCd = InspectionCd.ZYAcct;
		cscl.checkListDesc = "中银记账交易检查";
		cscl.checkListType = CheckListType.END_DAY_JOB;
		cscl.checkTimes = 10;
		cscl.checkStatus = CheckStatusDef.WAIT;
		cscl.skipable = Boolean.TRUE;
		cscl.skipConditionType = SkipConditionTypeDef.TIME;
		cscl.skipConMaxCount = 20;
		cscl.skipConDeadline = format.parse("22:00:00");
		list.add(cscl);

		cscl = new CactSysCheck();
		cscl.inspectionCd = InspectionCd.InterestProvision;
		cscl.checkListDesc = "利息计提记账交易检查";
		cscl.checkListType = CheckListType.END_DAY_JOB;
		cscl.checkTimes = 10;
		cscl.checkStatus = CheckStatusDef.WAIT;
		cscl.skipable = Boolean.TRUE;
		cscl.skipConditionType = SkipConditionTypeDef.TIME;
		cscl.skipConMaxCount = 20;
		cscl.skipConDeadline = format.parse("22:00:00");
		list.add(cscl);

		cscl = new CactSysCheck();
		cscl.inspectionCd = InspectionCd.PaymentRecord;
		cscl.checkListDesc = "罚息计收记账交易检查";
		cscl.checkListType = CheckListType.END_DAY_JOB;
		cscl.checkTimes = 10;
		cscl.checkStatus = CheckStatusDef.WAIT;
		cscl.skipable = Boolean.TRUE;
		cscl.skipConditionType = SkipConditionTypeDef.TIME;
		cscl.skipConMaxCount = 20;
		cscl.skipConDeadline = format.parse("22:00:00");
		list.add(cscl);

		cscl = new CactSysCheck();
		cscl.inspectionCd = InspectionCd.BalCarriedForward;
		cscl.checkListDesc = "余额成分结转记账交易检查";
		cscl.checkListType = CheckListType.END_DAY_JOB;
		cscl.checkTimes = 10;
		cscl.checkStatus = CheckStatusDef.WAIT;
		cscl.skipable = Boolean.TRUE;
		cscl.skipConditionType = SkipConditionTypeDef.TIME;
		cscl.skipConMaxCount = 20;
		cscl.skipConDeadline = format.parse("22:00:00");
		list.add(cscl);

		cscl = new CactSysCheck();
		cscl.inspectionCd = InspectionCd.RepaymentAcct;
		cscl.checkListDesc = "批量还款记账交易检查";
		cscl.checkListType = CheckListType.END_DAY_JOB;
		cscl.checkTimes = 10;
		cscl.checkStatus = CheckStatusDef.WAIT;
		cscl.skipable = Boolean.TRUE;
		cscl.skipConditionType = SkipConditionTypeDef.TIME;
		cscl.skipConMaxCount = 20;
		cscl.skipConDeadline = format.parse("22:00:00");
		list.add(cscl);

		return list;

	}
}
