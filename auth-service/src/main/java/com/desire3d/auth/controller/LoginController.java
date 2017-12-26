package com.desire3d.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.desire3d.auth.beans.ResponseBean;
import com.desire3d.auth.exceptions.BusinessServiceException;
import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.fw.query.service.LoginQueryService;

import io.reactivex.Single;

@RestController
@RequestMapping("/login")
@Scope(value = "request")
public class LoginController {

	@Autowired
	private LoginQueryService loginService;

	@RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<ResponseBean>> logout(HttpServletRequest request) throws Throwable {
		System.out.println("*****Reactive call " + Thread.currentThread().getStackTrace()[1].getClassName() + "::"
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " started*****");

		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>();
		try {
			Single<Boolean> single = Single.just(loginService.userLogout());
			single.subscribe(isLogout -> {
				if (isLogout) {
					ResponseBean responseBean = new ResponseBean(true, "You have successfully logged out!",
							"user.logout", null, null, isLogout);
					deferredResult.setResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
				} else {
					ResponseBean responseBean = new ResponseBean(false, null, null, "Logout failed",
							"user.logoutfailed", isLogout);
					deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
				}
			}, exception -> {
				ResponseBean responseBean = new ResponseBean(false, null, null, exception.getMessage(), "", null);
				deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
			});

		} catch (BusinessServiceException e) {
			ResponseBean responseBean = new ResponseBean(false, null, null, e.getMessage(), e.getMessageId(), null);
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
		} catch (PersistenceException e) {
			ResponseBean responseBean = new ResponseBean(false, null, null, e.getMessage(), e.getMessage(), null);
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
}
