package net.engining.sccc.biz.bean;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.pcx.cc.param.model.enums.Deadline;
import net.engining.sccc.biz.enums.AccountTradingDef;

public class TxnRule {

	private AccountTradingDef type;
	private Deadline deadline;
	private AgeGroupCd ageGroupCd;
	private Boolean isOutFalg;

	public TxnRule(AccountTradingDef type, Deadline deadline, AgeGroupCd ageGroupCd, Boolean isOutFalg) {
		this.type = type;
		this.deadline = deadline;
		this.ageGroupCd = ageGroupCd;
		this.isOutFalg = isOutFalg;

	}

	public String toString() {
		String s1;
		String s2;
		String s3;
		if(deadline == null){
			s1 = "";
		}else{
			s1 = deadline.toString();
		}
		if(ageGroupCd == null){
			s2 = "";
		}else{
			s2 = ageGroupCd.toString();
		}
		if(isOutFalg == null){
			s3 = "";
		}else{
			s3 = isOutFalg.toString();
		}
		
		return type + "|" + s1 + "|" + s2 + "|" + s3;
		
	}
}
