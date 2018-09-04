package com.desire3d.auth.command.repository;

import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.fw.command.repository.RecoveryTokenCommandRepository;
import com.desire3d.auth.model.transactions.RecoveryToken;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class RecoveryTokenCommandRepositoryImpl implements RecoveryTokenCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	private Logger LOGGER = LoggerFactory.getLogger(RecoveryTokenCommandRepositoryImpl.class);

	@Override
	public RecoveryToken save(RecoveryToken recoveryToken) throws PersistenceFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			recoveryToken = pm.makePersistent(recoveryToken);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			LOGGER.error(new Date() + " [ " + "Recovery Token save failed. " + "]");

			throw new PersistenceFailureException(ExceptionID.ERROR_SAVE, e);
		} finally {
			pm.close();
		}
		return recoveryToken;
	}

}
