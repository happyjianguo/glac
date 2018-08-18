package net.engining.sccc.batch.sccc0800;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;
import net.engining.sccc.biz.bean.AccountBean;
import net.engining.sccc.biz.service.CheckAcctSeqService;
import net.engining.sccc.entity.model.QBtDataMigrationTempdtl;
@Service
@StepScope
public class Sccc0810R01BtDataMigrationTempdtl extends AbstractKeyBasedStreamReader<String, List<AccountBean>>{

	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CheckAcctSeqService checkAcctSeqService;
	
	@Override
	protected List<String> loadKeys() {
		
		QBtDataMigrationTempdtl qBtDataMigrationTempdtl =  QBtDataMigrationTempdtl.btDataMigrationTempdtl;
		List<String> keyLs = new JPAQueryFactory(em)
				.select(qBtDataMigrationTempdtl.custId)
				.from(qBtDataMigrationTempdtl)
				.where(qBtDataMigrationTempdtl.bizDate.eq(bizDate))
				.groupBy(qBtDataMigrationTempdtl.custId)
				.fetch();
				
		return keyLs;
	}

	@Override
	protected List<AccountBean> loadItemByKey(String key) {
		
		QBtDataMigrationTempdtl q =  QBtDataMigrationTempdtl.btDataMigrationTempdtl;
		List<Tuple> tupleList = new JPAQueryFactory(em)
		.select(q.txnDetailSeq,q.custId,q.iouNo,q.productId)
		.from(q).where(q.custId.eq(key)).fetch();
		
		List<AccountBean> accList = new ArrayList<AccountBean>();
		List<String> iouNoList = new ArrayList<String>();
		AccountBean acc = null;
		for(Tuple t : tupleList){
			if(iouNoList.contains(t.get(q.iouNo))){
				continue;
			}else{
				iouNoList.add(t.get(q.iouNo));
			}
			acc = checkAcctSeqService.getAccountBean(t.get(q.custId), t.get(q.iouNo), 5, t.get(q.productId));
			if(acc != null){
				accList.add(acc);
				acc = null;
			}
		}
		
		if(accList.size() > 0){
			return accList;
		}else{
			return null;
		}
		
	}

}
