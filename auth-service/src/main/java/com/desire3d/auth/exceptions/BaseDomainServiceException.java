package com.desire3d.auth.exceptions;

/**
 * @author rashmi
 *
 */
public class BaseDomainServiceException extends BaseException {

	private static final long serialVersionUID = 5329187651538325888L;

	protected BaseDomainServiceException(String messageId) {
		super(messageId);
	}

	protected BaseDomainServiceException(String messageId, Throwable throwable) {
		super(messageId, throwable);
	}
}