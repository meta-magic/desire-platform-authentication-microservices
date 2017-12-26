package com.desire3d.auth.exceptions;

public final class PersistenceException extends BaseRepositoryException {

	private static final long serialVersionUID = -3018974792633623849L;

	public PersistenceException(String messageId) {
		super(messageId);
	}

	public PersistenceException(String messageId, Throwable throwable) {
		super(messageId, throwable);
	}
}