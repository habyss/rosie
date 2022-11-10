package com.rosie.aop.redis;

import com.rosie.response.BaseResponse;
import com.rosie.utils.RedisUtil;
import com.rosie.utils.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class RedisCacheContract {

    @Resource
    RedisUtil redisUtil;

    @Around("@annotation(redisCache)")
    public Object redisAround(final ProceedingJoinPoint joinPoint, RedisCache redisCache) throws Throwable {

        Object result;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String key = redisCache.key() + ":" + Arrays.toString(joinPoint.getArgs());
        if (redisUtil.hasKey(key)) {
            log.info("hint redisCache {}", key);
            // 默认注解 直接使用原方法的returnType来解析json
            if (redisCache.listForClass().isAssignableFrom(RedisCache.class)) {
                Class<?> returnType = signature.getReturnType();
                result = redisUtil.get(key, returnType);
                if (result instanceof BaseResponse<?> r) {
                    // 设置新的traceId
                    r.setTraceId(TraceIdUtil.getTraceId());
                }
            } else {
                // list注解, 使用自定义class解析为List
                result = redisUtil.getAsList(key, redisCache.listForClass());
            }
        } else {
            result = joinPoint.proceed();
            redisUtil.set(key, result, redisCache.expire(), redisCache.timeUnit());
        }
        return result;
    }
}