package com.rosie.filter;

import com.alibaba.fastjson2.JSONObject;
import com.rosie.constant.ConstantMsg;
import com.rosie.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * token校验全局过滤器
 *
 * @author kun.han
 */
@Slf4j
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {

    private static void run() {
        log.info("------------ 请求结束 --------------");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("------------ 全局拦截处理 --------------");
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        log.info("---path   : {}", path);
        log.info("---header : {}", request.getHeaders());
        log.info("---query  : {}", request.getQueryParams());
        String requestBody = exchange.getAttribute("cachedRequestBodyObject");
        if (StringUtils.hasText(requestBody)) {
            log.info("---body   : {}", JSONObject.parseObject(requestBody).toJSONString());
        }
        // 某些路径不作验证
        if (path.startsWith(ConstantMsg.URI_ANON)) {
            return chain.filter(exchange).then(Mono.fromRunnable(MyGlobalFilter::run));
        }
        // 试图从header获取token
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(ConstantMsg.AUTHORIZE_TOKEN);
        // 获取到验证参数 进行验证
        if (AuthUtils.authTokenNormal(token)) {
            return chain.filter(exchange).then(Mono.fromRunnable(MyGlobalFilter::run));
        } else {
            return AuthUtils.authFailure(exchange);
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}