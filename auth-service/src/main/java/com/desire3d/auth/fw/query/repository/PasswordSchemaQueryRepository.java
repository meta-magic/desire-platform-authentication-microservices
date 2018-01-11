package com.desire3d.auth.fw.query.repository;

import java.util.Collection;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.model.transactions.PasswordSchema;

public interface PasswordSchemaQueryRepository {

	public PasswordSchema findByUserUUID(String userUUID) throws DataRetrievalFailureException;

	public Collection<PasswordSchema> findAll() throws DataRetrievalFailureException;

}
