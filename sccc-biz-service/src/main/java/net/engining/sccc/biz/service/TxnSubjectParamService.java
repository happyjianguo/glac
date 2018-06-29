package net.engining.sccc.biz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.engining.pcx.cc.param.model.Account;
import net.engining.pcx.cc.param.model.PostCode;
import net.engining.pcx.cc.param.model.ProductPost;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.TxnPostCode;
import net.engining.pcx.cc.param.model.TxnSubjectParam;
import net.engining.pcx.cc.param.model.enums.AdjustIndicator;
import net.engining.pcx.cc.param.model.enums.Deadline;
import net.engining.pcx.cc.param.model.enums.PostProcessor;
import net.engining.pcx.cc.param.model.enums.SysTxnCd;
import net.engining.pcx.cc.process.service.support.Provider7x24;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.parameter.entity.model.ParameterSeqence;
import net.engining.sccc.biz.bean.PostCodeInsertOneRes;
import net.engining.sccc.biz.bean.TransactionScene;
import net.engining.sccc.biz.bean.TxnSubjectParamInsertReq;
import net.engining.sccc.biz.bean.TxnSubjectParamReq;
import net.engining.sccc.biz.bean.TxnSubjectParamRes;
import net.engining.sccc.biz.service.params.ProductAccount;

@Service
public class TxnSubjectParamService {
	@Autowired
	private ParameterFacility facility;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Provider7x24 provider7x24;

	public List<TxnSubjectParamRes> txnSubjectParamRecord(String productCd, Deadline deadline) {
		
		List<ProductAccount> list = new ArrayList<ProductAccount>();
		// 获取所有的产品套型
		for (ProductAccount pa : facility.getParameterMap(ProductAccount.class).values()) {
			// 期限不为空
			if (StringUtils.isNoneBlank(productCd) && deadline != null) {
				// 找到符合查询条件的 产品
				if (pa.getProductId().equals(productCd) && pa.getDeadline().equals(deadline)) {
					list.add(pa);
				}
			}
			// 期限为空
			else if (StringUtils.isNoneBlank(productCd) && deadline == null) {
				// 找到符合查询条件的 产品
				if (pa.getProductId().equals(productCd)) {
					list.add(pa);
				}
			}
		}

		return getTxnSubjectParamRes(list);
	}
	
	private List<TxnSubjectParamRes> getTxnSubjectParamRes(List<ProductAccount> list) {

		List<TxnSubjectParamRes> txnList = new ArrayList<TxnSubjectParamRes>();

		for (ProductAccount pa : list) {
			//判断账户是否为空  不为空jqh 为空zy
			if(StringUtils.isBlank(pa.getAccountId())){
				ProductPost pp = facility.getParameter(ProductPost.class, pa.getProductId());
				txnList.addAll(getTxnSubjectParam(pp.postList, null));
			}else{
				
				Account account = facility.getParameter(Account.class, pa.getAccountId());
				// 获取postCode
				Map<SysTxnCd, String> map = account.sysTxnCdMapping;
				List<String> postCodeList = new ArrayList<String>();
				for (String post : map.values()) {
					postCodeList.add(post);
				}
				// 获取PostCode对应的名称与套型 放到返回的Bean中
				txnList.addAll(getTxnSubjectParam(postCodeList, pa.getDeadline().toString()));
			}
		}
		return txnList;
	}

	/**
	 * 获取PostCode对应的名称与套型
	 * 
	 * @param postCodeList
	 * @return
	 */
	private List<TxnSubjectParamRes> getTxnSubjectParam(List<String> postCodeList, String deadline) {

		List<TxnSubjectParamRes> txnSubjectParamResList = new ArrayList<TxnSubjectParamRes>();
		// 获取所有套型
		for (TxnSubjectParam tsp : facility.getParameterMap(TxnSubjectParam.class).values()) {
			// 判断套型的PostCode（txnCd）是否存在于 所需要的postCodeList中
			if (postCodeList.contains(tsp.txnCd)) {
				TxnSubjectParamRes tspr = new TxnSubjectParamRes();
				// 添加postCode
				tspr.setPostCode(tsp.txnCd);
				// 获取postCode对应的中文翻译
				tspr.setPostName(facility.getParameter(PostCode.class, tsp.txnCd).shortDesc);
				// 添加套型
				tspr.setTxnSubjectParam(tsp);
				tspr.setDeadline(deadline);

				txnSubjectParamResList.add(tspr);
			}
		}
		return txnSubjectParamResList;
	}

	
	public void txnSubjectParamInsert(TxnSubjectParamInsertReq tspir) {

		//判断新产品是否存在 或 新产品是否与之前产品一样
		if (tspir.getNewProductCd() == null) {
			update(tspir);
		} else if(tspir.getProductCd().equals(tspir.getNewProductCd().trim())){
			update(tspir);
		}else{
			insert(tspir);
		}

	}
	
