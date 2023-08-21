package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * global filter는 어떠한 라우터 정보와 상관없이 공통적으로 사용될 수 있는 필터
 */
@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public GlobalFilter(){
        super(Config.class);
    }

    /**
     * 작동하고자 하는 내용을 작성함
     * - gateway filter를 반환함
     */
    @Override
    public GatewayFilter apply(Config config) {
        // Custom pre filter
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage : {}", config.getBaseMessage());

            if(config.isPreLogger()){
                log.info("Global Filter start : request id -> {}", request.getId());
            }
            //Custom Post filter - 처리가 다 끝난 상태에서 실행
            return chain.filter(exchange).then(Mono.fromRunnable(() -> { // Mono -> 비동기 서버에서 단위값을 전달함
                if(config.isPostLogger()){
                    log.info("Global Filter end : response code -> {}", response.getStatusCode());
                }
            }));
        });
    }


    @Data
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
