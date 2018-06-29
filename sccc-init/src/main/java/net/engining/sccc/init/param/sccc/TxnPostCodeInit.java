package net.engining.sccc.init.param.sccc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.param.model.TxnPostCode;
import net.engining.pcx.cc.param.model.enums.SysTxnCd;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.init.ParameterInitializer;
@Service
public class TxnPostCodeInit implements ParameterInitializer{
	
	@Autowired
	private ParameterFacility facility;

	@Override
	public void init() throws Exception {
		for(String s : facility.getParameterMap(TxnPostCode.class).keySet()){
			facility.removeParameter(TxnPostCode.class, s);
		}
		
		TxnPostCode tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T003;
		tp.postCode = "SC0001";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T004;
		tp.postCode = "SC0002";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T005;
		tp.postCode = "SC0003";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T006;
		tp.postCode = "SC0004";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T007;
		tp.postCode = "SC0005";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T008;
		tp.postCode = "SC0006";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T009;
		tp.postCode = "SC0007";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T400;
		tp.postCode = "SC0400";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T010;
		tp.postCode = "SC0008";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T011;
		tp.postCode = "SC0009";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T401;
		tp.postCode = "SC0401";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T012;
		tp.postCode = "SC0010";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T013;
		tp.postCode = "SC0011";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T014;
		tp.postCode = "SC0012";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T015;
		tp.postCode = "SC0013";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T016;
		tp.postCode = "SC0014";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T017;
		tp.postCode = "SC0015";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T018;
		tp.postCode = "SC0016";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T019;
		tp.postCode = "SC0017";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T020;
		tp.postCode = "SC0018";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T021;
		tp.postCode = "SC0019";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T022;
		tp.postCode = "SC0020";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T023;
		tp.postCode = "SC0021";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T024;
		tp.postCode = "SC0022";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T025;
		tp.postCode = "SC0023";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T026;
		tp.postCode = "SC0024";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T027;
		tp.postCode = "SC0025";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T028;
		tp.postCode = "SC0026";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T029;
		tp.postCode = "SC0027";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T030;
		tp.postCode = "SC0028";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T031;
		tp.postCode = "SC0029";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T032;
		tp.postCode = "SC0030";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T033;
		tp.postCode = "SC0031";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T034;
		tp.postCode = "SC0032";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T035;
		tp.postCode = "SC0033";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T036;
		tp.postCode = "SC0034";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T037;
		tp.postCode = "SC0035";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T038;
		tp.postCode = "SC0036";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T039;
		tp.postCode = "SC0037";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T040;
		tp.postCode = "SC0038";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T041;
		tp.postCode = "SC0039";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T042;
		tp.postCode = "SC0040";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T043;
		tp.postCode = "SC0041";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T044;
		tp.postCode = "SC0042";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T045;
		tp.postCode = "SC0043";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T046;
		tp.postCode = "SC0044";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T047;
		tp.postCode = "SC0045";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T048;
		tp.postCode = "SC0046";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T049;
		tp.postCode = "SC0047";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T050;
		tp.postCode = "SC0048";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T051;
		tp.postCode = "SC0049";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T052;
		tp.postCode = "SC0050";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T402;
		tp.postCode = "SC0402";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T053;
		tp.postCode = "SC0051";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T403;
		tp.postCode = "SC0403";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T054;
		tp.postCode = "SC0052";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T055;
		tp.postCode = "SC0053";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T404;
		tp.postCode = "SC0404";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T405;
		tp.postCode = "SC0405";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T406;
		tp.postCode = "SC0406";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T407;
		tp.postCode = "SC0407";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T408;
		tp.postCode = "SC0408";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T409;
		tp.postCode = "SC0409";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T410;
		tp.postCode = "SC0410";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T411;
		tp.postCode = "SC0411";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T412;
		tp.postCode = "SC0412";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T413;
		tp.postCode = "SC0413";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T414;
		tp.postCode = "SC0414";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T415;
		tp.postCode = "SC0415";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T416;
		tp.postCode = "SC0416";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T417;
		tp.postCode = "SC0417";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T418;
		tp.postCode = "SC0418";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T419;
		tp.postCode = "SC0419";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T420;
		tp.postCode = "SC0420";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T421;
		tp.postCode = "SC0421";
		facility.addParameter(tp.getKey(), tp);
		
		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T422;
		tp.postCode = "SC0422";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T423;
		tp.postCode = "SC0423";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T424;
		tp.postCode = "SC0424";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T425;
		tp.postCode = "SC0425";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T426;
		tp.postCode = "SC0426";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T427;
		tp.postCode = "SC0427";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T428;
		tp.postCode = "SC0428";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T429;
		tp.postCode = "SC0429";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T430;
		tp.postCode = "SC0430";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T431;
		tp.postCode = "SC0431";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T432;
		tp.postCode = "SC0432";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T433;
		tp.postCode = "SC0433";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T434;
		tp.postCode = "SC0434";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T435;
		tp.postCode = "SC0435";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T436;
		tp.postCode = "SC0436";
		facility.addParameter(tp.getKey(), tp);

		tp = new TxnPostCode();
		tp.txnCd = SysTxnCd.T437;
		tp.postCode = "SC0437";
		facility.addParameter(tp.getKey(), tp);

	}

}
