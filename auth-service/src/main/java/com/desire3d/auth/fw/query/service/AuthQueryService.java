package com.desire3d.auth.fw.query.service;

import javax.servlet.http.HttpServletRequest;

import com.desire3d.auth.dto.LoginResponseDto;

public interface AuthQueryService {

	public boolean validateLoginId(String loginId) throws Throwable;

	public LoginResponseDto authenticate(String loginId, String password, HttpServletRequest request) throws Throwable;

}
