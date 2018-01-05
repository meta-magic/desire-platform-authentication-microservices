package com.desire3d.auth.exceptions;

public final class PersistenceFailureException extends BaseRepositoryException {

	private static final long serialVersionUID = -3018974792633623849L;

	public PersistenceFailureException(String messageId) {
		super(messageId);
	}

	public PersistenceFailureException(String messageId, Throwable throwable) {
		super(messageId, throwable);
	}
}