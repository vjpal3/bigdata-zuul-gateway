package com.vrishalipal.microservices.bigdatazuulgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class BigdataZuulGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigdataZuulGatewayApplication.class, args);
	}

}
