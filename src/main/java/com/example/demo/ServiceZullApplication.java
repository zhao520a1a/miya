package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy  //开启zuul的功能
@EnableEurekaClient
@SpringBootApplication
public class ServiceZullApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceZullApplication.class, args);
	}

}
