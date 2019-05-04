package com.miya.entity;

import com.springboot.ping.mybatis.annotation.Pk;
import com.springboot.ping.mybatis.annotation.Table;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;

/*商品类别信息*/
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table("item_cat")
public class ItemCat extends BaseTimeModel {
    @Pk
    private Long id;  //类目ID

    private Long parent_id;  //父类目ID=0时，代表的是一级的类目

    private String name;  //类目名称

    private Integer status;  //状态。可选值:1(正常),2(删除)

    private Integer sort_order;  //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数

    private Boolean is_parent;  //该类目是否为父类目，1为true，0为false

}