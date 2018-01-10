package com.desire3d.auth.fw.query.service;

import javax.servlet.http.HttpServletRequest;

public interface LoginQueryService {

	public boolean userLogout(Double latitude, Double longitude, HttpServletRequest request) throws Throwable;

}