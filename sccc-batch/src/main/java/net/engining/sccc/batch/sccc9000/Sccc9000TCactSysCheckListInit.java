package net.engining.sccc.batch.sccc9000;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd;
import net.engining.pcx.cc.infrastructure.shared.model.CactSysChecklist;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.biz.service.params.CactSysCheck;
@Service
@StepScope
public class Sccc9000TCactSysCheckListInit implements Tasklet{
	@Autowired
	private ParameterFacility parameterCacheFacility;
	@Value("#{new java.util.Date(jobParameters['bizDate'].time)}")
	private Date bizDate;
	@PersistenceContext
	private EntityManager em;
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		Map<String,CactSysCheck> map = parameterCacheFacility.getParameterMap(CactSysCheck.class);
		
		InspectionCd[] cds = InspectionCd.values();
		//循环检查项枚举，找到枚举对应的初始化参数，添加数据
		for(InspectionCd cd : cds){
			CactSysCheck cact = map.get(cd);
			CactSysChecklist check = new CactSysChecklist();
			BeanUtils.copyProperties(cact, check);
			check.setBizDate(bizDate);
			
			em.persist(check);
		}
		
		return null;
	}

}
