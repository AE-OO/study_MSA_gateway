package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * application.yml 파일에서 하던 작업을 java 코드로 구현함
 */
@Configuration
public class FilterConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/first-service/**") // 라우터 등록
                        .filters(f -> f.addRequestHeader("first-request","first-request-header")
                                       .addResponseHeader("first-response", "first-response-header")) // 필터를 어떻게 쓸 것인지 정의
                        .uri("http://localhost:8081"))  // 해당 uri로 이동
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request","second-request-header")
                                .addResponseHeader("second-response", "second-response-header"))
                        .uri("http://localhost:8082"))
                .build();
    }
}
