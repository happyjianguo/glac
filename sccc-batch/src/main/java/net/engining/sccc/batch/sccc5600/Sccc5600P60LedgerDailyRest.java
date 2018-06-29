package net.engining.sccc.batch.sccc5600;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.engining.pcx.cc.infrastructure.shared.model.ApGlBal;
import net.engining.pg.support.utils.DateUtilsExt;


/**
 * 总账每日重置
 * 
 * @author wanglidong
 *
 */
@Service
@StepScope
public class Sccc5600P60LedgerDailyRest implements ItemProcessor<ApGlBal, ApGlBal> {
	
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Override
	public ApGlBal process(ApGlBal item) throws Exception {
		item.setLastDbBal(item.getDbBal());
		item.setLastCrBal(item.getCrBal());

		Calendar cl = Calendar.getInstance();
		cl.setTime(batchDate);
		cl.add(Calendar.DAY_OF_MONTH, 1);

		if (DateUtilsExt.isFirstDayOfMonth(cl.getTime())) {// 每月更新余额和发生额
			item.setLastMthDbBal(item.getDbBal());
			item.setLastMthCrBal(item.getCrBal());

			item.setMtdDbAmt(BigDecimal.ZERO);
			item.setMtdCrAmt(BigDecimal.ZERO);

			if (DateUtilsExt.isFirstDayOfQuarter(cl.getTime())) {// 每季更新余额和发生额
				item.setLastQtrDbBal(item.getDbBal());
				item.setLastQtrCrBal(item.getCrBal());

				item.setQtdDbAmt(BigDecimal.ZERO);
				item.setQtdCrAmt(BigDecimal.ZERO);

				if (DateUtilsExt.isFirstDayOfYear(cl.getTime())) {// 每年更新余额和发生额
					item.setLastYrDbBal(item.getDbBal());
					item.setLastYrCrBal(item.getCrBal());

					item.setYtdDbAmt(BigDecimal.ZERO);
					item.setYtdCrAmt(BigDecimal.ZERO);
				}
			}
		}
		item.setDbAmt(BigDecimal.ZERO);
		item.setDbCount(0);
		item.setCrAmt(BigDecimal.ZERO);
		item.setCrCount(0);
		item.setBizDate(batchDate);

		return item;
	}
}
