package com.miya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MiyaOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiyaOrderServiceApplication.class, args);
	}
}
