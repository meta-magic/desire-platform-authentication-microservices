package com.desire3d.auth.dto;

import java.io.Serializable;

public class LoginResponseDto implements Serializable {

	private static final long serialVersionUID = 6695256963712013952L;

	private String appSessionId;

	private Boolean firstTimeLogin;

	private Integer accountBlocked;

	private Boolean accountExpired;

	private Boolean changePassword;

	private String tokenid;

	public LoginResponseDto(String appSessionId, Boolean firstTimeLogin, Integer accountBlocked, Boolean accountExpired, Boolean changePassword,
			String tokenid) {
		super();
		this.appSessionId = appSessionId;
		this.firstTimeLogin = firstTimeLogin;
		this.accountBlocked = accountBlocked;
		this.accountExpired = accountExpired;
		this.changePassword = changePassword;
		this.tokenid = tokenid;
	}

	public String getAppSessionId() {
		return appSessionId;
	}

	public void setAppSessionId(String appSessionId) {
		this.appSessionId = appSessionId;
	}

	public Boolean getFirstTimeLogin() {
		return firstTimeLogin;
	}

	public void setFirstTimeLogin(Boolean firstTimeLogin) {
		this.firstTimeLogin = firstTimeLogin;
	}

	public Integer getAccountBlocked() {
		return accountBlocked;
	}

	public void setAccountBlocked(Integer accountBlocked) {
		this.accountBlocked = accountBlocked;
	}

	public Boolean getAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public Boolean getChangePassword() {
		return changePassword;
	}

	public void setChangePassword(Boolean changePassword) {
		this.changePassword = changePassword;
	}

	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

}
