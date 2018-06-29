package net.engining.sccc.batch.config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.google.common.collect.Lists;

import net.engining.sccc.batch.infrastructure.metas.FileOperateActionBean;
import net.engining.sccc.batch.infrastructure.metas.FileOperationType;
import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * @author luxue
 *
 */
@Configuration
public class ResourceContextConfig {

	@Autowired
	BatchTaskProperties batchTaskProperties;

	/**
	 * 多个联机交易对账 .ok文件资源Pattern Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("multiAccountCheckingOkFileResources")
	public String multiAccountCheckingOkFileResources() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("ONLINE_TRANSACTION-*");
		urlPathStr.append(".ok");

		return urlPathStr.toString();
	}

	/**
	 * 多个联机交易对账 .dat文件资源Pattern Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("multiAccountCheckingDatFileResources")
	public String multiAccountCheckingDatFileResources() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("ONLINE_TRANSACTION-*");
		urlPathStr.append(".dat");

		return urlPathStr.toString();
	}

	/**
	 * 中银记账 .ok文件资源Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("zyLedgerOkFileResource")
	public String zyLedgerOkFileResource() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("sc_accounting_S0000005_*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".ok");

		return urlPathStr.toString();
	}

	/**
	 * 中银记账 .dat文件资源Bean
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	@Bean("zyLedgerDatFileResource")
	public String zyLedgerDatFileResource() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("sc_accounting_S0000005_*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".dat");

		return urlPathStr.toString();
	}

	/**
	 * 中银记账文件 .ok .dat文件操作Bean
	 * 
	 * @param zyLedgerOkFileResource
	 * @param zyLedgerDatFileResource
	 * @return
	 * @throws MalformedURLException
	 */
//	@Bean("zyLedgerFileOperateActions")
//	public List<FileOperateActionBean> zyLedgerFileOperateActions(
//			@Qualifier("zyLedgerOkFileResource") Resource[] zyLedgerOkFileResource,
//			@Qualifier("zyLedgerDatFileResource") Resource[] zyLedgerDatFileResource) throws MalformedURLException {
//		List<FileOperateActionBean> actions = Lists.newArrayList();
//		StringBuffer urlPathStr;
//
//		FileOperateActionBean zyLedgerOkFileOperateActionBean = null;
//		for (Resource resource : zyLedgerOkFileResource) {
//			zyLedgerOkFileOperateActionBean = new FileOperateActionBean();
//			zyLedgerOkFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			zyLedgerOkFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			zyLedgerOkFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(zyLedgerOkFileOperateActionBean);
//		}
//
//		FileOperateActionBean zyLedgerDatFileOperateActionBean = null;
//		for (Resource resource : zyLedgerDatFileResource) {
//			zyLedgerDatFileOperateActionBean = new FileOperateActionBean();
//			zyLedgerDatFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			zyLedgerDatFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			zyLedgerDatFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(zyLedgerDatFileOperateActionBean);
//		}
//
//		return actions;
//	}

	/**
	 * 拿去花记账 .ok文件资源Bean
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	@Bean("nqhLedgerOkFileResource")
	public String nqhLedgerOkFileResource() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("sc_accounting_S0000006_*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".ok");

		return urlPathStr.toString();
	}

	/**
	 * 拿去花记账 .dat文件资源Bean
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	@Bean("nqhLedgerDatFileResource")
	public String nqhLedgerDatFileResource() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("sc_accounting_S0000006_*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".dat");

		return urlPathStr.toString();
	}

	/**
	 * 拿去花记账文件 .ok .dat文件操作Bean
	 * 
	 * @param nqhLedgerOkFileResource
	 * @param nqhLedgerDatFileResource
	 * @return
	 * @throws MalformedURLException
	 */
//	@Bean("nqhLedgerFileOperateActions")
//	public List<FileOperateActionBean> nqhLedgerFileOperateActions(
//			@Qualifier("nqhLedgerOkFileResource") Resource[] nqhLedgerOkFileResource,
//			@Qualifier("nqhLedgerDatFileResource") Resource[] nqhLedgerDatFileResource) throws MalformedURLException {
//		List<FileOperateActionBean> actions = Lists.newArrayList();
//		StringBuffer urlPathStr;
//
//		FileOperateActionBean nqhLedgerOkFileOperateActionBean = null;
//		for (Resource resource : nqhLedgerOkFileResource) {
//			nqhLedgerOkFileOperateActionBean = new FileOperateActionBean();
//			nqhLedgerOkFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			nqhLedgerOkFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			nqhLedgerOkFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(nqhLedgerOkFileOperateActionBean);
//		}
//
//		FileOperateActionBean nqhLedgerDatFileOperateActionBean = null;
//		for (Resource resource : nqhLedgerDatFileResource) {
//			nqhLedgerDatFileOperateActionBean = new FileOperateActionBean();
//			nqhLedgerDatFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			nqhLedgerDatFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			nqhLedgerDatFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(nqhLedgerDatFileOperateActionBean);
//		}
//
//		return actions;
//	}

