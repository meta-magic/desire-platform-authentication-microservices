package com.desire3d.auth.aop;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

@Component
@Aspect
public class LoggingAspect {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LoggingAspect.class);

	@Around("execution(* com.desire3d.event.listener..*.*(..)) || execution(* com.desire3d.event.publisher..*.*(..)) || execution(* com.desire3d.auth.*.repository..*.*(..)) || execution(* com.desire3d.auth.*.service..*.*(..))")
	public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String msg = joinPoint.getTarget().getClass() + " " + signature.getName();
		LOGGER.info(new Date() + " Executing [ " + msg + "  ] starts");
		Object response = joinPoint.proceed();
		LOGGER.info(new Date() + " Executing [ " + msg + "  ] ends");
		return response;
	}
}
