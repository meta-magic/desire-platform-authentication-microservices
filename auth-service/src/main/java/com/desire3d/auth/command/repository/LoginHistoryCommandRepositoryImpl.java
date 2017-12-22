package com.desire3d.auth.command.repository;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.fw.command.repository.LoginHistoryCommandRepository;
import com.desire3d.auth.model.transactions.LoginHistory;

@Repository
public class LoginHistoryCommandRepositoryImpl implements LoginHistoryCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public LoginHistory save(LoginHistory loginHistory) throws PersistenceException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			loginHistory = pm.makePersistent(loginHistory);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new PersistenceException(e.getMessage());
		} finally {
			pm.close();
		}
		return loginHistory;
	}

}
