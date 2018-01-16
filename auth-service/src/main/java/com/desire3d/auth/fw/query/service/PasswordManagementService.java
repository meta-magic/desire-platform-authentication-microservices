package com.desire3d.auth.fw.query.service;

import com.desire3d.auth.dto.PasswordDTO;
import com.desire3d.auth.dto.UsernameAuthentication;

public interface PasswordManagementService {

	public void resetPassword(PasswordDTO passwordDTO) throws Throwable;

	public void sendRecoveryToken(UsernameAuthentication usernameAuthentication) throws Throwable;

	public void forgotPassword(UsernameAuthentication usernameAuthentication) throws Throwable;

}