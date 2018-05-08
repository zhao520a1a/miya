package com.miya.entity;

import com.springboot.ping.mybatis.annotation.Pk;
import com.springboot.ping.mybatis.annotation.Table;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;

/*商品参数*/
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table("item_param")
public class ItemParam extends BaseTimeModel {
    @Pk
    private Long id;

    private Long item_id;  //商品ID

    private String param_data;  //参数数据，格式为json格式

    public void setParamData(String paramData) {
        this.param_data = paramData == null ? null : paramData.trim();
    }
}