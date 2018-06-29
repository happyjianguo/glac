package net.engining.sccc.batch.sccc5701;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlAss;
import net.engining.sccc.biz.bean.AssistAccountData;

/**
 * 根据辅助核算项拆分
 * 
 * @author wanglidong
 */
@Service
@StepScope
public class Sccc5701P01AssistSplit implements ItemProcessor<ApGlVolDtl, Object> {
	@PersistenceContext
	private EntityManager em;
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;
	private static final Logger log = LoggerFactory.getLogger(Sccc5701P01AssistSplit.class);

	@Override
	public Object process(ApGlVolDtl item) throws Exception {

		// 辅助核算项不存在，直接返回
		if (StringUtils.isBlank(item.getAssistAccountData())) {
			return null;
		};
		
		// 拆分辅助核算项类型
		String s = item.getAssistAccountData();
		List<AssistAccountData> list = JSON.parseArray(s, AssistAccountData.class);
		// 按辅助核算项类型添加数据
		ApGlVolDtlAss ass = null;
		for (AssistAccountData atad : list) {
			ass = new ApGlVolDtlAss();
			log.debug("辅助核算项类型：" + atad.getKey() + ",辅助核算项数据：" + atad.getAssistAccountingDesc());

			ass.setSubjectCd(item.getTxnDirection().equals(TxnDirection.D) ? item.getDbsubjectCd() : item.getCrsubjectCd());
			ass.setAssistType(atad.getKey());
			ass.setAssistAccountValue(atad.getAssistAccountingDesc());
			ass.setOrg(item.getOrg());
			ass.setBranchNo(item.getBranchNo());
			ass.setVolDt(item.getVolDt());
			ass.setBranch(item.getBranch());
			ass.setTxnBrcd(item.getTxnBrcd());
			ass.setCurrCd(item.getCurrCd());
			ass.setSubjAmount(item.getSubjAmount());
			ass.setVolSeq(item.getVolSeq());
			ass.setRedBlueInd(item.getRedBlueInd());
			ass.setVolDesc(item.getVolDesc());
			ass.setRefNo(item.getRefNo());
			ass.setTxnDetailSeq(item.getGlvSeq());
			ass.setTxnDetailType(TxnDetailType.R);
			ass.setTxnDirection(item.getTxnDirection());
			ass.setTransDate(item.getTransDate());
			ass.setBizDate(batchDate);
			ass.fillDefaultValues();
			em.persist(ass);
		}

		return null;
	}

}
