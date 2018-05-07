package com.miya.order.dto;


import lombok.*;

import java.io.Serializable;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderShippingDto implements Serializable {

    private String order_id;

    private String receiver_name;

    private String receiver_phone;

    private String receiver_mobile;

    private String receiver_state;

    private String receiver_city;

    private String receiver_district;

    private String receiver_address;

    private String receiver_zip;

    public  String create_time;

    public  String update_time;


}