	/**
	 * 更新套型
	 * @param tspir
	 */
	private void update(TxnSubjectParamInsertReq tspir){
		ProductAccount pa = facility.getParameter(ProductAccount.class, ProductAccount.key(tspir.getProductCd(), tspir.getDeadline()));
		
		//判断是jqh还是zy
		if(StringUtils.isBlank(pa.getAccountId())){
			updateZy(tspir);
		}else{
			updateJQH(tspir,pa.getAccountId());
		}
		
	}
	
	private void updateZy(TxnSubjectParamInsertReq tspir){
		List<String> postList = new ArrayList<String>();
		//更新 zy 产品与PostCode对应参数
		ProductPost pp = facility.getParameter(ProductPost.class, tspir.getProductCd());
		for(TransactionScene ts : tspir.getTransList()){
			//判断 给过来的oldPostCode 是否 存在与 原有产品中
			if(pp.postList.contains(ts.getPostCode())){
				//更新套型
				TxnSubjectParam tsp = ts.getTxnSubjectParam();
				facility.updateParameter(tsp.getKey(),tsp);
				//记录PostCode
				postList.add(tsp.txnCd);
			}else{
				//记录PostCode
				postList.add(ts.getNewPostCode());
				
				//添加PostCode 添加套型
				insertPostCodeTxnSubject(ts);
			}
		}
		//更新 ProductPost
		pp.postList = postList;
		facility.updateParameter(tspir.getProductCd(), pp);
		
		
	}
	private void updateJQH(TxnSubjectParamInsertReq tspir,String accountId){
		List<String> postList = new ArrayList<String>();
		//获取 Mapping
		Account acc = facility.getParameter(Account.class, accountId);
		Map<SysTxnCd,String> sysMap = acc.sysTxnCdMapping;
		//获取所有TxnCdPostCode 通过PostCode找到TxnCd
		Map<String,TxnPostCode> txnMap = facility.getParameterMap(TxnPostCode.class);
		//定义一个新的 Mapping
		Map<SysTxnCd,String> newMap = new HashMap<SysTxnCd,String>();
		for(TransactionScene ts : tspir.getTransList()){
			//判断 给过来的oldPostCode 是否 存在与 原有产品中
			if(sysMap.containsValue(ts.getPostCode())){
				//更新套型
				TxnSubjectParam tsp = ts.getTxnSubjectParam();
				facility.updateParameter(tsp.getKey(),tsp);
				//记录PostCode
				postList.add(tsp.txnCd);
			}else{
				//记录PostCode
				postList.add(ts.getNewPostCode());
				
				//添加TxnPostCode
				for(TxnPostCode tp : txnMap.values()){
					if(tp.postCode.equals(ts.getPostCode())){
						TxnPostCode txnPost = new TxnPostCode();
						txnPost.txnCd = tp.txnCd;
						txnPost.postCode = ts.getNewPostCode();
						facility.addParameter(txnPost.getKey(), txnPost);
						//Mapping 添加 新 的 txnCd，PostCode
						newMap.put(tp.txnCd, ts.getNewPostCode());
					}
				}
				
				//添加PostCode 添加套型
				insertPostCodeTxnSubject(ts);
			}
		}
		
		//Mapping 添加 原有 的 txnCd，PostCode
		for(TxnPostCode tp : txnMap.values()){
			if(postList.contains(tp.postCode)){
				newMap.put(tp.txnCd, tp.postCode);
			}
		}
		
		//将新的Mapping存入 Account 中
		acc.sysTxnCdMapping = newMap;
		//更新Account
		facility.removeParameter(Account.class, acc.paramId);
		facility.addParameter(acc.paramId,acc);
	}
	
