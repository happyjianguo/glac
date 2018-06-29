package net.engining.sccc.batch.sccc5401;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.InOutFlagDef;
import net.engining.pcx.cc.infrastructure.shared.enums.SubjectLevelDef;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlBal;
import net.engining.pcx.cc.infrastructure.shared.model.ApSubjectSummary;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlBal;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.enums.SubjectType;
import net.engining.pg.parameter.ParameterFacility;

@Service
@StepScope
public class Sccc5401UpdateApGlBalance implements ItemProcessor<ApSubjectSummary, ApSubjectSummary> {

	@PersistenceContext
	private EntityManager em;
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;
	
	@Autowired
	private ParameterFacility facility;

	@Override
	public ApSubjectSummary process(ApSubjectSummary item) throws Exception {

		QApGlBal qApGlBal = QApGlBal.apGlBal;
		ApGlBal apGlBal = new JPAQueryFactory(em).select(qApGlBal).from(qApGlBal)
				.where(qApGlBal.subjectCd.eq(item.getSubjectCd())).fetchOne();
		if(apGlBal == null){
			ApGlBal bal = new ApGlBal();
			Subject subject = facility.loadParameter(Subject.class, item.getSubjectCd());
			
			bal.setOrg(item.getOrg());
			bal.setBranchNo(item.getBranchNo());
			bal.setSubjectCd(item.getSubjectCd());
			bal.setSubjectType(subject.type);
			bal.setSubjectName(subject.name);
			bal.setDbBal(item.getDbBal());
			bal.setCrBal(item.getCrBal());
			bal.setDbAmt(item.getDbAmt());
			bal.setCrAmt(item.getCrAmt());
			bal.setDbCount(item.getDbCount());
			bal.setCrCount(item.getCrCount());
			bal.setMtdDbAmt(item.getDbAmt());
			bal.setMtdCrAmt(item.getCrAmt());
			bal.setQtdDbAmt(item.getDbAmt());
			bal.setQtdCrAmt(item.getCrAmt());
			bal.setYtdDbAmt(item.getDbAmt());
			bal.setYtdCrAmt(item.getCrAmt());
			if(subject.subjectHierarchy.equals("1")){
				bal.setSubjectLevel(SubjectLevelDef.S);
			}else if(subject.isLast){
				bal.setSubjectLevel(SubjectLevelDef.L);
			}else{
				bal.setSubjectLevel(SubjectLevelDef.O);
			}
			if(SubjectType.F.equals(subject.type)){
				bal.setInOutFlag(InOutFlagDef.B);
			}else{
				bal.setInOutFlag(InOutFlagDef.A);
			}
			
			bal.setBizDate(batchDate);
			bal.fillDefaultValues();
			
			em.persist(bal);
			
			
		}else{
			apGlBal.setLastDbBal(apGlBal.getDbBal());
			apGlBal.setLastCrBal(apGlBal.getCrBal());
			apGlBal.setMtdDbAmt(apGlBal.getMtdDbAmt().add(item.getDbAmt()));
			apGlBal.setMtdCrAmt(apGlBal.getMtdCrAmt().add(item.getCrAmt()));
			apGlBal.setQtdDbAmt(apGlBal.getQtdDbAmt().add(item.getDbAmt()));
			apGlBal.setQtdCrAmt(apGlBal.getQtdCrAmt().add(item.getCrAmt()));
			apGlBal.setYtdDbAmt(apGlBal.getYtdDbAmt().add(item.getDbAmt()));
			apGlBal.setYtdCrAmt(apGlBal.getYtdCrAmt().add(item.getCrAmt()));
			apGlBal.setDbAmt(item.getDbAmt());
			apGlBal.setDbCount(item.getDbCount());
			apGlBal.setCrAmt(item.getCrAmt());
			apGlBal.setCrCount(item.getCrCount());
			apGlBal.setDbBal(item.getDbBal());
			apGlBal.setCrBal(item.getCrBal());
		}
		
		return null;
	}

}
