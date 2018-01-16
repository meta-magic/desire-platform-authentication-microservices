package com.desire3d.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.desire3d.auth.beans.ResponseBean;
import com.desire3d.auth.component.BaseComponent;
import com.desire3d.auth.fallback.FallbackMessage;
import com.desire3d.auth.fw.service.ReactiveService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/ForgotPasswordAPI")
public class ForgotPasswordController extends BaseComponent {

	@Autowired
	private ReactiveService reactiveService;

	@HystrixCommand(fallbackMethod = "sendRecoveryTokenFallback")
	@RequestMapping(value = "/sendRecoveryToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> validate(HttpServletRequest request, @RequestBody Object payload) {
		HttpHeaders headers = this.createHeaders(request);
		HttpEntity<?> requestEntity = new HttpEntity<>(payload, headers);
		return reactiveService.callService("/ForgotPasswordAPI/sendRecoveryToken", HttpMethod.POST, requestEntity);
	}

	public DeferredResult<ResponseEntity<ResponseBean>> sendRecoveryTokenFallback(HttpServletRequest request, @RequestBody Object payload) {
		return reactiveService.fallback(FallbackMessage.message, FallbackMessage.message);
	}

	@HystrixCommand(fallbackMethod = "forgotPasswordFallBack")
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> validateLoginId(HttpServletRequest request, @RequestBody Object object) {
		HttpHeaders headers = this.createHeaders(request);
		HttpEntity<?> requestEntity = new HttpEntity<>(object, headers);
		return reactiveService.callService("/ForgotPasswordAPI/forgotPassword", HttpMethod.POST, requestEntity);
	}

	DeferredResult<ResponseEntity<ResponseBean>> forgotPasswordFallBack(HttpServletRequest request, @RequestBody Object object) {
		return reactiveService.fallback(FallbackMessage.message, FallbackMessage.loginMessageId);
	}
}
