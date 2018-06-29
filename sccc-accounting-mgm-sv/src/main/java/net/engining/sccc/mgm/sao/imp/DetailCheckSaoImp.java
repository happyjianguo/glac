package net.engining.sccc.mgm.sao.imp;

import java.util.List;

import org.springframework.stereotype.Component;

import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.biz.bean.DetailCheck;
import net.engining.sccc.mgm.sao.DetailCheckSao;

@Component
public class DetailCheckSaoImp implements DetailCheckSao{

	@Override
	public List<DetailCheck> doDetailCheck(List<DetailCheck> accounting) {
		throw new ErrorMessageException(ErrorCode.SystemError, "系统繁忙，请稍后再试");
	}

}
