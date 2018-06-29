package net.engining.sccc.mgm.sao;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.engining.sccc.biz.bean.DetailCheck;
import net.engining.sccc.mgm.sao.imp.DetailCheckSaoImp;

@FeignClient(value = "sccc-accounting-sharding-sv", fallback = DetailCheckSaoImp.class)
public interface DetailCheckSao {
	
	
	@RequestMapping(value = "/trial/detailCheck", method = RequestMethod.POST)
	public List<DetailCheck> doDetailCheck(@RequestBody List<DetailCheck> accounting);
}
