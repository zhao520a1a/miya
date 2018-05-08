package com.miya.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableCircuitBreaker   //使用熔断器
@EnableHystrixDashboard //使用Hystrix仪表盘
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
//@SpringBootApplication(exclude = {
//		DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class
//})  //排除数据库配置
public class MiyaSsoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiyaSsoWebApplication.class, args);
	}
}
