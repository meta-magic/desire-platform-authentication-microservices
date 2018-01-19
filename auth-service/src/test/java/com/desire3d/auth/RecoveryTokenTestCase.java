package com.desire3d.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.desire3d.auth.dto.ForgotPasswordDTO;
import com.desire3d.auth.dto.UsernameAuthentication;
import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.fw.command.repository.RecoveryTokenCommandRepository;
import com.desire3d.auth.fw.query.repository.RecoveryTokenQueryRepository;
import com.desire3d.auth.fw.query.service.PasswordManagementService;
import com.desire3d.auth.model.transactions.RecoveryToken;

import atg.taglib.json.util.JSONException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RecoveryTokenTestCase {

//	@Autowired
	private PasswordManagementService managementService;

//	@Autowired
	private RecoveryTokenCommandRepository tokenCommandRepository;

	//	@Test
	public void tokenGeneratedEvent() throws PersistenceFailureException {
		try {
			managementService.sendRecoveryToken(new UsernameAuthentication("admin@metamagic.in"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	//	@Test
	public void testSave() {
		RecoveryToken recoveryToken = new RecoveryToken();
		recoveryToken.setToken("");
		recoveryToken.setTokenId("");
		recoveryToken.setPersonId("");
		recoveryToken.setTokenExpiry(60000L);
		try {
			System.out.println("**" + tokenCommandRepository.save(recoveryToken));
		} catch (PersistenceFailureException e) {
			e.printStackTrace();
		}
	}

	//	@Test
	public void validatetoken() throws Throwable {
		try {
			ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
			managementService.forgotPassword(forgotPasswordDTO);
		} catch (Throwable e) {
			System.out.println("**Invalid");
		}
	}

}
