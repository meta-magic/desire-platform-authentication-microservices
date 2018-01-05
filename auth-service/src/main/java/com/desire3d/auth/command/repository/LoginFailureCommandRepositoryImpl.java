package com.desire3d.auth.command.repository;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.fw.command.repository.LoginFailureCommandRepository;
import com.desire3d.auth.model.transactions.LoginFailure;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class LoginFailureCommandRepositoryImpl implements LoginFailureCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public LoginFailure save(LoginFailure loginFailure) throws PersistenceFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			loginFailure = pm.makePersistent(loginFailure);
			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new PersistenceFailureException(ExceptionID.ERROR_PERSISTENCE, e);
		} finally {
			pm.close();
		}
		return loginFailure;
	}

}
