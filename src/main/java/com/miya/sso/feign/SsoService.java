package com.miya.sso.feign;

import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.miya.sso.dto.LoginUserDto;
import com.miya.sso.dto.UserInputDto;
import com.miya.sso.dto.UserOutputDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;


//value为服务客户端名称，指定Hystrix的fallback类
@FeignClient(value = "miya-sso-service", fallback = SsoServiceHystric.class)
public interface SsoService {


    @GetMapping(value = "/user/check/{param}/{type}")
    ResponseModal checkData(@PathVariable(value = "param") String param, @PathVariable(value = "type") Integer type);

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    ResponseModal register(@RequestBody UserInputDto user);

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    RespObject<String> login(@RequestBody LoginUserDto loginUserDto);

    @RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET)
    RespObject<UserOutputDto> getUserByToken(@PathVariable(value = "token")String token);

    @RequestMapping(value = "/user/logout/{token}" )
    ResponseModal logout(@PathVariable(value = "token") String token);


}
