package com.miya.controller;


import com.golden.pojo.RespList;
import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.StringUtil;
import com.miya.dto.OrderItemDto;
import com.miya.entity.OrderItem;
import com.miya.pojo.OrderInfo;
import com.miya.service.impl.OrderServiceImpl;
import com.springboot.ping.mybatis.vo.Condition;
import com.springboot.ping.mybatis.vo.Page;
import com.springboot.ping.mybatis.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @RequestMapping(value="/create", method= RequestMethod.POST)
    public RespObject<String> createOrder(@RequestBody OrderInfo orderInfo) {
        String orderId = "";
        try {
            orderId =  orderService.createOrder(orderInfo);
        } catch (Exception e) {
            log.info("createOrder:{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("添加订单异常"), null);
        }
        return new RespObject<>(ResponseModal.success(),orderId);
    }



    @GetMapping(value="/itemList/{orderId}")
    RespList<OrderItemDto> showOrderItemList(@PathVariable(value = "orderId") String orderId){
        List<OrderItemDto> orderItemDtos = Lists.newArrayList();
        try {
            List<OrderItem> orderItems = orderService.getOrderItems(orderId);
             for(OrderItem orderItem : orderItems) {
                OrderItemDto orderItemDto = OrderItemDto.builder().build();
                orderItemDto = orderItemDto.converFor(orderItem);
                orderItemDtos.add(orderItemDto);
            }
        } catch (Exception e) {
            log.info("showOrderList:{}", StringUtil.getTrace(e));
            return new RespList<>(ResponseModal.errorMsg("获取订单列表异常"), null);
        }
        return new RespList<>(ResponseModal.successMsg("获取订单列表成功"), orderItemDtos);
    }

    
    
    
}
