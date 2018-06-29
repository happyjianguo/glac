package net.engining.sccc.init.param.sccc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.gm.infrastructure.enums.AgeGroupCd;
import net.engining.gm.infrastructure.enums.BusinessType;
import net.engining.pcx.cc.param.model.TxnSubjectMapping;
import net.engining.pcx.cc.param.model.TxnSubjectParam;
import net.engining.pcx.cc.param.model.enums.SubjectAmtType;
import net.engining.pcx.cc.param.model.enums.RedBlueInd;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.init.ParameterInitializer;
@Service
public class TxnSubjectParamInit implements ParameterInitializer{
	@Autowired
	private ParameterFacility facility;
	
	@Override
	public void init() throws Exception {
		for(String s : facility.getParameterMap(TxnSubjectParam.class).keySet()){
			facility.removeParameter(TxnSubjectParam.class, s);
		}
		
		for(TxnSubjectParam p : getSubjectList()){
			facility.addParameter(p.getKey(),p);
		}
		
	}
	
	public List<TxnSubjectParam> getSubjectList(){
		List<TxnSubjectParam> list = new ArrayList<TxnSubjectParam>();
		
		//额度增加
		TxnSubjectParam tsp = new TxnSubjectParam();
		TxnSubjectMapping tsm = new TxnSubjectMapping();
		tsp.txnCd = "SC0001";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm.ntDbSubjectCdOs = "7006";//收
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ADDLIMIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//额度减少
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0002";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//付
		tsm.ntCrSubjectCdOs = "7006";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.SUBLIMIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款发放-短期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0003";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "3001";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "7006";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LOAN;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "3001";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "7006";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULOAN;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款发放-短期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0004";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "3001";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "7006";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LOAN;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款发放-中长期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0005";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "3001";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "7006";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LOAN;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "3001";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "7006";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULOAN;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款发放-中长期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0006";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "3001";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "7006";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LOAN;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-短期-M0
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0007";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-短期-M0-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0414";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "60110101";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-短期-M1-M3
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0400";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110401";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);

