package net.engining.sccc.batch.sccc0901;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.batch.sccc0901.bean.DirectPostLedgerFileInfo;
import net.engining.sccc.entity.model.BtNqhImport;

/**
 * 拿去花数据导入
 * @author luxue
 *
 */
@Service
@StepScope
public class Sccc0901P02NqhDataImport implements ItemProcessor<DirectPostLedgerFileInfo, BtNqhImport> {

	@PersistenceContext
	private EntityManager em;

	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;
	
	@Value("#{jobParameters['batchSeq']}")
	private String batchSeq;
	
	@Autowired
	Provider4Organization provider4Organization;

	@Override
	public BtNqhImport process(DirectPostLedgerFileInfo item) throws Exception {
		// 根据主键防重复，主键用hashcode插入
		Integer id = item.hashCode();
		if (em.find(BtNqhImport.class, id) != null) {
			throw new ErrorMessageException(ErrorCode.CheckError,"数据重复，请核对文件！");
		}
		
		BtNqhImport btNqhImport = new BtNqhImport();
		btNqhImport.setId(id);
		btNqhImport.setBatchSeq(batchSeq);
		btNqhImport.setBizDate(bizDate);
		btNqhImport.setBranchNo(provider4Organization.getCurrentOrganizationId());
		btNqhImport.setChannelId(item.getChanneId());
		btNqhImport.setClearDate(item.getClearDate());
		btNqhImport.setProdId(item.getProdId());
		btNqhImport.setOrg(provider4Organization.getCurrentOrganizationId());
		btNqhImport.setPostAmt(item.getPostAmt());
		btNqhImport.setPostCode(item.getPostCode());
		btNqhImport.fillDefaultValues();
		em.persist(btNqhImport);
		return btNqhImport;
	}

}
