package com.desire3d.event.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.desire3d.channel.PasswordRecoveryChannel;
import com.desire3d.event.TokenGeneratedEvent;

/**
 * This class is used to publish {@link TokenGeneratedEvent}
 */

@Component
public class TokenGeneratedEventPublisher {

	private final Logger logger = LoggerFactory.getLogger(TokenGeneratedEventPublisher.class);

	@Autowired
	private PasswordRecoveryChannel passwordRecoveryChannel;

	public boolean publish(final TokenGeneratedEvent event) {
		Message<TokenGeneratedEvent> message = MessageBuilder.withPayload(event).build();
		logger.info("Publishing message '{}' with payload type '{}' ", message, TokenGeneratedEvent.class);
		boolean status = passwordRecoveryChannel.passwordRecoveryOutputChannel().send(message);
		logger.info("Published message '{}' with status '{}' ", message, status);
		return status;
	}

}
