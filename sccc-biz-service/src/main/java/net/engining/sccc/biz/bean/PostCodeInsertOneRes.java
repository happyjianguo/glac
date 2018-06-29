package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.List;

public class PostCodeInsertOneRes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String newPostCode;
	private List<TxnSubjectParamRes> tspr;
	public String getNewPostCode() {
		return newPostCode;
	}
	public void setNewPostCode(String newPostCode) {
		this.newPostCode = newPostCode;
	}
	public List<TxnSubjectParamRes> getTspr() {
		return tspr;
	}
	public void setTspr(List<TxnSubjectParamRes> tspr) {
		this.tspr = tspr;
	}
	

}
