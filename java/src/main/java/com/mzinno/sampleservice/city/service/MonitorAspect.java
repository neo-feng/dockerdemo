package com.mzinno.sampleservice.city.service;

import com.mzinno.sampleservice.models.Util.AspectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 性能监控切面
 */
@Aspect
@Component
public class MonitorAspect {

    @Around("execution(* com.mzinno.sampleservice.city.controller.*.*(..))")
    public Object arount(ProceedingJoinPoint joinPoint) throws Throwable {
        return AspectUtil.recordMethodTimeConsume(joinPoint);
    }
}
