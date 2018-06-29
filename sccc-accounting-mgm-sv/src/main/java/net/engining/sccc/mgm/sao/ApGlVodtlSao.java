package net.engining.sccc.mgm.sao;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import net.engining.sccc.biz.bean.ApGlVoldtlSumDetailReq;
import net.engining.sccc.biz.bean.FetchDataProcess;
import net.engining.sccc.biz.bean.VodtlAssSumHstDetail;
import net.engining.sccc.mgm.sao.imp.ApGlVodtlSaoImp;

@FeignClient(value = "sccc-accounting-sharding-sv", fallback = ApGlVodtlSaoImp.class)
public interface ApGlVodtlSao {



@RequestMapping(value = "/vodtlHst/vodtlAssSumHstDetailQuery", method = RequestMethod.POST)
public FetchDataProcess<VodtlAssSumHstDetail> vodtlAssSumHstDetailQuery(@RequestBody ApGlVoldtlSumDetailReq req);


}
