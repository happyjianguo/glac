package net.engining.sccc.batch.infrastructure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd;
import net.engining.pcx.cc.infrastructure.shared.model.CactSysChecklist;
import net.engining.pcx.cc.infrastructure.shared.model.QCactSysChecklist;
import net.engining.pg.batch.sdk.file.FlatFileHeader;

/**
 * File Header 处理组件；针对只用一行String表示文件头数据；
 * 记录数据到CACT_SYS_CHECKLIST表
 * @author luxue
 *
 */
public class FileHeaderLineCallbackHandler implements LineCallbackHandler {
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Header数据包含的行数
	 */
	private int headerLineNumber;
	
	/**
	 * 支持多个文件情况下，headerLineNumber的乘数
	 */
	private int mult = 1;
	
	/**
	 * 已读行数
	 */
	private int readLines = 0;
	
	private String batchSeq;
	
	private InspectionCd inspectionCd;
	
	private Date bizDate;
	
	private FlatFileHeader fileHeader;
	
	private FlatFileHeader.Type headerType;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.LineCallbackHandler#handleLine(java.
	 * lang.String)
	 */
	@Override
	@Transactional
	public void handleLine(String line) {
		readLines++;
		
		// 记录文件头数据到CACT_SYS_CHECKLIST表
		if(readLines <= headerLineNumber*mult){
			
			QCactSysChecklist qCactSysChecklist = QCactSysChecklist.cactSysChecklist;
			CactSysChecklist cactSysChecklist = new JPAQueryFactory(em)
					.select(qCactSysChecklist)
					.from(qCactSysChecklist)
					.where(
							qCactSysChecklist.batchSeq.eq(batchSeq),
							qCactSysChecklist.inspectionCd.eq(inspectionCd),
							qCactSysChecklist.bizDate.eq(bizDate)
							)
					.fetchOne();
			
			//保存文件Header数据
			if(headerType.equals(FlatFileHeader.Type.SimpleInteger)){
				fileHeader = new FlatFileHeader();
				fileHeader.setTotalLines(Integer.parseInt(line));
				
			}
			else if(headerType.equals(FlatFileHeader.Type.SimpleString)){
				fileHeader = new FlatFileHeader();
				//TODO 没想好怎么处理
			}
			else if(headerType.equals(FlatFileHeader.Type.JsonString)){
				fileHeader = new FlatFileHeader();
				fileHeader = JSON.parseObject(line, FlatFileHeader.class);
			}
			List<FlatFileHeader> list=new ArrayList<FlatFileHeader>();
			if(fileHeader != null){
				if(cactSysChecklist.getCheckBizData() != null){
					
					list= JSONObject.parseArray(cactSysChecklist.getCheckBizData(),FlatFileHeader.class);
					list.add(fileHeader);
					cactSysChecklist.setCheckBizData(JSON.toJSONString(list));
				}else{
					list.add(fileHeader);
					cactSysChecklist.setCheckBizData(JSON.toJSONString(list));
				}
			}
			
		}
	}

	public void setInspectionCd(InspectionCd inspectionCd) {
		this.inspectionCd = inspectionCd;
	}

	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}

	public void setHeaderLineNumber(int headerLineNumber) {
		this.headerLineNumber = headerLineNumber;
	}

	public void setHeaderType(FlatFileHeader.Type headerType) {
		this.headerType = headerType;
	}

	public void setBatchSeq(String batchSeq) {
		this.batchSeq = batchSeq;
	}

	public int getMult() {
		return mult;
	}

	public void setMult(int mult) {
		this.mult = mult;
	}

	public int getReadLines() {
		return readLines;
	}

	public void setReadLines(int readLines) {
		this.readLines = readLines;
	}

}
