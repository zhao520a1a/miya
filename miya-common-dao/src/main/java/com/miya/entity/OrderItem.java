package com.miya.entity;


import com.springboot.ping.mybatis.annotation.Pk;
import com.springboot.ping.mybatis.annotation.Table;
import com.springboot.ping.mybatis.extend.entity.BaseModel;
import lombok.*;

/*订单-商品信息详情*/
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table("order_item")
public class OrderItem extends BaseModel {
    @Pk
    private String id;

    private String item_id;

    private String order_id;

    private Integer num;

    private String title;

    private Long price;

    private Long total_fee;

    private String pic_path;


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public void setItemId(String itemId) {
        this.item_id = itemId == null ? null : itemId.trim();
    }


    public void setOrderId(String orderId) {
        this.order_id = orderId == null ? null : orderId.trim();
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }


    public void setPicPath(String picPath) {
        this.pic_path = picPath == null ? null : picPath.trim();
    }
}