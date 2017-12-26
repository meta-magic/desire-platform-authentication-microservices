package com.desire3d.auth.exceptions;

public class BaseException extends Throwable {

	private static final long serialVersionUID = -2753833738350620657L;

	private String messageId;

	private Throwable throwable;

	protected BaseException(String messageId) {
		this.messageId = messageId;
	}

	protected BaseException(String messageId, Throwable throwable) {
		super(throwable.getMessage());
		this.messageId = messageId;
		this.throwable = throwable;
	}

	public String getMessageId() {
		return messageId;
	}

	public Throwable getThrowable() {
		return throwable;
	}
}