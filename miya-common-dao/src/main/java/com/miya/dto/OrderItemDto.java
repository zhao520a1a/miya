package com.miya.dto;

import com.golden.util.DateTimeUtil;
import com.miya.entity.OrderItem;
import com.springboot.ping.mybatis.extend.entity.BaseModel;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.Date;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto extends BaseModel {

    private String id;

    private String item_id;

    private String order_id;

    private Integer num;

    private String title;

    private Long price;

    private Long total_fee;

    private String pic_path;



    public OrderItem converToOrderItem() {
        return new OrderItemDto.OrderItemDTOConvert().doForward(this);
    }

    public OrderItemDto converFor(OrderItem orderItem) {
        return new OrderItemDto.OrderItemDTOConvert().doBackward(orderItem);
    }

    private static class OrderItemDTOConvert extends Converter<OrderItemDto, OrderItem> {

        @Override
        protected OrderItem doForward(OrderItemDto orderItemDto) {
            OrderItem orderItem = OrderItem.builder().build();
            BeanUtils.copyProperties(orderItemDto, orderItem);
            return orderItem;
        }

        @Override
        protected OrderItemDto doBackward(OrderItem orderItem) {
            OrderItemDto orderItemDto =  builder().build();
            BeanUtils.copyProperties(orderItem, orderItemDto);
            return orderItemDto;
        }
    }
}
