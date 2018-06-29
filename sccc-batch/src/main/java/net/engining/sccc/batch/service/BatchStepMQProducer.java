package net.engining.sccc.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.engining.sccc.biz.bean.TransferDataByKey;

/**
 * 供批量任务中发送消息到MQ
 * @author luxue
 *
 */
@Service
public class BatchStepMQProducer {
	
	private static final Logger log = LoggerFactory.getLogger(BatchStepMQProducer.class);

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	Queue eodJob4Step9999Queue;
	
	public void senderMsg4Step9999(TransferDataByKey transferDataByKey){
		log.debug("将为Step9999发送消息到MQ，keys={}", JSON.toJSONString(transferDataByKey.getKeys()));
//		String keys = JSON.toJSONString(transferDataByKey);
		rabbitTemplate.convertAndSend(eodJob4Step9999Queue.getName(), transferDataByKey);
	}
}
