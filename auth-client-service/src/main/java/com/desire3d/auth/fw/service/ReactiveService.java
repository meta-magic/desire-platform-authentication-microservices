package com.desire3d.auth.fw.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.desire3d.auth.beans.ResponseBean;

/**
 * @author Mahesh Pardeshi
 *
 */
public interface ReactiveService {

	public DeferredResult<ResponseEntity<ResponseBean>> callService(String url, HttpMethod method, HttpEntity<?> requestEntity);

	public DeferredResult<ResponseEntity<ResponseBean>> fallback(String message, String messageId);

}