package com.miya.item.feign;

import com.golden.pojo.RespObject;
import com.miya.item.dto.ItemDescDto;
import com.miya.item.dto.ItemDto;
import com.miya.item.dto.ItemParamDto;
import com.miya.item.dto.Page;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


//value为服务客户端名称，指定Hystrix的fallback类
@FeignClient(value = "miya-item-service", fallback = ItemServiceHystric.class)
public interface ItemService {

    @GetMapping(value = "/item/{id}")
    RespObject<ItemDto> getItemById(@PathVariable(value = "id") Long id);

    @GetMapping(value = "/item/itemDesc/{itemId}")
    RespObject<ItemDescDto> getItemDesc(@PathVariable(value = "itemId") Long itemId);

    @GetMapping(value = "/item/itemParam/{itemId}")
    RespObject<ItemParamDto> getItemParam(@PathVariable(value = "itemId") Long itemId);


    @GetMapping(value = "/items/listByCatId")
    RespObject<Page> getItemsByCat(@RequestParam(value = "catId") Long catId, @RequestParam(value = "limit") int limit, @RequestParam(value = "currPage") int currPage);


}
