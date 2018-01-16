package com.desire3d.auth.query.repository;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.fw.query.repository.AuthSchemaQueryRepository;
import com.desire3d.auth.model.transactions.AuthSchema;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class AuthSchemaQueryRepositoryImpl implements AuthSchemaQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@SuppressWarnings("unchecked")
	@Override
	public AuthSchema findAuthSchemaByLoginId(String loginId) throws DataRetrievalFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		AuthSchema authSchema = null;
		try {
			Query query = (pm.newQuery(AuthSchema.class));
			query.setFilter("this.loginId==:loginId && this.isActive==:isActive");

			Collection<AuthSchema> authSchemas = (Collection<AuthSchema>) query.execute(loginId, true);
			if (authSchemas.isEmpty()) {
				throw new DataRetrievalFailureException(ExceptionID.ERROR_RETRIEVE);
			} else {
				authSchema = pm.detachCopy(authSchemas.iterator().next());
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
		return authSchema;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<AuthSchema> findByLoginId(String loginId) throws DataRetrievalFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			Query query = pm.newQuery(AuthSchema.class);
			query.setFilter("this.loginId==:loginId && this.isActive==:isActive");
			Collection<AuthSchema> authSchemas = (Collection<AuthSchema>) query.execute(loginId, true);
			return pm.detachCopyAll(authSchemas);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new DataRetrievalFailureException(ExceptionID.ERROR_RETRIEVE, e);
		} finally {
			pm.close();
		}
	}
}