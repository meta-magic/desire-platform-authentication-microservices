package com.desire3d.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @author sagar
 * 
 *         THIS INTERFACE IS USED FOR OUTPUT CHANNEL TO AUTH SERVICE
 */
public interface SessionHandlerChannel {

	String SESSION_HANDLER_INPUT_CHANNEL = "sessionHandlerInputChannel";

	@Input(SessionHandlerChannel.SESSION_HANDLER_INPUT_CHANNEL)
	MessageChannel sessionHandlerInputChannel();

}
