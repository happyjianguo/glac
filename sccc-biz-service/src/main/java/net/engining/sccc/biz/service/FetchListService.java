package net.engining.sccc.biz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.param.model.PostCode;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.TxnSubjectParam;
import net.engining.pg.parameter.ParameterFacility;

@Service
public class FetchListService {

	@Autowired
	ParameterFacility parameterFacility;

	/**
	 * 获取交易类型下拉框
	 * 
	 */
	public List<Map<String, Object>> fetchTradeTypeList() {
		Map<String, Object> pcMap = null;
		List<Map<String, Object>> pcList = new ArrayList<Map<String, Object>>();
		Map<String, PostCode> postCodes = parameterFacility.getParameterMap(PostCode.class);
		for (PostCode postCode : postCodes.values()) {
			pcMap = new HashMap<String, Object>();
			pcMap.put("subjectCd", postCode.postCode);
			pcMap.put("name", postCode.shortDesc);
			pcList.add(pcMap);
		}
		return pcList;
	}

	/**
	 * 获取科目下拉框
	 * 
	 */
	public List<Map<String, Object>> fetchSubjectList() {
		Map<String, Object> subMap = null;
		List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
		Map<String, Subject> subjects = parameterFacility.getParameterMap(Subject.class);
		for (Subject subject : subjects.values()) {
			subMap = new HashMap<String, Object>();
			subMap.put("subjectCd", subject.subjectCd);
			subMap.put("name", subject.name);
			subList.add(subMap);
		}
		return subList;
	}
}
