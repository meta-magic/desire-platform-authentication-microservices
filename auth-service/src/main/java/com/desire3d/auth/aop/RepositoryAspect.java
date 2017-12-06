package com.desire3d.auth.aop;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.desire3d.auth.exceptions.BaseException;
import com.desire3d.auth.exceptions.handler.RepositoryExceptionHandler;

@Component
@Aspect
public class RepositoryAspect {

	@Around("execution(* com.desire3d.auth.*.repository..*.*(..))")
	public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {

		Object retVal = joinPoint.proceed();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();

		RepositoryExceptionHandler myAnnotation = signature.getMethod().getAnnotation(RepositoryExceptionHandler.class);

		if (myAnnotation != null) {
			String message = String.format(myAnnotation.message(), joinPoint.getArgs());
			this.isNullOREmpty(retVal, myAnnotation.exception(), message);
		}
		return retVal;
	}

	private void isNullOREmpty(Object obj, String exceptionClass, String message) throws BaseException, NoSuchMethodException, SecurityException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		if (isNullOREmpty(obj)) {
			Constructor c = Class.forName(exceptionClass).getConstructor(String.class);
			BaseException e = (BaseException) c.newInstance(message);
			throw e;
		}

	}

	private boolean isNullOREmpty(Object obj) {
		if (obj != null && obj instanceof List) {
			List<Object> data = (List<Object>) obj;
			if (data.isEmpty())
				return true;
			else
				return false;
		} else if (obj == null) {
			return true;
		} else {
			return false;
		}
	}

}
