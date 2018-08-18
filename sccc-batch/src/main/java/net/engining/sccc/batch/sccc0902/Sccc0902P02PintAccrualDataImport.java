package net.engining.sccc.batch.sccc0902;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.alibaba.fastjson.JSON;

import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.batch.service.BizValidatorService;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.entity.enums.TxnTypeDef;
import net.engining.sccc.entity.model.BtEodTxnImport;

@Service
@StepScope
@Validated
public class Sccc0902P02PintAccrualDataImport implements ItemProcessor<EveryDayAccountingBean, BtEodTxnImport> {

	@PersistenceContext
	private EntityManager em;

	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;

	@Value("#{jobParameters['batchSeq']}")
	private String batchSeq;

	@Autowired
	Provider4Organization provider4Organization;

	@Autowired
	private BizValidatorService bizValidatorService;

	@Override
	public BtEodTxnImport process(EveryDayAccountingBean item) throws Exception {
		String id = item.getTxnSerialNo();
		BtEodTxnImport btEodTxnImport = em.find(BtEodTxnImport.class, id);
		if (btEodTxnImport != null) {
			throw new ErrorMessageException(ErrorCode.CheckError,
					String.format("数据重复，请核对文件！错误的流水号 %s", item.getTxnSerialNo()));
		}
		// 业务数据检查
		bizValidatorService.validatePint(item);

		// 保存日终交易记账数据导入流水表
		btEodTxnImport = new BtEodTxnImport();
		btEodTxnImport.setId(id);
		btEodTxnImport.setBatchSeq(batchSeq);
		btEodTxnImport.setBizDate(bizDate);
		btEodTxnImport.setBranchNo(provider4Organization.getCurrentOrganizationId());
		btEodTxnImport.setOrg(provider4Organization.getCurrentOrganizationId());
		btEodTxnImport.setTxnType(TxnTypeDef.PINTACC);
		String json = JSON.toJSONString(item);
		btEodTxnImport.setJsonInputData(json);
		btEodTxnImport.fillDefaultValues();
		em.persist(btEodTxnImport);
		return btEodTxnImport;
	}

}
