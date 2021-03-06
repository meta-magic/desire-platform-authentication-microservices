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
import com.desire3d.auth.fw.command.repository.PasswordSchemaCommandRepository;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class PasswordSchemaCommandRepositoryImpl implements PasswordSchemaCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	private Logger LOGGER = LoggerFactory.getLogger(PasswordSchemaCommandRepositoryImpl.class);

	@Override
	public PasswordSchema save(PasswordSchema passwordSchema) throws PersistenceFailureException {
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
			LOGGER.error(new Date() + " [ " + "Password Schema save failed. " + "]");

			throw new PersistenceFailureException(ExceptionID.ERROR_SAVE, e);
		} finally {
			pm.close();
		}
		return passwordSchema;
	}

	@Override
	public void update(PasswordSchema passwordSchema) throws PersistenceFailureException {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(passwordSchema);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			LOGGER.error(new Date() + " [ " + "Password History update failed for passwordId  '{}'",
					passwordSchema.getPasswordUUID() + "]");

			throw new PersistenceFailureException(ExceptionID.ERROR_UPDATE, e);
		} finally {
			pm.close();
		}
	}
}
