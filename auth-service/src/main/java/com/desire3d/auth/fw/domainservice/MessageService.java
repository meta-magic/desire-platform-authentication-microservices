package com.desire3d.auth.fw.domainservice;

import com.desire3d.auth.exceptions.BaseException;

/**
 * @author rashmi
 *
 */
public interface MessageService {

	String getMessageById(String messageId, Throwable throwable);

	String getMessageById(String messageId);

	String getExceptionMessage(BaseException exception);

}