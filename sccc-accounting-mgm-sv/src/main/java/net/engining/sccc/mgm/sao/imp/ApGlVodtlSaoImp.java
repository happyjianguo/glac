package net.engining.sccc.mgm.sao.imp;


import org.springframework.stereotype.Component;


import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.sccc.biz.bean.ApGlVoldtlSumDetailReq;
import net.engining.sccc.biz.bean.FetchDataProcess;
import net.engining.sccc.biz.bean.VodtlAssSumHstDetail;
import net.engining.sccc.mgm.sao.ApGlVodtlSao;

@Component
public class ApGlVodtlSaoImp implements ApGlVodtlSao{

	@Override
	public FetchDataProcess<VodtlAssSumHstDetail> vodtlAssSumHstDetailQuery(ApGlVoldtlSumDetailReq req) {
		FetchDataProcess<VodtlAssSumHstDetail> user= new FetchDataProcess<VodtlAssSumHstDetail>();
		user.setReturnCode(ErrorCode.Other.toString());
		user.setReturnDesc("server is busy, try it later");
		return user;
	}


	/*@Override
	public WebCommonResponse<FetchDataProcess<VodtlAssSumHstDetail>> vodtlAssSumHstDetailQuery(ApGlVoldtlSumDetailReq req) {
		FetchDataProcess<VodtlAssSumHstDetail> user= new FetchDataProcess<VodtlAssSumHstDetail>();
		user.setReturnCode(ErrorCode.Other.toString());
		user.setReturnDesc("server is busy, try it later");
		return new WebCommonResponseBuilder<FetchDataProcess<VodtlAssSumHstDetail>>().build().setStatusCode(WebCommonResponse.CODE_UNKNOW_FAIL).setStatusDesc(WebCommonResponse.DESC_UNKNOW_FAIL);
	}*/

}
