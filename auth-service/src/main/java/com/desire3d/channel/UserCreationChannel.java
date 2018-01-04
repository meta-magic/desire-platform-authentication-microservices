package com.desire3d.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Mahesh Pardeshi
 *
 */
public interface UserCreationChannel {

	String USER_CREATION_INPUT = "userCreationInputChannel";

	@Input(USER_CREATION_INPUT)
	SubscribableChannel userCreationInputChannel();

}