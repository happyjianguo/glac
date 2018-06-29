/**
 * 
 */
package net.engining.sccc.batch.runner.enums;

/**
 * 批量任务执行方式选项;
 * ST:启动新的批量; RS:重启最近一次失败的批量
 * @author luxue
 *
 */
public enum ActionRunnerOption {
	
	/**
	 * 启动新的批量
	 */
	ST,
	
	/**
	 * 重启最近一次失败的批量
	 */
	RS

}
