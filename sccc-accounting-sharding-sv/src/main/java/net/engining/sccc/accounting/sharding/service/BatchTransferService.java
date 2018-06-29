package net.engining.sccc.accounting.sharding.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchTransferService {

	@Autowired
	private BatchDataTransferService batchDataTransferService;

	// apgltxn数据迁移
	@Transactional
	public void apGlTxnData(List<String> keys) {
		batchDataTransferService.insertApGlTxnHst(keys);
		batchDataTransferService.deleteApGlTxn(keys);
	}

	// apGlVoldtl数据迁移
	@Transactional
	public void apGlVoldtlData(List<String> keys) {
		batchDataTransferService.insertApGlVolDtlHst(keys);
		batchDataTransferService.deleteApGlVolDtl(keys);
	}

	// apGlVoldtlAss数据迁移
	@Transactional
	public void apGlVoldtlAssData(List<String> keys) {
		batchDataTransferService.insertApGlVolDtlAssHst(keys);
		batchDataTransferService.deleteApGlVolDtlAss(keys);
	}

}
