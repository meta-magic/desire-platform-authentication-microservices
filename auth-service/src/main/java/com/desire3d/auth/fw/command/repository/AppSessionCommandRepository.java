package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.model.transactions.AppSession;

public interface AppSessionCommandRepository {

	public AppSession update(AppSession appSession) throws PersistenceFailureException;

	public AppSession save(AppSession appSession) throws PersistenceFailureException;

}
