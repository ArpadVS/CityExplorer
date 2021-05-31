package com.sbnz.CityExplorer.service;

import java.util.List;

import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
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
		if (rulesSession == null) {
			rulesSession = kieContainer.newKieSession("rulesSession");
		}
		return rulesSession;
	}

	public void setRulesSession(KieSession kieSession) {
		this.rulesSession = kieSession;
	}

	public void releaseRulesSession() {
		if(this.rulesSession != null) {
			this.rulesSession.dispose();
		}
		this.rulesSession = null;
	}

	public KieSession getEventsSession() {
		if (eventsSession == null) {
			eventsSession = kieContainer.newKieSession("eventsSession");
		}
		return eventsSession;
	}

	public void setEventsSession(KieSession kieSession) {
		this.eventsSession = kieSession;
	}

	public KieSession createKieSessionFromDRL(String drl) {
		KieHelper kieHelper = new KieHelper();
		kieHelper.addContent(drl, ResourceType.DRL);

		Results results = kieHelper.verify();

		if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
			List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
			for (Message message : messages) {
				System.out.println("Error: " + message.getText());
			}

			throw new IllegalStateException("Compilation errors were found. Check the logs.");
		}

		return kieHelper.build().newKieSession();
	}
}
