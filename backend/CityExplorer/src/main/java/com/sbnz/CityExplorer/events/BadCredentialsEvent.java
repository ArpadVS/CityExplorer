package com.sbnz.CityExplorer.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;
import org.kie.api.definition.type.Role.Type;

import com.sbnz.CityExplorer.model.User;

@Role(Type.EVENT)
@Timestamp("loginTime")
@Expires("11m")
public class BadCredentialsEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private User user;
	private Date loginTime;

	public BadCredentialsEvent() {
		super();
	}

	public BadCredentialsEvent(User user, Date loginTime) {
		super();
		this.user = user;
		this.loginTime = loginTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
