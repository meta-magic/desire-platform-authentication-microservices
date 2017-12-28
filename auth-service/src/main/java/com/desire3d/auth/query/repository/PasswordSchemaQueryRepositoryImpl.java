package com.desire3d.auth.query.repository;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.fw.query.repository.PasswordSchemaQueryRepository;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class PasswordSchemaQueryRepositoryImpl implements PasswordSchemaQueryRepository {
	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public PasswordSchema findPasswordSchemaByUserUUIDAndIsActive(String userUUID, Boolean isActive) throws DataNotFoundException {
		PersistenceManager pm = pmf.getPersistenceManager();
		PasswordSchema passwordSchema = null;
		try {
			Query query = pm.newQuery(PasswordSchema.class);
			query.setFilter("this.userUUID==:userUUID && isActive==:true");

			@SuppressWarnings("unchecked")
			List<PasswordSchema> passwordSchemas = ((List<PasswordSchema>) query.execute(userUUID, true));
			if (passwordSchemas.isEmpty()) {
				throw new DataNotFoundException(ExceptionID.ERROR_RETRIEVE);
			} else {
				passwordSchema = passwordSchemas.get(0);
			}
		} catch (Throwable e) {
			if (e instanceof DataNotFoundException) {
				throw e;
			} else {
				e.printStackTrace();
				throw new DataNotFoundException(e.getMessage(), e);
			}
		} finally {
			pm.close();
		}
		return passwordSchema;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PasswordSchema> findByIsActive(Boolean isActive) throws DataNotFoundException {
		PersistenceManager pm = pmf.getPersistenceManager();
		List<PasswordSchema> passwordSchemas = null;
		try {
			Query query = pm.newQuery(PasswordSchema.class);
			query.setFilter("isActive==:true");
			passwordSchemas = ((List<PasswordSchema>) query.execute(true));
		} catch (Throwable e) {
			e.printStackTrace();
			throw new DataNotFoundException(ExceptionID.ERROR_RETRIEVE, e);
		} finally {
			pm.close();
		}
		return passwordSchemas;
	}

}
