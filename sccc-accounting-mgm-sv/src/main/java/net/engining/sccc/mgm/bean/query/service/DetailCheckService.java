package net.engining.sccc.mgm.bean.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.sccc.biz.bean.DetailCheck;
import net.engining.sccc.mgm.sao.DetailCheckSao;

@Service
public class DetailCheckService {

	@Autowired 
	private DetailCheckSao detailCheckSao;
	
	public List<DetailCheck> getDetailCheck(List<DetailCheck> check) {
		return detailCheckSao.doDetailCheck(check);
	}
}
