package net.engining.sccc.batch.sccc0800;

import java.util.Date;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.engining.pcx.cc.infrastructure.shared.model.CactAccountTem;
import net.engining.sccc.biz.bean.AccountBean;
import net.engining.sccc.biz.service.CheckAcctSeqService;

@Service
@StepScope
public class Sccc0820P02InsertAccount implements ItemProcessor<CactAccountTem, Object>{
	
	@Autowired
	private CheckAcctSeqService checkAcctSeqService;
	
	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;

	@Override
	public Object process(CactAccountTem item) throws Exception {
		List<AccountBean> list = JSON.parseArray(item.getData(), AccountBean.class);
		checkAcctSeqService.insertAccount(list, bizDate);
		return null;
	}

}
