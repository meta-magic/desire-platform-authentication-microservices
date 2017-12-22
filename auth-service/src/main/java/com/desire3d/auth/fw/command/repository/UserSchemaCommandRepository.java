package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.model.transactions.UserSchema;

public interface UserSchemaCommandRepository {

	public UserSchema save(UserSchema userSchema) throws PersistenceException;

}