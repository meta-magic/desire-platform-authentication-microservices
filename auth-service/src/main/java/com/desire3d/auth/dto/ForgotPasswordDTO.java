package com.desire3d.auth.dto;

import java.io.Serializable;

public class ForgotPasswordDTO implements Serializable {

	private static final long serialVersionUID = 1888463898012238117L;

	private String loginId;

	private String onetimetoken;

	private String newPassword;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getOnetimetoken() {
		return onetimetoken;
	}

	public void setOnetimetoken(String onetimetoken) {
		this.onetimetoken = onetimetoken;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
