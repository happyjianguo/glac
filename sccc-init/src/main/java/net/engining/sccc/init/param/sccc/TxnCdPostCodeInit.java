package net.engining.sccc.init.param.sccc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.pcx.cc.param.model.enums.Deadline;
import net.engining.pcx.cc.param.model.enums.SysTxnCd;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.init.ParameterInitializer;
import net.engining.sccc.biz.bean.TxnCdPostCode;
import net.engining.sccc.biz.bean.TxnRule;
import net.engining.sccc.biz.enums.AccountTradingDef;
@Service
public class TxnCdPostCodeInit implements ParameterInitializer{
	
	@Autowired
	private ParameterFacility facility;

	@Override
	public void init() throws Exception {
		for(String s : facility.getParameterMap(TxnCdPostCode.class).keySet()){
			facility.removeParameter(TxnCdPostCode.class, s);
		}
		
		TxnCdPostCode tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T003;
		tp.postCode = "SC0001";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.S,Deadline.M,null,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T004;
		tp.postCode = "SC0002";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.S,Deadline.S,null,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T005;
		tp.postCode = "SC0003";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.D,Deadline.S,null,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T006;
		tp.postCode = "SC0004";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.D,Deadline.S,null,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T007;
		tp.postCode = "SC0005";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.D,Deadline.M,null,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T008;
		tp.postCode = "SC0006";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.D,Deadline.M,null,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T009;
		tp.postCode = "SC0007";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.INTEACC,Deadline.S,AgeGroupCd.Normality,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T400;
		tp.postCode = "SC0400";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.INTEACC,Deadline.S,AgeGroupCd.Attention,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T010;
		tp.postCode = "SC0008";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.INTEACC,Deadline.S,AgeGroupCd.Above4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T011;
		tp.postCode = "SC0009";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.INTEACC,Deadline.M,AgeGroupCd.Normality,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T401;
		tp.postCode = "SC0401";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.INTEACC,Deadline.M,AgeGroupCd.Attention,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T012;
		tp.postCode = "SC0010";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.INTEACC,Deadline.M,AgeGroupCd.Above4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T013;
		tp.postCode = "SC0011";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.PINTACC,Deadline.S,AgeGroupCd.Under4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T014;
		tp.postCode = "SC0012";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.PINTACC,Deadline.S,AgeGroupCd.Above4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T015;
		tp.postCode = "SC0013";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.PINTACC,Deadline.M,AgeGroupCd.Under4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T016;
		tp.postCode = "SC0014";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.PINTACC,Deadline.M,AgeGroupCd.Above4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T017;
		tp.postCode = "SC0015";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL,Deadline.S,AgeGroupCd.Attention,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T018;
		tp.postCode = "SC0016";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL,Deadline.S,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T019;
		tp.postCode = "SC0017";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL,Deadline.S,AgeGroupCd.Suspicious,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T020;
		tp.postCode = "SC0018";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL,Deadline.S,AgeGroupCd.Loss,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T021;
		tp.postCode = "SC0019";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL,Deadline.M,AgeGroupCd.Attention,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T022;
		tp.postCode = "SC0020";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL,Deadline.M,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T023;
		tp.postCode = "SC0021";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL,Deadline.M,AgeGroupCd.Suspicious,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T024;
		tp.postCode = "SC0022";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL,Deadline.M,AgeGroupCd.Loss,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T025;
		tp.postCode = "SC0023";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.INTE,Deadline.S,AgeGroupCd.Under4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T026;
		tp.postCode = "SC0024";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.INTE,Deadline.S,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T027;
		tp.postCode = "SC0025";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.INTE,Deadline.M,AgeGroupCd.Under4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T028;
		tp.postCode = "SC0026";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.INTE,Deadline.M,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T029;
		tp.postCode = "SC0027";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,null,null,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T030;
		tp.postCode = "SC0028";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.T,null,null,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T031;
		tp.postCode = "SC0029";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.F,null,null,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T032;
		tp.postCode = "SC0030";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.S,AgeGroupCd.Normality,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T033;
		tp.postCode = "SC0031";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.S,AgeGroupCd.Attention,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T034;
		tp.postCode = "SC0032";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.S,AgeGroupCd.Secondary,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T035;
		tp.postCode = "SC0033";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.S,AgeGroupCd.Suspicious,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T036;
		tp.postCode = "SC0034";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.S,AgeGroupCd.Loss,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T037;
		tp.postCode = "SC0035";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.S,AgeGroupCd.Normality,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T038;
		tp.postCode = "SC0036";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.S,AgeGroupCd.Attention,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T039;
		tp.postCode = "SC0037";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.S,AgeGroupCd.Secondary,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T040;
		tp.postCode = "SC0038";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.S,AgeGroupCd.Suspicious,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T041;
		tp.postCode = "SC0039";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.S,AgeGroupCd.Loss,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T042;
		tp.postCode = "SC0040";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.M,AgeGroupCd.Normality,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T043;
		tp.postCode = "SC0041";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.M,AgeGroupCd.Attention,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T044;
		tp.postCode = "SC0042";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.M,AgeGroupCd.Secondary,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T045;
		tp.postCode = "SC0043";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.M,AgeGroupCd.Suspicious,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T046;
		tp.postCode = "SC0044";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.M,AgeGroupCd.Loss,true)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T047;
		tp.postCode = "SC0045";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.M,AgeGroupCd.Normality,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T048;
		tp.postCode = "SC0046";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.M,AgeGroupCd.Attention,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T049;
		tp.postCode = "SC0047";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.M,AgeGroupCd.Secondary,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T050;
		tp.postCode = "SC0048";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.M,AgeGroupCd.Suspicious,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T051;
		tp.postCode = "SC0049";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.H,Deadline.M,AgeGroupCd.Loss,false)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T052;
		tp.postCode = "SC0050";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.OTAX_INTE,Deadline.S,AgeGroupCd.Normality,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T402;
		tp.postCode = "SC0402";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.OTAX_INTE,Deadline.S,AgeGroupCd.Attention,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T053;
		tp.postCode = "SC0051";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.OTAX_INTE,Deadline.M,AgeGroupCd.Normality,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T403;
		tp.postCode = "SC0403";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.OTAX_INTE,Deadline.M,AgeGroupCd.Attention,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T054;
		tp.postCode = "SC0052";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.PINT,Deadline.S,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T055;
		tp.postCode = "SC0053";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.PINT,Deadline.M,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T404;
		tp.postCode = "SC0404";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.OTAX_PINT,Deadline.S,null,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T405;
		tp.postCode = "SC0405";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.OTAX_PINT,Deadline.M,null,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T406;
		tp.postCode = "SC0406";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL_RETURN,Deadline.S,AgeGroupCd.Normality,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T407;
		tp.postCode = "SC0407";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL_RETURN,Deadline.S,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T408;
		tp.postCode = "SC0408";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL_RETURN,Deadline.S,AgeGroupCd.Suspicious,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T409;
		tp.postCode = "SC0409";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL_RETURN,Deadline.S,AgeGroupCd.Loss,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T410;
		tp.postCode = "SC0410";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL_RETURN,Deadline.M,AgeGroupCd.Normality,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T411;
		tp.postCode = "SC0411";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL_RETURN,Deadline.M,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T412;
		tp.postCode = "SC0412";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL_RETURN,Deadline.M,AgeGroupCd.Suspicious,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T413;
		tp.postCode = "SC0413";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.LBAL_RETURN,Deadline.M,AgeGroupCd.Loss,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T414;
		tp.postCode = "SC0414";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_INTEACC,Deadline.S,AgeGroupCd.Normality,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T415;
		tp.postCode = "SC0415";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_INTEACC,Deadline.S,AgeGroupCd.Attention,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T416;
		tp.postCode = "SC0416";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_INTEACC,Deadline.S,AgeGroupCd.Above4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T417;
		tp.postCode = "SC0417";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_INTEACC,Deadline.M,AgeGroupCd.Normality,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T418;
		tp.postCode = "SC0418";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_INTEACC,Deadline.M,AgeGroupCd.Attention,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T419;
		tp.postCode = "SC0419";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_INTEACC,Deadline.M,AgeGroupCd.Above4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T420;
		tp.postCode = "SC0420";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_PINTACC,Deadline.S,AgeGroupCd.Under4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T421;
		tp.postCode = "SC0421";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_PINTACC,Deadline.S,AgeGroupCd.Above4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T422;
		tp.postCode = "SC0422";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_PINTACC,Deadline.M,AgeGroupCd.Under4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T423;
		tp.postCode = "SC0423";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_PINTACC,Deadline.M,AgeGroupCd.Above4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T424;
		tp.postCode = "SC0424";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_LBAL,Deadline.S,AgeGroupCd.Attention,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T425;
		tp.postCode = "SC0425";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_LBAL,Deadline.S,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T426;
		tp.postCode = "SC0426";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_LBAL,Deadline.S,AgeGroupCd.Suspicious,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T427;
		tp.postCode = "SC0427";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_LBAL,Deadline.S,AgeGroupCd.Loss,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T428;
		tp.postCode = "SC0428";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_LBAL,Deadline.M,AgeGroupCd.Attention,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T429;
		tp.postCode = "SC0429";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_LBAL,Deadline.M,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T430;
		tp.postCode = "SC0430";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_LBAL,Deadline.M,AgeGroupCd.Suspicious,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T431;
		tp.postCode = "SC0431";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_LBAL,Deadline.M,AgeGroupCd.Loss,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T432;
		tp.postCode = "SC0432";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_INTE,Deadline.S,AgeGroupCd.Under4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T433;
		tp.postCode = "SC0433";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_INTE,Deadline.S,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T434;
		tp.postCode = "SC0434";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_INTE,Deadline.M,AgeGroupCd.Under4M3,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T435;
		tp.postCode = "SC0435";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_INTE,Deadline.M,AgeGroupCd.Secondary,null)), tp);

		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T436;
		tp.postCode = "SC0436";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_PINT,Deadline.S,AgeGroupCd.Secondary,null)), tp);
		
		tp = new TxnCdPostCode();
		tp.txnCd = SysTxnCd.T437;
		tp.postCode = "SC0437";
		facility.addParameter(TxnCdPostCode.key(new TxnRule(AccountTradingDef.C_PINT,Deadline.M,AgeGroupCd.Secondary,null)), tp);
		
		
	}
	
}
