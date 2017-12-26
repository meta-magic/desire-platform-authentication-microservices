package com.desire3d.auth.fw.query.service;

import com.desire3d.auth.dto.LoginResponseDto;
import com.desire3d.auth.exceptions.BusinessServiceException;
import com.desire3d.auth.exceptions.PersistenceException;

public interface AuthQueryService {

	public boolean validateLoginId(String loginId) throws BusinessServiceException, PersistenceException;

	public LoginResponseDto authenticate(String loginId, String password) throws Throwable;

}
