package com.desire3d.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * CHANNEL TO PUBLISH PASSWORD RECOVERY EVENTS 
 */

public interface PasswordRecoveryChannel {

	String PASSWORD_RECOVERY_OUTPUT_CHANNEL = "passwordRecoveryOutputChannel";

	/** OUTPUT CHANNEL FOR PASSWORD RECOVERY*/
	@Output(PASSWORD_RECOVERY_OUTPUT_CHANNEL)
	MessageChannel passwordRecoveryOutputChannel();

}
