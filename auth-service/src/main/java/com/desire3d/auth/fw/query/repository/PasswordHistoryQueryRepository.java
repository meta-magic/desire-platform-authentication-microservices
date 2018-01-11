package com.desire3d.auth.fw.query.repository;

import java.util.Collection;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.model.transactions.PasswordHistory;

public interface PasswordHistoryQueryRepository {

	public Collection<PasswordHistory> findByUserUUID(String userUUID) throws DataRetrievalFailureException;

}
