package net.engining.sccc.biz.service.params;

import java.io.Serializable;
import java.util.Date;

import net.engining.pcx.cc.infrastructure.shared.enums.CheckStatusDef;
import net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd;
import net.engining.pcx.cc.infrastructure.shared.enums.SkipConditionTypeDef;
import net.engining.pcx.cc.param.model.enums.CheckListType;
import net.engining.pg.support.meta.PropertyInfo;

public class CactSysCheck implements Serializable{

	private static final long serialVersionUID = 1L;
	
    /**
     * 检查项代码
     */
    @PropertyInfo(name="检查项代码", length=20)
    public InspectionCd inspectionCd;
	
	/**
     * 检查项描述
     */
    @PropertyInfo(name="检查项描述", length=40)
    public String checkListDesc;
    
    /**
     * 检查项类型
     */
	@PropertyInfo(name="检查项类型", length=30)
    public CheckListType checkListType;
    
    /**
     * 待检查次数
     */
    @PropertyInfo(name="待检查次数", length=10)
    public int checkTimes;
    
    /**
     * 状态(WAIT|待处理，SUCCESS|处理成功，FAILED|处理失败)
     */
    @PropertyInfo(name="状态", length=10)
    public CheckStatusDef checkStatus;
    
    /**
     * 是否可跳过
     */
    @PropertyInfo(name="是否可跳过", length=1)
    public Boolean skipable;
    
    /**
     * 跳过条件类型(时间TIME，次数COUNT)
     */
    @PropertyInfo(name="跳过条件类型", length=10)
    public SkipConditionTypeDef skipConditionType;

    /**
     * 跳过检查最大次数
     */
    @PropertyInfo(name="跳过检查最大次数", length=3)
    public int skipConMaxCount;

    /**
     * 跳过检查终结时间
     */
    @PropertyInfo(name="跳过检查终结时间", length=20)
    public Date skipConDeadline;
    
    public String getKey() {
    	return key(inspectionCd, checkListType);
    }
    
    public static String key(InspectionCd inspectionCd, CheckListType checkListType) {
    	return inspectionCd + "|" + checkListType;
    }

}
