package com.desire3d.auth.fw.query.repository;

import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.model.transactions.UserSchema;

public interface UserSchemaQueryRepository {

	public UserSchema findUserSchemaByUserUUIDAndIsActive(String userUUID, Boolean isActive) throws DataNotFoundException;
}
