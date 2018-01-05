package com.desire3d.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * CHANNELS TO HANDLE USER LOGIN CREATION PROCESS 
 * 
 * @author Mahesh Pardeshi
 *
 */
public interface UserCreationChannel {

	String USER_CREATION_INPUT = "userCreationInputChannel";

	String USER_CREATION_OUTPUT = "userCreationOutputChannel";

	/** INPUT CHANNEL TO CREATE USER LOGIN */
	@Input(USER_CREATION_INPUT)
	SubscribableChannel userCreationInputChannel();

	/** OUTPUT CHANNEL FOR USER LOGIN CREATION */
	@Output(USER_CREATION_OUTPUT)
	MessageChannel userCreationOutputChannel();
}