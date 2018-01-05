package com.desire3d.auth.domainservice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.fw.command.repository.AuthSchemaCommandRepository;
import com.desire3d.auth.fw.command.repository.PasswordSchemaCommandRepository;
import com.desire3d.auth.fw.command.repository.UserSchemaCommandRepository;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.AuthSchema;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.model.transactions.UserSchema;
import com.desire3d.auth.utils.Constants;
import com.desire3d.auth.utils.HashingAlgorithms;
import com.desire3d.event.EmailNotificationEvent;
import com.desire3d.event.UserCreatedEvent;
import com.desire3d.event.UserLoginCreatedEvent;
import com.desire3d.event.publisher.NotificationEventPublisher;
import com.desire3d.event.publisher.UserLoginCreatedEventPublisher;

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
	private NotificationEventPublisher notificationEventPublisher;

	@Autowired
	private UserLoginCreatedEventPublisher userLoginCreatedEventPublisher;
	
	private LoginInfoHelperBean loginInfoHelperBean = null;
	/**
	 * Method used to create user login after creation of user account 
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * 
	 * */
	public void createUserLogin(final UserCreatedEvent event, final LoginInfoHelperBean loginInfoHelperBean) throws Throwable {
		this.loginInfoHelperBean = loginInfoHelperBean;
		UserSchema userSchema = createUserSchema(event);
		createAuthSchema(event, userSchema);
		String password = createPasswordSchema(event, userSchema);
		publishLoginCreatedEvents(event, password, userSchema.getUserUUID());
	}

	/**
	 * Method used to create user schema
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * @return {@link UserSchema}
	 * @throws PersistenceException 
	 * */
	private UserSchema createUserSchema(final UserCreatedEvent event) throws PersistenceException {
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
	 * @throws PersistenceException 
	 * */
	private AuthSchema createAuthSchema(final UserCreatedEvent event, final UserSchema userSchema)
			throws PersistenceException {
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
	private String createPasswordSchema(final UserCreatedEvent event, final UserSchema userSchema)
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
	 * @param userId
	 * */
	private void publishLoginCreatedEvents(final UserCreatedEvent event, final String password, final String userId) {
		publishLoginIdNotification(event);
		publishPasswordNotification(event, password);
		publishUserLoginCreatedEvent(userId);
	}

	/**
	 * Method used publish {@link EmailNotificationEvent} for login id
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * */
	private boolean publishLoginIdNotification(final UserCreatedEvent event) {
		Map<String, Object> loginContext = new HashMap<String, Object>();
		loginContext.put("username", event.getFirstName() + " " + event.getLastName());
		loginContext.put("loginid", event.getLoginId());
		EmailNotificationEvent loginIdNotification = new EmailNotificationEvent(event.getEmailId(), "User Registration",
				Constants.TEMPLATE_ID_FOR_LOGINID_NOTIFICATION, loginContext);
		return notificationEventPublisher.publish(loginIdNotification);
	}

	/**
	 * Method used publish {@link EmailNotificationEvent} for login password
	 * 
	 * @param event an {@link UserCreatedEvent} consumed from kafka to process creation of user login
	 * @param password
	 * */
	private boolean publishPasswordNotification(final UserCreatedEvent event, final String password) {
		Map<String, Object> passwordContext = new HashMap<String, Object>();
		passwordContext.put("username", event.getFirstName() + " " + event.getLastName());
		passwordContext.put("password", password);
		EmailNotificationEvent passwordNotification = new EmailNotificationEvent(event.getEmailId(), "User Registration",
				Constants.TEMPLATE_ID_FOR_PASSWORD_NOTIFICATION, passwordContext);
		return notificationEventPublisher.publish(passwordNotification);
	}

	/**
	 * Method used publish {@link UserLoginCreatedEvent} for User role mapping & Instance creation
	 * 
	 * @param userId
	 * */
	private boolean publishUserLoginCreatedEvent(final String userId) {
		return userLoginCreatedEventPublisher.publish(new UserLoginCreatedEvent(userId), loginInfoHelperBean);
	}
}