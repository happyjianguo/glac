package net.engining.sccc.init.param.sccc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.param.model.SubAcctType;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.init.ParameterInitializer;
@Service
public class SubAcctTypeInit implements ParameterInitializer{

	@Autowired
	private ParameterFacility facility;

	@Override
	public void init() throws Exception {
		for(SubAcctType sat : facility.getParameterMap(SubAcctType.class).values()){
			facility.removeParameter(SubAcctType.class, sat.subAcctType);
		}
		
		for(SubAcctType sat : getSubAcctTypeList()){
			facility.addParameter(sat.subAcctType,sat);
		}
		
	}
	
	private List<SubAcctType> getSubAcctTypeList(){
		List<SubAcctType> list = new ArrayList<SubAcctType>();
		
		SubAcctType sat = new SubAcctType();
		sat.subAcctType = "LOAN";
		sat.description = "货款本金";
		list.add(sat);
		
		sat = new SubAcctType();
		sat.subAcctType = "LBAL";
		sat.description = "货款到期余额";
		list.add(sat);
		
		sat = new SubAcctType();
		sat.subAcctType = "SFEE";
		sat.description = "费用";
		list.add(sat);
		
		sat = new SubAcctType();
		sat.subAcctType = "PAYM";
		sat.description = "溢缴款";
		list.add(sat);
		
		sat = new SubAcctType();
		sat.subAcctType = "INTE";
		sat.description = "利息余额";
		list.add(sat);
		
		sat = new SubAcctType();
		sat.subAcctType = "PNIT";
		sat.description = "罚息余额";
		list.add(sat);
		
		sat = new SubAcctType();
		sat.subAcctType = "OTAX";
		sat.description = "销项税";
		list.add(sat);
		
		return list;
		
	}
}
