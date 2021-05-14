package com.sbnz.CityExplorer.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {

	@Autowired
	private KieContainer kieContainer;
	
    private KieSession rulesSession;
    private KieSession eventsSession;
  
    public KieContainer getKieContainer() {
        return kieContainer;
    }

    public KieSession getRulesSession() {
        if(rulesSession == null){
        	rulesSession = kieContainer.newKieSession("rulesSession");
        }
        return rulesSession;
    }

    public void setRulesSession(KieSession kieSession) {
        this.rulesSession = kieSession;
    }
    
    public void releaseRulesSession(){
        this.rulesSession.dispose();
        this.rulesSession = null;
    }
    
    public KieSession getEventsSession() {
        if(eventsSession == null){
    		eventsSession = kieContainer.newKieSession("eventsSession");
        }
        return eventsSession;
    }

    public void setEventsSession(KieSession kieSession) {
        this.eventsSession = kieSession;
    }
}
