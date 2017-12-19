package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.model.transactions.AuthSchema;

public interface AuthSchemaCommandRepository {

	public AuthSchema save(AuthSchema authSchema) throws PersistenceException;
}
