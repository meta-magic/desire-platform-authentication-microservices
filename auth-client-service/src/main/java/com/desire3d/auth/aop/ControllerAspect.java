package com.desire3d.auth.aop;

import java.io.IOException;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.desire3d.auth.beans.ResponseBean;
import com.desire3d.auth.fw.service.MessageService;

import ch.qos.logback.classic.Logger;

@Component
@Aspect
public class ControllerAspect {

	@Autowired
	private MessageService messageService;
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ControllerAspect.class);

	@Around("execution(* com.desire3d.auth.controller..*.*(..))")
	public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();

		String msg = joinPoint.getTarget().getClass() + " " + signature.getName();

		LOGGER.info(new Date() + " Executing [ " + msg + "  ] starts");
		Object retVal = joinPoint.proceed();
		LOGGER.info(new Date() + " Executing [ " + msg + "  ] starts");
		return retVal;
	}

	@Around("execution(* com.desire3d.persona.controller..*.*(..))")
	public Object addMessage(ProceedingJoinPoint joinPoint) {

		Object response = null;
		try {
			/* IF SERVICE RETURNS DATA SUCCCESSFULLY */
			response = joinPoint.proceed();
			if (response instanceof ResponseEntity<?>) {
				ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
				ResponseBean responseBean = (ResponseBean) responseEntity.getBody();
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
		} catch (Throwable error) {
			/* IF SERVICE THROWS AN EXCEPTION */
			String message = null;
			try {
				message = messageService.getMessage("error.global");
			} catch (IOException e) {
				// ADD LOG
				message = "The service failed to respond";
			}
			ResponseBean responseBean = new ResponseBean(false, message, "error.global");
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);

		}
		return response;
	}
}
