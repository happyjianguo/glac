package net.engining.sccc.accounting.sharding.controller.query;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.engining.pg.support.db.querydsl.FetchResponse;
import net.engining.sccc.biz.bean.ApGlVoldtlSumDetailReq;
import net.engining.sccc.biz.bean.FetchDataProcess;
import net.engining.sccc.biz.bean.TotalBookkingRes;
import net.engining.sccc.biz.bean.TransOprHstReq;
import net.engining.sccc.biz.bean.VodtlAssSumHstDetail;
import net.engining.sccc.biz.service.ApGlVolDtlSumService;
import net.engining.sccc.biz.service.TransOprHstService;

@RequestMapping("/vodtlHst")
@RestController
public class ApGlVodtlController {
	@Autowired
	private ApGlVolDtlSumService apGlVolDtlSumService;
	
	@Autowired
	private TransOprHstService transOprHstService;
	@RequestMapping(value="/vodtlAssSumHstDetailQuery", method=RequestMethod.POST)
	public @ResponseBody FetchDataProcess<VodtlAssSumHstDetail> historicalaccountingRecord(@RequestBody ApGlVoldtlSumDetailReq req) {
		FetchDataProcess<VodtlAssSumHstDetail> vodtl = apGlVolDtlSumService.vodtlAssSumHstDetailQuery(req.getSubjectCd(),req.getAssistType(),req.getAssistAccountData());
		return vodtl;
	}
	
	
	@RequestMapping(value="/postAccountQuery", method=RequestMethod.POST)
	public @ResponseBody FetchResponse<TotalBookkingRes> postAccountQuery(@RequestBody TransOprHstReq req) {
		FetchResponse<TotalBookkingRes> postAccountQuery = transOprHstService.historicalOne(req.getOperDate(), req.getTxnDetailSeq(), req.getRange());
		return postAccountQuery;
	}
}
