package com.desire3d.auth.fw.query.repository;

import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.model.transactions.LoginHistory;

public interface LoginHistoryQueryRepository {
	public LoginHistory findLoginHistoryByAppSessionIDAndUserUUIDAndIsActive(String appSessionId, String userUUID, Boolean isActive)
			throws DataNotFoundException;

}
