package com.miya.order.feign;

import com.golden.pojo.RespObject;
import com.miya.order.dto.UserOutputDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;


//value为服务客户端名称，指定Hystrix的fallback类
@FeignClient(value = "miya-sso-service", fallback = SsoServiceHystric.class)
public interface SsoService {

    @RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET)
    RespObject<UserOutputDto> getUserByToken(@PathVariable(value = "token") String token);


}
