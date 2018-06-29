package net.engining.sccc.mgm.sao;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.TotalBookkingRes;
import net.engining.sccc.biz.bean.TransOprHstReq;
import net.engining.sccc.mgm.sao.imp.PostQuerySaoImp;
@FeignClient(value = "sccc-accounting-sharding-sv", fallback = PostQuerySaoImp.class)
public interface PostQuerySao {
	@RequestMapping(value ="/vodtlHst/postAccountQuery", method = RequestMethod.POST)
	FetchResponse<TotalBookkingRes> doPostQueryServiceSao(@RequestBody TransOprHstReq req);

}
