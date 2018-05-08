package com.miya.order.feign;

import org.springframework.cloud.netflix.feign.FeignClient;


//value为服务客户端名称，指定Hystrix的fallback类
@FeignClient(value = "miya-user-service", fallback = UserServiceHystric.class)
public interface UserService {


}
