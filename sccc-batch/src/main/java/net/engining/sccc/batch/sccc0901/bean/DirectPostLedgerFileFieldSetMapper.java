package net.engining.sccc.batch.sccc0901.bean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;

import net.engining.pcx.cc.param.model.PostCode;
import net.engining.pcx.cc.param.model.TxnSubjectParam;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.sccc.batch.infrastructure.ExtFlatFileItemReader;

/**
 * 直接入账的Flat File 字段映射处理；字段赋值到Bean，以及业务校验处理；
 * 
 * @author luxue
 *
 */
public class DirectPostLedgerFileFieldSetMapper implements FieldSetMapper<DirectPostLedgerFileInfo> {
	private static final Logger logger = LoggerFactory.getLogger(DirectPostLedgerFileFieldSetMapper.class);

	@Autowired
	private ParameterFacility parameterFacility;

	@Override
	public DirectPostLedgerFileInfo mapFieldSet(FieldSet fieldSet) {

		for (int i = 0; i < fieldSet.getValues().length; i++) {
			String value = fieldSet.getValues()[i];
			if (StringUtils.isBlank(value)) {
				throw new ErrorMessageException(ErrorCode.CheckError,
						String.format("文件中字段[%s]为空字段，断批处理", fieldSet.getNames()[i]));
			}
		}

		// 校验 postCode 有效性
		String postCode = fieldSet.readString("postCode");
		PostCode PostCode = parameterFacility.getParameter(PostCode.class, postCode);
		if (PostCode == null) {
			// 没配参数则不处理
			throw new ErrorMessageException(ErrorCode.Null, "入账交易码参数没有配置！");
		}

		// 金额校验
		BigDecimal amt = fieldSet.readBigDecimal("postAmt");
		if (amt.signum() == 0) {
			logger.warn("金额为0，不用处理！");
		}

		// 清算日期
//		Date date = fieldSet.readDate("clearDate", "YYYYMMDD");
		
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		String str = fieldSet.readString("clearDate");
		Date date = null;
		try {
			date = f.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DirectPostLedgerFileInfo proInfo = new DirectPostLedgerFileInfo();

		proInfo.setPostCode(postCode);
		proInfo.setPostAmt(amt);
		proInfo.setPostCode(fieldSet.readString("postCode"));
		proInfo.setClearDate(date);
		proInfo.setProdId(fieldSet.readString("prodId"));
		proInfo.setChanneId(fieldSet.readString("channeId"));

		return proInfo;

	}
}
