package net.engining.sccc.biz.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.enums.SubjectType;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.ParameterFetchResponseBuilder;
import net.engining.pg.parameter.ParameterFilterCallback;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.biz.bean.SubjectInsert;
import net.engining.sccc.biz.bean.SubjectRecordRes;

@Service
public class SubjectService {

	@Autowired
	private ParameterFacility facility;

	public FetchResponse<Subject> subjectRecord(String subjectCd, SubjectType type, Boolean status, Range range) {

		return new ParameterFetchResponseBuilder<Subject>(facility).range(range).build(Subject.class,
				new ParameterFilterCallback<Subject>() {

					@Override
					public Map<String, Subject> filter(Map<String, Subject> map) {
						Map<String, Subject> returnMap = new HashMap<String, Subject>();
						for (Subject st : map.values()) {

							if (StringUtils.isNotBlank(subjectCd) && subjectCd.equals(st.subjectCd)
									&& st.type.equals(type) && st.enable.equals(status)) {
								returnMap.put(st.subjectCd, st);
							} else if (StringUtils.isBlank(subjectCd) && st.type.equals(type) && st.enable.equals(status)) {
								returnMap.put(st.subjectCd, st);
							}

						}

						return returnMap;
					}
				});
	}

	public SubjectRecordRes subjectRecordInquiryDetails(String subjectCd) {

		Subject s = facility.getParameter(Subject.class, subjectCd);

		SubjectRecordRes srr = new SubjectRecordRes();
		srr.setSubjectCd(s.subjectCd);
		srr.setName(s.name);
		srr.setDescription(s.description);
		srr.setBalDbCrFlag(s.balDbCrFlag);
		srr.setAmtDbCrFlag(s.amtDbCrFlag);
		srr.setType(s.type);
		srr.setCurrCd(s.currCd);
		srr.setParentSubjectCd(s.parentSubjectCd);
		srr.setEnable(s.enable);
		srr.setSubjectCode(s.subjectCode);
		srr.setIsOverdraft(s.isOverdraft);
		srr.setSubjectHierarchy(s.subjectHierarchy);
		srr.setIsLast(s.isLast);
		srr.setIsAccount(s.isAccount);
		srr.setAssistList(s.auxiliaryAssist);
		
		return srr;

	}

	public void subjectRecordInsert(SubjectInsert si) {

		Subject subject = new Subject();

		subject.subjectCd = si.getSubjectCd();
		subject.name = si.getName();
		subject.description = si.getDescription();
		subject.balDbCrFlag = si.getBalDbCrFlag();
		subject.amtDbCrFlag = si.getAmtDbCrFlag();
		subject.type = si.getType();
		subject.currCd = si.getCurrCd();
		subject.parentSubjectCd = si.getParentSubjectCd();
		subject.enable = si.getEnable();
		subject.subjectCode = si.getSubjectCode();
		subject.isOverdraft = si.getIsOverdraft();
		subject.subjectHierarchy = si.getSubjectHierarchy();
		subject.isLast = si.getIsLast();
		subject.isAccount = si.getIsAccount();
		subject.auxiliaryAssist = si.getAuxiliaryAssist();

		//判断科目是否存在 不存在添加  存在更新
		if (facility.getParameter(Subject.class, si.getSubjectCd()) != null) {
			facility.updateParameter(subject.subjectCd, subject);
		} else {
			facility.addParameter(subject.subjectCd, subject);
		}

	}
}
