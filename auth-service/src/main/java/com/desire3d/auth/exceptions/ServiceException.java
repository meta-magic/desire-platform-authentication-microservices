package com.desire3d.auth.exceptions;

public class ServiceException extends Exception {
	private String message;

	private String messageId;

	public ServiceException(String message) {
		super(message);
		this.message = message;
	}

	public ServiceException(String message, String messageId) {
		super(message);
		this.message = message;
		this.messageId = messageId;
	}

	public ServiceException(String message, Exception e) {
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
