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

	public UsernameAuthentication() {
		super();
	}

	public UsernameAuthentication(String loginId) {
		super();
		this.loginId = loginId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

}