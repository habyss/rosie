package com.rosie.config;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.*;

/**
 * requestBody之外的入参 时间戳->LocalDateTime
 *
 * @author hankun
 */
@ControllerAdvice
public class LocalDateTimeAdvice {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // 这里只需要LocalDateTime 如果需要转其他的,相应添加/修改
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String timestamp) throws IllegalArgumentException {
                if (!StringUtils.hasText(timestamp)) {
                    setValue(null);
                } else {
                    setValue(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestamp)), ZoneId.systemDefault()));
                }
            }
        });
        // 这里只需要LocalDate 如果需要转其他的,相应添加/修改
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String timestamp) throws IllegalArgumentException {
                if (!StringUtils.hasText(timestamp)) {
                    setValue(null);
                } else {
                    setValue(LocalDate.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestamp)), ZoneId.systemDefault()));
                }
            }
        });
        // 这里只需要LocalTime 如果需要转其他的,相应添加/修改
        binder.registerCustomEditor(LocalTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String timestamp) throws IllegalArgumentException {
                if (!StringUtils.hasText(timestamp)) {
                    setValue(null);
                } else {
                    setValue(LocalTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestamp)), ZoneId.systemDefault()));
                }
            }
        });
    }
}