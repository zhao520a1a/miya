package com.miya.order.feign;

import com.golden.pojo.RespList;
import com.golden.pojo.RespObject;
import com.miya.order.dto.OrderInfo;
import com.miya.order.dto.OrderItemDto;
import com.miya.order.dto.Page;
import org.springframework.stereotype.Component;


@Component
public class OrderServiceHystric implements OrderService {


    @Override
    public RespObject<String> createOrder(OrderInfo orderInfo) {
        return null;
    }

    @Override
    public RespObject<Page> showOrderList(Long userId, int curPage, int limit) {
        return null;
    }


    @Override
    public RespList<OrderItemDto> showItemList(String orderId) {
        return null;
    }


}