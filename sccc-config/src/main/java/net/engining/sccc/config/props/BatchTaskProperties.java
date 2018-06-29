package net.engining.sccc.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 批量任务参数配置
 * @author luxue
 *
 */
@ConfigurationProperties(prefix = "sccc.batch")
public class BatchTaskProperties {
	
	/**
	 * 日终批量任务执行时间
	 */
	private String endOfDaySchedule;
	
	/**
	 * 报表生成文件任务执行时间
	 */
	private long generateReportFileSchedule;
	
	/**
	 * 日终批量预处理任务执行时间
	 */
	private long prev4EODSchedule;
	
	/**
	 * 批量任务分片默认网格Size
	 */
	private int defaultGridSize;
	
	/**
	 * 批量任务分片网格Size
	 */
	private int sccc0810GridSize;
	
	/**
	 * 批量任务分片网格Size
	 */
	private int sccc0820GridSize;
	
	/**
	 * 批量任务分片网格Size
	 */
	private int sccc0830GridSize;
	
	/**
	 * 批量任务分片网格Size
	 */
	private int sccc5701GridSize;
	
	/**
	 * 批量任务分片网格Size
	 */
	private int sccc5801GridSize;
	
	/**
	 * 分布式批量下，Master获取Slave的心跳间隔毫秒数
	 */
	private long defaultPollMills;
	
	/**
	 * 默认批量任务分片执行的线程池core pool size
	 */
	private int defaultExecutorSize;
	
	/**
	 * 批量任务分片执行的线程池core pool size
	 */
	private int sccc0810ExecutorSize;
	
	/**
	 * 批量任务分片执行的线程池core pool size
	 */
	private int sccc0820ExecutorSize;
	
	/**
	 * 批量任务分片执行的线程池core pool size
	 */
	private int sccc0830ExecutorSize;
	
	/**
	 * 批量任务分片执行的线程池core pool size
	 */
	private int sccc5701ExecutorSize;
	
	/**
	 * 批量任务分片执行的线程池core pool size
	 */
	private int sccc5801ExecutorSize;
	
	/**
	 * 批量任务分片执行的线程池core pool size
	 */
	private int sccc0901ExecutorSize;
	
	/**
	 * 批量任务分片执行的线程池core pool size
	 */
	private int sccc0902ExecutorSize;
	
	/**
	 * 批量任务分片执行的线程池core pool size
	 */
	private int sccc1101ExecutorSize;
	
	/**
	 * 批量任务分片执行的线程池core pool size
	 */
	private int sccc9999ExecutorSize;
	
	/**
	 * 默认批量输出文件目录
	 */
	private String defaultBatchOutputDir;
	
	/**
	 * 默认批量输入文件目录
	 */
	private String defaultBatchInputDir;
	
	/**
	 * 默认批量输入备份文件目录
	 */
	private String defaultBackupInputDir;
	
	/**
	 * 是否使用远程分片策略
	 */
	private boolean enableRemotePartition = false;
	
	/**
	 * 配置RabbitMq消息监听器的线程池大小;
	 */
	private int rabbitmqMsgListenerPoolSize;
	
	/**
	 * 配置RabbitMq消息监听器的并发消费者数量;
	 */
	private int rabbitmqConcurrentConsumers;
	
	/**
	 * 配置RabbitMq消息监听器的一次性获取消息的数量;
	 */
	private int rabbitmqPrefetchCount;
	
	/**
	 * 默认事物提交间隔数量，每n条item批量框架触发一次事物提交
	 */
	private int defaultCommitInterval = 50;
	
	/**
	 * 批量9999ApGlTxn批次数据处理量大小
	 */
	private int dataHandleSizeOfApGlTxn;
	
	/**
	 * 批量9999ApGlVolDtl批次数据处理量大小
	 */
	private int dataHandleSizeOfApGlVolDtl;
	
	/**
	 * 批量9999ApGlVolDtlAss批次数据处理量大小
	 */
	private int dataHandleSizeOfApGlVolDtlAss;

