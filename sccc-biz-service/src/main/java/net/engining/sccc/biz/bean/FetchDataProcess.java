package net.engining.sccc.biz.bean;

import java.io.Serializable;
import java.util.List;

import net.engining.pg.web.BaseResponseBean;

public class FetchDataProcess<T> extends BaseResponseBean  implements Serializable{

	/**
	 * 历史会计分录查询
	 */
	private static final long serialVersionUID = 1L;
	private int rowCount;

	private List<T> data;
	
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	
}

