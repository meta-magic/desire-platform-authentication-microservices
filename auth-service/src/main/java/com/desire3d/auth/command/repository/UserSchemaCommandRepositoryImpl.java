package com.desire3d.auth.command.repository;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.fw.command.repository.UserSchemaCommandRepository;
import com.desire3d.auth.model.transactions.UserSchema;

/**
 * @author Mahesh Pardeshi
 *
 */
@Repository
@Transactional
public class UserSchemaCommandRepositoryImpl implements UserSchemaCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public UserSchema save(UserSchema userSchema) throws PersistenceException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			userSchema = pm.makePersistent(userSchema);
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
		return userSchema;
	}
}
