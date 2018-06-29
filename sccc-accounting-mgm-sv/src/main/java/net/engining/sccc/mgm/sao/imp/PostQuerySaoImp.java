package net.engining.sccc.mgm.sao.imp;


import org.springframework.stereotype.Component;

import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.TotalBookkingRes;
import net.engining.sccc.biz.bean.TransOprHstReq;
import net.engining.sccc.mgm.sao.PostQuerySao;
@Component
public class PostQuerySaoImp implements PostQuerySao{

	@Override
	public FetchResponse<TotalBookkingRes> doPostQueryServiceSao(TransOprHstReq req) {
		
		throw new ErrorMessageException(ErrorCode.Other, "连接超时");
	}

}
