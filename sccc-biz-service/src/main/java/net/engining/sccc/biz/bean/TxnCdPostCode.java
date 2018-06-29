package net.engining.sccc.biz.bean;

import java.io.Serializable;

import net.engining.pcx.cc.param.model.enums.SysTxnCd;
import net.engining.pg.support.meta.PropertyInfo;

public class TxnCdPostCode implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 外部交易码
     */
    @PropertyInfo(name="外部交易码", length=40)
    public SysTxnCd txnCd;
    
    /**
     * 入账交易码
     */
    @PropertyInfo(name="入账交易码", length=40)
    public String postCode;
    
    
//    public String getKey() {
//    	return key(new TxnRule(null,null,null,null));
//    }
    
    public static String key(TxnRule tr) {
    	return tr.toString();
    }

}
