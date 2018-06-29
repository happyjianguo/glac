package net.engining.sccc.mgm.sao.imp;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.Tuple;

import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.biz.bean.HistoricalSubjectDetail;
import net.engining.sccc.biz.bean.SubjectDetailForm;
import net.engining.sccc.mgm.sao.HistoricalSubjectDetailSao;

@Component
public class HistoricalSubjectDetailSaoImp implements HistoricalSubjectDetailSao{

	@Override
	public List<HistoricalSubjectDetail> doHistoricalSubjectListDetail(SubjectDetailForm subjectDetail) {
		throw new ErrorMessageException(ErrorCode.SystemError, "系统繁忙，请稍后再试");
	}

}
