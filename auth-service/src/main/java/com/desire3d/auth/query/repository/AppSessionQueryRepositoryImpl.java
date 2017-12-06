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
				throw new DataNotFoundException("Data not found exception %s");
			} else {
				appSession = appSessions.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return appSession;
	}
}