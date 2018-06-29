package net.engining.sccc.mgm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlAssSum;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAss;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.ApGlVoldtlSumRes;
import net.engining.sccc.biz.bean.VodtlAssSumDetail;

@Service
public class VolDtlSumService {

	public FetchResponse<Map<String, Object>> getVOlDtlSumRes(FetchResponse<Map<String, Object>> fetchResponse) {
		FetchResponse<Map<String, Object>> fetchResponseForm = new FetchResponse<Map<String, Object>>();
		List<Map<String, Object>> list = fetchResponse.getData();
		for (Map<String, Object> ap : list) {
			ApGlVoldtlSumRes res = new ApGlVoldtlSumRes();
			ApGlVolDtlAssSum sum = new ApGlVolDtlAssSum();
			BeanUtils.copyProperties(ap, sum);
			if (StringUtils.isNotEmpty(String.valueOf(sum.getDbAmt()))) {
				res.setTxnDirection(TxnDirection.D);
				res.setLastBal(sum.getLastDbBal());
				res.setBal(sum.getDbAmt());
				fetchResponseForm.setData(list);
			}
			if (StringUtils.isNotEmpty(String.valueOf(sum.getCrBal()))) {
				res.setTxnDirection(TxnDirection.C);
				res.setLastBal(sum.getLastCrBal());
				res.setBal(sum.getCrAmt());
			}
		}
		return fetchResponseForm;
	}

	/**
	 * 记账复合明细数据返回组装
	 * 
	 * @param fetchResponse
	 * @return
	 */
	@Transactional
	public List<VodtlAssSumDetail> transOprDetail(List<Tuple> fetchResponse) {
		List<VodtlAssSumDetail> list = new ArrayList<VodtlAssSumDetail>();
		for (Tuple tuple : fetchResponse) {
			QApGlVolDtlAss qApGlVolDtlAss = QApGlVolDtlAss.apGlVolDtlAss;
			VodtlAssSumDetail vodtlAssSumDetail = new VodtlAssSumDetail();
			vodtlAssSumDetail.setVolDt(String.valueOf(tuple.get(qApGlVolDtlAss.volDt)));
			vodtlAssSumDetail.setTxnDetailSeq(tuple.get(qApGlVolDtlAss.txnDetailSeq));
			vodtlAssSumDetail.setTransDate(String.valueOf(tuple.get(qApGlVolDtlAss.transDate)));
			vodtlAssSumDetail.setSubjAmount(String.valueOf(tuple.get(qApGlVolDtlAss.subjAmount)));
			vodtlAssSumDetail.setTxnDirection(String.valueOf(tuple.get(qApGlVolDtlAss.txnDirection)));
			vodtlAssSumDetail.setVolSeq(String.valueOf(tuple.get(qApGlVolDtlAss.volSeq)));
			vodtlAssSumDetail.setVolDesc(tuple.get(qApGlVolDtlAss.volDesc));
			vodtlAssSumDetail.setRedBlueInd(String.valueOf(tuple.get(qApGlVolDtlAss.redBlueInd)));
			vodtlAssSumDetail.setRefNo(tuple.get(qApGlVolDtlAss.refNo));
			list.add(vodtlAssSumDetail);
		}
		return list;
	}

}
