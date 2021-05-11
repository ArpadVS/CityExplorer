package com.sbnz.CityExplorer.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationTest {

//    @Autowired
//    private KieContainer kieContainer;
//    private KieSession session;
//    
//    public void testRules() {
//
//        session = kieContainer.newKieSession("ksession-rules");
//        session.fireAllRules();
//        session.destroy();
//    }
	
	public void testRules() throws MavenInvocationException, IOException {
		mavenCleanAndInstall();
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
               .newKieContainer(ks.newReleaseId("com.sbnz", "CityExplorerKJAR", "1.0.0-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(6000);
		KieSession session = kContainer.newKieSession("ksession-rules");
		session.fireAllRules();
		session.dispose();
		System.out.println("testrules");
	}
	
	public void mavenCleanAndInstall() throws MavenInvocationException, IOException {
      InvocationRequest request = new DefaultInvocationRequest();
      request.setPomFile(new File("../CityExplorerKJAR/pom.xml"));
      request.setGoals(Arrays.asList("clean", "install"));
      String currentPath = new File("../CityExplorerKJAR/pom.xml").getCanonicalPath();
      System.out.println("Current dir:" + currentPath);

      Invoker invoker = new DefaultInvoker();
      invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
      invoker.execute(request);
  }

}
