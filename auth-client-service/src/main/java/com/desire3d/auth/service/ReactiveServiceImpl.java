/**
 * ReactiveService used to call external services reactively with ribbon load balancer & Hystrix fallback support
 */
package com.desire3d.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import com.desire3d.auth.beans.ResponseBean;
import com.desire3d.auth.component.LoadBalancer;
import com.desire3d.auth.fallback.ReactiveFallback;
import com.desire3d.auth.fw.service.ReactiveService;

/**
 * @author Mahesh Pardeshi
 *
 */
@Service
public class ReactiveServiceImpl extends ReactiveFallback implements ReactiveService {

	@Autowired
	private AsyncRestTemplate asyncRestTemplate;

	@Autowired
	private LoadBalancer loadBalancer;

	/** 
	 * Method is used to call service reactively, method will auto configure host and post of service  
	 * 
	 * @param relativeUrl - Relative URL e.g /auth/validateloginid
	 * @param method - HttpMethod
	 * @param requestEntity - payload for request 
	 * */
	@Override
	public DeferredResult<ResponseEntity<ResponseBean>> callService(String relativeUrl, HttpMethod method, HttpEntity<?> requestEntity) {
		System.out.println("*****ReactiveService::callService " + relativeUrl + " call begins *****");

		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>();
		ListenableFuture<ResponseEntity<ResponseBean>> response = asyncRestTemplate.exchange(loadBalancer.getServiceURL() + relativeUrl, method, requestEntity,
				ResponseBean.class);

		response.addCallback(new ListenableFutureCallback<ResponseEntity<ResponseBean>>() {
			@Override
			public void onSuccess(ResponseEntity<ResponseBean> response) {
				System.out.println("*****ReactiveService::callService " + relativeUrl + " service call sucessful*****");
				deferredResult.setResult(response);
			}

			@Override
			public void onFailure(Throwable throwable) {
				throwable.printStackTrace();
				ResponseBean response = new ResponseBean(false, throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
				deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(response, HttpStatus.OK));
			}
		});
		System.out.println("*****ReactiveService::callService " + relativeUrl + " call end *****");
		return deferredResult;
	}

	/** 
	 * Method used to fallback reactive service with message and id
	 * @param message - fallback message
	 * @param messageId - fallback message id
	 * */
	@Override
	public DeferredResult<ResponseEntity<ResponseBean>> fallback(final String message, final String messageId) {
		return super.reactiveFallback(message, messageId);
	}
}
