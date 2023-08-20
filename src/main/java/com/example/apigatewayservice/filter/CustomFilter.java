package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter(){
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
            // netty 라는 비동기식 서버를 사용해 구동시키는데 이떄는 servletRequest 대신 serverRequest를 사용함
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE filter : request id -> {}", request.getId());

            //Custom Post filter - 처리가 다 끝난 상태에서 실행
            return chain.filter(exchange).then(Mono.fromRunnable(() -> { // Mono -> 비동기 서버에서 단위값을 전달함
                log.info("Custom POST filter : response code -> {}", response.getStatusCode());
            }));
        });
    }


    public static class Config{

    }
}
