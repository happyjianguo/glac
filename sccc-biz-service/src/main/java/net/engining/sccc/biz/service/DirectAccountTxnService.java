package net.engining.sccc.biz.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.param.model.TxnSubjectParam;
import net.engining.pg.parameter.ParameterFacility;

@Service
public class DirectAccountTxnService {
	@Autowired
	private ParameterFacility facility;
	
	public TxnSubjectParam directAccountRecord(String postCode){
		
		 Map<String, TxnSubjectParam> map = facility.getParameterMap(TxnSubjectParam.class);
		 for(TxnSubjectParam tsp : map.values()){
			 if(postCode.equals(tsp.txnCd)){
				 return tsp;
			 }
		 }
		
		return null;
	}
	
	
	/**
	 * 添加修改
	 * @param tspir
	 * @return
	 */
	public void directAccountInsert(TxnSubjectParam tsp){
		
		TxnSubjectParam txn = facility.getParameter(TxnSubjectParam.class, tsp.getKey());
		if(txn == null){
			facility.addParameter(tsp.getKey(), tsp);
		}else{
			facility.updateParameter(tsp.getKey(), tsp);
		}
	}
}
