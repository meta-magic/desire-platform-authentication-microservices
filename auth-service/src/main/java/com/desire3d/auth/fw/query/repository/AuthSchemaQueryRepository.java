package com.desire3d.auth.fw.query.repository;

import java.util.Collection;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.model.transactions.AuthSchema;

public interface AuthSchemaQueryRepository {

	public AuthSchema findAuthSchemaByLoginId(String loginId) throws DataRetrievalFailureException;

	public Collection<AuthSchema> findByLoginId(String loginId) throws DataRetrievalFailureException;

}