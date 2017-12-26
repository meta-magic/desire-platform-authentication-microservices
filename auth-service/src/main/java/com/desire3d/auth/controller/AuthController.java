package com.desire3d.auth.controller;

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
import com.desire3d.auth.exceptions.BusinessServiceException;
import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.fw.query.service.AuthQueryService;

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
		try {
			Single<Boolean> single = Single.just(authService.validateLoginId(usernameAuthentication.getLoginId()));
			single.subscribe(isValidLogin -> {
				if (isValidLogin) {
					deferredResult.setResult(new ResponseEntity<ResponseBean>(
							new ResponseBean(true, "Valid login-id", "valid.loginid", null, null, isValidLogin), HttpStatus.OK));
				} else {
					deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(
							new ResponseBean(false, null, null, "Invalid login-id", "invalid.loginid", isValidLogin), HttpStatus.OK));
				}
			}, exception -> {
				ResponseBean responseBean = new ResponseBean(false, null, null, exception.getMessage(), "", null);
				deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
			});
		} catch (BusinessServiceException e) {
			ResponseBean responseBean = new ResponseBean(false, null, null, e.getMessage(), e.getMessageId(), null);
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
		} catch (Exception e) {
			e.printStackTrace();
			ResponseBean responseBean = new ResponseBean(false, null, null, e.getMessage(), "", null);
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
			System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " completed*****");
		}
		return deferredResult;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> authenticate(HttpServletRequest request, @RequestBody LoginAuthentication loginAuthentication)
			throws Throwable {
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " started*****");
		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>();
		try {
			Single<LoginResponseDto> single = Single.just(authService.authenticate(loginAuthentication.getLoginId(), loginAuthentication.getPassword()));
			single.subscribe(loginResponse -> {
				if (loginResponse != null) {
					ResponseBean responseBean = new ResponseBean(true, "Valid login-id password", "valid.user.credentials", null, null, loginResponse);
					deferredResult.setResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
				} else {
					ResponseBean responseBean = new ResponseBean(false, null, null, "Invalid login-id", "invalid.user.credentials", null);
					deferredResult.setErrorResult(new ResponseEntity<>(responseBean, HttpStatus.OK));
				}
			}, exception -> {
				ResponseBean responseBean = new ResponseBean(false, null, null, exception.getMessage(), ((BusinessServiceException) exception).getMessageId(),
						null);
				deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
			});
		} catch (BusinessServiceException e) {
			ResponseBean responseBean = new ResponseBean(false, null, null, e.getMessage(), e.getMessageId(), null);
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
		} catch (PersistenceException e) {
			ResponseBean responseBean = new ResponseBean(false, null, null, "Save Error", "save.error", "Check Entered Data");
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
		} catch (Exception e) {
			e.printStackTrace();
			ResponseBean responseBean = new ResponseBean(false, null, null, e.getMessage(), "", null);
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
		}
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " completed*****");

		return deferredResult;
	}

	@RequestMapping(value = "/checkLoginIdAvailablility", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> checkLoginIdAvailablility(@RequestBody UsernameAuthentication usernameAuthentication) throws Throwable {
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " started %%%%%%%%%%%%%%*****");
		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>();
		try {
			Single<Boolean> single = Single.just(authService.validateLoginId(usernameAuthentication.getLoginId()));
			single.subscribe(isValidLogin -> {
				if (isValidLogin) {
					deferredResult.setErrorResult(
							new ResponseEntity<ResponseBean>(new ResponseBean(false, null, null, "Login id already exist", "", null), HttpStatus.OK));

				} else {
					deferredResult.setResult(new ResponseEntity<ResponseBean>(
							new ResponseBean(true, "Login id available", "", null, null, null), HttpStatus.OK));
				}
			}, exception -> {
				ResponseBean responseBean = new ResponseBean(false, null, null, exception.getMessage(), "", null);
				deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
			});
		} catch (BusinessServiceException e) {
			ResponseBean responseBean = new ResponseBean(false, null, null, e.getMessage(), e.getMessageId(), null);
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
		} catch (Exception e) {
			e.printStackTrace();
			ResponseBean responseBean = new ResponseBean(false, null, null, e.getMessage(), "", null);
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
			System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " completed*****");
		}
		return deferredResult;
	}
}