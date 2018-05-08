package com.miya.service.impl;

import com.miya.dto.OrderDto;
import com.miya.dto.OrderItemDto;
import com.miya.dto.OrderShippingDto;
import com.miya.entity.Order;
import com.miya.entity.OrderItem;
import com.miya.entity.OrderShipping;
import com.miya.entity.dao.OrderDao;
import com.miya.entity.dao.OrderItemDao;
import com.miya.entity.dao.OrderShippingDao;
import com.miya.pojo.OrderInfo;
import com.miya.redis.RedisServiceImpl;
import com.miya.service.OrderService;
import com.springboot.ping.mybatis.enums.DbType;
import com.springboot.ping.mybatis.enums.Operator;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import com.springboot.ping.mybatis.vo.Condition;
import com.springboot.ping.mybatis.vo.Page;
import com.springboot.ping.mybatis.vo.Pagination;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 订单处理Server
 */
@Service
public class OrderServiceImpl extends BaseCURDService<Order, OrderDao> implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private OrderShippingDao orderShippingDao;
    @Autowired
    private RedisServiceImpl redisService;

    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_BEGIN_VALUE}")
    private String ORDER_ID_BEGIN_VALUE;
    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;

    @Override
    public String createOrder(OrderInfo orderInfo) {
        //转换数据
        List<OrderItemDto> orderItemDtos = orderInfo.getOrderItems();
        List<OrderItem> orderItems = Lists.newArrayList();
        for (OrderItemDto itemDto : orderItemDtos) {
            orderItems.add(itemDto.converToOrderItem());
        }


        //生成订单号,可以使用redis的incr生成
        if (!redisService.exists(ORDER_ID_GEN_KEY)) {
            //设置初始值
            redisService.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
        }
        String orderId = redisService.incr(ORDER_ID_GEN_KEY).toString();
        //向订单表插入数据，需要补全pojo的属性
        Order order = orderInfo.converToOrder();
        order.setOrder_id(orderId);
        //免邮费
        order.setPost_fee("0");
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        order.setStatus(1);
        //订单创建时间
        order.setCreate_time(new Date());
        order.setUpdate_time(new Date());
        //向订单 表插入数据
        orderDao.insert(order);
        //向订单明细表插入数据。
        for (OrderItem orderItem : orderItems) {
            //获得明细主键
            String oid = redisService.incr(ORDER_ITEM_ID_GEN_KEY).toString();
            orderItem.setId(oid);
            orderItem.setOrderId(orderId);
            //插入明细数据
            orderItemDao.insert(orderItem);
        }
        //向订单物流表插入数据
        OrderShippingDto orderShippingDto = orderInfo.getOrderShipping();
        OrderShipping orderShipping = orderShippingDto.converToOrderShipping();
        orderShipping.setOrder_id(orderId);
        orderShipping.setCreate_time(new Date());
        orderShipping.setUpdate_time(new Date());
        orderShippingDao.insert(orderShipping);
        //返回订单号
        return orderId;
    }


    @Override
    public Page getOrdersByPage(int limit, int currPage, int count) {
        return this.getOrdersByPage(limit, currPage, count, null);
    }

    public Page getOrdersByPage(int limit, int currPage, int count, List<Condition> conditionList) {
        Page page = null;
        Pagination pagination = new Pagination();
        int curPage = currPage;
        pagination.setTotalCount(count);
        pagination.setPageSize(limit);
        pagination.setCurrentPage(curPage);
        page = super.find(pagination, conditionList);
        List<Order> orderList = page.getRows();

        List<OrderInfo> orderInfos = Lists.newArrayList();


        for (Order order : orderList) {
            OrderInfo orderInfo = new OrderInfo();
            OrderDto orderDto = OrderDto.builder().build();
            orderDto = orderInfo.converFor(order);
            orderInfo.setOrder_id(orderDto.getOrder_id());
            orderInfo.setPayment(orderDto.getPayment());
            orderInfo.setPayment_type(orderDto.getPayment_type());
            orderInfo.setPayment_type_desc(orderDto.getPayment_type_desc());
            orderInfo.setStatus(orderDto.getStatus());
            orderInfo.setPost_fee(orderDto.getPost_fee());
            orderInfo.setCreate_time(orderDto.getCreate_time());

            String orderId = order.getOrder_id();
            //订单关联的的商品
            List<Condition> conditions = Lists.newArrayList();
            Condition condition = new Condition("order_id", DbType.STRING, Operator.EQ, orderId);
            conditions.add(condition);
            List<OrderItem> orderItems = orderItemDao.find(null, conditions, null);
            List<OrderItemDto> orderItemDtos = Lists.newArrayList();
            for (OrderItem orderItem : orderItems) {
                OrderItemDto orderItemDto = OrderItemDto.builder().build();
                orderItemDto = orderItemDto.converFor(orderItem);
                orderItemDtos.add(orderItemDto);
            }
            orderInfo.setOrderItems(orderItemDtos);

            //若没有额外的查询条件，则查询订单的用户信息
            //订单关联的用户
            OrderShipping orderShipping = new OrderShipping();
            orderShipping.setOrder_id(orderId);
            orderShipping = orderShippingDao.findByPk(orderShipping);
            if(orderShipping != null) {
                OrderShippingDto orderShippingDto = OrderShippingDto.builder().build();
                orderShippingDto = orderShippingDto.converFor(orderShipping);
                orderInfo.setOrderShipping(orderShippingDto);
            }
            orderInfos.add(orderInfo);

        }

        page.setRows(orderInfos);

        return page;
    }


    @Override
    public List<OrderItem> getOrderItems(String orderId) {
        List<Condition> conditions = Lists.newArrayList();
        Condition condition = new Condition("order_id", DbType.STRING, Operator.EQ, orderId);
        conditions.add(condition);
        List<OrderItem> orderItems = orderItemDao.find(null, conditions, null);
        return orderItems;
    }

    @Override
    public Page getMyOrders(Long userId , int limit ,int currPage ) {
        List<Condition> conditions = Lists.newArrayList();
        Condition condition = new Condition("user_id", DbType.INT, Operator.EQ, userId);
        conditions.add(condition);
        Long count = super.count(conditions);
        Page page = this.getOrdersByPage(limit, currPage, Integer.parseInt(count.toString()), conditions);

        return page;
    }

}
