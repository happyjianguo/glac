package net.engining.sccc.mgm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.sccc.biz.bean.ApGlVoldtlSumDetailReq;
import net.engining.sccc.biz.bean.FetchDataProcess;
import net.engining.sccc.biz.bean.VodtlAssSumHstDetail;
import net.engining.sccc.mgm.sao.ApGlVodtlSao;

@Service
public class ApGlVodtlService {

	@Autowired
	private ApGlVodtlSao accountingRecordSao;
	
	
	public FetchDataProcess<VodtlAssSumHstDetail> vodtlAssSumHstDetailQuery(ApGlVoldtlSumDetailReq req){
		return accountingRecordSao.vodtlAssSumHstDetailQuery(req);
	}
	
}
