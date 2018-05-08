package com.miya.portal.feign;

import com.golden.pojo.ResponseModal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SsoServiceHystric implements SsoService {

    @Override
    public ResponseModal logout(String token) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }


}