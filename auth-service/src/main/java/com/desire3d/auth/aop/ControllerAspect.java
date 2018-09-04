package com.desire3d.auth.aop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.JDOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.desire3d.auth.beans.ResponseBean;
import com.desire3d.auth.exceptions.BaseException;
import com.desire3d.auth.fw.domainservice.MessageService;
import com.desire3d.auth.utils.CommonValidator;
import com.desire3d.auth.utils.DataValidator;
import com.desire3d.auth.utils.ExceptionID;

import ch.qos.logback.classic.Logger;

@Component
@Aspect
@Order(1)
public class ControllerAspect {

	@Autowired
	private MessageService messageService;

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ControllerAspect.class);

	@Around("allOperations() || insecureCalls()")
	public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String msg = joinPoint.getTarget().getClass() + " " + signature.getName();
		LOGGER.info(new Date() + " Executing [ " + msg + "  ] starts");
		Object response = joinPoint.proceed();
		LOGGER.info(new Date() + " Executing [ " + msg + "  ] ends");
		return response;
	}

	@Around("allOperations() || insecureCalls()")
	public Object processCall(ProceedingJoinPoint joinPoint) {
		List<String> errors = validate(joinPoint);
		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>(60000L);
		if (!errors.isEmpty()) {
			String message = messageService.getMessageById(ExceptionID.ERROR_VALIDATION);
			ResponseBean responseBean = new ResponseBean(false, ExceptionID.ERROR_VALIDATION, message, errors);
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
			return deferredResult;
		} else {
			Object response = null;
			try {
				/* IF SERVICE RETURNS DATA SUCCCESSFULLY */
				response = joinPoint.proceed();
				if (response instanceof ResponseEntity<?>) {
					ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
					addResponseMessage((ResponseBean) responseEntity.getBody());
				} else if (response instanceof DeferredResult<?>) {
					@SuppressWarnings("unchecked")
					ResponseEntity<ResponseBean> responseEntity = (ResponseEntity<ResponseBean>) ((DeferredResult<ResponseEntity<ResponseBean>>) response)
							.getResult();
					addResponseMessage((ResponseBean) responseEntity.getBody());
				}
				return response;
			} catch (Throwable error) {
				if (error instanceof BaseException) {
					BaseException exception = (BaseException) error;
					String message = messageService.getExceptionMessage(exception);
					ResponseBean responseBean = new ResponseBean(false, exception.getMessageId(), message,
							getErrorMessages(exception));
					deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
				} else {
					String message = messageService.getMessageById(ExceptionID.ERROR_GLOBAL, error);
					ResponseBean responseBean = new ResponseBean(false, ExceptionID.ERROR_GLOBAL, message,
							getErrorMessages(error));
					deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK));
				}
				return deferredResult;
			}
		}
	}

	@Pointcut("execution(* com.desire3d.auth.controller..*.*(..))")
	public void allOperations() {
	}

	@Pointcut("execution(* com.desire3d.auth.insecure.controller..*.*(..))")
	public void insecureCalls() {
	}

	/**
	 * METHOD FROM {@link BaseException}
	 * 
	 * @param exception
	 * @return errorMessages
	 */
	private List<String> getErrorMessages(final BaseException exception) {
		return getErrorMessages(exception.getThrowable());
	}

	/**
	 * METHOD FROM {@link Throwable}
	 * 
	 * @param throwable
	 * @return errorMessages
	 */
	private List<String> getErrorMessages(final Throwable throwable) {
		List<String> errorMessages = new ArrayList<String>();
		if (throwable != null) {
			String errorMessage = throwable.getMessage();
			if (throwable instanceof JDOException && errorMessage.contains("Detail:")) {
				errorMessages.add(errorMessage.substring(errorMessage.indexOf("Detail:"), errorMessage.length()));
			} else {
				errorMessages.add(errorMessage);
			}
		}
		return errorMessages;
	}

	/**
	 * METHOD TO VALIDATE BEAN AND IF BEAN IS NOT VALIDATE THEN GENERATE ERROR
	 * MESSAGES
	 * 
	 */
	private List<String> validate(ProceedingJoinPoint joinPoint) {
		Object obj[] = joinPoint.getArgs();
		List<String> errors = new ArrayList<String>();
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] instanceof CommonValidator) {
				errors = this.hasErrors((CommonValidator) obj[i]);
				if (!errors.isEmpty()) {
					break;
				}
			}
		}
		return errors;
	}

	private List<String> hasErrors(CommonValidator bean) {
		List<String> errors = DataValidator.validate(bean);
		return errors;
	}

	/**
	 * ADD SUCCESS MESSAGE TO {@link ResponseBean}
	 * 
	 * @param responseBean
	 */
	private void addResponseMessage(ResponseBean responseBean) {
		if (responseBean.isSuccess()) {
			String messageId = responseBean.getSuccessCode();
			String message = messageService.getMessageById(messageId);
			responseBean.setSuccessMessage(message);
		} else {
			String messageId = responseBean.getErrorCode();
			String message = messageService.getMessageById(messageId);
			responseBean.setErrorMessage(message);
		}
	}
}