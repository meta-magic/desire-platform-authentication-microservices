package com.desire3d.event.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.domainservice.HelperDomainService;
import com.desire3d.channel.UserCreationChannel;
import com.desire3d.event.UserLoginCreatedEvent;

/**
 * Class to publish {@link UserLoginCreatedEvent}
 * 
 * @author Mahesh Pardeshi
 *
 */
@Component
public class UserLoginCreatedEventPublisher extends HelperDomainService {

	private final Logger logger = LoggerFactory.getLogger(UserLoginCreatedEventPublisher.class);

	@Autowired
	private UserCreationChannel userCreationChannel;

	public boolean publish(final UserLoginCreatedEvent event, final LoginInfoHelperBean loginInfoHelperBean) {
		Message<UserLoginCreatedEvent> message = MessageBuilder.withPayload(event).setHeader("tokenId", prepareToken(loginInfoHelperBean)).build();
		boolean status = userCreationChannel.userCreationOutputChannel().send(message);
		logger.info("Published message '{}' with status '{}' ", message, status);
		return status;
	}
}