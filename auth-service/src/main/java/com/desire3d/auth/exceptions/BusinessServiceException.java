package com.desire3d.auth.exceptions;

public class BusinessServiceException extends Exception {

	private String message;

	private String messageId;

	public BusinessServiceException(String message) {
		super(message);
		this.message = message;
	}

	public BusinessServiceException(String message, String messageId) {
		super(message);
		this.message = message;
		this.messageId = messageId;
	}

	public BusinessServiceException(String message, Exception e) {
		super(message, e);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getMessageId() {
		return messageId;
	}
}
