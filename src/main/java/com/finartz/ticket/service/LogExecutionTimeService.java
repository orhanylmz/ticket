package com.finartz.ticket.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import com.finartz.ticket.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Service
@Slf4j
public class LogExecutionTimeService {
	@Around("@annotation(com.finartz.ticket.aop.LogExecutionTime)")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = DateUtil.currentTimeMillis();
		Object proceed = joinPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;

		log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
		return proceed;
	}
}
