package com.miya.controller;


import com.alibaba.fastjson.JSONObject;
import com.golden.pojo.RespList;
import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.StringUtil;
import com.miya.dto.OrderItemDto;
import com.miya.entity.OrderItem;
import com.miya.service.impl.OrderServiceImpl;
import com.springboot.ping.mybatis.vo.Condition;
import com.springboot.ping.mybatis.vo.Page;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderServiceImpl orderService;


    /* 获得订单的总数量 */
    @RequestMapping(value = "/count", method = {RequestMethod.POST, RequestMethod.GET})
    public RespObject<Integer> getOrderCount() {
        int sumNum = 0;
        try {
            Condition condition = null;
            sumNum = (int) orderService.count(condition);
            log.info("orderCount: {}", sumNum);
        } catch (Exception e) {
            log.info("getOrderCount:{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获取订单数量异常"), -1);
        }
        return new RespObject<>(ResponseModal.successMsg("获取订单数量成功"), sumNum);
    }


    /* 分页获得订单列表 */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public RespObject<JSONObject> getOrdersList(@RequestParam int limit, @RequestParam int currPage, @RequestParam int count) {
        log.info("分页limit:{}, currPage:{}, count:{}, httpSession:{}", limit, currPage, count);
        JSONObject orderData = new JSONObject();
        Page page;
        try {
            page = orderService.getOrdersByPage(limit, currPage, count);
            orderData.put("page", page);
        } catch (Exception e) {
            log.info("getOrdersList:{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获取订单列表异常"), null);
        }
        return new RespObject<>(ResponseModal.successMsg("获取订单列表成功"), orderData);
    }






    /* =====  前台   === */
    @GetMapping(value = "/myList/{userId}" )
    RespObject<Page> showMyOrders(@PathVariable Long userId,  @RequestParam int limit, @RequestParam int currPage) {
        Page page;
        try {
            page = orderService.getMyOrders(userId,limit,currPage);
        } catch (Exception e) {
            log.info("showOrderList:{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获取订单列表异常"), null);
        }
        return new RespObject<>(ResponseModal.successMsg("获取订单列表成功"), page);
    }





}
