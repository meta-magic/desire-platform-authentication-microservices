package com.desire3d.auth.query.repository;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.fw.query.repository.AppSessionQueryRepository;
import com.desire3d.auth.model.transactions.AppSession;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class AppSessionQueryRepositoryImpl implements AppSessionQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public AppSession findAppSessionByAppSessionIdAndIsActive(String appSessionId, Boolean isActive) throws DataNotFoundException {
		PersistenceManager pm = pmf.getPersistenceManager();
		AppSession appSession = null;
		try {
			Query query = pm.newQuery(AppSession.class);
			query.setFilter("this.appSessionId==:appSessionId && isActive==:true");

			@SuppressWarnings("unchecked")
			List<AppSession> appSessions = ((List<AppSession>) query.execute(appSessionId, true));
			if (appSessions.isEmpty()) {
				throw new DataNotFoundException(ExceptionID.ERROR_RETRIEVE);
			} else {
				appSession = appSessions.get(0);
			}
		} catch (Throwable e) {
			if (e instanceof DataNotFoundException) {
				throw e;
			} else {
				e.printStackTrace();
				throw new DataNotFoundException(e.getMessage(), e);
			}
		} finally {
			pm.close();
		}
		return appSession;
	}
}