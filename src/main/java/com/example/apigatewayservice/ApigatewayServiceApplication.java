package com.example.apigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApigatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayServiceApplication.class, args);
	}

	/**
	 * actuator의 end point로 httptrace를 사용하기 위해 bean으로 등록
	 */
	@Bean
	public HttpTraceRepository httpTraceRepository(){
		return new InMemoryHttpTraceRepository();
	}

}
