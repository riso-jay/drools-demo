package com.vip.jie.rule.util;

import org.kie.api.io.Resource;


/**
 * drools 资源类
 */
public class DroolsResource {
	private Resource resource;

	private String targetResourceName;

	public DroolsResource(Resource resource, String targetResourceName) {
		this.resource = resource;
		this.targetResourceName = targetResourceName;
	}

	public Resource getResource() {
		return resource;
	}

	public String getTargetResourceName() {
		return targetResourceName;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setTargetResourceName(String targetResourceName) {
		this.targetResourceName = targetResourceName;
	}
}
