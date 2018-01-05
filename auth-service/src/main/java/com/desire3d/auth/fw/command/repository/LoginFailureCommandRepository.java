package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.model.transactions.LoginFailure;

public interface LoginFailureCommandRepository {

	public LoginFailure save(LoginFailure loginFailure) throws PersistenceFailureException;

}
