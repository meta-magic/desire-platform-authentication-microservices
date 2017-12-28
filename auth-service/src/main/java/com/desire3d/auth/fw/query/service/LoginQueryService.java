package com.desire3d.auth.fw.query.service;

import javax.servlet.http.HttpServletRequest;

public interface LoginQueryService {

	public boolean userLogout(HttpServletRequest request) throws Throwable;

}