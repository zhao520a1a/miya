package com.miya.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


@EnableCircuitBreaker
//解决加了@EnableHystrixDashboard后项目中freemarker配置失效导致404问题
//@EnableHystrixDashboard   加上No mapping found for HTTP request with URI [/item] in DispatcherServlet with name 'dispatcherServlet'
//解决方案：https://blog.csdn.net/hxpjava1/article/details/78299749
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class
})  //排除配置数据库
public class MiyaItemWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiyaItemWebApplication.class, args);
	}
}
