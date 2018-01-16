package com.desire3d.auth;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.fw.query.repository.AuthSchemaQueryRepository;
import com.desire3d.auth.model.transactions.AuthSchema;

//@RunWith(SpringRunner.class)
//@SpringBootTest

public class AuthSchemaTestCase {

	@Autowired
	private AuthSchemaQueryRepository authSchemaQueryRepository;

//	@Test
//	public void findById() {
//		_findById(_findId());
//	}

//	@Test
	private AuthSchema findById(String loginId) {
		try {
			return authSchemaQueryRepository.findAuthSchemaByLoginId(loginId);
		} catch (DataRetrievalFailureException e) {
			e.printStackTrace();
		}
		return null;
	}
}
