package com.desire3d.auth.query.repository;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.fw.query.repository.LoginHistoryQueryRepository;
import com.desire3d.auth.model.transactions.LoginHistory;

@Repository
public class LoginHistoryQueryRepositoryImpl implements LoginHistoryQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public LoginHistory findLoginHistoryByAppSessionIDAndUserUUIDAndIsActive(String appSessionId, String userUUID, Boolean isActive)
			throws DataNotFoundException {

		PersistenceManager pm = pmf.getPersistenceManager();
		LoginHistory loginHistory = null;
		try {
			Query query = pm.newQuery(LoginHistory.class);
			query.setFilter("this.appSessionId==:appSessionId && this.userUUID==:userUUID  && isActive==:true");

			@SuppressWarnings("unchecked")
			List<LoginHistory> loginHistories = ((List<LoginHistory>) query.execute(appSessionId, userUUID, true));
			if (loginHistories.isEmpty()) {
				throw new DataNotFoundException("Data not found exception %s");
			} else {
				loginHistory = loginHistories.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return loginHistory;
	}

}
