/**
 * DTO to capture user name authentication data for Login ID validation
 */
package com.desire3d.auth.dto;

import java.io.Serializable;

/**
 * @author Mahesh Pardeshi
 *
 */
public class UsernameAuthentication implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String loginId;

	private String token;

	private String newPassword;

	public UsernameAuthentication() {
		super();
	}

	public UsernameAuthentication(String loginId) {
		super();
		this.loginId = loginId;
	}

	public UsernameAuthentication(String loginId, String token, String newPassword) {
		super();
		this.loginId = loginId;
		this.token = token;
		this.newPassword = newPassword;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}