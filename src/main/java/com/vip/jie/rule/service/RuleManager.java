package com.vip.jie.rule.service;

/**
 *  @author jie01.zhu
 *	@DateTime 2018/3/19 21:21
 *
 */
public interface RuleManager {

	/**
	 * 根据group ,content
	 * @param group
	 * @param content
	 * @param objects
	 * @throws Exception
	 */
	public void executeRule(String group, String content, Object... objects) throws Exception;
}
