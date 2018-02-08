package com.desire3d.auth.insecure.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.desire3d.auth.beans.ResponseBean;
import com.desire3d.auth.dto.ForgotPasswordDTO;
import com.desire3d.auth.dto.UsernameAuthentication;
import com.desire3d.auth.fw.query.service.PasswordManagementService;
import com.desire3d.auth.utils.ExceptionID;

@RestController
@Scope(value = "request")
@RequestMapping("/ForgotPasswordAPI")
public class ForgotPasswordController {

	@Autowired
	private PasswordManagementService passwordManagementService;

	@RequestMapping(value = "/sendRecoveryToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> validateForgotPassword(HttpServletRequest request,
			@RequestBody UsernameAuthentication usernameAuthentication) throws Throwable {
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " started*****");
		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>();
		passwordManagementService.sendRecoveryToken(usernameAuthentication);

		deferredResult.setResult(new ResponseEntity<ResponseBean>(new ResponseBean(true, ExceptionID.RECOVERYTOKEN_SENT, null), HttpStatus.OK));
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " completed*****");

		return deferredResult;
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> validateToken(@RequestBody ForgotPasswordDTO forgotPasswordDTO) throws Throwable {
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " started*****");
		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>();
		passwordManagementService.forgotPassword(forgotPasswordDTO);

		deferredResult.setResult(new ResponseEntity<ResponseBean>(new ResponseBean(true, ExceptionID.PASSWORD_CHANGED, null), HttpStatus.OK));
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " completed*****");

		return deferredResult;
	}

}
