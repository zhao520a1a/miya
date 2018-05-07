package com.miya.order.feign;

import com.golden.pojo.RespList;
import com.golden.pojo.RespObject;
import com.miya.order.dto.OrderInfo;
import com.miya.order.dto.OrderItemDto;
import com.miya.order.dto.Page;
import com.miya.order.dto.UserOutputDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


//value为服务客户端名称，指定Hystrix的fallback类
@FeignClient(value = "miya-order-service", fallback = OrderServiceHystric.class)
public interface OrderService {

    @RequestMapping(value="/order/create", method= RequestMethod.POST)
    RespObject<String> createOrder(@RequestBody OrderInfo orderInfo)  ;

    @GetMapping(value="/orders/myList/{userId}")
    RespObject<Page> showOrderList(@PathVariable(value = "userId") Long userId, @RequestParam(value = "limit") int limit,@RequestParam(value = "currPage") int currPage);

    @GetMapping(value="/order/itemList/{orderId}")
    RespList<OrderItemDto> showItemList(@PathVariable(value = "orderId") String orderId);



}
