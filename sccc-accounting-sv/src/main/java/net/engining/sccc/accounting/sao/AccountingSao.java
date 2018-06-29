package net.engining.sccc.accounting.sao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.engining.pg.web.WebCommonResponse;
import net.engining.sccc.accounting.bean.ApGlTxnHstResponseBean;
import net.engining.sccc.accounting.bean.ApGlTxnRequestBean;
import net.engining.sccc.accounting.sao.impl.AccountingSaoImpl;

/**
 * 远程调用微服务
 * @author luxue
 *
 */
// Hystrix控制熔断，当调用远程服务不可用达到阀值时（默认5秒20次），调用本地实现类的重写方法
@FeignClient(value = "sccc-accounting-sharding-sv", fallback = AccountingSaoImpl.class)
public interface AccountingSao {

	/**
	 * 调用微服务sccc-accounting-sharding-sv/queryOuterLedgerTxnHst
	 * @return
	 */
	@RequestMapping(value = "/queryOuterLedgerTxnHst", method = RequestMethod.POST)
	ApGlTxnHstResponseBean queryOuterLedgerTxnHst(@RequestBody ApGlTxnRequestBean apGlTxn);
	
}
