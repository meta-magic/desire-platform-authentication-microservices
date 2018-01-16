package com.desire3d.auth.exceptions;

public class DomainServiceFailureException extends BaseDomainServiceException {

	private static final long serialVersionUID = 1579192108737306643L;

	public DomainServiceFailureException(String messageId) {
		super(messageId);
	}

	public DomainServiceFailureException(String messageId, Throwable throwable) {
		super(messageId, throwable);
	}
}
