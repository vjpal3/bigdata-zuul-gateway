package com.vrishalipal.microservices.bigdatazuulgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.vrishalipal.microservices.bigdatazuulgateway.filters.AuthFilter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class BigdataZuulGatewayApplication {

	@Bean
	public AuthFilter AuthFilter() {
		return new AuthFilter();
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(BigdataZuulGatewayApplication.class, args);
	}

}
