package com.desire3d.auth.command.repository;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.fw.command.repository.AuthSchemaCommandRepository;
import com.desire3d.auth.model.transactions.AuthSchema;
import com.desire3d.auth.utils.ExceptionID;

/**
 * @author Mahesh Pardeshi
 *
 */
@Repository
public class AuthSchemaCommandRepositoryImpl implements AuthSchemaCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public AuthSchema save(AuthSchema authSchema) throws PersistenceException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			authSchema = pm.makePersistent(authSchema);
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
		return authSchema;
	}

}