package net.engining.sccc.batch.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.facility.SystemStatusFacility;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.sccc.batch.schedule.CheckListCriterion;
import net.engining.sccc.entity.enums.CheckStatusDef;
import net.engining.sccc.entity.model.QCactSysChecklist;
import net.engining.sccc.enums.CheckListType;
import net.engining.sccc.enums.InspectionCd;

@Service
public class CheckList4EndOfDayJobService implements CheckListCriterion {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Provider7x24 provider7x24;

	@Autowired
	SystemStatusFacility systemStatusFacility;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.engining.sccc.batch.schedule.AbstractCriterion#checkOk()
	 */
	@Override
	public boolean checkOk() {

		QCactSysChecklist q = QCactSysChecklist.cactSysChecklist;
		Boolean b = true;

		InspectionCd[] cds = InspectionCd.values();
		for (InspectionCd cd : cds) {
			CheckStatusDef t = new JPAQueryFactory(em).select(q.checkStatus).from(q).where(q.inspectionCd.eq(cd),
					q.bizDate.eq(provider7x24.getCurrentDate().toDate()), q.checkListType.eq(CheckListType.END_DAY_JOB))
					.orderBy(q.batchSeq.desc()).fetchFirst();
			if (!t.equals(CheckStatusDef.SUCCESS)) b = false;
		}

		return b;

	}

}