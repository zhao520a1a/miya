package com.golden.pojo;

import com.google.common.collect.Maps;

import java.util.Map;

public enum RespCode {

    OK(1, "成功"),
    FAILURE(2, "失败"),
    SYS_ERROR(3, "系统错误");


    private Integer key;
    private  String description;

    public static Map<Integer, String> kvMap = Maps.newHashMap();

    static {
        for (RespCode type : RespCode.values()) {
            kvMap.put(type.getKey(), type.getDesc());
        }
    }

    RespCode(Integer key, String description) {
        this.key = key;
        this.description = description;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return description;
    }
}

