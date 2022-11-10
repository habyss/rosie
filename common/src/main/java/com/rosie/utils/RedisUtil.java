package com.rosie.utils;

import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis 封装常用方法
 */
@Component
public class RedisUtil {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key, Class<T> clazz) {
        String value = redisTemplate.opsForValue().get(key);
        if (StringUtils.hasText(value)) {
            return JSON.parseObject(value, clazz);
        }
        return null;
    }

    /**
     * 前提:存进去的就是list
     *
     * @param key   key
     * @param clazz 类
     * @return {@link T}
     */
    public <T> List<T> getAsList(String key, Class<T> clazz) {
        String value = redisTemplate.opsForValue().get(key);
        if (StringUtils.hasText(value)) {
            return JSON.parseArray(value, clazz);
        }
        return null;
    }

    public void set(String key, Object value) {
        if (value instanceof String v) {
            redisTemplate.opsForValue().set(key, v);
        } else {
            String valueString = JSON.toJSONString(value);
            redisTemplate.opsForValue().set(key, valueString);
        }
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        if (value instanceof String v) {
            redisTemplate.opsForValue().set(key, v, timeout, unit);
        } else {
            String valueString = JSON.toJSONString(value);
            redisTemplate.opsForValue().set(key, valueString, timeout, unit);
        }
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Long delete(String... keys) {
        return redisTemplate.delete(Arrays.asList(keys));
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
