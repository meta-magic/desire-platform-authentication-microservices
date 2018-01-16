package com.desire3d.auth.exceptions;

public class PasswordRecoveryFailureException extends BaseDomainServiceException {

	private static final long serialVersionUID = 1579192108737306643L;

	public PasswordRecoveryFailureException(String messageId) {
		super(messageId);
	}

	public PasswordRecoveryFailureException(String messageId, Throwable throwable) {
		super(messageId, throwable);
	}
}
