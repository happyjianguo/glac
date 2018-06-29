package net.engining.sccc.biz.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import net.engining.pcx.cc.infrastructure.shared.model.ApGlTxn;

@Service
public class ApGlTxnService {
	
	@PersistenceContext
	private EntityManager em;
	
	public void apGlTxnAdd(ApGlTxn apGlTxn) {
		
		 em.persist(apGlTxn);
		
	}

}
