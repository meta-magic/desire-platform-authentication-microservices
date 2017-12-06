package com.desire3d.auth.command.repository;

import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.fw.command.repository.LoginHistoryCommandRepository;
import com.desire3d.auth.model.transactions.LoginHistory;

@Repository
public class LoginHistoryCommandRepositoryImpl implements LoginHistoryCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public LoginHistory save(LoginHistory loginHistory) {
		return pmf.getPersistenceManager().makePersistent(loginHistory);
	}

}
