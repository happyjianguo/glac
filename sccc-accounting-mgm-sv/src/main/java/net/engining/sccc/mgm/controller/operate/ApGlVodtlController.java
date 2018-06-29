package net.engining.sccc.mgm.controller.operate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.pg.web.WebCommonResponse;
import net.engining.pg.web.WebCommonResponseBuilder;
import net.engining.sccc.biz.bean.ApGlVoldtlSumDetailReq;
import net.engining.sccc.biz.bean.ApGlVoldtlSumReq;
import net.engining.sccc.biz.bean.FetchDataProcess;
import net.engining.sccc.biz.bean.VodtlAssSumDetail;
import net.engining.sccc.biz.bean.VodtlAssSumHstDetail;
import net.engining.sccc.biz.service.ApGlVolDtlSumService;
import net.engining.sccc.mgm.service.ApGlVodtlService;
import net.engining.sccc.mgm.service.VolDtlSumService;

@Api(value="ApGlVodtlController",description="辅助核算余额查询")
@RequestMapping("/vodtl")
@RestController
public class ApGlVodtlController {
	
	@Autowired
	private ApGlVolDtlSumService apGlVolDtlSumService;
	
	@Autowired
	private VolDtlSumService volDtlSumService;
	
	@Autowired
	private ApGlVodtlService apGlVodtlService;
	
	
	/**
	 * 
	 * 当日辅助核算余额查询
	 *
	 * @param
	 * @param 
	 * @return
	 */
	@ApiOperation(value="当日辅助核算余额查询", notes="")
	@RequestMapping(value="/vodtlAssSumQuery",method=RequestMethod.POST)
	@PreAuthorize("hasAuthority('vodtlAssSumQuery')")
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> vodtlAssSumQuery(@RequestBody ApGlVoldtlSumReq req) {

		FetchResponse<Map<String, Object>> result = apGlVolDtlSumService.getVolDtlSumRes(req);
		FetchResponse<Map<String, Object>> fetchResponse = apGlVolDtlSumService.insertSubjectIntoResult(result);
		
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(fetchResponse);
	}
	
	
	/**
	 * 
	 *当日辅助核算余额明细查询
	 *
	 * @param 
	 * @param 
	 * @return
	 */
	@ApiOperation(value="当日辅助核算余额明细查询", notes="")
	@RequestMapping(value="/vodtlAssSumDetailQuery",method=RequestMethod.POST)
	@PreAuthorize("hasAuthority('vodtlAssSumDetailQuery')")
	public @ResponseBody WebCommonResponse<List<VodtlAssSumDetail>> vodtlAssSumDetailQuery(@RequestBody ApGlVoldtlSumDetailReq req) {

		List<Tuple> fetchResponse = apGlVolDtlSumService.vodtlAssSumDetailQuery(req.getSubjectCd(),req.getAssistType(),req.getAssistAccountData());
		
		List<VodtlAssSumDetail> vodtlAssSumDetail= volDtlSumService.transOprDetail(fetchResponse);
		
		return new WebCommonResponseBuilder<List<VodtlAssSumDetail>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(vodtlAssSumDetail);
	}
	
	/**
	 * 
	 *历史辅助核算余额查询
	 *
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/vodtlAssSumHstQuery",method=RequestMethod.POST)
	@ApiOperation(value="历史辅助核算余额查询", notes="")
	@PreAuthorize("hasAuthority('vodtlAssSumHstQuery')")
	public @ResponseBody WebCommonResponse<FetchResponse<Map<String, Object>>> vodtlAssSumHstQuery(@RequestBody ApGlVoldtlSumReq req) {
		
		FetchResponse<Map<String, Object>> result=apGlVolDtlSumService.getVolDtlSumHstRes(req);
		FetchResponse<Map<String, Object>> fetchResponse = apGlVolDtlSumService.insertSubjectIntoResult(result);
		return new WebCommonResponseBuilder<FetchResponse<Map<String, Object>>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(fetchResponse);
	}
	
	/**
	 *历史辅助核算余额明细查询
	 *
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/vodtlAssSumHstDetailQuery",method=RequestMethod.POST)
	@ApiOperation(value="历史辅助核算余额明细查询", notes="")
	@PreAuthorize("hasAuthority('vodtlAssSumHstDetailQuery')")
	public @ResponseBody WebCommonResponse<FetchDataProcess<VodtlAssSumHstDetail>> vodtlAssSumHstDetailQuery(@RequestBody ApGlVoldtlSumDetailReq req) {
		
		FetchDataProcess<VodtlAssSumHstDetail> fetchResponse = apGlVodtlService.vodtlAssSumHstDetailQuery(req);

//		List<Tuple> fetchResponse = apGlVolDtlSumService.vodtlAssSumHstDetailQuery(req.getSubjectCd(),req.getAssistType(),req.getAssistAccountData());
		
		return new WebCommonResponseBuilder<FetchDataProcess<VodtlAssSumHstDetail>>()
				.build()
				.setStatusCode(WebCommonResponse.CODE_OK)
				.setResponseData(fetchResponse);
	}
	
}
