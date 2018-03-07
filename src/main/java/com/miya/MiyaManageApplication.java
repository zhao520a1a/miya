package com.miya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

@EnableEurekaClient
@SpringBootApplication
public class MiyaManageApplication {

	public static void main(String[] args) {
		final ApplicationContext ctx = SpringApplication.run(MiyaManageApplication.class, args);
	}

//
//	/*Json数据的转换*/
//	@Bean
//	public HttpMessageConverters fastJsonConverters(){
//		FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
//		FastJsonConfig fastConf = new FastJsonConfig();
//
//		fastConf.setSerializerFeatures(SerializerFeature.PrettyFormat);
//		fastJsonConverter.setFastJsonConfig(fastConf);
//
//		HttpMessageConverter<?> converter = fastJsonConverter;
//		return new HttpMessageConverters(converter);
//	}
}
