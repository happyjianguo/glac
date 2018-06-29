package net.engining.sccc.batch.sccc0901;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType;
import net.engining.pcx.cc.infrastructure.shared.model.BtZyImport;
import net.engining.pcx.cc.process.service.support.refactor.DirectAccountingEvent;
import net.engining.sccc.biz.bean.AssistAccountData;
import net.engining.sccc.biz.service.SplitVolService;

/**
 * 中银记账文件记账
 * @author xiachuanhu
 *
 */
@Service
@StepScope
public class Sccc0901P05ZyPostLedger implements ItemProcessor<BtZyImport, Object> {

	private static final Logger logger = LoggerFactory.getLogger(Sccc0901P05ZyPostLedger.class);
	@Value("#{new org.joda.time.LocalDate(jobParameters['bizDate'].time)}")
	private LocalDate bizDate;
	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private SplitVolService splitVolService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public Object process(BtZyImport item) throws Exception {
		String postCode = item.getPostCode();
		BigDecimal postAmt = item.getPostAmt();
		Date clearDate = item.getClearDate();
		String product = item.getProdId();
		String channel = item.getChannelId();
		/*
		 * 触发当日总账交易流水event 直接记账
		 */
		DirectAccountingEvent event = new DirectAccountingEvent(this);
		event.setPostCode(postCode);
		event.setPostAmount(postAmt);
		event.setClearDate(clearDate);
		event.setAcctSeq(0);
		event.setPostDate(bizDate.toDate());
		ctx.publishEvent(event);
		logger.info("入当日总账交易流水表结束！");

		List<AssistAccountData> list = new ArrayList<AssistAccountData>();
		AssistAccountData data = new AssistAccountData();
		data.setKey(AssistAccountingType.PRODUCT);// 产品
		data.setAssistAccountingDesc(product);
		list.add(data);
		data = new AssistAccountData();
		data.setKey(AssistAccountingType.TRENCH);// 渠道
		data.setAssistAccountingDesc(channel);
		list.add(data);
		String assistData = JSON.toJSONString(list);
		splitVolService.splitZyNqh(postCode, postAmt, clearDate, bizDate, assistData);

		return null;
	}

}
