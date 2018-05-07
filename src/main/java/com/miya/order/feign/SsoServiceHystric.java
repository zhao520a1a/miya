package com.miya.order.feign;

import com.golden.pojo.RespObject;
import com.miya.order.dto.UserOutputDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SsoServiceHystric implements SsoService {

    @Override
    public RespObject<UserOutputDto> getUserByToken(String token) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }




}