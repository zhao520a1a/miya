package com.miya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;



@EnableEurekaClient
@SpringBootApplication
public class MiyaItemApplication {

	public static void main(String[] args) {
		final ApplicationContext ctx = SpringApplication.run(MiyaItemApplication.class, args);
	}

}
