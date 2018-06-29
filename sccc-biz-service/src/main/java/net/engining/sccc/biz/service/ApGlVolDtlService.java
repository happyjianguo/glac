package net.engining.sccc.biz.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtl;

@Service
public class ApGlVolDtlService {
	@PersistenceContext
	private EntityManager em;

	public void apGlVolDtlAdd(ApGlVolDtl apGlVolDtl) {
		
		 em.persist(apGlVolDtl);
		
	}

}
