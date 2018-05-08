package com.miya.entity;

import com.springboot.ping.mybatis.annotation.Pk;
import com.springboot.ping.mybatis.annotation.Table;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;

/* 订单-用户详情*/
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table("order_shipping")
public class OrderShipping extends BaseTimeModel{
    @Pk
    private String order_id;

    private String receiver_name;

    private String receiver_phone;

    private String receiver_mobile;

    private String receiver_state;

    private String receiver_city;

    private String receiver_district;

    private String receiver_address;

    private String receiver_zip;


}