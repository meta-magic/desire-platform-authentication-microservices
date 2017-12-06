package com.desire3d.auth.aop.token;

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

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.beans.ResponseBean;
import com.desire3d.auth.service.TokenService;

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

	@Around("allOperations() && !skipAuthentication()")
	public Object validateToken(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		try {
			JSONObject jsonObject = tokenService.getTokenData((String) request.getHeader("tokenid"));
			loginInfoHelperBean.setProperty(jsonObject.getString("mteid"), jsonObject.getString("loginId"), jsonObject.getString("userId"),
					jsonObject.getString("personId"), jsonObject.getString("appSessionId"));
		} catch (ExpiredJwtException e) {
			ResponseBean response = new ResponseBean(false, null, null, "Token expired.", "token.expired", null);
			return new ResponseEntity<ResponseBean>(response, HttpStatus.UNAUTHORIZED);
		} catch (IllegalArgumentException e) {
			ResponseBean response = new ResponseBean(false, null, null, "Token required.", "token.required", null);
			return new ResponseEntity<ResponseBean>(response, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			ResponseBean response = new ResponseBean(false, null, null, "Invalid Token.", "token.invalid", null);
			return new ResponseEntity<ResponseBean>(response, HttpStatus.UNAUTHORIZED);
		}
		return joinPoint.proceed();
	}

	@Pointcut("execution(* com.desire3d.auth.controller..*.*(..))")
	public void allOperations() {
	}

	@Pointcut("execution(* com.desire3d.auth.controller.AuthController.*(..))")
	public void skipAuthentication() {
	}

}
