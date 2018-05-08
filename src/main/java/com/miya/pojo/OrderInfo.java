package com.miya.pojo;


import com.miya.dto.OrderDto;
import com.miya.dto.OrderItemDto;
import com.miya.dto.OrderShippingDto;

import java.io.Serializable;
import java.util.List;


public class OrderInfo extends OrderDto  {

    private List<OrderItemDto> orderItems;
    private OrderShippingDto orderShipping;

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderShippingDto getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(OrderShippingDto orderShipping) {
        this.orderShipping = orderShipping;
    }
}
