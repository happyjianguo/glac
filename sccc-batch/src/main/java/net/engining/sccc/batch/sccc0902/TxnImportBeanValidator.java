package net.engining.sccc.batch.sccc0902;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

import net.engining.sccc.biz.bean.batchBean.EveryDayAccountingBean;

/**
 * @author luxue
 *
 */
public class TxnImportBeanValidator implements Validator<EveryDayAccountingBean>{

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.validator.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(EveryDayAccountingBean value) throws ValidationException {
		// TODO 交易字段业务检查
		throw  new ValidationException("");
	}

}
