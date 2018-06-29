package net.engining.sccc.batch.service;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd;
import net.engining.pcx.cc.infrastructure.shared.model.CactSysChecklist;
import net.engining.pcx.cc.param.model.enums.CheckListType;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.biz.service.params.CactSysCheck;

@Service
public class CactSysCheckListService {
	@Autowired
	private ParameterFacility parameterCacheFacility;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insertCheck(Date bizDate, String batchSeq, CheckListType type) {

		Map<String, CactSysCheck> map = parameterCacheFacility.getParameterMap(CactSysCheck.class);

		InspectionCd[] cds = InspectionCd.values();
		// 循环检查项枚举，找到枚举对应的初始化参数，添加数据
		for (InspectionCd cd : cds) {
			CactSysCheck cact = map.get(cd + "|" + type);
			if (cact != null) {
				CactSysChecklist check = new CactSysChecklist();
				// BeanUtils.copyProperties(cact, check);

				check.setInspectionCd(cact.inspectionCd);
				check.setCheckListDesc(cact.checkListDesc);
				check.setCheckListType(cact.checkListType);
				check.setCheckTimes(cact.checkTimes);
				check.setCheckStatus(cact.checkStatus);
				check.setSkipable(cact.skipable);
				check.setSkipConditionType(cact.skipConditionType);
				check.setSkipConMaxCount(cact.skipConMaxCount);
				check.setSkipConDeadline(cact.skipConDeadline);
				check.setBizDate(bizDate);
				check.setBatchSeq(batchSeq);
				check.fillDefaultValues();

				em.persist(check);
			}
		}

	}
}
