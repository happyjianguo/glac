package net.engining.sccc.biz.service.enums;

import net.engining.pg.support.meta.EnumInfo;

@EnumInfo({
	"GeneralTaxpayer|一般纳税人",
	"SmallTaxpayer|小规模纳税人"
})
public enum TaxpayerAttribute {
	/**
	 * 一般纳税人
	 */
	GeneralTaxpayer, 
	/**
	 * 小规模纳税人
	 */
	SmallTaxpayer
}
