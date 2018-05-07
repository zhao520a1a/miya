package com.miya.cart.feign;

import com.golden.pojo.RespObject;
import com.miya.cart.dto.ItemOutputDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


//value为服务客户端名称，指定Hystrix的fallback类
@FeignClient(value = "miya-item-service", fallback = ItemServiceHystric.class)
public interface ItemService {

    @RequestMapping(value = "item/{id}")
    RespObject<ItemOutputDto> getItemById(@PathVariable(value = "id") Long itemId);

}
