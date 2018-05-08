package com.miya.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


@EnableCircuitBreaker   //使用熔断器
@EnableHystrixDashboard //使用Hystrix仪表盘
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MiyaSearchWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiyaSearchWebApplication.class, args);
	}
}
