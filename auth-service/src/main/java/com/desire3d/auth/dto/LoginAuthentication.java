/**
 * DTO to capture Login Authentication data for User Login validation
 */
package com.desire3d.auth.dto;

import java.io.Serializable;

/**
 * @author Mahesh Pardeshi
 *
 */
public class LoginAuthentication extends UsernameAuthentication implements Serializable {

	private static final long serialVersionUID = 1L;

	private String password;

	public LoginAuthentication() {
		super();
	}

	public LoginAuthentication(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}