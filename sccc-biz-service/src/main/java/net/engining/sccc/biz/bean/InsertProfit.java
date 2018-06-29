package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class InsertProfit implements Serializable{

	/**
	 * 结转提交
	 */
	private static final long serialVersionUID = 1L;

	private List<Map<String, Object>> profit;

	public List<Map<String, Object>> getProfit() {
		return profit;
	}

	public void setProfit(List<Map<String, Object>> profit) {
		this.profit = profit;
	}
	
}
