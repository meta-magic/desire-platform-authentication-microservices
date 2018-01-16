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
@RequestMapping("/PasswordManagement")
public class PasswordManagementController extends BaseComponent {

	@Autowired
	private ReactiveService reactiveService;

	@HystrixCommand(fallbackMethod = "changePasswordFallback")
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> update(HttpServletRequest request, @RequestBody Object payload) {
		HttpHeaders headers = this.createHeaders(request);
		HttpEntity<?> requestEntity = new HttpEntity<>(payload, headers);
		return reactiveService.callService("/PasswordManagement/changePassword", HttpMethod.POST, requestEntity);
	}

	public DeferredResult<ResponseEntity<ResponseBean>> changePasswordFallback(HttpServletRequest request, @RequestBody Object payload) {
		return reactiveService.fallback(FallbackMessage.message, FallbackMessage.message);
	}

}
