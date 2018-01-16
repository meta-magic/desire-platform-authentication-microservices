package com.desire3d.auth.query.repository;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.fw.query.repository.PasswordSchemaQueryRepository;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class PasswordSchemaQueryRepositoryImpl implements PasswordSchemaQueryRepository {
	
	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public PasswordSchema findByUserUUID(String userUUID) throws DataRetrievalFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		PasswordSchema passwordSchema = null;
		try {
			Query query = (pm.newQuery(PasswordSchema.class));
			query.setFilter("this.userUUID==:userUUID && this.isActive==:isActive");

			@SuppressWarnings("unchecked")
			Collection<PasswordSchema> passwordSchemas = (Collection<PasswordSchema>) query.execute(userUUID, true);
			if (passwordSchemas.isEmpty()) {
				throw new DataRetrievalFailureException(ExceptionID.ERROR_RETRIEVE);
			} else {
				passwordSchema = pm.detachCopy(passwordSchemas.iterator().next());
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
		return passwordSchema;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PasswordSchema> findAll() throws DataRetrievalFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		List<PasswordSchema> passwordSchemas = null;
		try {
			Query query = pm.newQuery(PasswordSchema.class);
			query.setFilter("isActive==:true");
			passwordSchemas = ((List<PasswordSchema>) query.execute(true));
		} catch (Throwable e) {
			e.printStackTrace();
			throw new DataRetrievalFailureException(ExceptionID.ERROR_RETRIEVE, e);
		} finally {
			pm.close();
		}
		return passwordSchemas;
	}

}
