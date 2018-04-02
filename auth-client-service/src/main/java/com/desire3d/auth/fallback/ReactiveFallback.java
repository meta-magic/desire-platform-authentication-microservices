/**
 * Class used to provide fallback for reactive calls
 * */
package com.desire3d.auth.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.desire3d.auth.beans.ResponseBean;

/**
 * @author Mahesh Pardeshi
 *
 */
public abstract class ReactiveFallback {

	/** 
	 * Method used to fallback reactive service with message and id
	 * @param message - fallback message
	 * @param messageId - fallback message id
	 * */
	public final DeferredResult<ResponseEntity<ResponseBean>> reactiveFallback(final String message, final String messageId) {
		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<ResponseEntity<ResponseBean>>();
		final ResponseBean responseBean = new ResponseBean(false, message, messageId);
		deferredResult.setErrorResult(new ResponseEntity<>(responseBean, HttpStatus.OK));
		return deferredResult;
	}
}