package net.engining.sccc.batch.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckStatusDef;
import net.engining.pcx.cc.infrastructure.shared.enums.SkipConditionTypeDef;
import net.engining.pcx.cc.infrastructure.shared.model.CactSysChecklist;

/**
 * 根据条件修改系统检查项状态
 * @author zhengzhaoxian
 *
 */
@Service
public class CheckListSysService {
	
	@PersistenceContext
	private EntityManager em;
	
//	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	@Transactional
	public void checkSys(CactSysChecklist cactSysChecklist){
		//FIXME 这里可能不是托管对象，在这里查出对象，再更新
		if (cactSysChecklist.getSkipable()) {
			if (SkipConditionTypeDef.COUNT.equals(cactSysChecklist.getSkipConditionType())) {
				if (cactSysChecklist.getCheckTimes() < cactSysChecklist.getSkipConMaxCount()) {
					cactSysChecklist.setCheckTimes(cactSysChecklist.getCheckTimes() + 1);
					cactSysChecklist.setCheckStatus(CheckStatusDef.FAILED);
				} else {
					cactSysChecklist.setCheckStatus(CheckStatusDef.SUCCESS);
				}
			}
			Date time = new Date();//dateFormat.parse(dateFormat.format(new Date()));
			if (SkipConditionTypeDef.TIME.equals(cactSysChecklist.getSkipConditionType())) {
				if (time.after(cactSysChecklist.getSkipConDeadline())) {
					cactSysChecklist.setCheckStatus(CheckStatusDef.SUCCESS);
				}else{
					cactSysChecklist.setCheckStatus(CheckStatusDef.FAILED);
				}
			}
		}else{
			cactSysChecklist.setCheckStatus(CheckStatusDef.SUCCESS);
		}
	}
}
