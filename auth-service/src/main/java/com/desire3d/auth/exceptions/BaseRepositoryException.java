package com.desire3d.auth.exceptions;

/**
 * @author rashmi
 *
 */
public class BaseRepositoryException extends BaseException {

	private static final long serialVersionUID = 7929780993953267853L;

	protected BaseRepositoryException(String messageId) {
		super(messageId);
	}

	protected BaseRepositoryException(String messageId, Throwable throwable) {
		super(messageId, throwable);
	}
}