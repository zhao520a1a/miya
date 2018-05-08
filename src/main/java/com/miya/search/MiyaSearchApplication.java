package com.miya.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import static java.lang.Boolean.parseBoolean;

@EnableEurekaClient
@SpringBootApplication
public class MiyaSearchApplication {


	public static void main(String[] args) {
		final ApplicationContext ctx = SpringApplication.run(MiyaSearchApplication.class, args);
	}





}
