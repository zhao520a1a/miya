package com.miya.entity;

import com.springboot.ping.mybatis.annotation.Pk;
import com.springboot.ping.mybatis.annotation.Table;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;


/* 商品类别详细信息 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table("item_cat_param")
public class ItemCatParam extends BaseTimeModel {
    @Pk
    private Long id;

    private Long item_cat_id;  //商品类目ID

    private String param_data;  //参数数据，格式为json格式

    public void setParamData(String paramData) {
        this.param_data = paramData == null ? null : paramData.trim();
    }
}