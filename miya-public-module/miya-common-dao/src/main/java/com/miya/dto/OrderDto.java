package com.miya.dto;

import com.golden.util.DateTimeUtil;
import com.miya.entity.Order;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends BaseTimeDto {

    private String order_id;

    private String payment;

    private Integer payment_type;

    private String payment_type_desc;

    private String post_fee;

    private String status;

    private String payment_time;

    private String consign_time;

    private String end_time;

    private String close_time;

    private String shipping_name;

    private String shipping_code;

    private Long user_id;

    private String buyer_message;

    private String buyer_nick;

    private Integer buyer_rate;


    public Order converToOrder() {
        return new OrderDto.OrderDTOConvert().doForward(this);
    }

    public OrderDto converFor(Order order) {
        return new OrderDto.OrderDTOConvert().doBackward(order);
    }

    private static class OrderDTOConvert extends Converter<OrderDto, Order> {

        @Override
        protected Order doForward(OrderDto orderDto) {
            Order order = Order.builder().build();
            BeanUtils.copyProperties(orderDto, order);
            return order;
        }

        @Override
        protected OrderDto doBackward(Order order) {
            OrderDto orderDto = builder().build();
            BeanUtils.copyProperties(order, orderDto);
            String status = "";
            switch (order.getStatus()) {
                case 1:
                    status = "未付款";
                    break;
                case 2:
                    status = "已付款";
                    break;
                case 3:
                    status = "未发货";
                    break;
                case 4:
                    status = "已发货";
                    break;
                case 5:
                    status = "交易成功";
                    break;
                case 6:
                    status = "交易关闭";
                    break;
                default:
                    status = "状态异常";
                    break;
            }
            orderDto.setStatus(status);


            String payment_type_desc;
            switch (order.getPayment_type()) {
                case 1:
                    payment_type_desc = "在线支付";
                    break;
                case 2:
                    payment_type_desc = "货到付款";
                    break;
                 default:
                     payment_type_desc = "异常";
                    break;
            }
            orderDto.setPayment_type_desc(payment_type_desc);

            orderDto.setCreate_time(DateTimeUtil.formatDate(order.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            orderDto.setUpdate_time(DateTimeUtil.formatDate(order.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return orderDto;
        }
    }
}
