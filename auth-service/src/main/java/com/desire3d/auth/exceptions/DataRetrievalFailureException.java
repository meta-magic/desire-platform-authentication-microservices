package com.desire3d.auth.exceptions;

public final class DataRetrievalFailureException extends BaseRepositoryException {

	private static final long serialVersionUID = 5470918555179516206L;

	public DataRetrievalFailureException(String messageId) {
		super(messageId);
	}

	public DataRetrievalFailureException(String messageId, Throwable throwable) {
		super(messageId, throwable);
	}
}
