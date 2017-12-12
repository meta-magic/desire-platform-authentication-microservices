package com.desire3d.event.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.desire3d.channel.UserCreationChannel;
import com.desire3d.event.UserCreatedEvent;
import com.desire3d.event.UserLoginCreatedEvent;

/**
 * This class is used to publish {@link UserCreatedEvent}
 * @author Mahesh Pardeshi
 *
 */
@Component
public class UserLoginCreatedEventPublisher {

	private final Logger logger = LoggerFactory.getLogger(UserLoginCreatedEventPublisher.class);

	@Autowired
	private UserCreationChannel userCreationChannel;

	public void publish(final UserLoginCreatedEvent event, final String tokenId) {
		Message<UserLoginCreatedEvent> message = MessageBuilder.withPayload(event).setHeader("tokenId", tokenId).build();
		logger.info("Publishing message '{}' with payload type '{}' ", message, UserCreatedEvent.class);
		boolean status = userCreationChannel.userCreationOutputChannel().send(message);
		logger.info("Published message '{}' with status '{}' ", message, status);
	}
}