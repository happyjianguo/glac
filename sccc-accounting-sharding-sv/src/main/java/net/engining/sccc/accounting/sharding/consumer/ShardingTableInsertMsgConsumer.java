package net.engining.sccc.accounting.sharding.consumer;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;

import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.sccc.accounting.sharding.service.BatchTransferService;
import net.engining.sccc.biz.bean.TransferDataByKey;

/**
 * 针对RPC调用分库分表Insert操作的消息消费者
 * @author luxue
 *
 */
@Service
public class ShardingTableInsertMsgConsumer {
	
	private static final Logger log = LoggerFactory.getLogger(ShardingTableInsertMsgConsumer.class);
	
	@Autowired
	SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory;
	
	@Autowired
	private BatchTransferService batchTransferService;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@RabbitListener(containerFactory="rabbitListenerContainerFactory", queues="eodjob.step9999.queue")
	public void handle4ApGlHst(TransferDataByKey transferDataByKey){
		try {
			if(transferDataByKey.getTxnDetailType().equals(TxnDetailType.C)){
				batchTransferService.apGlTxnData(transferDataByKey.getKeys());
				log.debug("消费者ApGlTxn获取消息为：keys={}", JSON.toJSONString(transferDataByKey.getKeys()));
			}else if(transferDataByKey.getTxnDetailType().equals(TxnDetailType.G)){
				batchTransferService.apGlVoldtlData(transferDataByKey.getKeys());
				log.debug("消费者ApGlVolDtl获取消息为：keys={}", JSON.toJSONString(transferDataByKey.getKeys()));
			}else if(transferDataByKey.getTxnDetailType().equals(TxnDetailType.T)){
				batchTransferService.apGlVoldtlAssData(transferDataByKey.getKeys());
				log.debug("消费者ApGlVolDtlAss获取消息为：keys={}", JSON.toJSONString(transferDataByKey.getKeys()));
			}
		} catch (Exception e) {
			log.debug(JSON.toJSONString(transferDataByKey));
			log.error(e.getMessage());
			dump(e.getCause());
			//TODO 方案1：需要有容错机制，消息入表，交给异步轮询任务重试或通知任务
			//TODO 方案2：batch sv 通过sao调用，利用Hystrix机制进行重试，可支持异步
		}
	}
	
	private void dump(Throwable t){
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		if(Optional.fromNullable(t).isPresent()){
			t.printStackTrace(printWriter);
			log.debug(StringUtils.CR+StringUtils.LF+stringWriter.toString()+StringUtils.CR+StringUtils.LF);
		}
		
	}
}
