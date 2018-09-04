package com.desire3d.auth.query.repository;

import java.util.Collection;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.fw.query.repository.AppSessionQueryRepository;
import com.desire3d.auth.model.transactions.AppSession;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class AppSessionQueryRepositoryImpl implements AppSessionQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	private Logger LOGGER = LoggerFactory.getLogger(AppSessionQueryRepositoryImpl.class);

	@Override
	public AppSession findAppSessionByAppSessionIdAndIsActive(String appSessionId, Boolean isActive)
			throws DataRetrievalFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		AppSession appSession = null;
		try {
			Query query = pm.newQuery(AppSession.class);
			query.setFilter("this.appSessionId==:appSessionId && isActive==:true");

			@SuppressWarnings("unchecked")
			Collection<AppSession> appSessions = ((Collection<AppSession>) query.execute(appSessionId, true));
			if (appSessions.isEmpty()) {
				throw new DataRetrievalFailureException(ExceptionID.ERROR_RETRIEVE);
			} else {
				appSession = pm.detachCopy(appSessions.iterator().next());
			}
		} catch (Throwable e) {
			if (e instanceof DataRetrievalFailureException) {
				throw e;
			} else {
				e.printStackTrace();
				LOGGER.error(new Date() + " [ " + "App Session Data retrieve failed for AppSessionId '{}' ",
						appSessionId + "]");
				throw new DataRetrievalFailureException(e.getMessage(), e);
			}
		} finally {
			pm.close();
		}
		return appSession;
	}
}