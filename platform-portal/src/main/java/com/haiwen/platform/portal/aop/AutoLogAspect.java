package com.haiwen.platform.portal.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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
    public void logBefore(JoinPoint joinPoint) {
        try {
            String className = joinPoint.getTarget().getClass().getName();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            if (!method.getName().contains("Excel")) {
                Object[] paramValues = joinPoint.getArgs();
                String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
                StringBuilder sbParam = new StringBuilder();
                StringBuilder sbValue = new StringBuilder();
                if (ArrayUtils.isEmpty(paramValues)) {
                    log.info("访问controller:" + className + "." + method.getName() + "(" + sbParam + ") value:{}",
                            sbValue);
                    return;
                }
                if (paramNames != null) {
                    for (int i = 0; i < paramNames.length; i++) {
                        if (paramValues[i] != null) {
                            sbParam.append(paramNames[i]).append(",");
                            sbValue.append(JSON.toJSONString(paramValues[i],
                                    SerializerFeature.IgnoreNonFieldGetter)).append(",");
                        }
                    }
                }
                sbParam.delete(sbParam.length() - 1, sbParam.length());
                sbValue.delete(sbValue.length() - 1, sbValue.length());
                log.info("访问controller:" + className + "." + method.getName() + "(" + sbParam + ") value:{}",
                        sbValue);
            }
        } catch (Exception e) {
            
        }
    }

    @AfterReturning(value = "logPointCut()", returning = "rvt")
    public void logAfter(JoinPoint joinPoint, Object rvt) {
        try {
            String className = joinPoint.getTarget().getClass().getName();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            if (!method.getName().contains("Excel")) {
                String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
                StringBuilder sbParam = new StringBuilder();
                if (ArrayUtils.isEmpty(paramNames)) {
                    log.info("访问controller:" + className + "." + method.getName() + "(" + sbParam + ") value:{}",
                            JSON.toJSONString(rvt));
                    return;
                }
                for (int i = 0; i < paramNames.length; i++) {
                    sbParam.append(paramNames[i]).append(",");
                }
                sbParam.delete(sbParam.length() - 1, sbParam.length());
                log.info("访问controller:" + className + "." + method.getName() + "(" + sbParam + ") return:{}",
                        JSON.toJSONString(rvt));
            }

        } catch (Exception e) {
            log.warn("切面日志打印返回值异常", e);
        }
    }
}
