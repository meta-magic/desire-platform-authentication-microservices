package com.desire3d.auth.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.desire3d.auth.beans.ResponseBean;
import com.desire3d.auth.dto.LoginAuthentication;
import com.desire3d.auth.dto.LoginResponseDto;
import com.desire3d.auth.dto.UsernameAuthentication;
import com.desire3d.auth.fw.query.service.AuthQueryService;
import com.desire3d.auth.utils.ExceptionID;

import io.reactivex.Single;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthQueryService authService;

	@RequestMapping(value = "/validateloginid", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> validateLoginId(HttpServletRequest request, @RequestBody UsernameAuthentication usernameAuthentication)
			throws Throwable {
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " started %%%%%%%%%%%%%%*****");
		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>();
		Single<Boolean> single = Single.just(authService.validateLoginId(usernameAuthentication.getLoginId()));
		single.subscribe(isValidLogin -> {
			if (isValidLogin) {
				deferredResult.setResult(new ResponseEntity<ResponseBean>(new ResponseBean(true, ExceptionID.VALID_LOGINID, isValidLogin), HttpStatus.OK));
			} else {
				deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(new ResponseBean(false, ExceptionID.INVALID_LOGINID), HttpStatus.OK));
			}
		}, exception -> {
			ResponseBean responseBean = new ResponseBean(false, ExceptionID.INVALID_LOGINID, exception.getMessage(), Arrays.asList(exception.getMessage()));
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
		});

		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " completed*****");
		return deferredResult;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> authenticate(HttpServletRequest request, @RequestBody LoginAuthentication loginAuthentication)
			throws Throwable {
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " started*****");
		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>();
		Single<LoginResponseDto> single = Single.just(authService.authenticate(loginAuthentication.getLoginId(), loginAuthentication.getPassword(),
				loginAuthentication.getLatitude(), loginAuthentication.getLongitude(), request));
		single.subscribe(loginResponse -> {
			if (loginResponse != null) {
				ResponseBean responseBean = new ResponseBean(true, ExceptionID.VALID_USERCREDENTIALS, loginResponse);
				deferredResult.setResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
			} else {
				ResponseBean responseBean = new ResponseBean(false, ExceptionID.INVALID_USER_CREDENTIALS);
				deferredResult.setErrorResult(new ResponseEntity<>(responseBean, HttpStatus.OK));
			}
		}, exception -> {
			ResponseBean responseBean = new ResponseBean(false, ExceptionID.INVALID_USER_CREDENTIALS, exception.getMessage(),
					Arrays.asList(exception.getMessage()));
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
		});

		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " completed*****");

		return deferredResult;
	}

	@RequestMapping(value = "/checkLoginIdAvailablility", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> checkLoginIdAvailablility(@RequestBody UsernameAuthentication usernameAuthentication) throws Throwable {
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " started %%%%%%%%%%%%%%*****");
		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>();
		Single<Boolean> single = Single.just(authService.validateLoginId(usernameAuthentication.getLoginId()));
		single.subscribe(isValidLogin -> {
			if (isValidLogin) {
				ResponseBean responseBean = new ResponseBean(false, ExceptionID.LOGINID_EXISTS);
				deferredResult.setErrorResult(new ResponseEntity<>(responseBean, HttpStatus.OK));

			} else {
				deferredResult.setResult(new ResponseEntity<ResponseBean>(new ResponseBean(true, ExceptionID.LOGINID_AVAILABLE, isValidLogin), HttpStatus.OK));
			}
		}, exception -> {
			ResponseBean responseBean = new ResponseBean(false, ExceptionID.LOGINID_EXISTS, exception.getMessage(), Arrays.asList(exception.getMessage()));
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
		});

		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " completed*****");
		return deferredResult;
	}
}