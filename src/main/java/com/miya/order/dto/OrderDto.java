package com.miya.order.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto  implements Serializable {

    private String order_id;

    private String payment;

    private String payment_type;

    private String post_fee;

    private String status;

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

    public  String create_time;

    public  String update_time;

//
//    public String getOrder_id() {
//        return order_id;
//    }
//
//    public void setOrder_id(String order_id) {
//        this.order_id = order_id;
//    }
//
//    public String getPayment() {
//        return payment;
//    }
//
//    public void setPayment(String payment) {
//        this.payment = payment;
//    }
//
//    public Integer getPayment_type() {
//        return payment_type;
//    }
//
//    public void setPayment_type(Integer payment_type) {
//        this.payment_type = payment_type;
//    }
//
//    public String getPost_fee() {
//        return post_fee;
//    }
//
//    public void setPost_fee(String post_fee) {
//        this.post_fee = post_fee;
//    }
//
//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    public Date getPayment_time() {
//        return payment_time;
//    }
//
//    public void setPayment_time(Date payment_time) {
//        this.payment_time = payment_time;
//    }
//
//    public Date getConsign_time() {
//        return consign_time;
//    }
//
//    public void setConsign_time(Date consign_time) {
//        this.consign_time = consign_time;
//    }
//
//    public Date getEnd_time() {
//        return end_time;
//    }
//
//    public void setEnd_time(Date end_time) {
//        this.end_time = end_time;
//    }
//
//    public Date getClose_time() {
//        return close_time;
//    }
//
//    public void setClose_time(Date close_time) {
//        this.close_time = close_time;
//    }
//
//    public String getShipping_name() {
//        return shipping_name;
//    }
//
//    public void setShipping_name(String shipping_name) {
//        this.shipping_name = shipping_name;
//    }
//
//    public String getShipping_code() {
//        return shipping_code;
//    }
//
//    public void setShipping_code(String shipping_code) {
//        this.shipping_code = shipping_code;
//    }
//
//    public Long getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(Long user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getBuyer_message() {
//        return buyer_message;
//    }
//
//    public void setBuyer_message(String buyer_message) {
//        this.buyer_message = buyer_message;
//    }
//
//    public String getBuyer_nick() {
//        return buyer_nick;
//    }
//
//    public void setBuyer_nick(String buyer_nick) {
//        this.buyer_nick = buyer_nick;
//    }
//
//    public Integer getBuyer_rate() {
//        return buyer_rate;
//    }
//
//    public void setBuyer_rate(Integer buyer_rate) {
//        this.buyer_rate = buyer_rate;
//    }
//
//    public String getCreate_time() {
//        return create_time;
//    }
//
//    public void setCreate_time(String create_time) {
//        this.create_time = create_time;
//    }
//
//    public String getUpdate_time() {
//        return update_time;
//    }
//
//    public void setUpdate_time(String update_time) {
//        this.update_time = update_time;
//    }


}
