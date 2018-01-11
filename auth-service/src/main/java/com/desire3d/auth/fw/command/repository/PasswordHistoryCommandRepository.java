package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.model.transactions.PasswordHistory;

public interface PasswordHistoryCommandRepository {

	public PasswordHistory save(PasswordHistory passwordHistory) throws PersistenceFailureException;

}
