package com.desire3d.auth.fw.query.repository;

import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.model.transactions.PasswordSchema;

public interface PasswordSchemaQueryRepository {

	public PasswordSchema findPasswordSchemaByUserUUIDAndIsActive(String userUUID, Boolean isActive) throws DataNotFoundException;

	public Iterable<PasswordSchema> findByIsActive(Boolean isActive);
}
