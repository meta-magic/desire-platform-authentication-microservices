package com.desire3d.auth.exceptions;

public final class DataNotFoundException extends BaseRepositoryException {

	private static final long serialVersionUID = 5470918555179516206L;

	public DataNotFoundException(String messageId) {
		super(messageId);
	}

	public DataNotFoundException(String messageId, Throwable throwable) {
		super(messageId, throwable);
	}
}
