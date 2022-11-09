package com.rosie.config;

import com.rosie.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class WebLog {
     /**
     * somenet包下的以Controller结尾的类的方法
     */
    @Pointcut("execution(public * com.rosie..*Controller.*(..))")
    public void webLog(){}

    @Around("webLog()")
    public Object around(ProceedingJoinPoint jp) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        long startTime = Instant.now().toEpochMilli();
        log.info("---请求来源 : {}", IpUtil.getIpAddress(request));
        log.info("---请求方法 : {}.{}", jp.getSignature().getDeclaringTypeName(), jp.getSignature().getName());
        log.info("---请求参数 : {}", Arrays.toString(jp.getArgs()));
        Object result = jp.proceed();
        log.info("---请求耗时 : {}", (Instant.now().toEpochMilli() - startTime) + "ms");
        log.info("---请求返回 : {}", result);
        return result;
    }
}