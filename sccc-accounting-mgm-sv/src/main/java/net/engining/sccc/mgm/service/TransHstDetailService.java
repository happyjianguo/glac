package net.engining.sccc.mgm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;

import net.engining.gm.infrastructure.enums.TxnDirection;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlHst;
import net.engining.pcx.cc.infrastructure.shared.model.GlTransOprHst;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtl;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.biz.bean.TransOprHstDetail;
import net.engining.sccc.biz.bean.TransOprHstDetailRes;

@Service
public class TransHstDetailService {
	
	/**
	 *  记账复合明细数据返回组装
	 * @param fetchResponse
	 * @return
	 */
	@Autowired
	private ParameterFacility parameterFacility;
	
	@SuppressWarnings("null")
	@Transactional
	public List<TransOprHstDetail> transOprDetail( List<Tuple> fetchResponse){
		List<TransOprHstDetail> list=new ArrayList<TransOprHstDetail>();
		Map<String, Subject> subjects = parameterFacility.getParameterMap(Subject.class);
		for(Tuple tuple  : fetchResponse){
			QApGlVolDtl apGlVolDtl = QApGlVolDtl.apGlVolDtl;
			TransOprHstDetail transOprHstDetail =new TransOprHstDetail();
			if(TxnDirection.D.equals(tuple.get(apGlVolDtl.txnDirection))){
				transOprHstDetail.setDbsubject(tuple.get(apGlVolDtl.dbsubjectCd));
			}
			if(TxnDirection.C.equals(tuple.get(apGlVolDtl.txnDirection))){
				transOprHstDetail.setDbsubject(tuple.get(apGlVolDtl.crsubjectCd));
			}
			String subjectName = subjects.get(transOprHstDetail.getDbsubject()).name;
			transOprHstDetail.setSubjectName(subjectName);
			transOprHstDetail.setTxnDirection(tuple.get(apGlVolDtl.txnDirection));
			transOprHstDetail.setRedBlueInd(tuple.get(apGlVolDtl.redBlueInd));
			transOprHstDetail.setPostAmount(tuple.get(apGlVolDtl.subjAmount));
			transOprHstDetail.setAssistAccountData(tuple.get(apGlVolDtl.assistAccountData));
			list.add(transOprHstDetail);
		}
		return list;
	}
	

	/**
	 * 综合记账明细返回数据组装
	 * @param hst
	 * @param fetchResponse
	 * @return
	 */
	@SuppressWarnings("null")
	@Transactional
	public TransOprHstDetailRes transOprHstDetail(GlTransOprHst hst,List<ApGlVolDtlHst> fetchResponse){
		
		TransOprHstDetailRes res =new TransOprHstDetailRes();
		BeanUtils.copyProperties(hst, res);
		
		List<TransOprHstDetail> list=null;
		for(ApGlVolDtlHst apGlVolDtlHst  : fetchResponse){
			TransOprHstDetail transOprHstDetail =new TransOprHstDetail();
			
			BeanUtils.copyProperties(transOprHstDetail, apGlVolDtlHst);
			if(TxnDirection.D.equals(apGlVolDtlHst.getTxnDirection())){
				transOprHstDetail.setDbsubject(apGlVolDtlHst.getDbsubjectCd());
			}
			if(TxnDirection.C.equals(apGlVolDtlHst.getTxnDirection())){
				transOprHstDetail.setDbsubject(apGlVolDtlHst.getCrsubjectCd());
			}
			list.add(transOprHstDetail);
		}
		res.setList(list);
		return res;
	}

}
