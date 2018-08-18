package net.engining.sccc.batch.sccc0900;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.alibaba.fastjson.JSONObject;

import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;
import net.engining.sccc.biz.enums.CheckDataResDef;
import net.engining.sccc.biz.service.CheckDataService;
import net.engining.sccc.entity.enums.TxnTypeDef;
import net.engining.sccc.entity.model.BtEodTxnImport;
import net.engining.sccc.entity.model.BtImportException;

@Service
@StepScope
@Validated
public class Sccc0900P00OnlineDataImport implements ItemProcessor<EveryDayAccountingBean, BtEodTxnImport> {
	
	private static final Logger log = LoggerFactory.getLogger(Sccc0900P00OnlineDataImport.class);

	@PersistenceContext
	private EntityManager em;

	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;

	@Value("#{jobParameters['batchSeq']}")
	private String batchSeq;

	@Autowired
	Provider4Organization provider4Organization;
	
	@Autowired
	private CheckDataService checkDataService;
	
	@Override
	public BtEodTxnImport process(EveryDayAccountingBean item) throws Exception {
		
		// 数据检查重复
		String id = item.getTxnSerialNo();
		BtEodTxnImport btEodTxnImportCheck = em.find(BtEodTxnImport.class, id);
		if (btEodTxnImportCheck != null) {
			throw new ErrorMessageException(ErrorCode.CheckError,
					String.format("数据重复，请核对文件！错误的流水号 %s", item.getTxnSerialNo()));
		}
		
		//数据校验
        String result = checkDataService.checkData(item);
        BtEodTxnImport btEodTxnImport = new BtEodTxnImport();
		if (!result.equals(CheckDataResDef.SUCCESS.toString())) {
			BtImportException btImportException = new BtImportException();
			btImportException.setExceptionMsg("数据异常" + item.getTxnSerialNo()+result);
			btImportException.fillDefaultValues();
			em.persist(btImportException);
			log.warn("处理文件数据记录出现异常：{}", ErrorCode.BadRequest, result + item.getTxnSerialNo());
		}else{
			btEodTxnImport.setBatchSeq(batchSeq);
			btEodTxnImport.setId(id);
			btEodTxnImport.setBizDate(bizDate);
			btEodTxnImport.setBranchNo(provider4Organization.getCurrentOrganizationId());
			btEodTxnImport.setOrg(provider4Organization.getCurrentOrganizationId());
			btEodTxnImport.setTxnType(TxnTypeDef.RECON);
			btEodTxnImport.setJsonInputData(JSONObject.toJSONString(item));
			btEodTxnImport.fillDefaultValues();
			em.persist(btEodTxnImport);
		}

		return btEodTxnImport;
	}

}
