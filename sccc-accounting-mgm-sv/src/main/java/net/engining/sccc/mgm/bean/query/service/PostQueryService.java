package net.engining.sccc.mgm.bean.query.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.TotalBookkingRes;
import net.engining.sccc.biz.bean.TransOprHstReq;
import net.engining.sccc.mgm.sao.PostQuerySao;

@Service
public class PostQueryService {

	@Autowired 
	private PostQuerySao postQuerySao;
	
	public FetchResponse<TotalBookkingRes> getPostQueryService(TransOprHstReq req) {
		return postQuerySao.doPostQueryServiceSao(req);
	}

}
