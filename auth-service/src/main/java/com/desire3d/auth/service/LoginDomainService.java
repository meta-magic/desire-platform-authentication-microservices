package com.desire3d.auth.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.fw.command.repository.AuthSchemaCommandRepository;
import com.desire3d.auth.fw.command.repository.PasswordSchemaCommandRepository;
import com.desire3d.auth.fw.command.repository.UserSchemaCommandRepository;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.AuthSchema;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.model.transactions.UserSchema;
import com.desire3d.auth.utils.HashingAlgorithms;
import com.desire3d.event.UserCreatedEvent;
import com.desire3d.event.UserLoginCreatedEvent;

/**
 * @author Mahesh Pardeshi
 *
 */
@Service
public final class LoginDomainService {

	@Autowired
	private UserSchemaCommandRepository userSchemaCommandRepository;

	@Autowired
	private AuthSchemaCommandRepository authSchemaCommandRepository;

	@Autowired
	private PasswordSchemaCommandRepository passwordSchemaCommandRepository;

	/**
	 * Method used to create user login after creation of user account 
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * @return an {@link UserLoginCreatedEvent} 
	 * */
	public UserLoginCreatedEvent createUserLogin(final UserCreatedEvent event, final LoginInfoHelperBean loginInfoHelperBean) throws Exception {
		UserSchema userSchema = createUserSchema(event, loginInfoHelperBean);
		createAuthSchema(event, userSchema, loginInfoHelperBean);
		String password = createPasswordSchema(event, userSchema, loginInfoHelperBean);
		return new UserLoginCreatedEvent(event.getLoginId(), userSchema.getUserUUID(), password, event.getFirstName(), event.getLastName(), event.getEmailId(), event.getPhoneNumber());
	}

	/**
	 * Method used to create user schema
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * @return {@link UserSchema}
	 * */
	private UserSchema createUserSchema(final UserCreatedEvent event, final LoginInfoHelperBean loginInfoHelperBean) {
		UserSchema userSchema = new UserSchema();
		userSchema.setFirstTimeLogin(true);
		userSchema.setUserType("1");
		userSchema.setMteid(loginInfoHelperBean.getMteid());
		userSchema.setAuditDetails(new AuditDetails(loginInfoHelperBean.getUserId(), new Date(), loginInfoHelperBean.getUserId(), new Date()));
		return userSchemaCommandRepository.save(userSchema);
	}

	/**
	 * Method used to create user authentication schema 
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * @return saved {@link AuthSchema} 
	 * */
	private AuthSchema createAuthSchema(final UserCreatedEvent event, final UserSchema userSchema, final LoginInfoHelperBean loginInfoHelperBean) {
		AuthSchema authSchema = new AuthSchema(loginInfoHelperBean.getMteid(), event.getEmailId(), userSchema.getUserUUID(), event.getPersonUUID());
		authSchema.setAuditDetails(new AuditDetails(loginInfoHelperBean.getUserId(), new Date(), loginInfoHelperBean.getUserId(), new Date()));
		return authSchemaCommandRepository.save(authSchema);
	}

	/**
	 * Method used to create password schema 
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * @param userSchema 
	 * @return created password
	 * */
	private String createPasswordSchema(final UserCreatedEvent event, final UserSchema userSchema, final LoginInfoHelperBean loginInfoHelperBean)
			throws Exception {
		String password = event.getFirstName() + "@" + (new Random().nextInt(900) + 100);
		PasswordSchema passwordSchema = new PasswordSchema(loginInfoHelperBean.getMteid(), userSchema.getUserUUID(), createPasswordHash(password));
		passwordSchema.setAuditDetails(new AuditDetails(loginInfoHelperBean.getUserId(), new Date(), loginInfoHelperBean.getUserId(), new Date()));
		passwordSchemaCommandRepository.save(passwordSchema);
		return password;
	}

	/**
	 * Method used to create password based on user's event and MD5 hashing algorithm
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * @return hashed password 
	 * */
	public String createPasswordHash(final String password) throws Exception {
		return HashingAlgorithms.getInstance().createHash(password, HashingAlgorithms.MD5);
	}
}
