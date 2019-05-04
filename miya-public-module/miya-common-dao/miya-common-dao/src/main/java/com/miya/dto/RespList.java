package com.miya.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespList<T>  {


    private ResponseModal responseModal;
    private List<T> data;



    public  RespList<T> successData(List<T> data) {
        this.setData(data);
        this.setResponseModal(ResponseModal.success());
        return this;
    }

    public  RespList<T> failData(List<T> data) {
        this.setData(data);
        this.setResponseModal(ResponseModal.fail());
        return this;
    }

    public  RespList<T> successMsgAndData(String msg,List<T> data) {
        this.setData(data);
        this.setResponseModal(ResponseModal.successMsg(msg));
        return this;
    }

    public  RespList<T> failMsgAndData(String msg, List<T> data) {
        this.setData(data);
        this.setResponseModal(ResponseModal.failMsg(msg));
        return this;
    }




}



