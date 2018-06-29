package net.engining.sccc.batch.sccc5901;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.ApGlBal;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlAssSum;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlBal;
import net.engining.pcx.cc.infrastructure.shared.model.QApGlVolDtlAssSum;
import net.engining.pcx.cc.param.model.Subject;
import net.engining.pg.batch.entity.model.PgFileItem;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.sccc.batch.sccc5901.bean.Ass;
import net.engining.sccc.batch.sccc5901.bean.Credit;
import net.engining.sccc.batch.sccc5901.bean.Debit;
import net.engining.sccc.batch.sccc5901.bean.Details;
import net.engining.sccc.batch.sccc5901.bean.JaxbUtil;
import net.engining.sccc.batch.sccc5901.bean.VoucherHead;
import net.engining.sccc.config.props.BatchTaskProperties;
@Service
@StepScope
public class Sccc5901TAddPgFileItem implements Tasklet {
	
	@Autowired
	private ParameterFacility parameterCacheFacility;
	@Value("#{new java.util.Date(jobParameters['batchDate'].time)}")
	private Date batchDate;

	@Value("#{jobParameters['batchSeq']}")
	private String batchSeq;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	BatchTaskProperties batchTaskProperties;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		QApGlBal qApGlBal = QApGlBal.apGlBal;
		List<ApGlBal> apList = new JPAQueryFactory(em).select(qApGlBal).from(qApGlBal).where(qApGlBal.bizDate.eq(batchDate)).fetch();
		
		String headXml = "";
		StringBuffer buffer = new StringBuffer();
		boolean b = false;
		for(ApGlBal item : apList){
			if(item.getDbAmt().compareTo(BigDecimal.ZERO)==0&&item.getCrAmt().compareTo(BigDecimal.ZERO)==0) continue;
			
			QApGlVolDtlAssSum q = QApGlVolDtlAssSum.apGlVolDtlAssSum;
			// 查询辅助核算项汇总表
			List<ApGlVolDtlAssSum> list = new JPAQueryFactory(em).select(q).from(q)
					.where(q.subjectCd.eq(item.getSubjectCd())).fetch();
			Details details = new Details();// 创建对象，初始化数据
			setDetails(list, details, item);
			VoucherHead head = new VoucherHead();// 创建对象，初始化数据
			head.setDetails(details);
			
			// 拼装组成xml样式
			String str = JaxbUtil.convertToXml(head, "UTF-8");
			String[] a = str.split("<voucher_head>");
			headXml = a[0];
			String before = "\n<voucher>\n<voucher_head>" + a[1];
			String after = "</voucher>";
			buffer.append(before).append(after);
			b = true;
		}
		
		
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(batchDate);
		String result = buffer.toString();
		buffer = new StringBuffer();
		String beforeUfinterface = "<ufinterface account=\"develop\" billtype=\"vouchergl\" businessunitcode=\"develop\" filename=\""+dateStr+"_glAcct.xml"+"\" groupcode=\"0001\" isexchange=\"\" orgcode=\"10\" receiver=\"0001A1100000000006ZW\" replace=\"\" roottag=\"\" sender=\"001\">";
		String afterUfinterface = "\n</ufinterface>";
		
		result = buffer.append(headXml).append(beforeUfinterface).append(result).append(afterUfinterface).toString();

		// 添加数据 批量处理文件数据行信息表
		PgFileItem file = new PgFileItem();
		if(b){
			file.setLine(result);
		}else{
			file.setLine("");
		}
		
		file.setFilename(batchTaskProperties.getDefaultBatchOutputDir() + dateStr + "_glAcct.xml");// D:\csii\20180424_glAcct.xml
		file.setBatchNumber(batchSeq);

		em.persist(file);
		
		return null;
	}
	
	
	private void setDetails(List<ApGlVolDtlAssSum> list, Details details, ApGlBal item) {
		//获取科目信息
		Subject subject = parameterCacheFacility.loadParameter(Subject.class, item.getSubjectCd());
		
		//添加辅助核算项数据
		List<Ass> assList = new ArrayList<Ass>();
		for (ApGlVolDtlAssSum sum : list) {
			Ass ass = new Ass();
			ass.setPkCheckType(sum.getAssistType().toString());
			ass.setPkCheckValue(sum.getAssistAccountValue());
			assList.add(ass);
		}

		//添加借方数据
		Debit debit = new Debit();
		debit.setDetailIndex(subject.subjectCd);
		debit.setExplanation(subject.name);
		debit.setVerifyDate(item.getBizDate());
		debit.setDebitQuantity(item.getDbCount());
		debit.setDebitAmount(item.getDbAmt());
		debit.setLocalDebitAmount(item.getDbAmt());
		debit.setPkAccasoa(subject.subjectCd);
		debit.setAss(assList);
		
		//添加贷方数据
		Credit credit = new Credit();
		credit.setDetailIndex(subject.subjectCd);
		credit.setExplanation(subject.name);
		credit.setVerifyDate(item.getBizDate());
		credit.setCreditQuantity(item.getCrCount());
		credit.setCreditAmount(item.getCrAmt());
		credit.setLocalCreditAmount(item.getCrAmt());
		credit.setPkAccasoa(subject.subjectCd);
		credit.setAss(assList);
		

		details.setDebit(debit);
		details.setCredit(credit);
	}

}
