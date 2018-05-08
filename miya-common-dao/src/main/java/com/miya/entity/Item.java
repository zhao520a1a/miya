package com.miya.entity;

import com.springboot.ping.mybatis.annotation.Pk;
import com.springboot.ping.mybatis.annotation.Table;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;


/* 商品信息 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table("item")
public class Item extends BaseTimeModel {
    @Pk
    private Long id;  //商品id

    private String view_id;  //商品编号

    private String title;  //商品标题

    private String sell_point;  //商品卖点

    private Long price;  //商品价格，单位为：分

    private Integer stock;  //库存数量

    private String barcode;  //商品条形码

    private String image;  //商品图片

    private Long cid;  //所属类目，叶子类目

    private Byte status;  //商品状态，1-正常，2-下架，3-删除

    private Integer satisfy_rate; //好评率

    private Integer express_fee;  //快递费

    private Integer evaluation_count; //评价数

    private Integer month_sales; //月销量

    private Byte sold_cat; //0:表示新品  1：普通商品   2：热门品  3：推荐品

}