		//利息计提-短期-M1-M3-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0415";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "60110401";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-短期-M3以上
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0008";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//收
		tsm.ntDbSubjectCdOs = "700301";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-短期-M3以上-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0416";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//收
		tsm.ntDbSubjectCdOs = "700301";
		tsm.ntDbRedFlagOs = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-中长期-M0
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0009";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-中长期-M0-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0417";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-中长期-M0_M3
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0401";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110402";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-中长期-M0_M3-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0418";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "60110402";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-中长期-M3以上
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0010";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//收
		tsm.ntDbSubjectCdOs = "700302";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//利息计提-中长期-M3以上-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0419";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//收
		tsm.ntDbSubjectCdOs = "700302";
		tsm.ntDbRedFlagOs = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//罚息计收-短期-M3（含）以下
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0011";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Under4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "11249901";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "11249901";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//罚息计收-短期-M3（含）以下-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0420";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Under4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "11249901";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "11249901";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//罚息计收-短期-M3以上
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0012";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//收
		tsm.ntDbSubjectCdOs = "701801";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//罚息计收-短期-M3以上-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0421";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//收
		tsm.ntDbSubjectCdOs = "701801";
		tsm.ntDbRedFlagOs = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//罚息计收-中长期-M3(含)以下
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0013";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Under4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "11249902";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "11249902";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//罚息计收-中长期-M3(含)以下-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0422";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Under4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "11249902";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "11249902";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//罚息计收-中长期-M3以上
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0014";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//收
		tsm.ntDbSubjectCdOs = "701802";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//罚息计收-中长期-M3以上-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0423";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//收
		tsm.ntDbSubjectCdOs = "701802";
		tsm.ntDbRedFlagOs = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0015";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080101";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0424";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080101";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "130301";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0016";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0425";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080201";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "13080101";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0017";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Suspicious;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0426";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Suspicious;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080301";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "13080201";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0018";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Loss;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080401";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0427";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Loss;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080401";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "13080301";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0019";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0428";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080102";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "130302";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0020";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080202";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0429";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080202";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "13080102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0021";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Suspicious;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0430";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Suspicious;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080302";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "13080202";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0022";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Loss;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080402";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0431";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Loss;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080402";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "13080302";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期后利息结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0023";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Under4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期后利息结转-短期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0432";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Under4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113201";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "113301";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期后利息结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0024";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113201";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "60110101";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.ntDbSubjectCdOs = "700301";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "113201";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "60110401";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.ntDbSubjectCdOs = "700301";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.Attention_RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113201";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期后利息结转-短期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0433";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "700301";
		tsm.ntDbRedFlagOs = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "113201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110401";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "700301";
		tsm.ntDbRedFlagOs = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.Attention_RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期后利息结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0025";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Under4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113202";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期后利息结转-中长期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0434";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Under4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113202";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "113302";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期后利息结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0026";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113202";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.ntDbSubjectCdOs = "700302";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113202";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "60110402";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.ntDbSubjectCdOs = "700302";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.Attention_RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113202";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期后利息结转-中长期-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0435";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113202";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "700302";
		tsm.ntDbRedFlagOs = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113202";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110402";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "700302";
		tsm.ntDbRedFlagOs = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.Attention_RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113202";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//溢缴款处理-发生
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0027";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22410501";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PAYM;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//溢缴款处理-撤回
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0028";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "22410501";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "3001";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PAYM;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//提前还款手续费处理
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0029";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "630105";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.SFEE;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0030";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULBAL;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0031";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "11249901";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULBAL;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0032";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULBAL;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "601201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0033";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Suspicious;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULBAL;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "601202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0034";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Loss;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080401";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULBAL;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "601203";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0035";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0036";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "11249901";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0037";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm = new TxnSubjectMapping();//收
		tsm.amtType = SubjectAmtType.LBAL;
		tsm.ntDbSubjectCdOs = "7006";
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "601201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0038";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Suspicious;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "601202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0039";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Loss;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080401";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "601203";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
			