	public int getRabbitmqMsgListenerPoolSize() {
		return rabbitmqMsgListenerPoolSize;
	}

	public void setRabbitmqMsgListenerPoolSize(int rabbitmqMsgListenerPoolSize) {
		this.rabbitmqMsgListenerPoolSize = rabbitmqMsgListenerPoolSize;
	}

	public int getRabbitmqConcurrentConsumers() {
		return rabbitmqConcurrentConsumers;
	}

	public void setRabbitmqConcurrentConsumers(int rabbitmqConcurrentConsumers) {
		this.rabbitmqConcurrentConsumers = rabbitmqConcurrentConsumers;
	}

	public int getRabbitmqPrefetchCount() {
		return rabbitmqPrefetchCount;
	}

	public void setRabbitmqPrefetchCount(int rabbitmqPrefetchCount) {
		this.rabbitmqPrefetchCount = rabbitmqPrefetchCount;
	}

	public long getPrev4EODSchedule() {
		return prev4EODSchedule;
	}

	public void setPrev4EODSchedule(long prev4eodSchedule) {
		prev4EODSchedule = prev4eodSchedule;
	}

	public int getDefaultCommitInterval() {
		return defaultCommitInterval;
	}

	public void setDefaultCommitInterval(int defaultCommitInterval) {
		this.defaultCommitInterval = defaultCommitInterval;
	}

	public String getDefaultBatchOutputDir() {
		return defaultBatchOutputDir;
	}

	public void setDefaultBatchOutputDir(String defaultBatchOutputDir) {
		this.defaultBatchOutputDir = defaultBatchOutputDir;
	}

	public String getDefaultBatchInputDir() {
		return defaultBatchInputDir;
	}

	public void setDefaultBatchInputDir(String defaultBatchInputDir) {
		this.defaultBatchInputDir = defaultBatchInputDir;
	}

	public int getDefaultExecutorSize() {
		return defaultExecutorSize;
	}

	public void setDefaultExecutorSize(int defaultExecutorSize) {
		this.defaultExecutorSize = defaultExecutorSize;
	}

	public int getSccc0810ExecutorSize() {
		return sccc0810ExecutorSize;
	}

	public void setSccc0810ExecutorSize(int sccc0810ExecutorSize) {
		this.sccc0810ExecutorSize = sccc0810ExecutorSize;
	}

	public int getSccc0820ExecutorSize() {
		return sccc0820ExecutorSize;
	}

	public void setSccc0820ExecutorSize(int sccc0820ExecutorSize) {
		this.sccc0820ExecutorSize = sccc0820ExecutorSize;
	}

	public int getSccc0830ExecutorSize() {
		return sccc0830ExecutorSize;
	}

	public void setSccc0830ExecutorSize(int sccc0830ExecutorSize) {
		this.sccc0830ExecutorSize = sccc0830ExecutorSize;
	}

	public String getEndOfDaySchedule() {
		return endOfDaySchedule;
	}

	public void setEndOfDaySchedule(String endOfDaySchedule) {
		this.endOfDaySchedule = endOfDaySchedule;
	}

	public long getGenerateReportFileSchedule() {
		return generateReportFileSchedule;
	}

	public void setGenerateReportFileSchedule(long generateReportFileSchedule) {
		this.generateReportFileSchedule = generateReportFileSchedule;
	}

	public int getDefaultGridSize() {
		return defaultGridSize;
	}

	public void setDefaultGridSize(int defaultGridSize) {
		this.defaultGridSize = defaultGridSize;
	}

	public long getDefaultPollMills() {
		return defaultPollMills;
	}

	public void setDefaultPollMills(long defaultPollMills) {
		this.defaultPollMills = defaultPollMills;
	}

	public String getDefaultBackupInputDir() {
		return defaultBackupInputDir;
	}

	public void setDefaultBackupInputDir(String defaultBackupInputDir) {
		this.defaultBackupInputDir = defaultBackupInputDir;
	}

