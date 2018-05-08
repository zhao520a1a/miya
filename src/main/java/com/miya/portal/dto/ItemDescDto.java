package com.miya.portal.dto;


import lombok.*;

import java.io.Serializable;

/* 商品描述信息 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDescDto implements Serializable {

    private Long item_id;

    private String item_desc;

    private String create_time;

    private String update_time;


}