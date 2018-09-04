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
import com.desire3d.auth.fw.query.repository.RecoveryTokenQueryRepository;
import com.desire3d.auth.model.transactions.RecoveryToken;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class RecoveryTokenQueryRepositoryImpl implements RecoveryTokenQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	private Logger LOGGER = LoggerFactory.getLogger(RecoveryTokenQueryRepositoryImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public RecoveryToken findById(String tokenId) throws DataRetrievalFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		RecoveryToken recoveryToken = null;
		try {
			Query query = (pm.newQuery(RecoveryToken.class));
			query.setFilter("this.tokenId==:tokenId");

			Collection<RecoveryToken> recoveryTokens = (Collection<RecoveryToken>) query.execute(tokenId);
			if (recoveryTokens.isEmpty()) {
				LOGGER.error(new Date() + " [ " + "Recovery Token retrieve failed for tokenId '{}' ", tokenId + "]");
				throw new DataRetrievalFailureException(ExceptionID.ERROR_RETRIEVE);
			} else {
				recoveryToken = pm.detachCopy(recoveryTokens.iterator().next());
			}
		} catch (Throwable e) {
			if (e instanceof DataRetrievalFailureException) {
				throw e;
			} else {
				e.printStackTrace();
				throw new DataRetrievalFailureException(e.getMessage(), e);
			}
		} finally {
			pm.close();
		}
		return recoveryToken;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RecoveryToken findByToken(String token) throws DataRetrievalFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		RecoveryToken recoveryToken = null;
		try {
			Query query = (pm.newQuery(RecoveryToken.class));
			query.setFilter("this.token==:token");

			Collection<RecoveryToken> recoveryTokens = (Collection<RecoveryToken>) query.execute(token);
			if (recoveryTokens.isEmpty()) {
				LOGGER.error(new Date() + " [ " + "Recovery Token retrieve failed for token '{}' ", token + "]");
				throw new DataRetrievalFailureException(ExceptionID.ERROR_RETRIEVE);
			} else {
				recoveryToken = pm.detachCopy(recoveryTokens.iterator().next());
			}
		} catch (Throwable e) {
			if (e instanceof DataRetrievalFailureException) {
				throw e;
			} else {
				LOGGER.error(new Date() + " [ " + "Recovery Tokena retrieve failed for userId '{}' ", token + "]");
				e.printStackTrace();
				throw new DataRetrievalFailureException(e.getMessage(), e);
			}
		} finally {
			pm.close();
		}
		return recoveryToken;
	}
}