	/**
	 * 多个利息计提记账 .ok文件资源Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("multiInteAccrualOkFileResources")
	public String multiInteAccrualOkFileResources() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("INTEREST-001-*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".ok");

		return urlPathStr.toString();
	}

	/**
	 * 多个利息计提记账 .dat文件资源Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("multiInteAccrualDatFileResources")
	public String multiInteAccrualDatFileResources() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("INTEREST-001-*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".dat");

		return urlPathStr.toString();
	}

	/**
	 * 多个利息计提记账 .ok .dat文件操作Bean
	 * 
	 * @param multiInteAccrualOkFileResources
	 * @param multiInteAccrualDatFileResources
	 * @return
	 * @throws MalformedURLException
	 */
//	@Bean("multiInteAccrualFileOperateActions")
//	public List<FileOperateActionBean> multiInteAccrualFileOperateActions(
//			@Qualifier("multiInteAccrualOkFileResources") Resource[] multiInteAccrualOkFileResources,
//			@Qualifier("multiInteAccrualDatFileResources") Resource[] multiInteAccrualDatFileResources)
//			throws MalformedURLException {
//
//		List<FileOperateActionBean> actions = Lists.newArrayList();
//		StringBuffer urlPathStr;
//
//		FileOperateActionBean inteAccrualOkFileOperateActionBean = null;
//		for (Resource resource : multiInteAccrualOkFileResources) {
//			inteAccrualOkFileOperateActionBean = new FileOperateActionBean();
//			inteAccrualOkFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			inteAccrualOkFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			inteAccrualOkFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(inteAccrualOkFileOperateActionBean);
//		}
//
//		FileOperateActionBean inteAccrualDatFileOperateActionBean = null;
//		for (Resource resource : multiInteAccrualDatFileResources) {
//			inteAccrualDatFileOperateActionBean = new FileOperateActionBean();
//			inteAccrualDatFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			inteAccrualDatFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			inteAccrualDatFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(inteAccrualDatFileOperateActionBean);
//		}
//
//		return actions;
//	}

	/**
	 * 多个罚息计提记账 .ok文件资源Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("multiPintAccrualOkFileResources")
	public String multiPintAccrualOkFileResources() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("PINTEREST-001-*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".ok");

		return urlPathStr.toString();
	}

	/**
	 * 多个罚息计提记账 .dat文件资源Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("multiPintAccrualDatFileResources")
	public String pintAccrualDatFileResources() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("PINTEREST-001-*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".dat");

		return urlPathStr.toString();
	}

	/**
	 * 多个罚息计提记账 .ok .dat文件操作Bean
	 * 
	 * @param multiPintAccrualOkFileResources
	 * @param multiPintAccrualDatFileResources
	 * @return
	 * @throws MalformedURLException
	 */
//	@Bean("multiPintAccrualFileOperateActions")
//	public List<FileOperateActionBean> multiPintAccrualFileOperateActions(
//			@Qualifier("multiPintAccrualOkFileResources") Resource[] multiPintAccrualOkFileResources,
//			@Qualifier("multiPintAccrualDatFileResources") Resource[] multiPintAccrualDatFileResources)
//			throws MalformedURLException {
//		List<FileOperateActionBean> actions = Lists.newArrayList();
//		StringBuffer urlPathStr;
//
//		FileOperateActionBean pintAccrualOkFileOperateActionBean = null;
//		for (Resource resource : multiPintAccrualOkFileResources) {
//			pintAccrualOkFileOperateActionBean = new FileOperateActionBean();
//			pintAccrualOkFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			pintAccrualOkFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			pintAccrualOkFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(pintAccrualOkFileOperateActionBean);
//		}
//		FileOperateActionBean pintAccrualDatFileOperateActionBean = null;
//		for (Resource resource : multiPintAccrualDatFileResources) {
//			pintAccrualDatFileOperateActionBean = new FileOperateActionBean();
//			pintAccrualDatFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			pintAccrualDatFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			pintAccrualDatFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(pintAccrualDatFileOperateActionBean);
//		}
//
//		return actions;
//	}

