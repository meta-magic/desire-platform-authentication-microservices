package com.desire3d.event.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.desire3d.channel.NotificationChannel;
import com.desire3d.event.EmailNotificationEvent;

/**
 * This class is used to publish {@link EmailNotificationEvent}
 * 
 * @author Mahesh Pardeshi
 */
@Component
public class NotificationEventPublisher {

	private final Logger logger = LoggerFactory.getLogger(NotificationEventPublisher.class);

	@Autowired
	private NotificationChannel notificationChannel;

	public boolean publish(final EmailNotificationEvent event) {
		Message<EmailNotificationEvent> message = MessageBuilder.withPayload(event).build();
		logger.info("Publishing message '{}' with payload type '{}' ", message, EmailNotificationEvent.class);
		boolean status = notificationChannel.emailNotificationOutputChannel().send(message);
		logger.info("Published message '{}' with status '{}' ", message, status);
		return status;
	}
}