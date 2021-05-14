package com.sbnz.CityExplorer.service;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationTest {

//  version 1, update after every request -- slower
	
//    private KieContainer kieContainer;
//    private KieSession session;
//    
//    public void testRules() throws MavenInvocationException {
//    	kieContainer = KieContainerConfiguration.kieContainer();
//        session = kieContainer.newKieSession("ksession-rules");
//        session.fireAllRules();
//        session.dispose();
//    }

//  version 2, update at start and manually build kjar when update -- MUCH faster
	
    @Autowired
    private KieContainer kieContainer;
    private KieSession session;
    
    public void testRules(){
        session = kieContainer.newKieSession("ksession-rules");
        session.fireAllRules();
        session.dispose();
    }

}
