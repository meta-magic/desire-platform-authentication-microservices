package com.desire3d.auth.query.repository;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.fw.query.repository.AuthSchemaQueryRepository;
import com.desire3d.auth.model.transactions.AuthSchema;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class AuthSchemaQueryRepositoryImpl implements AuthSchemaQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@SuppressWarnings("unchecked")
	@Override
	public AuthSchema findAuthSchemaByLoginIdAndIsActive(String loginId, Boolean isActive) throws DataNotFoundException {
		PersistenceManager pm = pmf.getPersistenceManager();
		AuthSchema schema = null;
		try {
			Query query = pm.newQuery(AuthSchema.class);
			query.setFilter("this.loginId==:loginId && this.isActive==:isActive");
			List<AuthSchema> schemas = ((List<AuthSchema>) query.execute(loginId, true));
			if (schemas.isEmpty()) {
				throw new DataNotFoundException(ExceptionID.ERROR_RETRIEVE);
			} else {
				schema = schemas.get(0);
			}
		} catch (Throwable e) {
			if (e instanceof DataNotFoundException) {
				throw e;
			} else {
				e.printStackTrace();
				throw new DataNotFoundException(e.getMessage(), e);
			}
		} finally {
//			pm.close();
		}
		return schema;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthSchema> findByLoginIdAndIsActive(String loginId, boolean isActive) throws DataNotFoundException {
		PersistenceManager pm = pmf.getPersistenceManager();
		List<AuthSchema> schemas = null;
		try {
			Query query = pm.newQuery(AuthSchema.class);
			query.setFilter("this.loginId==:loginId && this.isActive==:isActive");
			schemas = ((List<AuthSchema>) query.execute(loginId, true));
		} catch (Throwable e) {
			e.printStackTrace();
			throw new DataNotFoundException(ExceptionID.ERROR_RETRIEVE, e);
		} finally {
			pm.close();
		}
		return schemas;

	}
}