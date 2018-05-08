package com.miya.portal.feign;

import com.golden.pojo.ResponseModal;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;


//value为服务客户端名称，指定Hystrix的fallback类
@FeignClient(value = "miya-sso-service", fallback = SsoServiceHystric.class)
public interface SsoService {


    @RequestMapping(value = "/user/logout/{token}" )
    ResponseModal logout(@PathVariable(value = "token") String token);


}