	/**
	 * 添加PostCode 
	 * 添加套型
	 * @param ts
	 */
	private void insertPostCodeTxnSubject(TransactionScene ts){
		//添加PostCode
		PostCode pc = new PostCode();
		pc.postCode = ts.getNewPostCode();
		pc.description = ts.getPostName();
		pc.shortDesc = ts.getPostName();
		pc.blkcdCheckInd = Boolean.TRUE;
		pc.stmtInd = Boolean.TRUE;
		pc.adjustInd = AdjustIndicator.B;
		pc.processor = PostProcessor.DN;
		pc.subAcctType = "";
		pc.isReversal = false;
		pc.isAccounting = true;
		facility.addParameter(pc.postCode,pc);
		
		//添加套型
		TxnSubjectParam tsp = new TxnSubjectParam();
		tsp = ts.getTxnSubjectParam();
		tsp.txnCd = ts.getNewPostCode();
		facility.addParameter(tsp.getKey(), tsp);
	}
	
	/**
	 * 添加套型
	 * @param tspir
	 */
	private void insert(TxnSubjectParamInsertReq tspir){
		
		ProductAccount pa = facility.getParameter(ProductAccount.class, ProductAccount.key(tspir.getProductCd(), tspir.getDeadline()));
		if(StringUtils.isBlank(pa.getAccountId())){
			insertZy(tspir);
		}else{
			insertJqh(tspir);
		}
	}
	
	/**
	 * 添加 类似中银产品的套型
	 * @param tspir
	 */
	private void insertZy(TxnSubjectParamInsertReq tspir){
		//添加产品
		ProductAccount pa = new ProductAccount();
		pa.setProductId(tspir.getNewProductCd());
		pa.setDeadline(Deadline.S);
		facility.addParameter(pa.getKey(), pa);
		
		//添加ProductPost
		ProductPost pp = new ProductPost();
		List<String> postList = new ArrayList<String>();
		for(TransactionScene ts : tspir.getTransList()){
			postList.add(ts.getNewPostCode());
		}
		pp.productId = tspir.getNewProductCd();
		pp.postList = postList;
		facility.addParameter(pp.productId,pp);
		
		//添加postCode
		insertPostCode(tspir.getTransList());
		
		//添加套型
		insertTxnSubjectParam(tspir.getTransList());
	}
	
	/**
	 * 添加类似借趣花产品的套型
	 * @param tspir
	 */
	private void insertJqh(TxnSubjectParamInsertReq tspir){
		//添加产品
		ProductAccount pa = new ProductAccount();
		pa.setProductId(tspir.getNewProductCd());
		pa.setDeadline(tspir.getDeadline());
		pa.setAccountId(tspir.getNewProductCd() + tspir.getDeadline());//账户：产品+期限
		facility.addParameter(pa.getKey(), pa);
		
		//添加账户
		ProductAccount oldPa = facility.getParameter(ProductAccount.class, ProductAccount.key(tspir.getProductCd(), tspir.getDeadline()));
		Account acc = facility.getParameter(Account.class, oldPa.getAccountId());
		acc.paramId = pa.getAccountId();
		Map<SysTxnCd,String> sysMap = new HashMap<SysTxnCd, String>();
		//获取所有TxnCdPostCode
		Map<String,TxnPostCode> txnPostMap = facility.getParameterMap(TxnPostCode.class);
		//循环找到PostCode所对应的TxnCd
		for(TxnPostCode tp : txnPostMap.values()){
			for(TransactionScene ts : tspir.getTransList()){
				if(ts.getPostCode().equals(tp.postCode)){
					sysMap.put(tp.txnCd, ts.getNewPostCode());
					
					//添加TxnPostCode
					TxnPostCode txnPost = new TxnPostCode();
					txnPost.txnCd = tp.txnCd;
					txnPost.postCode = ts.getNewPostCode();
					facility.addParameter(txnPost.getKey(), txnPost);
				}
			}
		}
		acc.sysTxnCdMapping = sysMap;
		facility.addParameter(acc.paramId, acc);
		
		
		//添加postCode
		insertPostCode(tspir.getTransList());
		
		//添加套型
		insertTxnSubjectParam(tspir.getTransList());
	}
	