		//贷款归还-中长期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0040";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULBAL;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0041";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "11249901";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULBAL;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0042";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULBAL;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0043";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Suspicious;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULBAL;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-联合
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0044";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Loss;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080402";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ULBAL;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0045";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130300";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0046";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "11249902";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.ACCRUED_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0047";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0048";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Suspicious;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-独立
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0049";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Loss;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080402";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_INTEREST;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.INTE_OTAX;
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.PNIT_OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//短期销项税
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0050";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "60110101";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//短期销项税
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0402";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "60110401";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//中长期销项税
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0051";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "60110102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//中长期销项税
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0403";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "60110402";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//短期逾期后罚息结转
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0052";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "11249901";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.ntDbSubjectCdOs = "701801";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "11249901";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//短期逾期后罚息结转-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0436";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "11249901";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "701801";
		tsm.ntDbRedFlagOs = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "11249901";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//中长期逾期后罚息结转
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0053";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "11249902";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.ntDbSubjectCdOs = "701802";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "11249902";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//中长期逾期后罚息结转-冲正
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0437";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Above4M3;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "11249902";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "701802";
		tsm.ntDbRedFlagOs = RedBlueInd.R;
		tsm.amtType = SubjectAmtType.RECEIVABLE_PNIT;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "11249902";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0404";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "11249901";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0405";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "11249902";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0406";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0407";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0408";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Suspicious;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0409";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Loss;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080401";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0410";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Attention;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0411";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Secondary;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0412";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Suspicious;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC0413";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Loss;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080402";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.LBAL;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//拿去花
		//额度增加
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC050";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCdOs = "7006";//收
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//额度减少
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC051";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//付
		tsm.ntCrSubjectCdOs = "7006";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);

		//贷款发放-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC054";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "3001";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "7006";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC055";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC056";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC057";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC058";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC059";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080401";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCdOs = "7006";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-分期手续费还款
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC052";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60210105";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-违约金还款
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC053";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "630105";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//转分期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC060";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//中银
		//贷款发放
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC001";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款发放
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC018";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "130302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//计提利息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC002";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//计提利息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC003";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm.ntDbSubjectCdOs = "700301";//收
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//计提利息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC019";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//计提利息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC020";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm.ntDbSubjectCdOs = "700302";//收
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC005";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080101";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC006";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC007";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC008";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080401";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC022";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC023";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080202";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC024";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期本金结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC025";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "13080402";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期后利息结转-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC009";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "60110101";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.ntDbSubjectCdOs = "700301";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113301";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.ntDbSubjectCdOs = "700301";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//逾期后利息结转-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC026";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.ntDbSubjectCdOs = "700302";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "113302";
		tsm.ntDbRedFlag = RedBlueInd.R;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.R;
		tsm.ntDbSubjectCdOs = "700302";
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC010";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC011";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC012";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC013";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC014";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080401";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-利息还款
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC015";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-利息还款
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC016";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-短期-罚息还款
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC017";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010601";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC027";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "130302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC028";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC029";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080202";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC030";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC031";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080402";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-利息还款
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC032";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-利息还款
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC033";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60110102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-中长期-罚息还款
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC034";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "63010602";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//贷款归还-收取其他费用还款
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC035";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "60210199";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//贷
		tsm.ntDbSubjectCd = "122102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22211102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//息费减免-短期-减免利息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC037";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "67119901";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113301";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "67119901";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntDbSubjectCd = "22211102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//息费减免-短期-减免利息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC038";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//付
		tsm.ntCrSubjectCdOs = "700301";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//息费减免-短期-减免罚息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC039";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//付
		tsm.ntCrSubjectCdOs = "701801";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//息费减免-中长期-减免利息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC040";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "67119901";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "22211102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "113302";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//息费减免-中长期-减免利息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC041";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//付
		tsm.ntCrSubjectCdOs = "700302";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//息费减免-中长期-减免罚息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC042";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//付
		tsm.ntCrSubjectCdOs = "701802";
		tsm.ntCrRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//息费返还-返还利息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC043";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "67119901";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "22211102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//息费返还-返回罚息
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC044";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "67119902";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "22211102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//息费返还-返还其他费用
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC036";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "67119903";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "22211102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsm.amtType = SubjectAmtType.OTAX;
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//短期罚息计收
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC004";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCdOs = "701801";//收
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//中长期罚息计收
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC021";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();
		tsm.ntDbSubjectCdOs = "701802";//收
		tsm.ntDbRedFlagOs = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//短期关注类贷款转正常
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC045";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "130301";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//中长期关注类贷款转正常
		tsp = new TxnSubjectParam();
		tsp.txnCd = "SC046";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "130302";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "13080102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		//商户清算
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0001";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "100201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0002";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "100201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "3001";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0003";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "224102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "100201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0004";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "100201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "122102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0005";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "224102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "100201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0006";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "100201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "122102";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0007";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0008";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "224102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0009";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "220201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "224101";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0010";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "224102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "100201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0011";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "224102";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "100201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0012";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "224101";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "100201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0013";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "12210401";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "3001";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0014";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "3001";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "22410501";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0015";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "100201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "12210401";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0016";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "22410501";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "100201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0017";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "100201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "12210401";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0018";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "22410501";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "100201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0019";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "220201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "100201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		tsp = new TxnSubjectParam();
		tsp.txnCd = "S0020";
		tsp.businessType = BusinessType.BL;
		tsp.ageGroupCd = AgeGroupCd.Normality;
		tsm = new TxnSubjectMapping();//借
		tsm.ntDbSubjectCd = "220201";
		tsm.ntDbRedFlag = RedBlueInd.N;
		tsm.ntCrSubjectCd = "100201";
		tsm.ntCrRedFlag = RedBlueInd.N;
		tsp.entryList = new ArrayList<TxnSubjectMapping>();
		tsp.entryList.add(tsm);
		list.add(tsp);
		
		
