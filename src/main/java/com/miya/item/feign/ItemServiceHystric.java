package com.miya.item.feign;

import com.golden.pojo.RespObject;
import com.miya.item.dto.ItemDescDto;
import com.miya.item.dto.ItemDto;
import com.miya.item.dto.ItemParamDto;
import com.miya.item.dto.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;


@Log4j2
@Component
public class ItemServiceHystric implements ItemService {


    @Override
    public RespObject<ItemDto> getItemById(Long id) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }

    @Override
    public RespObject<ItemDescDto>  getItemDesc(Long itemId) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }

    @Override
    public RespObject<ItemParamDto> getItemParam(Long itemId) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }

    @Override
    public RespObject<Page> getItemsByCat(Long catId, int limit, int currPage) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }
}