package com.miya.order.dto;

import lombok.*;

import java.io.Serializable;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto implements Serializable {

    private String id;

    private String item_id;

    private String order_id;

    private Integer num;

    private String title;

    private Long price;

    private Long total_fee;

    private String pic_path;


}
