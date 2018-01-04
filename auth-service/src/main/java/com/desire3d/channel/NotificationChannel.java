package com.desire3d.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * CHANNEL TO PUBLISH NOTIFICATION EVENTS i.e EMAIL AND SMS EVENTS
 * 
 * @author Mahesh Pardeshi
 *
 */
public interface NotificationChannel {

	String EMAIL_NOTIFICATION_OUTPUT_CHANNEL = "emailNotificationOutputChannel";

	/** OUTPUT CHANNEL FOR EMAIL NOTIFICATION*/
	@Output(EMAIL_NOTIFICATION_OUTPUT_CHANNEL)
	MessageChannel emailNotificationOutputChannel();

}