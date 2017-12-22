package com.desire3d.auth.fw.command.repository;

import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.model.transactions.AppSession;

public interface AppSessionCommandRepository {

	//	public Mono<Mono<AppSession>> save(Mono<AppSession> appSession);

	public AppSession delete(String appSessionId);

	public AppSession update(AppSession appSession) throws PersistenceException;

	public AppSession save(AppSession appSession) throws PersistenceException;

}