	/**
	 * 多个余额成分结转记账 .ok文件资源Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("multiTransformOkFileResources")
	public String multiTransformOkFileResources() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("TRANSFORM-001-*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".ok");

		return urlPathStr.toString();
	}

	/**
	 * 多个余额成分结转记账 .dat文件资源Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("multiTransformDatFileResources")
	public String multiTransformDatFileResources() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("TRANSFORM-001-*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".dat");

		return urlPathStr.toString();
	}

	/**
	 * 多个余额成分结转记账 .ok .dat文件操作Bean
	 * 
	 * @param multiTransformOkFileResources
	 * @param multiTransformDatFileResources
	 * @return
	 * @throws MalformedURLException
	 */
//	@Bean("multiTransformFileOperateActions")
//	public List<FileOperateActionBean> multiTransformFileOperateActions(
//			@Qualifier("multiTransformOkFileResources") Resource[] multiTransformOkFileResources,
//			@Qualifier("multiTransformDatFileResources") Resource[] multiTransformDatFileResources)
//			throws MalformedURLException {
//		List<FileOperateActionBean> actions = Lists.newArrayList();
//		StringBuffer urlPathStr;
//
//		FileOperateActionBean transformOkFileOperateActionBean = null;
//		for (Resource resource : multiTransformOkFileResources) {
//			transformOkFileOperateActionBean = new FileOperateActionBean();
//			transformOkFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			transformOkFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			transformOkFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(transformOkFileOperateActionBean);
//		}
//
//		FileOperateActionBean transformDatFileOperateActionBean = null;
//		for (Resource resource : multiTransformDatFileResources) {
//			transformDatFileOperateActionBean = new FileOperateActionBean();
//			transformDatFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			transformDatFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			transformDatFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//
//			actions.add(transformDatFileOperateActionBean);
//		}
//
//		return actions;
//	}

	/**
	 * 多个批量还款记账 .ok文件资源Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("multiBatchRepayOkFileResources")
	public String multiBatchRepayOkFileResources() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("BATCHREPAY-001-*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".ok");

		return urlPathStr.toString();

	}

	/**
	 * 多个批量还款记账 .dat文件资源Bean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean("multiBatchRepayDatFileResources")
	public String multiBatchRepayDatFileResources() throws IOException {
		StringBuffer urlPathStr = new StringBuffer();
		urlPathStr.append("file:");
		urlPathStr.append(batchTaskProperties.getDefaultBatchInputDir());
		urlPathStr.append("BATCHREPAY-001-*");
		// FIXME
//		String today = DateFormatUtils.format(new Date(), "YYYYMMdd");
//		urlPathStr.append(today);
		urlPathStr.append(".dat");

		return urlPathStr.toString();
	}

	/**
	 * 多个批量还款记账 .ok .dat文件操作Bean
	 * 
	 * @param multiBatchRepayOkFileResources
	 * @param multiBatchRepayDatFileResources
	 * @return
	 * @throws MalformedURLException
	 */
//	@Bean("multiBatchRepayFileOperateActions")
//	public List<FileOperateActionBean> multiBatchRepayFileOperateActions(
//			@Qualifier("multiBatchRepayOkFileResources") Resource[] multiBatchRepayOkFileResources,
//			@Qualifier("multiBatchRepayDatFileResources") Resource[] multiBatchRepayDatFileResources)
//			throws MalformedURLException {
//		List<FileOperateActionBean> actions = Lists.newArrayList();
//		StringBuffer urlPathStr;
//
//		FileOperateActionBean batchRepayOkFileOperateActionBean = null;
//		for (Resource resource : multiBatchRepayOkFileResources) {
//			batchRepayOkFileOperateActionBean = new FileOperateActionBean();
//			batchRepayOkFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			batchRepayOkFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			batchRepayOkFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(batchRepayOkFileOperateActionBean);
//		}
//		FileOperateActionBean batchRepayDatFileOperateActionBean = null;
//		for (Resource resource : multiBatchRepayDatFileResources) {
//			batchRepayDatFileOperateActionBean = new FileOperateActionBean();
//			batchRepayDatFileOperateActionBean.setSrcResource(resource);
//			urlPathStr = new StringBuffer();
//			urlPathStr.append("file:");
//			urlPathStr.append(batchTaskProperties.getDefaultBackupInputDir());
//			batchRepayDatFileOperateActionBean.setDestResource(new UrlResource(urlPathStr.toString()));
//			batchRepayDatFileOperateActionBean.setFileOperationType(FileOperationType.MV2DIR);
//			actions.add(batchRepayDatFileOperateActionBean);
//
//		}
//
//		return actions;
//	}

}
