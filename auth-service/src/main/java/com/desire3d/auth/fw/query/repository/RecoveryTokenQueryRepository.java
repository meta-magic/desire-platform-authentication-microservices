package com.desire3d.auth.fw.query.repository;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.model.transactions.RecoveryToken;

public interface RecoveryTokenQueryRepository {

	public RecoveryToken findById(String tokenId) throws DataRetrievalFailureException;

}
