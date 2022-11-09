package com.rosie.config;

import com.rosie.utils.TraceIdUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class OpenFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String traceId = TraceIdUtil.getTraceId();
        requestTemplate.header(TraceIdUtil.TRACE_ID, traceId);
    }
}