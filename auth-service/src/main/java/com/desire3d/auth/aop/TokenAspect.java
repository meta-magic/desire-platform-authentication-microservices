package com.desire3d.auth.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.beans.ResponseBean;
import com.desire3d.auth.fw.domainservice.MessageService;
import com.desire3d.auth.fw.domainservice.TokenService;
import com.desire3d.auth.utils.ExceptionID;

import atg.taglib.json.util.JSONObject;
import io.jsonwebtoken.ExpiredJwtException;

@Component
@Aspect
@Order(2)
@Scope(value = "request")
public class TokenAspect {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private LoginInfoHelperBean loginInfoHelperBean;

	@Autowired
	private MessageService messageService;

	@Around("allOperations() && !insecureCalls()")
	public Object validateToken(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		DeferredResult<ResponseEntity<ResponseBean>> deferredResult = new DeferredResult<>(60000L);
		try {
			JSONObject jsonObject = tokenService.getTokenData((String) request.getHeader("tokenid"));
			loginInfoHelperBean.setProperty(jsonObject.getString(TokenService.MTE_ID_KEY), jsonObject.getString(TokenService.LOGIN_ID_KEY),
					jsonObject.getString(TokenService.USER_ID_KEY), jsonObject.getString(TokenService.PERSON_ID_KEY),
					jsonObject.getString(TokenService.APP_SESSION_ID_KEY), jsonObject.getInt(TokenService.SUBSCRIPTION_TYPE_KEY));
		} catch (ExpiredJwtException e) {
			ResponseBean responseBean = new ResponseBean(false, ExceptionID.TOKEN_EXPIRED, messageService.getMessageById(ExceptionID.TOKEN_EXPIRED),
					Arrays.asList(e.getMessage()));
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.UNAUTHORIZED));
			return deferredResult;
		} catch (IllegalArgumentException e) {
			ResponseBean responseBean = new ResponseBean(false, ExceptionID.TOKEN_REQUIRED, messageService.getMessageById(ExceptionID.TOKEN_REQUIRED),
					Arrays.asList(e.getMessage()));
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.UNAUTHORIZED));
			return deferredResult;
		} catch (Exception e) {
			ResponseBean responseBean = new ResponseBean(false, ExceptionID.TOKEN_INVALID, messageService.getMessageById(ExceptionID.TOKEN_INVALID),
					Arrays.asList(e.getMessage()));
			deferredResult.setErrorResult(new ResponseEntity<ResponseBean>(responseBean, HttpStatus.UNAUTHORIZED));
			return deferredResult;
		}
		return joinPoint.proceed();
	}

	@Pointcut("execution(* com.desire3d.auth.controller..*.*(..))")
	public void allOperations() {
	}

	@Pointcut("execution(* com.desire3d.auth.insecure.controller..*.*(..))")
	public void insecureCalls() {
	}
}