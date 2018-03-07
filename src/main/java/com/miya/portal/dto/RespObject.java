package com.miya.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
*  相应工具类
*/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespObject<T>  {


    private ResponseModal responseModal;
    private T data;



    public  RespObject<T> successData(T data) {
        this.setData(data);
        this.setResponseModal(ResponseModal.success());
        return this;
    }

    public  RespObject<T> failData(T data) {
        this.setData(data);
        this.setResponseModal(ResponseModal.fail());
        return this;
    }

    public  RespObject<T> successMsgAndData(String msg,T data) {
        this.setData(data);
        this.setResponseModal(ResponseModal.successMsg(msg));
        return this;
    }

    public  RespObject<T> failMsgAndData(String msg,T data) {
        this.setData(data);
        this.setResponseModal(ResponseModal.failMsg(msg));
        return this;
    }




    public static void main(String[] args) {
        RespObject<String>  respObject = new RespObject<String>(ResponseModal.success(),"inffda");
        System.out.println(respObject.toString());

        RespObject<String>  respObject1 = new RespObject<String>();
        System.out.println(respObject1.successMsgAndData("nida","1111").toString());
    }

}