	public int getDataHandleSizeOfApGlTxn() {
		return dataHandleSizeOfApGlTxn;
	}

	public void setDataHandleSizeOfApGlTxn(int dataHandleSizeOfApGlTxn) {
		this.dataHandleSizeOfApGlTxn = dataHandleSizeOfApGlTxn;
	}

	public int getDataHandleSizeOfApGlVolDtl() {
		return dataHandleSizeOfApGlVolDtl;
	}

	public void setDataHandleSizeOfApGlVolDtl(int dataHandleSizeOfApGlVolDtl) {
		this.dataHandleSizeOfApGlVolDtl = dataHandleSizeOfApGlVolDtl;
	}

	public int getDataHandleSizeOfApGlVolDtlAss() {
		return dataHandleSizeOfApGlVolDtlAss;
	}

	public void setDataHandleSizeOfApGlVolDtlAss(int dataHandleSizeOfApGlVolDtlAss) {
		this.dataHandleSizeOfApGlVolDtlAss = dataHandleSizeOfApGlVolDtlAss;
	}

	public boolean isEnableRemotePartition() {
		return enableRemotePartition;
	}

	public void setEnableRemotePartition(boolean enableRemotePartition) {
		this.enableRemotePartition = enableRemotePartition;
	}

	public int getSccc5701ExecutorSize() {
		return sccc5701ExecutorSize;
	}

	public void setSccc5701ExecutorSize(int sccc5701ExecutorSize) {
		this.sccc5701ExecutorSize = sccc5701ExecutorSize;
	}

	public int getSccc5801ExecutorSize() {
		return sccc5801ExecutorSize;
	}

	public void setSccc5801ExecutorSize(int sccc5801ExecutorSize) {
		this.sccc5801ExecutorSize = sccc5801ExecutorSize;
	}

	public int getSccc0901ExecutorSize() {
		return sccc0901ExecutorSize;
	}

	public void setSccc0901ExecutorSize(int sccc0901ExecutorSize) {
		this.sccc0901ExecutorSize = sccc0901ExecutorSize;
	}

	public int getSccc0902ExecutorSize() {
		return sccc0902ExecutorSize;
	}

	public void setSccc0902ExecutorSize(int sccc0902ExecutorSize) {
		this.sccc0902ExecutorSize = sccc0902ExecutorSize;
	}

	public int getSccc1101ExecutorSize() {
		return sccc1101ExecutorSize;
	}

	public void setSccc1101ExecutorSize(int sccc1101ExecutorSize) {
		this.sccc1101ExecutorSize = sccc1101ExecutorSize;
	}

	public int getSccc9999ExecutorSize() {
		return sccc9999ExecutorSize;
	}

	public void setSccc9999ExecutorSize(int sccc9999ExecutorSize) {
		this.sccc9999ExecutorSize = sccc9999ExecutorSize;
	}

	public int getSccc0810GridSize() {
		return sccc0810GridSize;
	}

	public void setSccc0810GridSize(int sccc0810GridSize) {
		this.sccc0810GridSize = sccc0810GridSize;
	}

	public int getSccc0820GridSize() {
		return sccc0820GridSize;
	}

	public void setSccc0820GridSize(int sccc0820GridSize) {
		this.sccc0820GridSize = sccc0820GridSize;
	}

	public int getSccc0830GridSize() {
		return sccc0830GridSize;
	}

	public void setSccc0830GridSize(int sccc0830GridSize) {
		this.sccc0830GridSize = sccc0830GridSize;
	}

	public int getSccc5701GridSize() {
		return sccc5701GridSize;
	}

	public void setSccc5701GridSize(int sccc5701GridSize) {
		this.sccc5701GridSize = sccc5701GridSize;
	}

	public int getSccc5801GridSize() {
		return sccc5801GridSize;
	}

	public void setSccc5801GridSize(int sccc5801GridSize) {
		this.sccc5801GridSize = sccc5801GridSize;
	}

	
}
