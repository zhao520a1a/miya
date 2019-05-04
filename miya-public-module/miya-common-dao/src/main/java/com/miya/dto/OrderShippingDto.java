package com.miya.dto;

import com.miya.entity.OrderShipping;
import com.springboot.ping.mybatis.extend.entity.BaseModel;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;
import org.springframework.beans.BeanUtils;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderShippingDto extends BaseTimeModel {

    private String order_id;

    private String receiver_name;

    private String receiver_phone;

    private String receiver_mobile;

    private String receiver_state;

    private String receiver_city;

    private String receiver_district;

    private String receiver_address;

    private String receiver_zip;


    public OrderShipping converToOrderShipping() {
        return new OrderShippingDto.OrderShippingDTOConvert().doForward(this);
    }

    public OrderShippingDto converFor(OrderShipping orderShipping) {
        return new OrderShippingDto.OrderShippingDTOConvert().doBackward(orderShipping);
    }

    private static class OrderShippingDTOConvert extends Converter<OrderShippingDto, OrderShipping> {

        @Override
        protected OrderShipping doForward(OrderShippingDto orderShippingDto) {
            OrderShipping orderShipping = OrderShipping.builder().build();
            BeanUtils.copyProperties(orderShippingDto, orderShipping);
            return orderShipping;
        }

        @Override
        protected OrderShippingDto doBackward(OrderShipping orderShipping) {
            OrderShippingDto orderShippingDto =  builder().build();
            BeanUtils.copyProperties(orderShipping, orderShippingDto);
            return orderShippingDto;
        }
    }
}
