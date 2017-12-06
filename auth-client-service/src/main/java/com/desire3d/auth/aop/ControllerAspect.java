package com.desire3d.auth.aop;

import java.io.IOException;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.desire3d.auth.beans.ResponseBean;
import com.desire3d.auth.fw.service.MessageService;

@Component
@Aspect
public class ControllerAspect {

	@Autowired
	private MessageService messageService;

	@Around("execution(* com.desire3d.auth.controller..*.*(..))")
	public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();

		String msg = joinPoint.getTarget().getClass() + " " + signature.getName();

		System.out.println(new Date() + " Executing [ " + msg + "  ] starts");
		Object retVal = joinPoint.proceed();
		System.out.println(new Date() + " Execution [ " + msg + "  ] ends");
		return retVal;
	}

	@Around("execution(* com.desire3d.auth.controller..*.*(..))")
	public Object addMessage(ProceedingJoinPoint joinPoint) {
		System.out.println("*****Inside addMessage advice*****");
		Object response = null;
		try {
			/* IF SERVICE RETURNS DATA SUCCCESSFULLY */
			response = joinPoint.proceed();
			if (response instanceof ResponseEntity<?>) {
				System.out.println("*****Inside addMessage advice ResponseEntity<?>*****");
				ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
				ResponseBean responseBean = (ResponseBean) responseEntity.getBody();
				this.prepareResponseBean(responseBean);
			} else if (response instanceof DeferredResult<?>) {
				System.out.println("*****Inside addMessage advice DeferredResult<?>*****");
				//				DeferredResult<?> deferredResult = (DeferredResult<?>) response;
				//				ResponseEntity<?> responseEntity = (ResponseEntity<?>) deferredResult.getResult();
				//				ResponseBean responseBean = (ResponseBean) responseEntity.getBody();
				//	this.prepareResponseBean(responseBean);
			}
		} catch (Throwable error) {
			/* IF SERVICE THROWS AN EXCEPTION */
			String message = null;
			try {
				message = messageService.getMessage("error.global");
			} catch (IOException e) {
				// ADD LOG
				message = "The service failed to respond";
			}
			ResponseBean responseBean = new ResponseBean(false, message, "error.global", 404);
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
		return response;
	}

	private void prepareResponseBean(ResponseBean responseBean) {
		if (responseBean.isSuccess()) {
			try {
				String message = messageService.getMessage(responseBean.getSuccessCode());
				if (message != null) {
					responseBean.setSuccessMessage(message);
				}
			} catch (IOException e) {
				// ADD LOG
			}
		} else {
			try {
				String message = messageService.getMessage(responseBean.getErrorCode());
				if (message != null) {
					responseBean.setErrorMessage(message);
				}
			} catch (IOException e) {
				// ADD LOG
			}
		}
	}
}
