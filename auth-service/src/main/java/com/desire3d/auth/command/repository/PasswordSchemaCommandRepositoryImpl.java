package com.desire3d.auth.command.repository;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.fw.command.repository.PasswordSchemaCommandRepository;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class PasswordSchemaCommandRepositoryImpl implements PasswordSchemaCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public PasswordSchema save(PasswordSchema passwordSchema) throws PersistenceException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			passwordSchema = pm.makePersistent(passwordSchema);
			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new PersistenceException(ExceptionID.ERROR_PERSISTENCE, e);
		} finally {
			pm.close();
		}
		return passwordSchema;
	}
}