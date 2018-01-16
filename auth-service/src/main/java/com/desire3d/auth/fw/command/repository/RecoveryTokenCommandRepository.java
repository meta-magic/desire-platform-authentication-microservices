package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.model.transactions.RecoveryToken;

public interface RecoveryTokenCommandRepository {

	public RecoveryToken save(RecoveryToken recoverytoken) throws PersistenceFailureException;

}
