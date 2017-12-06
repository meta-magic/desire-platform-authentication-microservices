package com.desire3d.auth.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class LoginInfoHelperBean {

	private String mteid;

	private String loginId;

	private String userId;

	private String personId;

	private String appSessionId;

	public void setProperty(String mteid, String loginId, String userId, String personId, String appSessionId) {
		this.mteid = mteid;
		this.loginId = loginId;
		this.userId = userId;
		this.personId = personId;
		this.appSessionId = appSessionId;
	}

	public String getMteid() {
		return mteid;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getUserId() {
		return userId;
	}

	public String getPersonId() {
		return personId;
	}

	public String getAppSessionId() {
		return appSessionId;
	}

	@Override
	public String toString() {
		return "LoginInfoHelperBean [mteid=" + mteid + ", loginId=" + loginId + ", userId=" + userId + ", personId=" + personId + ", appSessionId="
				+ appSessionId + "]";
	}
}