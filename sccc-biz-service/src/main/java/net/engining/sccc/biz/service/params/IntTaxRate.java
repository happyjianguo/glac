package net.engining.sccc.biz.service.params;

import java.io.Serializable;
import java.math.BigDecimal;

import net.engining.pg.support.meta.PropertyInfo;
import net.engining.sccc.biz.service.enums.InvolceType;
import net.engining.sccc.biz.service.enums.TaxpayerAttribute;

/**
 * 利息税
 * @author Ronny
 *
 */
public class IntTaxRate implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 币种数字代码
	 */
	@PropertyInfo(name="货币代码", length=3)
	public String taxCd;

    /**
	 * 应税项目
	 */
	@PropertyInfo(name="应税项目", length=30)
	public String taxableItem;

	 /**
     * 是否代扣利息税
     */
    @PropertyInfo(name="代扣利息税", length=1)
	public Boolean withHoldingInt;
	
	/**
	 * 是否开票
	 */
	@PropertyInfo(name="是否开票", length=1)
	public Boolean isInvoice;

	/**
	 * 开票类型
	 */
	@PropertyInfo(name="开票类型", length=30)
	public InvolceType involceType;

	/**
	 * 本方纳税人属性
	 */
	@PropertyInfo(name="本方纳税人属性", length=30)
	public TaxpayerAttribute taxpayerAttribute;

	/**
     * 折算本币汇率
     * 用于购汇和账务计算的汇率
     */
    @PropertyInfo(name="税率", length=9, precision=4)
    public BigDecimal taxRt;
    
    /**
     * 科目编号
     */
    @PropertyInfo(name="科目编号", length=20)
    public String subjectCd;
    
}
