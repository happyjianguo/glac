package net.engining.sccc.biz.enums;

/**
 * 日期类型
 */
import net.engining.pg.support.meta.EnumInfo;
@EnumInfo({
	"C|\u6e05\u7b97\u65e5\u671f",
	"T|\u4ea4\u6613\u65e5\u671f"
})
public enum DateTypeDef {

	/** 清算日期 */	C,
    /** 交易日期 */	T,
	/** 记账日期*/ P;
}

