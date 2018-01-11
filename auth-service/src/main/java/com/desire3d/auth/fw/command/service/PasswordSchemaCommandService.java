package com.desire3d.auth.fw.command.service;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.exceptions.PersistenceFailureException;

public interface PasswordSchemaCommandService {

	public void update(String newPasswordHash) throws PersistenceFailureException, DataRetrievalFailureException;

}
