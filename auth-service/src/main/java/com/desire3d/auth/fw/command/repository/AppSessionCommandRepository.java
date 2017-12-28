package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.model.transactions.AppSession;

public interface AppSessionCommandRepository {

	public AppSession update(AppSession appSession) throws PersistenceException;

	public AppSession save(AppSession appSession) throws PersistenceException;

}
