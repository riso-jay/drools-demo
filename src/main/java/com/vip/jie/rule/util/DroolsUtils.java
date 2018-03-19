package com.vip.jie.rule.util;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;

import java.io.IOException;



/**
 * 动态生成kjar
 * @author jie01.zhu
 * @DateTime 2018/3/19 22:14
 */
public class DroolsUtils {

	/**
	 * 创建默认的kbase和stateful的kiesession
	 *
	 * @param ks
	 * @param isdefault
	 * @return
	 */
	private static KieFileSystem createKieFileSystemWithKProject(KieServices ks, boolean isdefault) {
		KieModuleModel kproj = ks.newKieModuleModel();
		KieBaseModel kieBaseModel1 = kproj.newKieBaseModel("KBase").setDefault(isdefault)
				.setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
				.setEventProcessingMode(EventProcessingOption.STREAM);
		// Configure the KieSession.
		kieBaseModel1.newKieSessionModel("KSession").setDefault(isdefault)
				.setType(KieSessionModel.KieSessionType.STATEFUL);
		KieFileSystem kfs = ks.newKieFileSystem();
		kfs.writeKModuleXML(kproj.toXML());
		return kfs;
	}

	/**
	 * 创建kjar的pom
	 *
	 * @param releaseId
	 * @param dependencies
	 * @return
	 */
	private static String getPom(ReleaseId releaseId, ReleaseId... dependencies) {
		String pom = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
				+ "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n"
				+ "  <modelVersion>4.0.0</modelVersion>\n" + "\n" + "  <groupId>" + releaseId.getGroupId()
				+ "</groupId>\n" + "  <artifactId>" + releaseId.getArtifactId() + "</artifactId>\n" + "  <version>"
				+ releaseId.getVersion() + "</version>\n" + "\n";
		if (dependencies != null && dependencies.length > 0) {
			pom += "<dependencies>\n";
			for (ReleaseId dep : dependencies) {
				pom += "<dependency>\n";
				pom += "  <groupId>" + dep.getGroupId() + "</groupId>\n";
				pom += "  <artifactId>" + dep.getArtifactId() + "</artifactId>\n";
				pom += "  <version>" + dep.getVersion() + "</version>\n";
				pom += "</dependency>\n";
			}
			pom += "</dependencies>\n";
		}
		pom += "</project>";
		return pom;
	}

	/**
	 * 初始化一个kjar：把原有的drl包含进新建的kjar中
	 *
	 * @param ks
	 * @param releaseId
	 * @return
	 * @throws IOException
	 */
	public static InternalKieModule initKieJar(KieServices ks, ReleaseId releaseId) throws IOException {
		KieFileSystem kfs = createKieFileSystemWithKProject(ks, true);
		kfs.writePomXML(getPom(releaseId));
		KieBuilder kieBuilder = ks.newKieBuilder(kfs);
		if (!kieBuilder.buildAll().getResults().getMessages().isEmpty()) {
			throw new IllegalStateException("Error creating KieBuilder.");
		}
		return (InternalKieModule) kieBuilder.getKieModule();
	}

	public static InternalKieModule createKieJar(KieServices ks, ReleaseId releaseId, DroolsResource droolsResource) {
		KieFileSystem kfs = createKieFileSystemWithKProject(ks, true);
		kfs.writePomXML(getPom(releaseId));
		kfs.write("src/main/resources/" + droolsResource.getTargetResourceName(), droolsResource.getResource());

		KieBuilder kieBuilder = ks.newKieBuilder(kfs);
		if (!kieBuilder.getResults().getMessages().isEmpty()) {
			throw new IllegalStateException(
					"Error creating KieBuilder. errorMsg:" + kieBuilder.getResults().getMessages());
		}
		return (InternalKieModule) kieBuilder.getKieModule();
	}


}
