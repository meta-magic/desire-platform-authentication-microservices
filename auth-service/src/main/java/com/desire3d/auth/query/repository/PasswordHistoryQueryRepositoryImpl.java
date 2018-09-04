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
import com.desire3d.auth.fw.query.repository.PasswordHistoryQueryRepository;
import com.desire3d.auth.model.transactions.PasswordHistory;
import com.desire3d.auth.utils.Constants;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class PasswordHistoryQueryRepositoryImpl implements PasswordHistoryQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	private Logger LOGGER = LoggerFactory.getLogger(PasswordHistoryQueryRepositoryImpl.class);

	@Override
	public Collection<PasswordHistory> findByUserUUID(String userUUID) throws DataRetrievalFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			Query query = pm.newQuery(PasswordHistory.class);
			query.setFilter("this.userUUID==:userUUID && this.isActive==:isActive");
			query.setOrdering("this.auditDetails.createdTime descending");
			query.setRange(0, Constants.PASSWORDHISTORY_LIMIT);
			@SuppressWarnings("unchecked")
			Collection<PasswordHistory> passwordHistories = (Collection<PasswordHistory>) query.execute(userUUID, true);
			return pm.detachCopyAll(passwordHistories);

		} catch (Throwable e) {
			e.printStackTrace();
			LOGGER.error(new Date() + " [ " + "Password History Data retrieve failed for userId '{}' ", userUUID + "]");

			throw new DataRetrievalFailureException(ExceptionID.ERROR_RETRIEVE, e);
		} finally {
			pm.close();
		}
	}

}