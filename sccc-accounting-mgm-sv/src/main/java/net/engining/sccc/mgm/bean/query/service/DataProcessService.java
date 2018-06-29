package net.engining.sccc.mgm.bean.query.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.engining.pg.support.db.querydsl.Range;
import net.engining.sccc.biz.bean.FetchDataProcess;
@Service
public class DataProcessService<T> {
/**
 * 为了返回前端分页
 * @param range
 * @param txnHstQuery
 * @return
 */
	public FetchDataProcess<T> getDataProgress(Range range,List<T> txnHstQuery){
		int start=range.getStart();
		int end = range.getStart()+range.getLength();
		List<T> alist = new ArrayList<T>();
		if(txnHstQuery.size()>end){
			for(int i=start;i<end;i++){
				alist.add(txnHstQuery.get(i));
			}
		}else{
			for(int i=start;i<txnHstQuery.size();i++){
				alist.add(txnHstQuery.get(i));
			}
		}
		
		
		FetchDataProcess<T> fetchDataProcess = new FetchDataProcess<T>();
		fetchDataProcess.setData(alist);
		fetchDataProcess.setRowCount(txnHstQuery.size());
	return fetchDataProcess;
	}
}
