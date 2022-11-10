package com.rosie.aop.limit;


import com.rosie.exception.ServiceException;
import com.rosie.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author kun.han
 * 如果有网关的话 应该是做在网关的吧
 */
@Slf4j
@Aspect
@Component
public class RequestLimitContract {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    private final static String KEY_PREFIX = "requestLimit:";

    @Before("@annotation(requestLimit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit requestLimit) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        log.info("limit - count" + requestLimit.count() + " time" + requestLimit.time());
        // 1. redis获取设置用户的count, 过期时间为time 判断是否拦截
        long userId = 123123123L;
        String userKey = KEY_PREFIX + userId;
        Long count = ops.increment(userKey);
        count = Objects.isNull(count) ? 0 : count;
        if (count > requestLimit.count()) {
            throw new ServiceException("限制访问");
        }
        if (requestLimit.limitIp()) {
            // 2. ip拦截 redis获取设置ip的count, 过期时间为time 判断是否拦截
            String ip = IpUtil.getIpAddress();
            userKey = KEY_PREFIX + ip.replaceAll("\\.", "-");
            count = ops.increment(userKey);
            count = Objects.isNull(count) ? 0 : count;
            if (count > requestLimit.count()) {
                throw new ServiceException("限制访问");
            }
            log.info(ip);
        }
    }
}
