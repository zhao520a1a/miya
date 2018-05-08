package com.miya.service;


import com.golden.pojo.RespList;
import com.golden.pojo.RespObject;
import com.miya.dto.OrderItemDto;
import com.miya.entity.OrderItem;
import com.miya.pojo.OrderInfo;
import com.springboot.ping.mybatis.vo.Page;

import java.util.List;

public interface OrderService {

     String createOrder(OrderInfo orderInfo);

    Page getOrdersByPage(int limit, int currPage, int count);

    List<OrderItem> getOrderItems(String orderId);

    Page getMyOrders(Long userId,int currPage, int limit);
}
