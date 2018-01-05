package com.desire3d.auth.fw.query.repository;

import java.util.List;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.model.transactions.AuthSchema;

public interface AuthSchemaQueryRepository {

	public AuthSchema findAuthSchemaByLoginIdAndIsActive(String loginId, Boolean isActive) throws DataRetrievalFailureException;

	public List<AuthSchema> findByLoginIdAndIsActive(String loginId, boolean isActive) throws DataRetrievalFailureException;

}