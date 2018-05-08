package com.miya.portal.feign;


import com.golden.pojo.RespObject;
import com.miya.portal.dto.ItemDescDto;
import com.miya.portal.dto.ItemDto;
import com.miya.portal.dto.Page;
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
    public RespObject<Page> getItemsByCat(Long catId, int limit, int currPage) {
        return null;
    }
}