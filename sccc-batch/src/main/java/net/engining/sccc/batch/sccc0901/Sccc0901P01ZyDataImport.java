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
import net.engining.sccc.entity.model.BtZyImport;

@Service
@StepScope
public class Sccc0901P01ZyDataImport implements ItemProcessor<DirectPostLedgerFileInfo, BtZyImport> {

	@PersistenceContext
	private EntityManager em;

	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;
	
	@Value("#{jobParameters['batchSeq']}")
	private String batchSeq;
	
	@Autowired
	Provider4Organization provider4Organization;

	@Override
	public BtZyImport process(DirectPostLedgerFileInfo item) throws Exception {
		
		// 根据主键防重复，主键用hashcode插入
		Integer id = item.hashCode();
		if(em.find(BtZyImport.class, id) != null){
			throw new ErrorMessageException(ErrorCode.CheckError,"数据重复，请核对文件！");
		}
		
		BtZyImport btZyImport = new BtZyImport();
		btZyImport.setId(id);
		btZyImport.setBatchSeq(batchSeq);
		btZyImport.setBizDate(bizDate);
		btZyImport.setBranchNo(provider4Organization.getCurrentOrganizationId());
		btZyImport.setChannelId(item.getChanneId());
		btZyImport.setClearDate(item.getClearDate());
		btZyImport.setProdId(item.getProdId());
		btZyImport.setOrg(provider4Organization.getCurrentOrganizationId());
		btZyImport.setPostAmt(item.getPostAmt());
		btZyImport.setPostCode(item.getPostCode());
		btZyImport.fillDefaultValues();
		em.persist(btZyImport);
		return btZyImport;
	}

}
