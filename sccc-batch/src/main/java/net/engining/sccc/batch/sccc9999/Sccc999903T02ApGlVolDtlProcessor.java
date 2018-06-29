package net.engining.sccc.batch.sccc9999;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.sccc.batch.service.BatchStepMQProducer;
import net.engining.sccc.biz.bean.TransferDataByKey;

/**
 * 
 * 数据治理，删除当日总帐相关数据；<br>
 * AP_GL_TXN;<br> 
 *
 */
@Service
@StepScope
public class Sccc999903T02ApGlVolDtlProcessor implements ItemProcessor<List<String>, RepeatStatus> {

	private static final Logger log = LoggerFactory.getLogger(Sccc999903T02ApGlVolDtlProcessor.class);
	
	@Autowired
	private BatchStepMQProducer batchStepMQProducer;

	@PersistenceContext
	private EntityManager em;

	public RepeatStatus process(List<String> item) throws Exception {
		if(item!=null){
			TransferDataByKey bean = new TransferDataByKey();
			bean.setKeys(item);
			bean.setTxnDetailType(TxnDetailType.G);
			log.debug("将为Step9999ApGlVolDtl发送消息到MQ，keys={}", JSON.toJSONString(item));
			batchStepMQProducer.senderMsg4Step9999(bean);
		}
		
		return RepeatStatus.FINISHED;
	}
}
