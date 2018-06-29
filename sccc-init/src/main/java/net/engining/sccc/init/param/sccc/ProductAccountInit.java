/**
 * 
 */
package net.engining.sccc.init.param.sccc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.engining.pcx.cc.param.model.SubAcctType;
import net.engining.pcx.cc.param.model.enums.Deadline;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.Provider4Organization;
import net.engining.pg.support.init.TableDataInitializer;
import net.engining.sccc.biz.service.params.ProductAccount;

/**
 * @author luxue
 *
 */
@Service
public class ProductAccountInit implements TableDataInitializer {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	Provider4Organization provider4Organization;

	@Autowired
	private ParameterFacility facility;
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.engining.pg.support.init.TableDataInitializer#init()
	 */
	@Override
	@Transactional
	public void init() throws Exception {

		for (ProductAccount sat : facility.getParameterMap(ProductAccount.class).values()) {
			facility.removeParameter(ProductAccount.class, sat.getKey());
		}

		for (ProductAccount sat : getProductAccountList()) {
			facility.addParameter(sat.getKey(), sat);
		}

	}

	private List<ProductAccount> getProductAccountList() {
		List<ProductAccount> list = new ArrayList<ProductAccount>();
		ProductAccount product = new ProductAccount();
		product.setProductId("CP0002");
		product.setDeadline(Deadline.S);
		product.setAccountId("JQH01");
		list.add(product);

		product = new ProductAccount();
		product.setProductId("CP0002");
		product.setDeadline(Deadline.M);
		product.setAccountId("JQH02");
		list.add(product);
		
		product = new ProductAccount();
		product.setProductId("ZY01");
		product.setDeadline(Deadline.S);
		list.add(product);
		
		return list;

	}

}
