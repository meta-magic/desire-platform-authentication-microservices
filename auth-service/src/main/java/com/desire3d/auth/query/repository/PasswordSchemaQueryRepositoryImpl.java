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

@Repository
public class PasswordSchemaQueryRepositoryImpl implements PasswordSchemaQueryRepository {
	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public PasswordSchema findPasswordSchemaByUserUUIDAndIsActive(String userUUID, Boolean isActive) throws DataNotFoundException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query query = pm.newQuery(PasswordSchema.class);
		query.setFilter("this.userUUID==:userUUID && isActive==:true");

		@SuppressWarnings("unchecked")
		List<PasswordSchema> passwordSchema = ((List<PasswordSchema>) query.execute(userUUID, true));
		if (passwordSchema.isEmpty()) {
			throw new DataNotFoundException("Data not found exception %s");
		} else {
			return passwordSchema.get(0);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PasswordSchema> findByIsActive(Boolean isActive) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query query = pm.newQuery(PasswordSchema.class);
		query.setFilter("isActive==:true");
		List<PasswordSchema> schemas = (List<PasswordSchema>) query.execute(true);
		return schemas;

	}

}
