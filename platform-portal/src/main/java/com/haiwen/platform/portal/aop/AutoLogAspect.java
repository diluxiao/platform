package com.haiwen.platform.portal.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class AutoLogAspect {

    @Pointcut("@within(com.haiwen.platform.common.annotation.AutoLog)")
    public void logPointCut(){

    }

    @Before("logPointCut()")
    public void logBefore(ProceedingJoinPoint point) {

    }

    @After("logPointCut()")
    public void logAfter(ProceedingJoinPoint point) {

    }
}
