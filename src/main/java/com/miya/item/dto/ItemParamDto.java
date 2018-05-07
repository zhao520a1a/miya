package com.miya.item.dto;


import lombok.*;

/* 商品描述信息 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemParamDto  {

    private Long id;

    private Long item_id;  //商品ID

    private String param_data;  //参数数据，格式为json格式

    private String create_time;

    private String update_time;

}