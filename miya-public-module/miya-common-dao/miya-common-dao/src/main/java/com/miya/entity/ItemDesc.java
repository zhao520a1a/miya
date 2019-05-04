package com.miya.entity;

import com.springboot.ping.mybatis.annotation.Pk;
import com.springboot.ping.mybatis.annotation.Table;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;

/* 商品描述信息 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table("item_desc")
public class ItemDesc extends BaseTimeModel {
    @Pk
    private Long item_id;

    private String item_desc;


    public void setItem_desc(String itemDesc) {
        this.item_desc = itemDesc == null ? null : itemDesc.trim();
    }



}