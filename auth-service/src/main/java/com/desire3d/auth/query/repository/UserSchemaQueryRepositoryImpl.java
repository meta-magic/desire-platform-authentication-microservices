package com.desire3d.auth.query.repository;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.fw.query.repository.UserSchemaQueryRepository;
import com.desire3d.auth.model.transactions.UserSchema;

@Repository
public class UserSchemaQueryRepositoryImpl implements UserSchemaQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	/*@Override
	public UserSchema findUserSchemaByUserUUIDAndIsActive(String userUUID, Boolean isActive) throws DataNotFoundException {
		PersistenceManager pm = pmf.getPersistenceManager();
		UserSchema userSchema = null;
		try {
			Query query = pm.newQuery(UserSchema.class);
			query.setFilter("this.userUUID==:userUUID && isActive==:true");
			@SuppressWarnings("unchecked")
			List<UserSchema> userSchemas = ((List<UserSchema>) query.execute(userUUID, true));
			if (userSchemas.isEmpty()) {
				throw new DataNotFoundException(ExceptionID.ERROR_RETRIEVE);
			} else {
				userSchema = pm.detachCopy(userSchemas.get(0));
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
		return userSchema;
	}*/

	@Override
	public UserSchema findUserSchemaByUserUUIDAndIsActive(String userUUID, Boolean isActive) throws DataNotFoundException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query query = pm.newQuery(UserSchema.class);
		query.setFilter("this.userUUID==:userUUID && isActive==:true");

		@SuppressWarnings("unchecked")
		List<UserSchema> userSchemas = ((List<UserSchema>) query.execute(userUUID, true));
		if (userSchemas.isEmpty()) {
			throw new DataNotFoundException("Data not found exception %s");
		} else {
			return userSchemas.get(0);
		}
	}
}