	/**
	 * 添加新的PostCode
	 * @param newOldMap
	 */
	private void insertPostCode(List<TransactionScene> transList){
		for(TransactionScene ts : transList){
			PostCode pc = new PostCode();
			pc.postCode = ts.getNewPostCode();
			pc.description = ts.getPostName();
			pc.shortDesc = ts.getPostName();
			pc.blkcdCheckInd = Boolean.TRUE;
			pc.stmtInd = Boolean.TRUE;
			pc.adjustInd = AdjustIndicator.B;
			pc.processor = PostProcessor.DN;
			pc.subAcctType = "";
			pc.isReversal = false;
			pc.isAccounting = true;
			
			facility.addParameter(pc.postCode,pc);
		}
		
	}
	/**
	 * 添加新的套型
	 * @param list
	 * @param newOldMap
	 */
	private void insertTxnSubjectParam(List<TransactionScene> tsList){
		
		for(TransactionScene ts : tsList){
			TxnSubjectParam tsp = new TxnSubjectParam();
			tsp = ts.getTxnSubjectParam();
			tsp.txnCd = ts.getNewPostCode();
			facility.addParameter(tsp.getKey(), tsp);
		}
		
	}
	
	/**
	 * 复制套型时 获取新的PostCode
	 * @param sum
	 * @return
	 */
	@Transactional
	public List<String> postCodeInsert(int sum){
		List<String> list = new ArrayList<String>();
		
		ParameterSeqence parameterSeqence = em.find(ParameterSeqence.class, Subject.class.getCanonicalName());
		for(int i = 0 ; i < sum ; i++){
			//判断对象是否存在  不存在添加
			if(parameterSeqence == null){
				parameterSeqence = new ParameterSeqence();
				parameterSeqence.setParamName(Subject.class.getCanonicalName());
				parameterSeqence.setParamSeq(1);
				parameterSeqence.setBizDate(provider7x24.getCurrentDate().toDate());
				parameterSeqence.fillDefaultValues();
				list.add("POST00001");
				em.persist(parameterSeqence);
			}else{
				parameterSeqence.setBizDate(provider7x24.getCurrentDate().toDate());
				parameterSeqence.setParamSeq(parameterSeqence.getParamSeq() + 1);
				String parameter ="POST" + StringUtils.leftPad(parameterSeqence.getParamSeq().toString(), 5, "0");
				list.add(parameter);
			}
		}
		
		return list;
	}
	
	/**
	 * 单独添加 一个新的PostCode
	 * @param productId
	 * @return
	 */
	public PostCodeInsertOneRes postCodeInsertOne(TxnSubjectParamReq req){
		List<String> postList = new ArrayList<String>(); 
		//获取产品
		ProductAccount pa = facility.getParameter(ProductAccount.class, ProductAccount.key(req.getProductCd(), req.getDeadline()));
		//判断产品的账户 是否为空 
		if(pa.getAccountId() == null){
			//账户为空情况  获取类似zy的PostCode
			for(ProductPost tp : facility.getParameterMap(ProductPost.class).values()){
				if(!tp.productId.equals(req.getProductCd())){
					postList.addAll(tp.postList);
				}
			}
		}else{
			//账户不为空情况  获取类似jqh的PostCode
			for(Account acc : facility.getParameterMap(Account.class).values()){
				if(!acc.paramId.equals(pa.getAccountId())){
					for(String s : acc.sysTxnCdMapping.values()){
						postList.add(s);
					}
				}
			}
		}
		
		PostCodeInsertOneRes res = new PostCodeInsertOneRes();
		//获取新的PostCode
		String newPostCode = postCodeInsert(1).get(0);
		res.setNewPostCode(newPostCode);
		//获取PostCode所对应的套型
		res.setTspr(getTxnSubjectParam(postList,null));
		return res;
	}
	
}
