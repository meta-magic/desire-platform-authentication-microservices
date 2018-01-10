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

	private Double latitude;

	private Double longitude;

	public LoginAuthentication() {
		super();
	}

	public LoginAuthentication(String loginId, String password, Double latitude, Double longitude) {
		this.loginId = loginId;
		this.password = password;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}