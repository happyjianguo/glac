package net.engining.sccc.batch.sccc0800;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pg.batch.sdk.AbstractKeyBasedStreamReader;
import net.engining.sccc.entity.model.BtDataMigrationTempdtl;
import net.engining.sccc.entity.model.QBtDataMigrationTempdtl;

/**
 * @author luxue
 *
 */
@Service
@StepScope
public class Sccc0830R03BtDataMigrationTempdtl extends AbstractKeyBasedStreamReader<String, List<BtDataMigrationTempdtl>>{
	
	private static final Logger log = LoggerFactory.getLogger(Sccc0830R03BtDataMigrationTempdtl.class);
	
	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;
	
	@PersistenceContext
	private EntityManager em;
	
//	public Sccc0800R(){
//		//设置批量Step的Reader，在进行分片处理时的最小分片数据量
//		setMinPartitionSize(100);
//	}

	@Override
	protected List<String> loadKeys() {
		QBtDataMigrationTempdtl qBtDataMigrationTempdtl =  QBtDataMigrationTempdtl.btDataMigrationTempdtl;
		List<String> keyLs = new JPAQueryFactory(em)
				.select(qBtDataMigrationTempdtl.txnDetailSeq)
				.from(qBtDataMigrationTempdtl)
				.where(qBtDataMigrationTempdtl.bizDate.eq(bizDate))
				.groupBy(qBtDataMigrationTempdtl.txnDetailSeq)
				.fetch();
				
		return keyLs;
	}

	@Override
	protected List<BtDataMigrationTempdtl> loadItemByKey(String key) {
		QBtDataMigrationTempdtl qBtDataMigrationTempdtl =  QBtDataMigrationTempdtl.btDataMigrationTempdtl;
		List<BtDataMigrationTempdtl> datas = new JPAQueryFactory(em)
				.select(qBtDataMigrationTempdtl)
				.from(qBtDataMigrationTempdtl)
				.where(qBtDataMigrationTempdtl.txnDetailSeq.eq(key))
				.fetch();
		
		return datas;
	}

}
