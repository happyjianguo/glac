package net.engining.sccc.batch.sccc5901;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.engining.pcx.cc.infrastructure.shared.model.ApGlBal;
import net.engining.pcx.cc.infrastructure.shared.model.ApGlVolDtlAssSum;
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
public class Sccc5901P01AddPgFileItem implements ItemProcessor<ApGlBal, Object> {
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
	public Object process(ApGlBal item) throws Exception {
		if(item.getDbAmt().compareTo(BigDecimal.ZERO)==0&&item.getCrAmt().compareTo(BigDecimal.ZERO)==0) return null;
		
		QApGlVolDtlAssSum q = QApGlVolDtlAssSum.apGlVolDtlAssSum;
		// 查询辅助核算项汇总表
		List<ApGlVolDtlAssSum> list = new JPAQueryFactory(em).select(q).from(q)
				.where(q.subjectCd.eq(item.getSubjectCd())).fetch();
		Details details = new Details();// 创建对象，初始化数据
		setDetails(list, details, item);
		// 查询会计分录表贷方数据
		// List<ApGlVolDtl> crList = new
		// JPAQueryFactory(em).select(q).from(q).where(q.crsubjectCd.eq(item.getSubjectCd())).fetch();
		// setDbDetails(details, dbList);//添加借方数据
		// setCrDetails(details, crList);//添加贷方数据
		VoucherHead head = new VoucherHead();// 创建对象，初始化数据
		head.setDetails(details);
//		Voucher voucher = new Voucher();// 创建对象
//		voucher.setHead(head);

		// 拼装组成xml样式
		String result = JaxbUtil.convertToXml(head, "UTF-8");
		String[] a = result.split("<voucher_head>");
		String before = "<ufinterface account=\"develop\" billtype=\"vouchergl\" businessunitcode=\"develop\" filename=\""+item.getSubjectCd() + ".xml"+"\" groupcode=\"0001\" isexchange=\"\" orgcode=\"10\" receiver=\"0001A1100000000006ZW\" replace=\"\" roottag=\"\" sender=\"001\">";
		String after = "<voucher>\n</ufinterface>";
		String b = "\n<voucher>\n<voucher_head>" + a[1];
		StringBuffer buffer = new StringBuffer();
		buffer.append(a[0]).append(before).append(b).append(after);
		result = buffer.toString();

		// 添加数据 批量处理文件数据行信息表
		PgFileItem file = new PgFileItem();
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(batchDate);
		file.setLine(result);
		file.setFilename(batchTaskProperties.getDefaultBatchOutputDir() + dateStr + "\\" + item.getSubjectCd() + ".xml");// D:\csii\20180424\1002.xml
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
		

//		if (subject.balDbCrFlag.equals(BalDbCrFlag.D)) {
//			setDebit(debit, list, item);
//		} else if (subject.balDbCrFlag.equals(BalDbCrFlag.C)) {
//			setCredit(credit, list, item);
//		} else {
//			if (item.getDbAmt().compareTo(item.getCrAmt()) > 0) {
//				setDebit(debit, list, item);
//			} else {
//				setCredit(credit, list, item);
//			}
//		}
		details.setDebit(debit);
		details.setCredit(credit);
	}

//	private void setDebit(Debit debit, List<ApGlVolDtlAssSum> list, ApGlBal item) {
//		// debit.setDetailIndex(0);
//		// debit.setExplanation("");
//		debit.setVerifyDate(batchDate);
//		debit.setPkAccasoa(item.getSubjectCd());
//		debit.setDebitQuantity(item.getDbCount());
//		debit.setDebitAmount(item.getDbAmt());
//		debit.setLocalDebitAmount(item.getDbAmt());
//		List<Ass> assList = new ArrayList<Ass>();
//		List<CashFlow> cfList = new ArrayList<CashFlow>();
//		for (ApGlVolDtlAssSum sum : list) {
//			Ass ass = new Ass();
//			ass.setPkCheckType(sum.getAssistType());
//			ass.setPkCheckValue(sum.getAssistAccountValue());
//			assList.add(ass);
//			CashFlow cf = new CashFlow();
//			cf.setMoney(item.getDbAmt());
//			cf.setMoneyMain(item.getDbAmt());
//			cfList.add(cf);
//		}
//
//		debit.setAss(assList);
//		debit.setCashFlow(cfList);
//	}

//	private void setCredit(Credit credit, List<ApGlVolDtlAssSum> list, ApGlBal item) {
//		// credit.setDetailIndex(0);
//		// credit.setExplanation("");
//		credit.setVerifyDate(batchDate);
//		credit.setPkAccasoa(item.getSubjectCd());
//		credit.setCreditQuantity(item.getCrCount());
//		credit.setCreditAmount(item.getCrAmt());
//		credit.setLocalCreditAmount(item.getCrAmt());
//		List<Ass> assList = new ArrayList<Ass>();
//		List<CashFlow> cfList = new ArrayList<CashFlow>();
//		for (ApGlVolDtlAssSum sum : list) {
//			Ass ass = new Ass();
//			ass.setPkCheckType(sum.getAssistType());
//			ass.setPkCheckValue(sum.getAssistAccountValue());
//			assList.add(ass);
//			CashFlow cf = new CashFlow();
//			cf.setMoney(item.getDbAmt());
//			cf.setMoneyMain(item.getDbAmt());
//			cfList.add(cf);
//		}
//		credit.setAss(assList);
//		credit.setCashFlow(cfList);
//	}

	// private void setDbDetails(Details details , List<ApGlVolDtl> dbList){
	// if(dbList == null) return;
	// List<Debit> list = new ArrayList<Debit>();
	// for(ApGlVolDtl l : dbList){
	// Debit db = new Debit();
	// db.setDetailIndex(l.getVolSeq());
	// db.setExplanation(l.getVolDesc());
	// db.setVerifyDate(l.P_BizDate);
	// String ass = l.getAssistAccountData();
	// List<Ass> assList = JSON.parseArray(ass, Ass.class);
	// db.setAss(assList);
	// list.add(db);
	// }
	// details.setDebit(list);
	// }
	// private void setCrDetails(Details details , List<ApGlVolDtl> crList){
	// if(crList == null) return;
	// List<Credit> list = new ArrayList<Credit>();
	// for(ApGlVolDtl l : crList){
	// Credit cr = new Credit();
	// cr.setDetailIndex(l.getVolSeq());
	// cr.setExplanation(l.getVolDesc());
	// cr.setVerifyDate(l.P_BizDate);
	// String ass = l.getAssistAccountData();
	// List<Ass> assList = JSON.parseArray(ass, Ass.class);
	// cr.setAss(assList);
	// list.add(cr);
	// }
	// details.setCredit(list);
	// }

}
