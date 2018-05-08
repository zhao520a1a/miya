package com.miya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MiyaSsoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiyaSsoServiceApplication.class, args);
	}
}
