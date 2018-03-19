package com.vip.jie.rule.service.impl;


import com.vip.jie.rule.object.User;
import com.vip.jie.rule.service.RuleManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static junit.framework.TestCase.fail;

/**
 * 规则测试
 *  @author jie01.zhu
 *	@DateTime 2018/3/19 21:17
 *
 */
@Slf4j
public class RuleManagerImplTest {

	private RuleManager ruleManager = new RuleManagerImpl();

	private String getRuleContent() {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("import com.vip.jie.rule.object.User;\n");
		stringBuilder.append("rule test1 when\n");
		stringBuilder.append("user : User(age==20)\n");
		stringBuilder.append("then\n");
		stringBuilder.append("user.setName(\"张三\");\n");
		stringBuilder.append("end\n");
		return stringBuilder.toString();
	}

	@Test
	public void executeRule1() throws Exception {

		User user = new User();
		user.setAge(20);

		//调用规则
		ruleManager.executeRule("test1", getRuleContent(), user);
		log.info("test result:{}", user.toString());

		if (!"张三".equals(user.getName())) {
			fail("error rule");
		}
	}

	@Test
	public void executeRule2() throws Exception {

		User user = new User();
		user.setAge(21);

		//调用规则
		ruleManager.executeRule("test1", getRuleContent(), user);
		log.info("test result:{}", user.toString());

		if ("张三".equals(user.getName())) {
			fail("error rule");
		}
	}
}