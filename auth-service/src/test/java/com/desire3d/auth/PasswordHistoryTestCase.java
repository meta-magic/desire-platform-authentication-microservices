package com.desire3d.auth;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.fw.command.repository.PasswordHistoryCommandRepository;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.PasswordHistory;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PasswordHistoryTestCase {

	//	@Autowired
	private PasswordHistoryCommandRepository passwordHistoryCommandRepository;

	//	@Test
	public void save() {
		PasswordHistory passwordHistory = new PasswordHistory();
		passwordHistory.setMteid("");
		passwordHistory.setIsActive(true);
		passwordHistory.setPasswordHash("");
		passwordHistory.setUserUUID("");
		passwordHistory.setAuditDetails(new AuditDetails("", new Date(), "", new Date()));
		try {
			passwordHistoryCommandRepository.save(passwordHistory);
		} catch (PersistenceFailureException e) {
			e.printStackTrace();
		}
	}

}
