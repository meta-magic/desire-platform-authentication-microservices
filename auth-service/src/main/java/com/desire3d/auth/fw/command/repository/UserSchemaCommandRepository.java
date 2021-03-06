package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.model.transactions.UserSchema;

public interface UserSchemaCommandRepository {

	public UserSchema save(UserSchema userSchema) throws PersistenceFailureException;

	public void update(UserSchema userSchema) throws PersistenceFailureException;

}