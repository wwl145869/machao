package com.dci.service.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;



@Log4j2
@Component
@Aspect
public class AspectService {

    @Around("execution(* com.dci.service.*.*(..))")
   public Object around(ProceedingJoinPoint pj) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = pj.proceed();
        long endTime = System.currentTimeMillis();
        long hs=endTime-startTime;
        log.info(pj.getSignature()+"耗时："+hs);
        return proceed;
    }

}
