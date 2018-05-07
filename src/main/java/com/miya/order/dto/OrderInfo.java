package com.miya.order.dto;


import java.io.Serializable;
import java.util.List;



public class OrderInfo extends OrderDto implements Serializable {

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
