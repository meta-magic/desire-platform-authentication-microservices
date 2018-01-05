package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.model.transactions.PasswordSchema;

public interface PasswordSchemaCommandRepository {

	public PasswordSchema save(PasswordSchema passwordSchema) throws PersistenceFailureException;

}