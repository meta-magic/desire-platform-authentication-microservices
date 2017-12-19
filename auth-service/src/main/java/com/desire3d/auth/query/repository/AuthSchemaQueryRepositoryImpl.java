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

@Repository
public class AuthSchemaQueryRepositoryImpl implements AuthSchemaQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@SuppressWarnings("unchecked")
	@Override
	public AuthSchema findAuthSchemaByLoginIdAndIsActive(String loginId, Boolean isActive)
			throws DataNotFoundException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query query = pm.newQuery(AuthSchema.class);
		query.setFilter("this.loginId==:loginId && this.isActive==:isActive");
		List<AuthSchema> schemas = ((List<AuthSchema>) query.execute(loginId, true));
		System.out.println("result : " + schemas);
		if (schemas.isEmpty()) {
			throw new DataNotFoundException("Data not found exception %s");
		} else {
			return schemas.get(0);
		}
	}

	public List<AuthSchema> findByLoginIdAndIsActive(String loginId, boolean isActive) throws DataNotFoundException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query query = pm.newQuery(AuthSchema.class);
		query.setFilter("this.loginId==:loginId && this.isActive==:isActive");
		List<AuthSchema> schemas = (List<AuthSchema>) query.execute(loginId, true);
		if (schemas.isEmpty()) {
			throw new DataNotFoundException("Data not found exception %s");
		} else {
			return schemas;
		}
	}
}