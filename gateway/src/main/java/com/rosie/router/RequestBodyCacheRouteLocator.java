package com.rosie.router;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author kun.han
 */
@Component
public class RequestBodyCacheRouteLocator {

    @Bean
    public RouteLocator requestBodyCacheRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("requestBodyCache",
                        r -> r
                                // 读到缓存 cachedRequestBodyObject 中
                                .readBody(String.class, readBody -> true)
                                .and()
                                .path("/xxx/**")
                                .filters(f -> {
                                    f.stripPrefix(1);
                                    return f;
                                })
                                .uri("http://localhost:8084"))
                .build();
    }
}