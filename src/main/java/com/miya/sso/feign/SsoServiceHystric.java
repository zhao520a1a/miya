package com.miya.sso.feign;

import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.miya.sso.dto.LoginUserDto;
import com.miya.sso.dto.UserInputDto;
import com.miya.sso.dto.UserOutputDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SsoServiceHystric implements SsoService {
    @Override
    public ResponseModal checkData(String param, Integer type) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }

    @Override
    public ResponseModal register(UserInputDto user) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }

    @Override
    public RespObject<String> login(LoginUserDto loginUserDto) {
       log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }

    @Override
    public RespObject<UserOutputDto> getUserByToken(String token) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }

    @Override
    public ResponseModal logout(String token) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }


}