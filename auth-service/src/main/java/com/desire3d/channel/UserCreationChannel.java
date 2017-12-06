package com.desire3d.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Mahesh Pardeshi
 *
 */
public interface UserCreationChannel {

	String USER_CREATION_INPUT = "userCreationInputChannel";

	String USER_CREATION_OUTPUT = "userCreationOutputChannel";

	@Input(USER_CREATION_INPUT)
	SubscribableChannel userCreationInputChannel();

	@Output(USER_CREATION_OUTPUT)
	MessageChannel userCreationOutputChannel();
}