package com.vip.jie.rule.object;

/**
 * 用户对象
 *  @author jie01.zhu
 *	@DateTime 2018/3/19 21:24
 *
 */
public class User {

	private String name = "";
	private int age = 0;

	public User() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" + "name='" + name + '\'' + ", age=" + age + '}';
	}
}