//		//商户清算
//		//商户清算资金划款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0001";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "3001";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "100201";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//商户清算资金划款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0002";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "100201";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "3001";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//联合贷参与方清算资金划款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0003";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "224102";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "100202";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//联合贷参与方清算资金划款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0004";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "100201";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "122102";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//联合贷代理方清算资金划款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0005";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "224102";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "100201";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//联合贷代理方清算资金划款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0006";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "100201";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "122102";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//商户清算资金暂缓划付
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0007";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "3001";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "224101";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//商户清算资金暂缓划付
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0008";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "224102";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "224101";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//商户清算资金暂缓划付
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0009";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "220201";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "224101";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//商户清算资金重新发起划付
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0010";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "224101";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "100201";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//清算短款挂账
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0011";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "12210401";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "3001";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//清算长款挂账
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0012";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "3001";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "22410501";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//商户请款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0013";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "100201";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "12210401";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//客户请款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0014";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "130301";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "12210401";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//付
//		tsm.ntCrSubjectCdOs = "7006";
//		tsm.ntCrRedFlagOs = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//客户请款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0015";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "130302";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "12210401";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//付
//		tsm.ntCrSubjectCdOs = "7006";
//		tsm.ntCrRedFlagOs = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//客户请款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0016";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "130301";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "122102";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "12210401";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//付
//		tsm.ntCrSubjectCdOs = "7006";
//		tsm.ntCrRedFlagOs = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//客户请款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0017";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "130302";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "122102";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "12210401";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//付
//		tsm.ntCrSubjectCdOs = "7006";
//		tsm.ntCrRedFlagOs = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0018";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.N;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "100201";
//		tsm.ntCrRedFlag = RedBlueInd.N;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0019";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "130301";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "113301";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "60110101";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "22211102";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//付
//		tsm.ntCrSubjectCdOs = "7006";
//		tsm.ntCrRedFlagOs = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0020";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "130302";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "113302";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "60110102";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "22211102";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//付
//		tsm.ntCrSubjectCdOs = "7006";
//		tsm.ntCrRedFlagOs = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0021";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "130301";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "122102";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "60110101";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "22211102";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//付
//		tsm.ntCrSubjectCdOs = "7006";
//		tsm.ntCrRedFlagOs = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0022";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "130302";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "122102";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "60110102";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "22211102";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//付
//		tsm.ntCrSubjectCdOs = "7006";
//		tsm.ntCrRedFlagOs = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//		
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0023";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "100201";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户请款
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0024";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "100201";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "12210401";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0025";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "100201";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0026";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "130301";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0027";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Attention;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "13080101";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0028";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Secondary;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "13080201";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0029";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Suspicious;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "13080301";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0030";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Loss;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "13080401";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0031";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "130302";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0032";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Attention;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "13080102";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0033";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Secondary;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "13080202";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0034";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Suspicious;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "13080302";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户退单
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0035";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Loss;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "22410501";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "13080402";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//客户分润款项划付
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0036";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "220201";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "100201";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
//
//		//支付通路结算手续费款项划付
//		tsp = new TxnSubjectParam();
//		tsp.txnCd = "Q0037";
//		tsp.businessType = BusinessType.BL;
//		tsp.ageGroupCd = AgeGroupCd.Normality;
//		tsm = new TxnSubjectMapping();//借
//		tsm.ntDbSubjectCd = "220201";
//		tsm.ntDbRedFlag = RedBlueInd.R;
//		tsp.entryList = new ArrayList<TxnSubjectMapping>();
//		tsp.entryList.add(tsm);
//		tsm = new TxnSubjectMapping();//贷
//		tsm.ntCrSubjectCd = "100201";
//		tsm.ntCrRedFlag = RedBlueInd.R;
//		tsp.entryList.add(tsm);
//		list.add(tsp);
		
		
		
		return list;
	}
}
