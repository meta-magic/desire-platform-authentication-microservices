package com.desire3d.auth.exceptions;

public class DomainServiceFailedException extends BaseDomainServiceException {

	private static final long serialVersionUID = 1579192108737306643L;

	public DomainServiceFailedException(String messageId) {
		super(messageId);
	}

	public DomainServiceFailedException(String messageId, Throwable throwable) {
		super(messageId, throwable);
	}
}
