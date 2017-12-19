package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.model.transactions.LoginHistory;

public interface LoginHistoryCommandRepository {

	public LoginHistory save(LoginHistory loginHistory) throws PersistenceException;

}
