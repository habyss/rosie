package com.rosie.aop.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisCache {

    /**
     * key
     *
     * @return {@link String}
     */
    String key();

    /**
     * 过期时间
     *
     * @return int
     */
    long expire() default 1;

    /**
     * 时间单位
     *
     * @return {@link TimeUnit}
     */
    TimeUnit timeUnit() default TimeUnit.DAYS;

    /**
     * 方法返回为list集合时 必传 否则出错
     *
     * @return {@link Class}
     */
    Class<?> listForClass() default RedisCache.class;
}