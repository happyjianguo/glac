package net.engining.sccc.init.param.sccc;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.param.model.ProductPost;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.init.ParameterInitializer;
@Service
public class ProductPostInit implements ParameterInitializer{
	
	@Autowired
	private ParameterFacility facility;

	@Override
	@Transactional
	public void init() throws Exception {
		for(String s : facility.getParameterMap(ProductPost.class).keySet()){
			facility.removeParameter(ProductPost.class, s);
		}
		
		for(ProductPost pp : getProductPostList()){
			facility.addParameter(pp.productId,pp);
		}
		
	}
	
	private List<ProductPost> getProductPostList(){
		List<ProductPost> ppList = new ArrayList<ProductPost>();
		
		ProductPost pp = new ProductPost();
		pp.productId="ZY01";
		List<String> list = new ArrayList<String>();
		list.add("SC001");
		list.add("SC018");
		list.add("SC002");
		list.add("SC003");
		list.add("SC019");
		list.add("SC020");
		list.add("SC005");
		list.add("SC006");
		list.add("SC007");
		list.add("SC008");
		list.add("SC022");
		list.add("SC023");
		list.add("SC024");
		list.add("SC025");
		list.add("SC009");
		list.add("SC026");
		list.add("SC010");
		list.add("SC011");
		list.add("SC012");
		list.add("SC013");
		list.add("SC014");
		list.add("SC015");
		list.add("SC016");
		list.add("SC017");
		list.add("SC027");
		list.add("SC028");
		list.add("SC029");
		list.add("SC030");
		list.add("SC031");
		list.add("SC032");
		list.add("SC033");
		list.add("SC034");
		list.add("SC035");
		list.add("SC037");
		list.add("SC038");
		list.add("SC039");
		list.add("SC040");
		list.add("SC041");
		list.add("SC042");
		list.add("SC043");
		list.add("SC044");
		list.add("SC036");
		list.add("SC004");
		list.add("SC021");
		list.add("SC045");
		list.add("SC046");
		pp.postList = list;
		ppList.add(pp);
		
		pp = new ProductPost();
		pp.productId="NQH01";
		list = new ArrayList<String>();
		list.add("SC050");
		list.add("SC051");
		list.add("SC054");
		list.add("SC005");
		list.add("SC006");
		list.add("SC007");
		list.add("SC008");
		list.add("SC055");
		list.add("SC056");
		list.add("SC057");
		list.add("SC058");
		list.add("SC059");
		list.add("SC052");
		list.add("SC053");
		list.add("SC060");
		pp.postList = list;
		ppList.add(pp);
		
		return ppList;
	}

}
