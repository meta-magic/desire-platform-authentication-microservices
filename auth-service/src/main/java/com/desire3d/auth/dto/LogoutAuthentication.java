package com.desire3d.auth.dto;

import java.io.Serializable;

public class LogoutAuthentication implements Serializable {

	private static final long serialVersionUID = -8187552386944338547L;

	private Double latitude;

	private Double longitude;

	public LogoutAuthentication() {
		super();
	}

	public LogoutAuthentication(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
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
