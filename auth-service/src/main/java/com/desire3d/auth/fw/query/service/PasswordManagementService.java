package com.desire3d.auth.fw.query.service;

import com.desire3d.auth.dto.PasswordDTO;

public interface PasswordManagementService {

	public void resetPassword(PasswordDTO passwordDTO) throws Throwable;

}