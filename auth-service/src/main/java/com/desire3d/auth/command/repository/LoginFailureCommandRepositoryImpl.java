package com.desire3d.auth.command.repository;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.fw.command.repository.LoginFailureCommandRepository;
import com.desire3d.auth.model.transactions.LoginFailure;

@Repository
public class LoginFailureCommandRepositoryImpl implements LoginFailureCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public LoginFailure save(LoginFailure loginFailure) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			loginFailure = pm.makePersistent(loginFailure);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			pm.close();
		}
		return loginFailure;
	}

}
