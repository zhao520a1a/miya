package com.miya.entity;

import com.springboot.ping.mybatis.annotation.Pk;
import com.springboot.ping.mybatis.annotation.Table;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;

import java.util.Date;

/* 订单信息 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table("orders")
public class Order extends BaseTimeModel {
    @Pk
    private String order_id;

    private String payment;

    private Integer payment_type;

    private String post_fee;

    private Integer status;

    private Date payment_time;

    private Date consign_time;

    private Date end_time;

    private Date close_time;

    private String shipping_name;

    private String shipping_code;

    private Long user_id;

    private String buyer_message;

    private String buyer_nick;

    private Integer buyer_rate;


    public void setOrderId(String orderId) {
        this.order_id = orderId == null ? null : orderId.trim();
    }


    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }


    public void setPostFee(String postFee) {
        this.post_fee = postFee == null ? null : postFee.trim();
    }


    public void setShippingName(String shippingName) {
        this.shipping_name = shippingName == null ? null : shippingName.trim();
    }


    public void setShippingCode(String shippingCode) {
        this.shipping_code = shippingCode == null ? null : shippingCode.trim();
    }



    public void setBuyerMessage(String buyerMessage) {
        this.buyer_message = buyerMessage == null ? null : buyerMessage.trim();
    }


    public void setBuyerNick(String buyerNick) {
        this.buyer_nick = buyerNick == null ? null : buyerNick.trim();
    }


}