package net.engining.sccc.batch.sccc0800;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.engining.pcx.cc.infrastructure.shared.model.CactAccountTem;
import net.engining.sccc.biz.bean.AccountBean;

@Service
@StepScope
public class Sccc0810P01InsertCactAccountTem implements ItemProcessor<List<AccountBean>, Object>{
	
	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Object process(List<AccountBean> item) throws Exception {
		if(item == null) return null;
		String data = JSON.toJSONString(item);
		
		CactAccountTem tem = new CactAccountTem();
		tem.setData(data);
		tem.setBizDate(bizDate);
		tem.fillDefaultValues();
		
		em.persist(tem);
		
		return null;
	}

}
