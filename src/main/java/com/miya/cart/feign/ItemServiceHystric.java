package com.miya.cart.feign;

import com.golden.pojo.RespObject;
import com.miya.cart.dto.ItemOutputDto;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ItemServiceHystric implements ItemService {


    @Override
    public RespObject<ItemOutputDto> getItemById(Long itemId) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }
}