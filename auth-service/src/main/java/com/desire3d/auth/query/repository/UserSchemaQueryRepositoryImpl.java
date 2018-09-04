package com.desire3d.auth.query.repository;

import java.util.Collection;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.fw.query.repository.UserSchemaQueryRepository;
import com.desire3d.auth.model.transactions.UserSchema;
import com.desire3d.auth.utils.ExceptionID;

@Repository
public class UserSchemaQueryRepositoryImpl implements UserSchemaQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	private Logger LOGGER = LoggerFactory.getLogger(UserSchemaQueryRepositoryImpl.class);

	@Override
	public UserSchema findById(String userUUID) throws DataRetrievalFailureException {
		PersistenceManager pm = pmf.getPersistenceManager();
		UserSchema userSchema = null;
		try {
			Query query = (pm.newQuery(UserSchema.class));
			query.setFilter("this.userUUID==:userUUID && isActive==:isActive");

			@SuppressWarnings("unchecked")
			Collection<UserSchema> userSchemas = (Collection<UserSchema>) query.execute(userUUID, true);
			if (userSchemas.isEmpty()) {
				LOGGER.error(new Date() + " [ " + "User Schema retrieve failed for userId '{}' ", userUUID + "]");
				throw new DataRetrievalFailureException(ExceptionID.ERROR_RETRIEVE);
			} else {
				userSchema = pm.detachCopy(userSchemas.iterator().next());
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
		return userSchema;
	}
}