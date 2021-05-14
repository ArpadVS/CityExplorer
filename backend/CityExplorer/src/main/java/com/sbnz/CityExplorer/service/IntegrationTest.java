package com.sbnz.CityExplorer.service;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationTest {

//  version 1, update after every request -- slower   COMMENT BEAN IN DroolService !
	
//	KieContainer kieContainer;
//    private KieSession session;
//    
//    public void testRules() throws MavenInvocationException {
//    	kieContainer = KieContainerConfiguration.kieContainer();
//        session = kieContainer.newKieSession("rulesSession");
//        session.fireAllRules();
//        session.dispose();
//    }

//  version 2, update at start and manually build kjar when update -- MUCH faster
	
    @Autowired
    DroolsService droolsService;
    private KieSession session;
    
    public void testRules(){
        session = droolsService.getRulesSession();
        session.fireAllRules();
        droolsService.releaseRulesSession();
    }

}
