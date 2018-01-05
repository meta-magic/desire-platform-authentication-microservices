package com.desire3d.auth.domainservice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.fw.command.repository.AuthSchemaCommandRepository;
import com.desire3d.auth.fw.command.repository.PasswordSchemaCommandRepository;
import com.desire3d.auth.fw.command.repository.UserSchemaCommandRepository;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.AuthSchema;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.model.transactions.UserSchema;
import com.desire3d.auth.utils.HashingAlgorithms;
import com.desire3d.event.EmailNotificationEvent;
import com.desire3d.event.UserCreatedEvent;
import com.desire3d.event.publisher.NotificationEventPublisher;

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

	@Autowired
	private NotificationEventPublisher publisher;

	/**
	 * Method used to create user login after creation of user account 
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * 
	 * */
	public void createUserLogin(final UserCreatedEvent event, final LoginInfoHelperBean loginInfoHelperBean) throws Throwable {
		UserSchema userSchema = createUserSchema(event, loginInfoHelperBean);
		createAuthSchema(event, userSchema, loginInfoHelperBean);
		String password = createPasswordSchema(event, userSchema, loginInfoHelperBean);
		publishLoginCreatedEvents(event, password);
	}

	/**
	 * Method used to create user schema
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * @return {@link UserSchema}
	 * @throws PersistenceFailureException 
	 * */
	private UserSchema createUserSchema(final UserCreatedEvent event, final LoginInfoHelperBean loginInfoHelperBean) throws PersistenceFailureException {
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
	 * @throws PersistenceFailureException 
	 * */
	private AuthSchema createAuthSchema(final UserCreatedEvent event, final UserSchema userSchema, final LoginInfoHelperBean loginInfoHelperBean)
			throws PersistenceFailureException {
		AuthSchema authSchema = new AuthSchema(loginInfoHelperBean.getMteid(), event.getLoginId(), userSchema.getUserUUID(), event.getPersonUUID());
		authSchema.setAuditDetails(new AuditDetails(loginInfoHelperBean.getUserId(), new Date(), loginInfoHelperBean.getUserId(), new Date()));
		return authSchemaCommandRepository.save(authSchema);
	}

	/**
	 * Method used to create password schema 
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * @param userSchema 
	 * @return created password
	 * @throws Exception 
	 * */
	private String createPasswordSchema(final UserCreatedEvent event, final UserSchema userSchema, final LoginInfoHelperBean loginInfoHelperBean)
			throws Throwable {
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
	 * @throws Exception 
	 * */
	public String createPasswordHash(final String password) throws Exception {
		return HashingAlgorithms.getInstance().createHash(password, HashingAlgorithms.MD5);
	}

	/**
	 * Method used publish events after user login created successfully 
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * @param password
	 * */
	private void publishLoginCreatedEvents(final UserCreatedEvent event, final String password) {
		Map<String, Object> loginContext = new HashMap<String, Object>();
		loginContext.put("username", event.getFirstName() + " " + event.getLastName());
		loginContext.put("loginid", event.getLoginId());
		EmailNotificationEvent loginIdNotification = new EmailNotificationEvent(event.getEmailId(), "User Registration", "a5cbc718-c650-440d-a23d-f2185b80f431",
				loginContext);
		publisher.publish(loginIdNotification);

		Map<String, Object> passwordContext = new HashMap<String, Object>();
		passwordContext.put("username", event.getFirstName() + " " + event.getLastName());
		passwordContext.put("password", password);
		EmailNotificationEvent passwordNotification = new EmailNotificationEvent(event.getEmailId(), "User Registration",
				"b5cbc718-c650-440d-a23d-f2185b80f432", passwordContext);
		publisher.publish(passwordNotification);
	}
}
