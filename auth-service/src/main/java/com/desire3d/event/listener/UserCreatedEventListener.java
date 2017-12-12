package com.desire3d.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.service.HelperDomainService;
import com.desire3d.auth.service.LoginDomainService;
import com.desire3d.channel.UserCreationChannel;
import com.desire3d.event.UserCreatedEvent;
import com.desire3d.event.UserLoginCreatedEvent;
import com.desire3d.event.publisher.UserLoginCreatedEventPublisher;

/**
 * @author Mahesh Pardeshi
 *
 */
@Component
public class UserCreatedEventListener extends HelperDomainService {

	private final Logger logger = LoggerFactory.getLogger(UserCreatedEventListener.class);

	@Autowired
	private LoginDomainService loginDomainService;

	@Autowired
	private UserLoginCreatedEventPublisher publisher;

	@StreamListener(UserCreationChannel.USER_CREATION_INPUT)
	public void listen(Message<UserCreatedEvent> message) {
		logger.info("Message '{}' received ", message);
		final UserCreatedEvent event = message.getPayload();
		try {
			String tokenId = (String) message.getHeaders().get("tokenId");
			LoginInfoHelperBean loginInfoHelperBean = deserializeToken(tokenId);
			UserLoginCreatedEvent userLoginCreatedEvent = loginDomainService.createUserLogin(event, loginInfoHelperBean);
			System.err.println("userLoginCreatedEvent : " + userLoginCreatedEvent);
			logger.info("Login Created Successfully for event '{}'", event);
			publisher.publish(userLoginCreatedEvent, tokenId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Login Creation Failed for event '{}'", event);
		}
	}
}