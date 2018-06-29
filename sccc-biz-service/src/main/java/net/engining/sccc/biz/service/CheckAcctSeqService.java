package net.engining.sccc.biz.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.gm.infrastructure.enums.BusinessType;
import net.engining.pcx.cc.infrastructure.shared.model.CactAccount;
import net.engining.pcx.cc.infrastructure.shared.model.CactAccountAddi;
import net.engining.pcx.cc.infrastructure.shared.model.CactAccountNo;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccount;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccountAddi;
import net.engining.pcx.cc.infrastructure.shared.model.QCactAccountNo;
import net.engining.pcx.cc.param.model.enums.Deadline;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.sccc.biz.bean.AccountBean;
import net.engining.sccc.biz.service.params.ProductAccount;

@Service
public class CheckAcctSeqService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Provider4Organization provider4Organization;

	@Autowired
	private ParameterFacility parameterFacility;

	@Transactional
	public Integer checkAcctSeq(String custId, String iouNo, Integer totalPeriod, String productId, Date bizDate) {
		QCactAccount qCactAccount = QCactAccount.cactAccount;
		QCactAccountAddi qCactAccountAddi = QCactAccountAddi.cactAccountAddi;
		
		Integer acctSeq = null;
		QCactAccountNo qCactAccountNo = QCactAccountNo.cactAccountNo;
		Integer acctNo= new JPAQueryFactory(em).select(qCactAccountNo.acctNo).from(qCactAccountNo)
				.where(qCactAccountNo.custId.eq(custId)).fetchOne();

		
		if (acctNo == null) {
			ProductAccount init = null;
			String acctParamId=null;
			if(totalPeriod !=null){
				Deadline line = null;
				if (totalPeriod <= 12) {
					line = Deadline.S;
				} else {
					line = Deadline.M;
				}
				
				init = parameterFacility.getParameter(ProductAccount.class,ProductAccount.key(productId, line));
			}
			if(init !=null){
				acctParamId = init.getAccountId();
			}
			
			
			CactAccountNo accountNo = new CactAccountNo();
			accountNo.setOrg(provider4Organization.getCurrentOrganizationId());
			accountNo.setBranchNo(provider4Organization.getCurrentOrganizationId());
			accountNo.setCustId(custId);
			accountNo.setBizDate(bizDate);
			accountNo.fillDefaultValues();
			em.persist(accountNo);
			
			// 新增数据到cactAccount
			CactAccount acctFinal = new CactAccount();
			acctFinal.setOrg(provider4Organization.getCurrentOrganizationId());
			acctFinal.setBranchNo(provider4Organization.getCurrentOrganizationId());
			acctFinal.setBusinessType(BusinessType.BL);
			acctFinal.setAcctNo(accountNo.getAcctNo());
			acctFinal.setTotalLoanPeriod(totalPeriod);
			acctFinal.setBizDate(bizDate);
			acctFinal.setAcctParamId(acctParamId);
			acctFinal.fillDefaultValues();
			em.persist(acctFinal);
			
			// 新增数据到 CactAccountAddi
			CactAccountAddi addi = new CactAccountAddi();
			addi.setAcctSeq(acctFinal.getAcctSeq());
			addi.setOrg(provider4Organization.getCurrentOrganizationId());
			addi.setBranchNo(provider4Organization.getCurrentOrganizationId());
			addi.setIouNo(iouNo);
			addi.setProductNo(productId);
			addi.setBizDate(bizDate);
			addi.fillDefaultValues();
			em.persist(addi);
			
			acctSeq = acctFinal.getAcctSeq();
		} else {
			
			acctSeq = new JPAQueryFactory(em).select(qCactAccount.acctSeq).from(qCactAccountAddi,qCactAccount)
					.where(qCactAccountAddi.iouNo.eq(iouNo),qCactAccount.acctSeq.eq(qCactAccountAddi.acctSeq)).fetchOne();
			if(acctSeq == null){

				ProductAccount init = null;
				String acctParamId=null;
				if(totalPeriod !=null){
					Deadline line = null;
					if (totalPeriod <= 12) {
						line = Deadline.S;
					} else {
						line = Deadline.M;
					}
					
					init = parameterFacility.getParameter(ProductAccount.class,ProductAccount.key(productId, line));
				}
				if(init !=null){
					acctParamId = init.getAccountId();
				}
				
				// 新增数据到cactAccount
				CactAccount acctFinal = new CactAccount();
				acctFinal.setOrg(provider4Organization.getCurrentOrganizationId());
				acctFinal.setBranchNo(provider4Organization.getCurrentOrganizationId());
				acctFinal.setBusinessType(BusinessType.BL);
				acctFinal.setAcctNo(acctNo);
				acctFinal.setTotalLoanPeriod(totalPeriod);
				acctFinal.setBizDate(bizDate);
				acctFinal.setAcctParamId(acctParamId);
				acctFinal.fillDefaultValues();
				em.persist(acctFinal);
				
				// 新增数据到 CactAccountAddi
				CactAccountAddi addi = new CactAccountAddi();
				addi.setAcctSeq(acctFinal.getAcctSeq());
				addi.setOrg(provider4Organization.getCurrentOrganizationId());
				addi.setBranchNo(provider4Organization.getCurrentOrganizationId());
				addi.setIouNo(iouNo);
				addi.setProductNo(productId);
				addi.setBizDate(bizDate);
				addi.fillDefaultValues();
				em.persist(addi);
				acctSeq = acctFinal.getAcctSeq();
			}
		}
		return acctSeq;
	}
	
	@Transactional
	public AccountBean getAccountBean(String custId, String iouNo, Integer totalPeriod, String productId) {
		
		QCactAccountNo qCactAccountNo = QCactAccountNo.cactAccountNo;
		Integer acctNo= new JPAQueryFactory(em).select(qCactAccountNo.acctNo).from(qCactAccountNo)
				.where(qCactAccountNo.custId.eq(custId)).fetchOne();
		
		AccountBean acc = null;
		if (acctNo == null) {
			ProductAccount init = null;
			String acctParamId=null;
			if(totalPeriod !=null){
				Deadline line = null;
				if (totalPeriod <= 12) {
					line = Deadline.S;
				} else {
					line = Deadline.M;
				}
				
				init = parameterFacility.getParameter(ProductAccount.class,ProductAccount.key(productId, line));
			}
			if(init !=null){
				acctParamId = init.getAccountId();
			}
			acc = new AccountBean();
			acc.setAcctParamId(acctParamId);
			acc.setIouNo(iouNo);
			acc.setProductId(productId);
			acc.setCustId(custId);
			acc.setTotalPeriod(totalPeriod);
		} else {
			QCactAccount qCactAccount = QCactAccount.cactAccount;
			QCactAccountAddi qCactAccountAddi = QCactAccountAddi.cactAccountAddi;
			Integer acctSeq = new JPAQueryFactory(em).select(qCactAccount.acctSeq).from(qCactAccountAddi,qCactAccount)
					.where(qCactAccountAddi.iouNo.eq(iouNo),qCactAccount.acctSeq.eq(qCactAccountAddi.acctSeq)).fetchOne();
			if(acctSeq == null){
				ProductAccount init = null;
				String acctParamId=null;
				if(totalPeriod !=null){
					Deadline line = null;
					if (totalPeriod <= 12) {
						line = Deadline.S;
					} else {
						line = Deadline.M;
					}
					
					init = parameterFacility.getParameter(ProductAccount.class,ProductAccount.key(productId, line));
				}
				if(init !=null){
					acctParamId = init.getAccountId();
				}
				acc = new AccountBean();
				acc.setAcctParamId(acctParamId);
				acc.setIouNo(iouNo);
				acc.setProductId(productId);
				acc.setAcctNo(acctNo);
				acc.setCustId(custId);
				acc.setTotalPeriod(totalPeriod);
			}
		}
		return acc;
	}
	
	@Transactional
	public void insertAccount(List<AccountBean> list,Date bizDate){
		
		if(list.get(0).getAcctNo() == null){
			
			CactAccountNo accountNo = new CactAccountNo();
			accountNo.setOrg(provider4Organization.getCurrentOrganizationId());
			accountNo.setBranchNo(provider4Organization.getCurrentOrganizationId());
			accountNo.setCustId(list.get(0).getCustId());
			accountNo.setBizDate(bizDate);
			accountNo.fillDefaultValues();
			em.persist(accountNo);
			
			for(AccountBean ab : list){
				// 新增数据到cactAccount
				CactAccount acctFinal = new CactAccount();
				acctFinal.setOrg(provider4Organization.getCurrentOrganizationId());
				acctFinal.setBranchNo(provider4Organization.getCurrentOrganizationId());
				acctFinal.setBusinessType(BusinessType.BL);
				acctFinal.setAcctNo(accountNo.getAcctNo());
				acctFinal.setTotalLoanPeriod(ab.getTotalPeriod());
				acctFinal.setBizDate(bizDate);
				acctFinal.setAcctParamId(ab.getAcctParamId());
				acctFinal.fillDefaultValues();
				em.persist(acctFinal);
				
				QCactAccount qCactAccount = QCactAccount.cactAccount;
				QCactAccountAddi qCactAccountAddi = QCactAccountAddi.cactAccountAddi;
				Integer acctSeq = new JPAQueryFactory(em).select(qCactAccount.acctSeq).from(qCactAccountAddi,qCactAccount)
						.where(qCactAccountAddi.iouNo.eq(ab.getIouNo()),qCactAccount.acctSeq.eq(qCactAccountAddi.acctSeq)).fetchOne();
				if(acctSeq == null){
					// 新增数据到 CactAccountAddi
					CactAccountAddi addi = new CactAccountAddi();
					addi.setAcctSeq(acctFinal.getAcctSeq());
					addi.setOrg(provider4Organization.getCurrentOrganizationId());
					addi.setBranchNo(provider4Organization.getCurrentOrganizationId());
					addi.setIouNo(ab.getIouNo());
					addi.setProductNo(ab.getProductId());
					addi.setBizDate(bizDate);
					addi.fillDefaultValues();
					em.persist(addi);
				}
			}
			
		}else{
			
			for(AccountBean ab : list){
				// 新增数据到cactAccount
				CactAccount acctFinal = new CactAccount();
				acctFinal.setOrg(provider4Organization.getCurrentOrganizationId());
				acctFinal.setBranchNo(provider4Organization.getCurrentOrganizationId());
				acctFinal.setBusinessType(BusinessType.BL);
				acctFinal.setAcctNo(ab.getAcctNo());
				acctFinal.setTotalLoanPeriod(ab.getTotalPeriod());
				acctFinal.setBizDate(bizDate);
				acctFinal.setAcctParamId(ab.getAcctParamId());
				acctFinal.fillDefaultValues();
				em.persist(acctFinal);
				
				QCactAccount qCactAccount = QCactAccount.cactAccount;
				QCactAccountAddi qCactAccountAddi = QCactAccountAddi.cactAccountAddi;
				Integer acctSeq = new JPAQueryFactory(em).select(qCactAccount.acctSeq).from(qCactAccountAddi,qCactAccount)
						.where(qCactAccountAddi.iouNo.eq(ab.getIouNo()),qCactAccount.acctSeq.eq(qCactAccountAddi.acctSeq)).fetchOne();
				if(acctSeq == null){
					// 新增数据到 CactAccountAddi
					CactAccountAddi addi = new CactAccountAddi();
					addi.setAcctSeq(acctFinal.getAcctSeq());
					addi.setOrg(provider4Organization.getCurrentOrganizationId());
					addi.setBranchNo(provider4Organization.getCurrentOrganizationId());
					addi.setIouNo(ab.getIouNo());
					addi.setProductNo(ab.getProductId());
					addi.setBizDate(bizDate);
					addi.fillDefaultValues();
					em.persist(addi);
				}
				
			}
			
		}
	}

}
